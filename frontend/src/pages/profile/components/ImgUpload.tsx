import { Title } from 'src/components'
import { useCallback, useEffect, useRef, useState } from 'react'
import axios from 'axios'
import styled from 'styled-components'
import useModel from '../store'

const ImgUploadWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  gap: 10px;
  height: 100px;
  margin-top: 30px;
  margin-left: 50px;
`
const ImgWrapper = styled.img`
  min-width: 150px;
  max-width: 150px;
  height: auto;
  border: 1px solid lightgrey;
  outline: none;
  border-radius: 10px;
`
const LabberWrapper = styled.label`
  background-color: gray;
  display: flex;
  width: 20px;
  height: 13px;
  color: white;
  padding: 8px;
  font-size: 11px;
  border-radius: 5px;
  cursor: pointer;
`

const ImgUpload = () => {

  const inputRef = useRef<HTMLInputElement | null>(null);
  const [ imgSrc, setImgSrc ] = useState(null)
  const { setImage } = useModel()

  const onUploadImage = useCallback(( e: React.ChangeEvent<HTMLInputElement> ) => {
    
    if ( !e.target.files || e.target.files.length ===0 ) return

    const formData = new FormData();
    const reader = new FileReader();

    formData.append( 'image', e.target.files[0] )

    reader.readAsDataURL( e.target.files[0] )
    reader.onloadend = () => {
      setImgSrc( reader.result )
      setImage( reader.result )
    }

    // axios({
    //   baseURL: process.env.REACT_APP_BASE_URL,
    //   url: '/images/:username/thumbnail',
    //   method: 'POST',
    //   data: formData,
    //   headers: {
    //     'Content-Type': 'multipart/form-data',
    //   },
    // })
    //   .then(response => {
    //     console.log(response.data);
    //   })
    //   .catch(error => {
    //     console.error(error);
    //   });
  }, []);
  
  
  return (
    <ImgUploadWrapper>
      <ImgWrapper src={ imgSrc }/>
      <input style= {{ display: 'none' }} type= 'file' accept= 'image/*' id= 'files' ref={ inputRef } onChange={ onUploadImage }/>
      <div style= {{ display: 'flex', flexDirection: 'column' }}>
        <LabberWrapper htmlFor= 'files'>변경</LabberWrapper>
        <Title type = 'small' color = 'gray'>
          확장자: png, jpg, jpeg / 용량 1MB 이하
        </Title>
      </div>
    </ImgUploadWrapper>
  );
}

export default ImgUpload