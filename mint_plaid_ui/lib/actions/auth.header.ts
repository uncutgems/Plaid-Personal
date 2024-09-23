import axios from "axios";
import {REFRESH_TOKEN, SANDBOX_HOST} from "@/constants/endpoints";
import {
    getLocalAccessToken,
    getLocalRefreshToken,
    isTokenValid,
    updateLocalAccessToken
} from "@/lib/actions/token.service";
import {redirect} from "next/navigation";


const instance = axios.create({
    baseURL: SANDBOX_HOST,
    headers: {
        "Content-Type": "application/json",
    }
})

instance.interceptors.request.use(
    (config) => {
        const token = getLocalAccessToken();
        if (token && config.headers) {
            config.headers["Authorization"] = 'Bearer ' + token;
        }
        return config;
    }, (error) => {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    (res) => {
        return res;
    },
    async (err) => {
        const originalConfig = err.config;

        if (err.response) {
            // Access Token was expired
            if (err.response.status === 401 || err.response.status === 403  && !originalConfig._retry) {
                originalConfig._retry = true;
                if (isTokenValid(REFRESH_TOKEN)){
                    try {
                        const rs = await instance.post(REFRESH_TOKEN, {
                            refreshToken: getLocalRefreshToken(),
                        });
                        if (rs.status === 200) {
                            updateLocalAccessToken(rs.data);
                        } else {
                            redirect('/login');
                        }

                        return instance(originalConfig);
                    } catch (_error) {
                        return Promise.reject(_error);
                    }
                } else {
                    redirect('/login');
                }
            }
        }

        return Promise.reject(err);
    }
);

export default instance;