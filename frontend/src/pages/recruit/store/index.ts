import { create } from 'zustand'


// 나중에는 여기에 객체로 게임 정보를 가져와야한다
const useStore = create<Model>((set, get) => ({
    match: '',
    setMatch: (str: string) => set({ match : str }),
  }));

  export interface Model{
    match : string
    setMatch : ( str: string ) => void,
  }

 
export default useStore;