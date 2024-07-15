import React from 'react';
import HeaderBox from '@/components/HeaderBox'
import TotalBalanceBox from "@/components/TotalBalanceBox";
import RightSidebar from "@/components/RightSidebar";

const Home = () => {
    const loggedIn : User = { $id: '1', userId: '1', username: 'qdinh', email: 'qdinh@gmail.com', ssn: 'qdinh@gmail.com' }

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

            <RightSidebar user={loggedIn} transactions={[]} banks={[{currentBalance: 99999999.75}, {currentBalance: 99999999.75}]}/>
        </section>
    )
}

export default Home;