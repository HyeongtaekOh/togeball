import styled, { css } from 'styled-components';
import { Tag, TagBtn, Title } from 'src/components'
import { useNavigate } from 'react-router-dom';
import lufi from 'src/asset/images/lufi.jpg'
import lgtwinslogo from'src/asset/images/lgtwinslogo.jpg'
import barcode from 'src/asset/images/barcode.jpg'

const BarcodeWrapper = styled.div`
height: 200px;
width: 10%;
border : 1px solid white;
border-radius: 20px;
display: flex;
justify-content: center;
align-items: center;
background-color: #7D74B4;
`

const Barcodeimgwrapper = styled.img`
width: 180%;
height: 72%;
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
const Ticket = ( () => {
    
    
    

    const user = { id: 'test01', myTeam : 'LG 트윈스', stadium : '고척 돔', nickName: '플레권', tag: ['#Estp', '#응원가형', '#시즌권보유', '#자차보유', '#플레플레','#챌린저'], logo: ''}
    return (
        

        <div style={{ display: 'flex', marginBottom: '80px' , maxWidth:'1000px', marginTop:'50px'}}>
       <BarcodeWrapper>
        <Barcodeimgwrapper src={ barcode }></Barcodeimgwrapper>
       </BarcodeWrapper>
       <InfoWrapper>
        <MyinfoWrapper>
            <div style={{ width: '60%', height: '100%', display: 'flex', flexWrap: 'wrap' }}>
                <div style={{ width:'30%'}}>
            <PictureWrapper src={ lufi }></PictureWrapper>
                </div>
            <div style={{ width: '70%', display: 'flex', flexDirection:'column', justifyContent:'space-evenly', flexWrap:'wrap' }}>
                <IndexWrapper>아이디:  <ValueWrapper>{ user.id }</ValueWrapper></IndexWrapper>
                <IndexWrapper>응원팀:  <ValueWrapper>{ user.myTeam } </ValueWrapper></IndexWrapper>
                <IndexWrapper>선호구장:  <ValueWrapper>{ user.stadium }</ValueWrapper></IndexWrapper>
                <IndexWrapper>닉네임:  <ValueWrapper>{ user.nickName }</ValueWrapper></IndexWrapper>
                
            </div>
            <div style={{ display: 'flex', paddingLeft: '120px', flexWrap: 'wrap', width: '100%' }}>
                { user.tag.length > 0 ? (
                    user.tag.map(( tag, index ) => (
                        <Tag key={ index } height='10px'>{ tag }</Tag>  
                    ))) 
                : null }
            </div>
            </div>
            <div style={{ width: '40%', height: '100%' }}>
               <LogoWrapper src={ lgtwinslogo }></LogoWrapper>
            </div>
            <a style={{ color:'white', paddingTop: '5px', paddingRight: '10px' }} href="">회원정보수정</a>  
        </MyinfoWrapper>
       </InfoWrapper>
        </div>
   
    )
})

export default Ticket