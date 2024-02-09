import { Title } from 'src/components'
import { getImgPath } from 'src/api'
import { useCallback, useRef, useState } from 'react'
import lufi from 'src/asset/images/lufi.jpg'
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
  const [ imgSrc, setImgSrc ] = useState(lufi)
  const { setImage } = useModel()

  const onUploadImage = useCallback( async( e: React.ChangeEvent<HTMLInputElement> ) => {
    if ( !e.target.files || e.target.files.length ===0 ) return

    const file = e.target.files[0];
    try {
      const presignedUrl = await getImgPath() // S3에 이미지를 업로드할 URL을 가져옴
      console.log(presignedUrl)
      await fetch(presignedUrl, {
        method: 'PUT',
        headers: {
          'Content-Type': file.type
        },
        body: file
      });
      setImgSrc(URL.createObjectURL(file)) // 로컬에 업로드한 이미지 표시
      setImage(presignedUrl) // 이미지 URL 저장
    } catch (error) {
      console.error('Error uploading image:', error);
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