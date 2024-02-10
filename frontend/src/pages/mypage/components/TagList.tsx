import { Tag } from 'src/components'
import styled from 'styled-components'

const TagListWrapper = styled.div`
    display: flex;
    padding-left: 120px;
    flex-wrap: wrap;
    width: 100%;
`

const TagList = (( props ) => {

    const { tags } = props

    return (
        <TagListWrapper>
            { tags?.map(( tag, index ) => (
                <Tag key={ index } height='10px'>{ tag }</Tag>  
            ))}
        </TagListWrapper>
    )
} )

export default TagList