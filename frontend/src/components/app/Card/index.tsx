import { styled } from "styled-components";
import { Button } from "../..";
import { GameType } from "src/types";

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
    const {
       games
    } = props

    const { datetime, homeClubName, awayClubName, homeClubLogo, awayClubLogo } = games

    
    const time = datetime.substring(11,16)

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
            <Button>참가하기</Button>
            </WrapTxtWrapper>
        </Mainwrapper>
    )
}

export default OpenChatCard

type Textprops = {
    games?: GameType
}