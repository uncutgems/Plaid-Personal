import React from 'react';
import HeaderBox from '@/components/HeaderBox'
import TotalBalanceBox from "@/components/TotalBalanceBox";
import RightSidebar from "@/components/RightSidebar";
import {getLoggedInUser} from "@/lib/actions/user.actions";
import {redirect} from "next/navigation";

const Home = async () => {
    const loggedIn = await getLoggedInUser();

    if (!loggedIn) {
        console.log(loggedIn)
        redirect("/sign-in");
    }

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
                    <TotalBalanceBox accounts={[]} totalBanks={1} totalCurrentBalance={9999999999.75} />
                </header>
                RECENT TRANSACTION
            </div>

            <RightSidebar user={loggedIn} transactions={[]} banks={[{currentBalance: 9999999999.75}, {currentBalance: 9999999999.75}]}/>
        </section>
    )
}

export default Home;