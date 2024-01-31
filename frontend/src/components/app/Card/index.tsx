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
  box-shadow: 5px 5px 5px 5px gray;
  margin: 10px;
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
       games, child,
    } = props

    const { datetime, homeClubName, awayClubName } = games

    return(
        <Mainwrapper>
            <WrapImgWrapper>
                <ImgWrapper src='' alt='Hometeam'/>
                <ImgWrapper src='' alt='Awayteam'/>
            </WrapImgWrapper>
            <WrapTxtWrapper>
            <TextWrapper >
               { homeClubName }  vs   { awayClubName }         
            </TextWrapper>
            <TextWrapper>
               <p style={{ color:'lightgray' }}>
                { datetime }
                </p> 
            </TextWrapper>
            <Button>참가하기</Button>
            </WrapTxtWrapper>
        </Mainwrapper>
    )
}

export default OpenChatCard

type Textprops = {
    child?: string,
    games?: GameType
}