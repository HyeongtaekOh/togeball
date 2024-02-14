import { create } from 'zustand'

const useHeaderStore = create<Model>((set) => ({
  count: 0,
  updateCount: ( number ) => set({ count: number }),
}))

export interface Model {
  count: number
  updateCount: ( number: number ) => void
}

export default useHeaderStore
