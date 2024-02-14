import { useEffect, useState } from 'react';
import useModel from '../store'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styled, { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  .react-datepicker__header {
    background-color: #DEDCEE !important;
  }
  .react-datepicker__navigation-icon react-datepicker__navigation-icon--next {
    border-color: #6A60A9 !important;
  }
  .react-datepicker__year-read-view--down-arrow {
    border-color: #6A60A9 !important;
  }
`

const TitleWrapper = styled.div`
  display: flex;
  text-align: right;
  width: 100px;
  font-weight: bold;
  font-size: 13px;
  margin-top: 30px;
`
const StyledDatePicker = styled(DatePicker)`
  display: flex;
  justify-content: center;
  margin-top: 30px;
  width: 50%;
  background-color : #DEDCEE;
  padding: 10px 0px 10px 30px;
  border-radius: 30px;  
`

const BirthdayPicker = ( props ) => {

  const { selectedDate, setSelectedDate } = useModel();

  useEffect(() =>{
    if( selectedDate==='' ){
      const day = props.mybirth===null ? '' : props.mybirth
      setSelectedDate( day )
    }
},[])

  const handleChange = date => {
    setSelectedDate( date );
  };

  return (
    <div style={{ display: 'flex'}}>
      <TitleWrapper>생년월일</TitleWrapper>
      <GlobalStyle />
      <StyledDatePicker
        selected={ selectedDate }
        onChange={ handleChange }
        dateFormat="yyyy-MM-dd"
        placeholderText="  설정하기"
        showYearDropdown
        scrollableYearDropdown
        yearDropdownItemNumber={100}
      />
    </div>
  );
};

export default BirthdayPicker;