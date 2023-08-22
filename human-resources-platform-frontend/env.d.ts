/// <reference types="vite/client" />


interface ImportMetaEnv {
    readonly VITE_SCHEME: string
    readonly VITE_PORT: string
    readonly VITE_DOMAIN_NAME: string
    readonly VITE_CLIENT_ID: string,
    readonly VITE_RESPONSE_TYPE: string,
    readonly VITE_STATE: string,
    readonly VITE_REDIRECT_URI: string,
    readonly VITE_PORT: number
    readonly VITE_URL: string
}

interface ImportMeta {
    readonly env: ImportMetaEnv
}