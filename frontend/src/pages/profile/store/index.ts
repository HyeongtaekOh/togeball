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
    team: '',
    setTeam: ( data ) => set(() => ({ team: data })),
    stadium: '',
    setStadium: ( data ) => set(() => ({ stadium: data })),
    image: '',
    setImage: ( data ) => set(() => ({ image: data }) )
  }
))

export default useModel

export interface Model{
  selectTags : TagType[]
  addSelectTags : ( tag: TagType ) => void,
  deleteTags : ( tag: TagType ) => void,
  team: string,
  setTeam: ( data ) => void,
  stadium: string,
  setStadium: ( data ) => void
  image: string,
  setImage: ( data ) => void
}