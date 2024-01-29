import { useRouter } from '../hooks'

export const routes = [
    
    { path: 'login' },
    { path: 'dev' },
    { path: 'home' },
    { path: 'recruit/post' },
    { path: 'recruit' },
    { path: 'signup' },
    { path: 'profile' },
<<<<<<< HEAD
    { path: 'todaygames' },
    { path: 'boards' }
    
=======
    { path: 'calender' },
    { path: 'calender/week'}

>>>>>>> 8c6cf755354b889c122fb3e62d0326a322633d01
]

const lazyModules = routes.map( ( { path, dynamicPath } ) => useRouter( path, dynamicPath ) )

lazyModules.unshift( {
    path: '/',
    lazy: async () => {
        const module = await import( `./home` )
        return {
            Component: module.default
        }
    }
} )

export default lazyModules