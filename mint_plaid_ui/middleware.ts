"use server"

import {getLocalAccessToken, getLocalRefreshToken} from "@/lib/actions/token.service";
import {REFRESH_TOKEN_ENDPOINT, SANDBOX_HOST} from "@/constants/endpoints";
import axios from "axios";
import {updateLocalAccessToken} from "@/lib/actions/user.actions";
import {NextRequest, NextResponse} from "next/server";
import {cookies} from "next/headers";
import {ACCESS_TOKEN, EMAIL, REFRESH_TOKEN, USERNAME} from "@/constants";
import {redirect} from "next/navigation";

export async function middleware(request: NextRequest) {
    const response = NextResponse.next()
    const refreshToken = getLocalRefreshToken();
    const accessToken = getLocalAccessToken();
    if (!accessToken && refreshToken) {
        try {
            const respondData = await refreshTokens(refreshToken)
            await updateLocalAccessToken(JSON.stringify(respondData))
            response.headers.set("Authorization", `Bearer ${respondData.accessToken}`);
        } catch (error) {
            console.error('Error refreshing token:', error);
            await removeUser();
            return NextResponse.redirect(new URL("/sign-in", request.url));
        }
    } else if (accessToken) {
        response.headers.set("Authorization", `Bearer ${accessToken}`);
    }
    return response;
}

async function refreshTokens(refreshToken: string): Promise<
    {
        accessToken: string,
        refreshToken: string,
        accessExpire: number,
        refreshExpire: number,
        email: string,
        role: string,
        enabled: boolean
    }> {
    if (!refreshToken) {
        throw new Error("No refresh token available");
    }

    const response = await axios.post(SANDBOX_HOST + REFRESH_TOKEN_ENDPOINT, {
        refreshToken: refreshToken
    });

    if (response.status !== 200) {
        throw new Error("Failed to refresh token");
    }
    const { accessToken, refreshToken: newRefreshToken, accessExpire, refreshExpire, email, role, enabled } = await response.data();
    return { accessToken, refreshToken: newRefreshToken, accessExpire, refreshExpire, email, role, enabled };

}

const removeUser = async () => {
    cookies().delete(ACCESS_TOKEN)
    cookies().delete(REFRESH_TOKEN)
    cookies().delete(USERNAME)
    cookies().delete(EMAIL)
    redirect('/login')
}
