import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { getMyInfo } from 'src/api'
import { TagList } from './index';
import lufi from 'src/asset/images/lufi.jpg'
import Logo from 'src/asset/images/Logo.jpg'
import barcode from 'src/asset/images/barcode.jpg'
import { useQuery } from 'react-query';
import { useEffect, useState } from 'react';

const TicketWrapper = styled.div`
    display: flex;
    marginBottom: 80px; 
    marginTop: 300px;
`

const BarcodeWrapper = styled.div`
    height: 280px;
    width: 10%;
    border : 1px solid white;
    border-radius: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #7D74B4;
`
    
const InfoWrapper = styled.div`
    height: 280px;
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
    height: 75%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
`

const PictureWrapper = styled.img`
    width: 84px;
    height: 84px;
    margin-left: 30px;
    margin-top: 30px;
    border-radius: 20%;
`

const MySelfWrapper = styled.div`
    display: flex;
    flex-direction: column;
    width: 70%;
    flexWrap: wrap;
    margin-top: 30px;
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

const EditProfileWrapper = styled.p`
    color: white;
    padding-top: 9px;
    margin-left: auto;
    padding-right: 16px;
    cursor: pointer;
`


const Ticket = ( () => {

    const navigate = useNavigate()

    const EditProfile = () => {
        navigate('/profile')
    }

    const { data: info } = useQuery([ 'user' ], () => getMyInfo())
    const [ user, setUser ] = useState( null )

    useEffect(() => {
        setUser({
            email: info?.email,
            myTeam : info?.clubSponsorName+info?.clubName,
            profileImage : info?.profileImage, 
            nickName: info?.nickname,
            tag: info?.tags?.map(( tag ) =>{ return '#'+tag.content }), 
            logo: info?.clubLogo
        })
    }, [ info ])


    return (
            <TicketWrapper>
                <BarcodeWrapper>
                    <img src={ barcode } alt = '' style={{ width: '100%', height: '75%' }}/>
                </BarcodeWrapper>
                <InfoWrapper>
                <MyinfoWrapper>
                    <div style={{ width: '65%', height: '100%', display: 'flex', flexWrap: 'wrap' }}>
                        <div style={{ width:'30%'}}>
                            <PictureWrapper src={( user?.profileImage===null || user?.profileImage==='' )? lufi : user?.profileImage } />
                        </div>
                        <MySelfWrapper>
                            <IndexWrapper>이메일:  
                                <ValueWrapper>{ user?.email }</ValueWrapper>
                            </IndexWrapper>
                            <div style={{ display: 'flex', gap: '40px' }}>
                                <IndexWrapper>응원팀:  
                                    <ValueWrapper>{ ( user?.myTeam===0 ) ? '미정' : user?.myTeam }</ValueWrapper>
                                </IndexWrapper>
                                <IndexWrapper>닉네임:  
                                    <ValueWrapper>{ user?.nickName }</ValueWrapper>
                                </IndexWrapper>
                            </div>
                        </MySelfWrapper>
                        <TagList tags = { user?.tag }/>  
                    </div>
                    <div style={{ width: '30%', height: '100%' }}>
                        <img src={ user?.logo===null ? Logo : user?.logo } alt=''
                            style={{ width: '90%', height: '90%', opacity: '0.5', marginTop: '5%' }}/>
                    </div>
                    <EditProfileWrapper onClick={ EditProfile }>회원정보수정</EditProfileWrapper>  
                </MyinfoWrapper>
                </InfoWrapper>
            </TicketWrapper>
    )
})

export default Ticket