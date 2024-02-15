import { useRouter } from '../hooks'

export const routes = [
    
    { path: 'login' },
    { path: 'dev' },
    { path: 'home' },
    { path: 'recruit/post' },
    { path: 'recruit' },
    { path: 'signup' },
    { path: 'profile' },
    { path: 'todaygames' },
    { path: 'boards' },
    
    { path: 'calendar' },
    { path: 'mypage' },

    { path : 'matching' },

    { path: 'matchChat' },
    { path: 'matchChat', dynamicPath: `matchChat/:chatroomId` },
    
    { path: 'chat' },
    { path: 'chat', dynamicPath: `chat/:chatroomId` },

    { path: 'openChat' },
    { path: 'openChat', dynamicPath: `openChat/:chatroomId` },

    { path: 'login/kakao' }

]

const lazyModules = routes?.map( ( { path, dynamicPath } ) => useRouter( path, dynamicPath ) )

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
