// export interface TagType  {
//   id?: number;
//   content?: string;
//   value?: number;
//   isSelect?: boolean
// }

export interface TagType {
  id?: number,
  content?: string,
  type?: string
  isSelect?: boolean
}

export type TagApiType = {
  content?: contentItem[],
  totalElements?: number,
}