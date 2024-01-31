import { create } from 'zustand'

const useStore = create<Model>((set, get) => ({
    match: '',
    setMatch: (str: string) => set({ match : str }),
  }));

  export interface Model{
    match : string
    setMatch : ( str: string ) => void,
  }

 
export default useStore;