import { Tag } from 'src/components'

const TagList = ( () => {

    const user = { id: 'test01', myTeam : 'LG 트윈스', stadium : '고척 돔', nickName: '플레권',
                 tag: ['#Estp', '#응원가형', '#시즌권보유', '#자차보유', '#플레플레','#챌린저'], logo: ''}

    return (
        <div style={{ display: 'flex', paddingLeft: '120px', flexWrap: 'wrap', width: '100%' }}>
            { user.tag.length > 0 ? (
                user.tag.map(( tag, index ) => (
                    <Tag key={ index } height='10px'>{ tag }</Tag>  
                ))) 
            : null }
        </div>
    )
} )

export default TagList