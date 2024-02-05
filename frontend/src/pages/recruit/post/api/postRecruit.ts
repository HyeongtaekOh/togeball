import { postAxios } from 'src/api/util'

interface dataType {
    gameId: number;
    userId: number;
    title: string;
    description: string;
    capacity: number;
    cheeringTeam: string;
    tags: string[];
    preferSeats: string;
}

export const postRecruit = async( data: dataType ) => {
    try{
        return await postAxios('/chatrooms', data)
    }catch( err ){
        console.log(err)
    }
}
