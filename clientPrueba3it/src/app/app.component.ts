import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { DataState } from './enum/dataStateEnum';
import { AppState } from './interface/appState';
import { CustomResponse } from './interface/customResponse';
import { Survey } from './interface/survey';
import { ServerService } from './service/server.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

//implementar onINit para ejecutar codigo al instante
export class AppComponent implements OnInit{
  
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = DataState;
  public show:Boolean = false;
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();

  constructor(private serverService: ServerService){}

  toggle():void{
    this.show = !this.show
  }
  
  ngOnInit(): void {
    this.appState$ = this.serverService.surveys$
    .pipe(
      map(response => {
          this.dataSubject.next(response);
          return {
            datatState: DataState.LOADED, 
            appData: response
          } 
      }),
      startWith({ datatState: DataState.LOADING }),
      catchError((error: string) => {
        return of({
          datatState: DataState.ERROR, 
          error : error
          })
      })
    );


  }

  onSubmit(surveyForm: NgForm): void{
    console.log(surveyForm.value);
    this.isLoading.next(true);
    this.appState$ = this.serverService.create$(surveyForm.value as Survey)
    .pipe(
      map(response => {
        //actualizamos la data a mostar con el nuevo registro insertado
        console.log(response);
        this.dataSubject.next(
          {...response, data: { Surveys: [response.data.Survey, ...this.dataSubject.value.data.Surveys] } }
        );
        this.isLoading.next(false);
        if(response.statusCode === 201){
          Swal.fire('OK','La encuesta se ha guardado exitosamente!', 'success');
        }
        surveyForm.resetForm();
        return {
          datatState: DataState.LOADED, 
          appData: this.dataSubject.value
        } 
    }),
    startWith({ datatState: DataState.LOADING, appData:  this.dataSubject.value}),
    catchError((error: string) => {
        this.isLoading.next(false);
        console.log(error);
        return of({
          datatState: DataState.ERROR, 
          error : error
          })
      })
    );

  }


  

}
