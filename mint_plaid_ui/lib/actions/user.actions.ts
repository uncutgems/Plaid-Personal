'use server';

import * as Endpoints from "../../constants/endpoints"
import {parseStringify} from "@/lib/utils";
import {removeUser, updateLocalAccessToken} from "@/lib/actions/token.service";
import authHeader from "@/lib/actions/auth.header";
import {EMAIL, USERNAME} from "@/constants";
import {cookies} from "next/headers";

export const signIn = async ({email, password} : signInProps) => {
    try {
        if (email && password) {
             return await authHeader.post(Endpoints.LOGIN_ENDPOINT, {
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
        return await authHeader.post(Endpoints.REGISTER_ENDPOINT, {
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
    removeUser();
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
        publicToken: publicToken
    })
    return response.status === 200;

}

export async function getLoggedInUser() {
    const username = cookies().get(USERNAME)?.value as string;
    const email = cookies().get(EMAIL)?.value as string;
    return {
        username: username,
        email: email,
    }
}