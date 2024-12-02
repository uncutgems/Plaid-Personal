import React from 'react';
import HeaderBox from '@/components/HeaderBox'
import TotalBalanceBox from "@/components/TotalBalanceBox";
import RightSidebar from "@/components/RightSidebar";
import {getLoggedInUser} from "@/lib/actions/user.actions";
import {redirect} from "next/navigation";
import {useGetAccounts} from "@/lib/actions/transaction.service";

const Home = async ({searchParams: {id, page}}: SearchParamProps) => {
    const loggedIn = await getLoggedInUser();

    if (loggedIn.username === "" && loggedIn.email === "") {
        redirect("/sign-in");
    }

    const accounts = await useGetAccounts();
    if (!accounts) return;


    return (
        <section className={"home"}>
            <div className={"home-content"}>
                <header className={"home-header"}>
                    <HeaderBox
                        type="greeting"
                        title={"Welcome"}
                        user={loggedIn?.username || 'Guest'}
                        subtext="Easily manage your spending to reach your financial goal"
                    />
                    <TotalBalanceBox accounts={accounts.data} totalBanks={accounts.totalBanks} totalCurrentBalance={accounts.totalCurrentBalance} />
                </header>
                RECENT TRANSACTION
            </div>

            <RightSidebar user={loggedIn} transactions={[]} banks={accounts.data?.slice(0, 2)}/>
        </section>
    )
}

export default Home;