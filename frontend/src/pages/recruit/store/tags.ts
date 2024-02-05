import { create } from 'zustand'

const tagStore = create<Model>(( set, get ) => ({
    tagItem: '',
    tagList: [],
    setTagItem: ( tag ) => set(() => ({ tagItem: tag })),
    setTagList:( tag ) => set(( state ) => ({tagList: [...state.tagList, state.tagItem] })),

}))

export interface Model{
  tagItem: string,
  tagList: string[],
  setTagItem: ( tag: string ) => void
  setTagList: ( list: [...string[], string ] | string[] ) => void
}
  
export default tagStore;