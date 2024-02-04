import { postAxios } from 'src/api/util'

interface dataType {
    title: string;
    gameId: number;
    cheeringTeam: string;
    preferSeats: string;
    personnel: number;
    tags: string[];
    describe: string;
}

export const postRecruit = async( data: dataType ) => {
    try{
        return await postAxios('/api/recruit', data)
    }catch( err ){
        console.log(err)
    }
}
