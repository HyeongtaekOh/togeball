export const useRouter = ( path, dynamicPath ) => {
    const lazy = async () => {
        const module = await import( `../pages/${ path || dynamicPath }` )

        return {
            Component: module.default,
        }
    }

    return { path: dynamicPath || path, lazy }
}