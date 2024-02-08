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
          state.selectTags.filter(( item ) => item.content !== tag.content )
      }
    )),
    team: 0,
    setTeam: ( data ) => set(() => ({ team: data })),
    stadiums: [],
    addStadiums: ( stadium ) => set(( state ) => (
      {
         stadiums: 
         [
          ...state.stadiums,
          stadium
        ] 
      }
    )),
    deleteStadiums: ( stadium ) => set(( state ) => (
      {
        stadiums: 
          state.stadiums.filter(( item ) => item !== stadium )
      }
    )),
    image: {},
    setImage: ( data ) => set(() => ({ image: data }) )
  }
))

export default useModel

export interface Model{
  selectTags : TagType[]
  addSelectTags : ( tag: TagType ) => void
  deleteTags : ( tag: TagType ) => void
  team: number
  setTeam: ( data: number ) => void
  stadiums: TagType[]
  addStadiums: ( stadium: TagType ) => void
  deleteStadiums: ( stadium: TagType ) => void
  image: object
  setImage: ( data ) => void
}