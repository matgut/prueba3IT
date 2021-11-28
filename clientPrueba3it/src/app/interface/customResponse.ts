import { Survey } from "./survey";


export interface CustomResponse{
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    data: { Surveys: Survey[], Survey?: Survey};
}