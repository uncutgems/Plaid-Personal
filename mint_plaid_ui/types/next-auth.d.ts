declare module "next-auth" {
    interface Session {
        user: {
            accessToken: string;
            refreshToken: string;
            accessExpire: number;
            refreshExpire: number;
            email: string;
            username: string;
            role: string;
            enabled: boolean;
            message: string;
        };
    }
}