import { create } from 'zustand'
import { GameType } from 'src/types'

const useStore = create<Model>(( set, get ) => ({
  match: {},
  updateMatch: ( game: GameType ) => set({ match : game }),

  tagItem: '',
  setTagItem: ( tag ) => set(() => ({ tagItem: tag })),

  tagList: [],
  setTagList:( tagList ) => set({ tagList: [...tagList ]}),

  isModalOpened: false,
  updateModal: () => set(( state ) => ({ isModalOpened: !state.isModalOpened })),
}))

export interface Model{
  match : GameType,
  updateMatch : ( game: GameType ) => void,

  isModalOpened : boolean
  updateModal: () => void

  tagItem: string,
  setTagItem: ( tag: string ) => void

  tagList: string[],
  setTagList: ( list: [...string[], string ] | string[] ) => void
}
 
export default useStore;