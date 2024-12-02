'use server';

import * as Endpoints from "../../constants/endpoints"
import {parseStringify} from "@/lib/utils";
import authHeader from "@/lib/actions/auth.header";
import {ACCESS_TOKEN, EMAIL, REFRESH_TOKEN, USERNAME} from "@/constants";
import {cookies} from "next/headers";
import axios from "axios";
import {SANDBOX_HOST} from "@/constants/endpoints";
import {redirect} from "next/navigation";

const axiosInstance = axios.create({
    baseURL: SANDBOX_HOST,
    headers: {
        "Content-Type": "application/json",
    }
})

export const signIn = async ({email, password} : signInProps) => {
    try {
        if (email && password) {
             return await axiosInstance.post(Endpoints.LOGIN_ENDPOINT, {
                email: email,
                password: password
            }).then((response) => {
                if (response.status === 200) {
                    updateLocalAccessToken(JSON.stringify(response.data));
                    return parseStringify(response.data)
                }
            })
        }
    } catch (error) {
        console.log(error);
    }
    return null
}

export const signUp = async ({username, email, password} : SignUpParams) => {
    try {
        return await axiosInstance.post(Endpoints.REGISTER_ENDPOINT, {
            username:username,
            email: email,
            password: password
        }).then((response) => {
            if (response.data["responseCode"] === 200) {
                return parseStringify(response.data)
            }
        })
    } catch (error) {
        console.log(error);
    }
}

export const logout = async () => {
    await removeUser();
}

export const createLinkToken = async () => {
    try {
        const response = await authHeader.get(Endpoints.PLAID_LINK_TOKEN_INITIAL)
        return parseStringify(response.data)
    } catch  (error) {
        console.log(error);
    }

}

export const exchangePublicToken = async ({publicToken}: exchangePublicTokenProps) => {
    const response = await authHeader.post(Endpoints.PLAID_EXCHANGE_TOKEN, {
        public_token: publicToken
    })
    return response.status === 200;

}

export async function getLoggedInUser(): Promise<User> {
    const username = cookies().get(USERNAME)?.value as string;
    const email = cookies().get(EMAIL)?.value as string;
    if (username && email) {
        return {
            username: username,
            email: email,
        }
    }
    return {
        username: "",
        email: "",
    }
}

export const updateLocalAccessToken = async (userSession: string) => {
    let userJson = JSON.parse(userSession || '{}');
    cookies().set(ACCESS_TOKEN, userJson.accessToken, {
        httpOnly: true,
        secure: true,
        expires: userJson['accessExpire'],
    });
    if (userJson.refreshToken) {
        cookies().set(REFRESH_TOKEN, userJson.refreshToken, {
            httpOnly: true,
            secure: true,
            expires: userJson['refreshExpire'],
        });
    }
    cookies().set(USERNAME, userJson.username, {
        httpOnly: true,
        secure: true,
        expires: userJson['refreshExpire'],
    });
    cookies().set(EMAIL, userJson.email, {
        httpOnly: true,
        secure: true,
        expires: userJson['refreshExpire'],
    })
}

export const removeUser = async () => {
    cookies().delete(ACCESS_TOKEN)
    cookies().delete(REFRESH_TOKEN)
    cookies().delete(USERNAME)
    cookies().delete(EMAIL)
    redirect('/login')
}
