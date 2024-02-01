import { create } from 'zustand'

const useStore = create<Model>(( update ) => ({
    isVisible: true,
    setIsVisible: () => update(( state ) => ({ isVisible: !state.isVisible })),
}));

export interface Model{
    isVisible : boolean
    setIsVisible: () => void
}

 
export default useStore;