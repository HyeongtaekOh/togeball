import styled, { css } from 'styled-components'

const size = {
    'large' : '20px',
    'medium' : '18px',
    'small' : '14px',
} 

const TitleWrapper = styled.p<{ type : string, color : string, bold : boolean }>`
    font-size: ${( props ) => size[ props.type ]};
    color: ${ ( props ) => props.color };
    line-height: 130%;
    white-space: nowrap;
    
    ${(props) =>
        ( props.type !== 'small' || props.bold ) &&
        css`
            font-weight: bold;
        `
    }


`

const Title = ( props : TitleProps ) =>{

    const { 
        type = 'large', color ='black', children, 
        style, bold = false 
    } = props

    return(
        <TitleWrapper 
            type = { type } color = { color } 
            style= { style } bold = { bold }
        >
            { children }
        </TitleWrapper>
    )

}

export default Title;

type TitleProps = {
    type?: 'large' | 'medium' | 'small',
    color?: string,
    children?: Array<string | any> | string
    style?: React.CSSProperties,
    bold?: boolean,
}