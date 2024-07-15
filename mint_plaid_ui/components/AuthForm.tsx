'use client';

import React, {useState} from 'react';
import Image from "next/image";
import Link from "next/link";

import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Button} from "@/components/ui/button";
import {useRouter} from 'next/navigation';


import {
    Form,

} from "@/components/ui/form";

import CustomInput from "@/components/CustomInput";
import {authFormSchema} from "@/lib/utils";
import {Loader2} from "lucide-react";
import {signIn, signUp} from "@/lib/actions/user.actions";


const AuthForm = ({type}: { type: string }) => {
    const router = useRouter();
    const formSchema = authFormSchema(type);

    const [user, setUser] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            email: "",
            password: ""
        },
    });

    const onSubmit = async (data: z.infer<typeof formSchema>) => {
        setIsLoading(true);
        try {
            // Sign up with backend
            if (type === 'sign-up') {
                const userData = {
                    username: data.username!,
                    email: data.email!,
                    password: data.password!,
                }
                const newUser = await signUp(userData);
                setUser(newUser);
            }
            if (type === 'sign-in') {
                const response = await signIn({
                    email: data.email,
                    password: data.password,
                })
                setUser(response);
                if (response) {
                    router.push("/");
                }
            }
        } catch (error) {
            console.log(error)
        }
        setIsLoading(false);
    }

    return (
        <section className={"auth-form"}>
            <header className={"flex flex-col gap-5 md:gap-8"}>
                <Link href={"/"} className={"cursor-pointer flex items-center gap-1"}>
                    <Image src={"/icons/logo.svg"} width={34} height={34} alt={"MintPlaid Logo"}/>
                    <h1 className={"text-26 font-ibm-plex-serif font-bold text-black-1"}>Mint Plaid</h1>
                </Link>
                <div className={"flex flex-col gap-1 md:gap-3"}>
                    <h1 className={"text-24 lg:text-36 font-semibold text-gray-900"}>
                        {user
                            ? user['message']
                            : type === 'sign-in'
                                ? 'Sign In'
                                : 'Sign Up'
                        }
                        <p className={"text-16 font-normal text-gray-600"}>
                            {user
                                ? (
                                    user['responseCode'] === 200
                                        ? <footer className={"flex justify-left gap-1"}>
                                            <p className={"text-14 font-normal text-gray-600"}>
                                                {"Please confirm your email address before "}
                                            </p>
                                            <Link href={"sign-in"}
                                                  className={"form-link"}>
                                                {"signing in"}
                                            </Link>
                                        </footer>
                                        : 'Username or Email is already exist. Please use different one.'
                                )
                                : 'Please enter your details'
                            }
                        </p>
                    </h1>
                </div>
            </header>
            {user
                ? (
                    <div className="flex flex-col gap-4">

                    </div>
                )
                : (
                    <>
                        <Form {...form}>
                            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                                {type === 'sign-up' && (
                                    <>

                                        <CustomInput control={form.control} name={"username"} label={"Username"}
                                                     placeholder={"Enter Your Username"}/>

                                    </>
                                )}
                                <CustomInput control={form.control} name="email" label="Email"
                                             placeholder={"Enter your email"}/>
                                <CustomInput control={form.control} name="password" label="Password"
                                             placeholder={"Enter your password"}/>
                                <div className={'flex flex-col gap-4'}>
                                    <Button type="submit" className={"form-btn"} disabled={isLoading}>
                                        {isLoading ? (
                                            <>
                                                <Loader2 size={20} className={"animate-spin"}/> &nbsp; Loading...
                                            </>
                                        ) : type === 'sign-in' ? 'Sign In' : 'Sign Up'
                                        }
                                    </Button>
                                </div>

                            </form>
                        </Form>
                        <footer className={"flex justify-center gap-1"}>
                            <p className={"text-14 font-normal text-gray-600"}>
                                {type === 'sign-in' ? "Don't have an account ?" : "Already have an account ?"}
                            </p>
                            <Link href={type === 'sign-in' ? "/sign-up" : "sign-in"} className={"form-link"}>
                                {type === 'sign-in' ? "Sign Up" : "Sign In"}
                            </Link>
                        </footer>
                    </>
                )
            }
        </section>
    );
}

export default AuthForm;