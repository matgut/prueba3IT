import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { CustomResponse } from '../interface/customResponse';
import { Survey } from '../interface/survey';

@Injectable({
  providedIn: 'root'
})
export class ServerService {
  
  private readonly apiUrl = 'http://localhost:8080/api/v1';

  constructor(private http: HttpClient) { }

  
  surveys$ = <Observable<CustomResponse>>this.http.get<CustomResponse>(`${this.apiUrl}/survey/list`)
  .pipe(
    tap(console.log),
    catchError(this.handleError)

  );
  //http://localhost:8080/api/v1/survey/create
  create$ = (survey: Survey) => <Observable<CustomResponse>>
  this.http.post<CustomResponse>(`${this.apiUrl}/survey/create`, survey)
  .pipe(
    tap(console.log),
    catchError(this.handleError)

  );

  handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(`a ocurrido un error: ${error.status}`);
  }


}
