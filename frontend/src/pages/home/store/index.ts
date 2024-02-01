import { GameType } from 'src/types'
import { create } from 'zustand'

const useStore = create<Model>(( set ) => ({ 
    todayGames: [],
    updateTodayGames: ( games ) => set({ todayGames: games  }),
}))

export interface Model{
  todayGames: GameType[]
  updateTodayGames : ( games: GameType[] ) => void
}

export default useStore;