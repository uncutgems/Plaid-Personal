export const SANDBOX_HOST = 'http://localhost:8080'
export const SANDBOX_WEB = 'http://localhost:3000'
export const PROD_HOST = '';

// Authentication
export const LOGIN_ENDPOINT = '/api/auth/login';
export const REGISTER_ENDPOINT = '/api/auth/register';
export const REFRESH_TOKEN_ENDPOINT = '/api/auth/refreshToken';

// Plaid
export const PLAID_EXCHANGE_TOKEN = '/api/plaidSync/exchangeToken';
export const PLAID_LINK_TOKEN_INITIAL = "/api/plaidSync/initial";

// Transaction Read & Edit
export const GET_TRANSACTION = '/api/get-transaction/transactions';

// Balance Read
export const GET_ACCOUNTS = '/api/get-balance/all-account';