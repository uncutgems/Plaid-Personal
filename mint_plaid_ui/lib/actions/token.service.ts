import {ACCESS_TOKEN, REFRESH_TOKEN} from "@/constants";
import {cookies} from "next/headers";
import {redirect} from "next/navigation";

export const getLocalRefreshToken = () => {
    return cookies().get(REFRESH_TOKEN)?.value;
}

export const getLocalAccessToken = () => {
    return cookies().get(ACCESS_TOKEN)?.value
}

export const isTokenValid = (tokenKey: string) => {
    return cookies().has(tokenKey);
}