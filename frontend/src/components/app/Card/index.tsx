import { styled } from "styled-components"
import { Button } from "../.."
import { GameType } from "src/types"
import { useNavigate } from "react-router-dom"
import { useMutation } from "react-query"
import { partiChat } from "src/api"
import { getAxios } from "src/api/util"

const Mainwrapper = styled.div`
  display: flex;
  width: 30%;
  height: 250px;
  border-radius: 10px;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  box-shadow: 1px 1px 1px 1px lightgray;
  margin: 10px;
  padding-top: 15px;
`
const WrapImgWrapper = styled.div`
    display: flex;
    justify-content: center;
    
`
const WrapTxtWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    gap: 10px;
    
`
const ImgWrapper = styled.img`
    width: 45%;
    
`
const TextWrapper = styled.p<Textprops>`
    font-size: 20px;
    text-align: center;

`

const OpenChatCard = ( props: Textprops ) => {
  
    const { games } = props

    const navigator = useNavigate()

    const { id, datetime, homeClubName, awayClubName, homeClubLogo, awayClubLogo } = games

    const partiMutation = useMutation( partiChat, {
        onSuccess: async() => {
          const gameChat = await getAxios(`/api/chatrooms/game`,  { gameId: id } )
          navigator(`/openChat/${ gameChat?.id }` , { state: games })
        }
      })

    
    const time = datetime.substring(11,16)

    const goChat =  async() => {
        if( !localStorage.getItem('userId') ){
          alert(' 로그인 하세요 ')
          navigator('/login')
        } 
        else {
          const gameInfo = await getAxios(`/api/chatrooms/game`, { gameId: id } )
          if(!gameInfo) {
            alert(' 로그인 하세요 ')
            navigator('/login')
          }
          partiMutation.mutateAsync({ chatRoomId : gameInfo?.id })
        }
      }

    return(
        <Mainwrapper>
            <WrapImgWrapper>
                <ImgWrapper src={ homeClubLogo } alt='Hometeam'/>
                <ImgWrapper src={ awayClubLogo } alt='Awayteam'/>
            </WrapImgWrapper>
            <WrapTxtWrapper>
            <TextWrapper >
               { homeClubName }  vs   { awayClubName }         
            </TextWrapper>
            <TextWrapper>
               <p style={{ color:'lightgray' }}>
                { time }
                </p> 
            </TextWrapper>
            <Button onClick={() => goChat()}>참가하기</Button>
            </WrapTxtWrapper>
        </Mainwrapper>
    )
}

export default OpenChatCard

type Textprops = {
    games?: GameType
}