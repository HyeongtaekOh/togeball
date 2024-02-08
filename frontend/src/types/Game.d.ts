export interface GameType {
  id?: number,
  // chatroomId?: number,
  datetime?: string,
  homeClubName?: string,
  awayClubName?: string,
  stadiumName?: string,
  homeClubLogo?: string,
  awayClubLogo?: string,
}
// name => id
// join해서 넘기기 때문에 타입이 변경될 가능성이 있음.
