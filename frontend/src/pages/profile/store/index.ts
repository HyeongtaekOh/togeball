import { Tag } from 'src/types'
import { create } from 'zustand'

const useModel = create<Model>( ( set, get ) => (
  {
    selectTags: [],
    addSelectTags : ( tag ) => set(( state ) => (
      {
        selectTags: [
          ...state.selectTags,
          tag
        ]
      }
    )),
    deleteTags: ( tag ) => set(( state ) => (
      {
        selectTags: 
        state.selectTags.length > 0 && 
          state.selectTags.filter(( item ) => item.name!== tag.name )
      }
    ))
  }
))

export default useModel

export interface Model{
  selectTags : Tag[]
  addSelectTags : ( tag: Tag ) => void,
  deleteTags : ( tag: Tag ) => void
}