import { TagType } from 'src/types'
import { create } from 'zustand'

const useModel = create<Model>( ( set, get ) => (
  {
    selectTags: [],
    addSelectTags : ( tag ) => set(( state ) => (
      ( state.selectTags.length<15 )?
      { 
        selectTags: 
        [
          ...state.selectTags,
          tag
        ]
      }: state
    )),
    deleteTags: ( tag ) => set(( state ) => (
      {
        selectTags: 
          state.selectTags.filter(( item ) => item.content!== tag.content )
      }
    )),
  }
))

export default useModel

export interface Model{
  selectTags : TagType[]
  addSelectTags : ( tag: TagType ) => void,
  deleteTags : ( tag: TagType ) => void
}