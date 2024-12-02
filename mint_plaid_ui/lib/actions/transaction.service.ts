import {GET_ACCOUNTS} from "@/constants/endpoints";
import {parseStringify} from "@/lib/utils";
import useAxiosAuth from "@/lib/hooks/useAxiosAuth";


export const useGetAccounts = async () => {
    try {
        return await useAxiosAuth().get(GET_ACCOUNTS).then((response) => {
            if (response.status === 200) {
                const totalBanks = response.data.length;
                const totalCurrentBalance = response.data.reduce((total: number, account: { currentBalance: number; }) => {
                    return total + account.currentBalance;
                }, 0);
                return parseStringify({data: response.data, totalBanks: totalBanks, totalCurrentBalance: totalCurrentBalance});
            }
        })
    } catch (error) {
        console.log(error);
    }
    return null;
}

export const getTransactions = async () => {

}