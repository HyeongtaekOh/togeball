import useStore from 'src/store/index'

type UseSession = () => ( value: UserValue ) => string | number

export const useSession: UseSession = () => {
    const session = useStore( ( { session } ) => session || {} )

    return ( value ) => session[ value ]
}

export type UserValue =
    'id' |
    'userCode' |
    'userNm' |
    'userType' |
    'userTel' |
    'userEmail' |
    'simple'


