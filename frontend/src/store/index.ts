import { create } from 'zustand'

const useStore = create<MainStore>( ( set, get ) => ( {

  session: null,
  setSession: ( session ) => set( { session } ),

  accessToken: '',
  setAccessToken: ( accessToken ) => set( { accessToken } ),

  isLogin: false,
  setIsLogin: ( isLogin ) => set( { isLogin } ),
  
}))

export default useStore

type MainStore = {
  session: Session,
  setSession: ( session: Session ) => void

  accessToken: string,
  setAccessToken: ( accessToken: string ) => void

  isLogin: boolean
  setIsLogin: ( isLogin: boolean ) => void
}

type Session = {
  id: number,
  userCode: string,
  userNm: string,
  userType: string,
  userTel: string,
  userEmail: string,
  simple: string
}