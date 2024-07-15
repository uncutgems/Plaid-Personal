import React, {useCallback, useEffect, useState} from 'react';
import {Button} from "@/components/ui/button";
import Image from "next/image";
import {createLinkToken, exchangePublicToken} from "@/lib/actions/user.actions";
import {useRouter} from "next/navigation";
import {PlaidLinkOnSuccess, PlaidLinkOptions, usePlaidLink} from 'react-plaid-link';


const PlaidLink = ({user,variant}: PlaidLinkProps) => {
    const router = useRouter();
    const [token, setToken] = useState('');
    useEffect(() => {
        const getLinkToken = async () => {
            const data = await createLinkToken();

            setToken(data?.['link_token']);
        }
        getLinkToken().then();
    }, [user]);
    const onSuccess = useCallback<PlaidLinkOnSuccess>(async (public_token: string) => {
        const success = await exchangePublicToken({
            publicToken: public_token
        })
        if (success)
            router.push("/");
    }, [router])

    const config: PlaidLinkOptions = {
        token,
        onSuccess
    }

    const { open, ready } = usePlaidLink(config);

    const useConnectBank = useCallback(async () => {
        const getLinkToken = async () => {
            const data = await createLinkToken();
            return data['link_token'];
        }
        const linkToken = await getLinkToken();
        const {open, ready} = usePlaidLink({
            token: linkToken,
            onSuccess: (public_token: string, metadata) => {
                exchangePublicToken({publicToken: public_token})
            }
        })
        open()
    })
    
    return (
        <>
            {variant === 'primary' ? (
                <Button
                    onClick={() => useConnectBank}
                    className="plaidlink-primary"
                >
                    Connect bank
                </Button>
            ): variant === 'ghost' ? (
                <Button onClick={() => useConnectBank} variant="ghost" className="plaidlink-ghost">
                    <Image
                        src="/icons/connect-bank.svg"
                        alt="connect bank"
                        width={24}
                        height={24}
                    />
                    <p className='hiddenl text-[16px] font-semibold text-black-2 xl:block'>Connect bank</p>
                </Button>
            ): (
                <Button onClick={() => useConnectBank} className="plaidlink-default">
                    <Image
                        src="/icons/connect-bank.svg"
                        alt="connect bank"
                        width={24}
                        height={24}
                    />
                    <p className='text-[16px] font-semibold text-black-2'>Connect bank</p>
                </Button>
            )}
        </>
    );
};

export default PlaidLink;