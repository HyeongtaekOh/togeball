import { create } from 'zustand'

const tagStore = create<Model>(( set, get ) => ({
    tags: [],
    updateTags: ( tag: string ) => set({  })
}))

export interface Model{
  tags : string[],
  updateTags : ( tag: string ) => void
}
 
export default tagStore;