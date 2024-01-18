export const useRouter = ( path, dynamicPath ) => {
    const lazy = async () => {
        const module = await import( `../pages/${ path }` )

        return {
            Component: module.default,
        }
    }

    return { path: dynamicPath || path, lazy }
}