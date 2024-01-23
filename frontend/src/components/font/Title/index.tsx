import styled, { css } from 'styled-components'

const size = {
    'large' : '25px',
    'medium' : '23px',
    'small' : '15px'
} 

const TitleWrapper = styled.p<{ type : string, color : string }>`
    font-size: ${( props ) => size[ props.type ]};
    color: ${ ( props ) => props.color };
    line-height: 130%;
    white-space: nowrap;
    
    ${(props) =>
        props.type !== 'small' &&
        css`
            font-weight: bold;
        `
    }


`

const Title = ( props : TitleProps ) =>{

    const { type = 'large', color ='black', children, style } = props

    return(
        <TitleWrapper type = { type } color = { color } style= { style }>
            { children }
        </TitleWrapper>
    )

}

export default Title;

type TitleProps = {
    type?: 'large' | 'medium' | 'small',
    color?: string,
    children?: Array<string | any> | string
    style?: React.CSSProperties
}