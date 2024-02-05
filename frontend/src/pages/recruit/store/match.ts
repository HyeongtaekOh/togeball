import { create } from 'zustand'
import { GameType } from 'src/types'

// 나중에는 여기에 객체로 게임 정보를 가져와야한다
const matchStore = create<Model>(( set, get ) => ({
    match: {
      id: 0,
      datetime: '2024-02-01',
      homeClubName: '',
      awayClubName: '',
      stadiumName: ''
    },
    updateMatch: ( game: GameType ) => set({ match : game }),

    isModalOpened: false,
    updateModal: () => set(( state ) => ({ isModalOpened: !state.isModalOpened })),
}))

export interface Model{
  match : GameType,
  updateMatch : ( game: GameType ) => void,
  isModalOpened : boolean
  updateModal: () => void
}
 
export default matchStore;