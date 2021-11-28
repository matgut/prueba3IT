import { DataState } from "../enum/dataStateEnum";

export interface AppState<T>{
    datatState: DataState;
    appData?: T;
    error?:string;
}