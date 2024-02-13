import { useEffect, useState } from 'react';
import useModel from '../store'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import styled from 'styled-components';

const TitleWrapper = styled.div`
  display: flex;
  text-align: right;
  width: 100px;
  font-weight: bold;
  font-size: 13px;
  margin-top: 30px;
  margin-right: 12px;
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
      <DatePicker
        selected={selectedDate}
        onChange={handleChange}
        dateFormat="yyyy-MM-dd"
        placeholderText="생년월일을 선택하세요"
        showYearDropdown
        scrollableYearDropdown
        yearDropdownItemNumber={100}
      />
    </div>
  );
};

export default BirthdayPicker;