import { create } from 'zustand'

const useStore = create<Model>(( update ) => ({
    isMonth: true,
    setIsMonth: () => update(( state ) => ({ isMonth: !state.isMonth })),
}));

export interface Model{
    isMonth : boolean
    setIsMonth: () => void
}

 
export default useStore;