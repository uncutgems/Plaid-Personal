import {ACCESS_TOKEN, EMAIL, REFRESH_TOKEN, USERNAME} from "@/constants";
import {cookies} from "next/headers";
import {redirect} from "next/navigation";

export const getLocalRefreshToken = () => {
    return cookies().get(REFRESH_TOKEN)?.value;
}

export const getLocalAccessToken = () => {
    return cookies().get(ACCESS_TOKEN)?.value
}

export const updateLocalAccessToken = (userSession: string) => {
    let userJson = JSON.parse(userSession || '{}');
    cookies().set(ACCESS_TOKEN, userJson.accessToken, {
        httpOnly: true,
        secure: true,
        expires: userJson['accessExpire'],
    });
    cookies().set(REFRESH_TOKEN, userJson.refreshToken, {
        httpOnly: true,
        secure: true,
        expires: userJson['refreshExpire'],
    });
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

export const removeUser = () => {
    cookies().delete(ACCESS_TOKEN)
    cookies().delete(REFRESH_TOKEN)
    redirect('/login')
}
