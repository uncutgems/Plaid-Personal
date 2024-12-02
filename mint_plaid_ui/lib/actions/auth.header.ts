import axios, {AxiosError, AxiosInstance, InternalAxiosRequestConfig} from "axios";
import {SANDBOX_HOST} from "@/constants/endpoints";
import {getLocalAccessToken} from "@/lib/actions/token.service";


const instance: AxiosInstance = axios.create({
    baseURL: SANDBOX_HOST,
    headers: {
        "Content-Type": "application/json",
    }
})

instance.interceptors.request.use(
    async (config: InternalAxiosRequestConfig) => {
        const token = getLocalAccessToken();
        if (token && config.headers) {
            config.headers["Authorization"] = 'Bearer ' + token;
        }
        return config;
    }, (error: AxiosError) => {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    (response) => response,
    async (error) => {
        if (error.response?.status === 401 || error.response?.status === 403) {
            // Instead of handling the refresh here, we'll let the middleware handle it
            // Just retry the original request
            return instance(error.config);
        }
        return Promise.reject(error);
    }
);

export default instance;