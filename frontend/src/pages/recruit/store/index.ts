import { create } from 'zustand'

// 나중에는 여기에 객체로 게임 정보를 가져와야한다
const useStore = create<Model>(( set, get ) => ({
    match: '',
    updateMatch: ( str: string ) => set({ match : str }),

    isModalOpened: false,
    updateModal: () => set(( state ) => ({ isModalOpened: !state.isModalOpened })),
}))

export interface Model{
  match : string
  updateMatch : ( str: string ) => void,
  isModalOpened : boolean
  updateModal: () => void
}
 
export default useStore;