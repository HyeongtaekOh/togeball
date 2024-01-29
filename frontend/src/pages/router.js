import { useRouter } from '../hooks'

export const routes = [
    
    { path: 'login' },
    { path: 'dev' },
    { path: 'home' },
    { path: 'recruit/post' },
    { path: 'signup' },
    { path: 'profile' },
    { path: 'todaygames' },
    { path: 'boards' }
    
]

const lazyModules = routes.map( ( { path, dynamicPath } ) => useRouter( path, dynamicPath ) )

lazyModules.unshift( {
    path: '/',
    lazy: async () => {
        const module = await import( `./dev` )
        return {
            Component: module.default
        }
    }
} )

export default lazyModules