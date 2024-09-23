import React, {useCallback, useEffect, useState} from 'react';
import {Button} from "@/components/ui/button";
import Image from "next/image";
import {createLinkToken, exchangePublicToken} from "@/lib/actions/user.actions";
import {usePlaidLink} from 'react-plaid-link';
import { getCookie, setCookie } from 'cookies-next'
import {LINK_TOKEN} from "@/constants";



const PlaidLink = ({variant}: PlaidLinkProps) => {
    const [linkToken, setLinkToken] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        const linkToken = getCookie(LINK_TOKEN)
        if (linkToken) {
            setLinkToken(linkToken)
        }
    }, []);

    const getLinkToken = useCallback(async () => {
        setIsLoading(true);
        try {
            const response = await createLinkToken();
            setLinkToken(response['link_token']);
            setCookie(LINK_TOKEN, response['link_token'], {
                expires: response['expiration'],
                httpOnly: true,
            });
        } catch (error) {
            console.error('Error fetching link token:', error);
        } finally {
            setIsLoading(false);
        }
    }, []);

    const onSuccess = useCallback(async (public_token: string) => {
        try {
            console.log("on success")
            const response = await exchangePublicToken({
                publicToken: public_token
            });
            if (response) {
                console.log('Successfully exchanged token and linked account');
            }
        } catch (error) {
            console.error('Error exchanging public token:', error);
        }
    }, []);

    const config = {
        token: linkToken,
        onSuccess,
    };

    const {open, ready} = usePlaidLink(config);

    const handleClick = useCallback(async () => {
        if (linkToken) {
            open();
        } else {
            await getLinkToken();
        }
    }, [linkToken, open, getLinkToken]);

    // Effect to open Plaid Link once we have a token
    React.useEffect(() => {
        if (linkToken && ready) {
            open();
        }
    }, [linkToken, ready, open]);

    return (
        <>
            {variant === 'primary' ? (
                <Button
                    onClick={handleClick}
                    disabled={isLoading}
                    className="plaidlink-primary"
                >
                    {isLoading ? 'Loading...' : 'Connect a bank account'}
                </Button>
            ) : variant === 'ghost' ? (
                <Button onClick={handleClick} variant="ghost" className="plaidlink-ghost">
                    <Image
                        src="/icons/connect-bank.svg"
                        alt="connect bank"
                        width={24}
                        height={24}
                    />
                    <p className='hiddenl text-[16px] font-semibold text-black-2 xl:block'>
                        {isLoading ? 'Loading...' : 'Connect a bank account'}
                    </p>
                </Button>
            ) : (
                <Button onClick={handleClick} className="plaidlink-default">
                    <Image
                        src="/icons/connect-bank.svg"
                        alt="connect bank"
                        width={24}
                        height={24}
                    />
                    <p className='text-[16px] font-semibold text-black-2'>
                        {isLoading ? 'Loading...' : 'Connect a bank account'}
                    </p>
                </Button>
            )}
        </>
    );
};

export default PlaidLink;