<<<<<<< HEAD
import styled, { css } from 'styled-components';
import { Tag, TagBtn, Title } from 'src/components'
import { useNavigate } from 'react-router-dom';

const BarcodeWrapper = styled.div`
height: 200px;
width: 10%;
border : 1px solid lightgray;
border-radius: 20px;
display: flex;
justify-content: center;
align-items: center;
background-color: #7D74B4;
`

const Barcodeimgwrapper = styled.img`
width: 180%;
height: 38.6%;
transform: rotate(90deg);
`
const InfoWrapper = styled.div`
height: 200px;
width: 90%;
border : 1px solid lightgray;
background-color: #7D74B4;
display: flex;
justify-content: center;
align-items: center;
border-radius: 20px;
`
const MyinfoWrapper = styled.div`
width: 100%;
background-color: white;
height: 72%;
display: flex;
flex-wrap: wrap;
justify-content: right;
`
const PictureWrapper = styled.img`
border : 1px solid #2C2C06;
width: 50%;
margin-left: 20px;
margin-top: 10px;
border-radius: 40px;
`
const LogoWrapper = styled.img`
width: 100%;
height: 100%;
opacity: 0.5;


`
const IndexWrapper = styled.p`
  margin: 3px;
  color: lightgray;
  height: 33%;
  
`

const ValueWrapper = styled.span`
  color: black;
  padding: 5px 10px 5px 20px;
  font-weight: bolder;
  position: relative;
  z-index: 999;
`
const TicketProfile = ( () => {
    
    const lupi = 'data:image/jpeg;base64,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'
    const logo = 'https://upload.wikimedia.org/wikipedia/ko/8/8a/LG_%ED%8A%B8%EC%9C%88%EC%8A%A4_%EB%A1%9C%EA%B3%A0.png'
    

    const user = { id: 'test01', myTeam : 'LG 트윈스', stadium : '고척 돔', nickName: '플레권', tag: ['#Estp', '#응원가형', '#시즌권보유', '#자차보유'], logo: ''}
    return (
        <div style={{ display: 'flex', marginBottom: '80px'}}>
       <BarcodeWrapper>
        <Barcodeimgwrapper src='https://img.freepik.com/free-psd/barcode-illustration-isolated_23-2150584086.jpg'></Barcodeimgwrapper>
       </BarcodeWrapper>
       <InfoWrapper>
        <MyinfoWrapper>
            <div style={{width: '60%', height: '100%', display: 'flex', flexWrap: 'wrap'}}>
                <div style={{ width:'30%'}}>
            <PictureWrapper src={lupi}></PictureWrapper>
                </div>
            <div style={{width: '70%', display: 'flex', flexDirection:'column', justifyContent:'space-evenly', flexWrap:'wrap' }}>
                <IndexWrapper>아이디:  <ValueWrapper>  { user.id }</ValueWrapper></IndexWrapper>
                <IndexWrapper>응원팀:  <ValueWrapper>   { user.myTeam } </ValueWrapper></IndexWrapper>
                <IndexWrapper>선호구장:  <ValueWrapper> { user.stadium }</ValueWrapper></IndexWrapper>
                <IndexWrapper>닉네임:  <ValueWrapper>   {user.nickName }</ValueWrapper></IndexWrapper>
                
            </div>
            <div style={{ display: 'flex', paddingLeft: '120px', flexWrap: 'wrap', width: '100%' }}>
                {
                    user.tag.length > 0 ? (
                        user.tag.map(( tag, index ) => (
                            <Tag key={ index } height='10px'>{ tag }</Tag>  
                        ))
                    ) : null
                }
            </div>
            </div>
            <div style={{ width: '40%', height: '100%' }}>
               <LogoWrapper src={logo}></LogoWrapper>
            </div>
            <a style={{ color:'white', paddingTop: '5px', paddingRight: '10px'  }} href="">회원정보수정</a>
            
            
            
        </MyinfoWrapper>
       </InfoWrapper>
        </div>
    )
})

export default TicketProfile
=======
import RowTagList from './RowTagList'
import ColTagList from './ColTagList'
import TagBtn from './TagButton'
import TagList from './TagList'

export { RowTagList, ColTagList, TagBtn, TagList }
>>>>>>> 426b51fd1672252d39f7974728b3e0411ecda701
