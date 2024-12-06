export const SANDBOX_HOST = 'http://localhost:8080/api'
export const SANDBOX_WEB = 'http://localhost:3000'
export const PROD_HOST = '';

// Authentication
export const LOGIN_ENDPOINT = '/auth/login';
export const REGISTER_ENDPOINT = '/auth/register';
export const REFRESH_TOKEN_ENDPOINT = '/auth/refreshToken';

// Plaid
export const PLAID_EXCHANGE_TOKEN = '/plaidSync/exchangeToken';
export const PLAID_LINK_TOKEN_INITIAL = "/plaidSync/initial";

// Transaction Read & Edit
export const GET_TRANSACTION = '/get-transaction/transactions';

// Balance Read
export const GET_ACCOUNTS = '/get-balance/all-account';