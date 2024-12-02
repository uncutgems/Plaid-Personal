import axios from "axios";
import {SANDBOX_HOST} from "@/constants/endpoints";
export default axios.create({
    baseURL: SANDBOX_HOST,
    headers: { "Content-Type": "application/json" },
});
export const axiosAuth = axios.create({
    baseURL: SANDBOX_HOST,
    headers: { "Content-Type": "application/json" },
});
