import { Injectable } from '@angular/core';
import { Http, Headers, Response , RequestOptions,URLSearchParams} from '@angular/http';
import { Usuario } from "../model/usuario";
import {Observable} from "rxjs";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Router} from "@angular/router";
import {EmpresaShortDto} from "../dto/empresa-dto/empresa-short.dto";
import {SERVER_ERROR, SIN_CONEXION_INTERNET} from "../utils/gop-constantes";
import {AppConfig} from "../app.config";

@Injectable()
export class AutenticacionService {

  private _loggedIn = new BehaviorSubject<boolean>(false);
  private _isSelectedCompany = new BehaviorSubject<boolean>(false);
  private token: string;
  public redirectUrl;
  private usuario: Usuario;
  private username: string;
  private _isSuperUser: boolean;
  private _selectedCompany: EmpresaShortDto = {idcompanyPk:0,companynameChr:""};
  private _companies : EmpresaShortDto[] = [];

  constructor(private http: Http, private _router: Router, private appConfig: AppConfig) {
    this._loggedIn.next(!!sessionStorage.getItem('auth_token'));
    this._isSelectedCompany .next(!!sessionStorage.getItem('currentCompany'));
    if ( sessionStorage.getItem('currentUser')) {
      this.username = JSON.parse(sessionStorage.getItem('currentUser')).username;
    }
    if ( sessionStorage.getItem('currentCompany')) {
      this._selectedCompany = JSON.parse(sessionStorage.getItem('currentCompany')).currentCompany;
    }
    if ( sessionStorage.getItem('isSuperUser')) {
      this._isSuperUser = JSON.parse(sessionStorage.getItem('isSuperUser'));
    }
    if ( sessionStorage.getItem('companies')) {
      this._companies = JSON.parse(sessionStorage.getItem('companies')).companies;
    }
  }

  login ( username: any, password: any, inputCaptchaValue: any, captchaEncode:any): Observable<any> {
    const headers = new Headers({ 'Content-Type': 'application/json'});
    const options = new RequestOptions({ headers: headers });

    return this.http.post(this.appConfig.getConfig('URL_API_AUTH'),
      JSON.stringify({ clientId: username, key: password, captchaEncode:captchaEncode, userInputCaptchaValue: inputCaptchaValue }),
      options)
      .map(res => res.json())
      .map((res) => {
        if (res && res.token) {
          this.token = res.token;
          this.username = username;
          this._loggedIn.next(true);
          sessionStorage.setItem('auth_token', res.token);
          sessionStorage.setItem('permissions', JSON.stringify({permissions: res.permissions}))
          if(res.newUser){
            this._isSelectedCompany.next(false);
          }else{
            this.companies = res.companies;
            this.isSuperUser = res.superUser;
            sessionStorage.setItem("companies",JSON.stringify({companies:res.companies}));
            sessionStorage.setItem("isSuperUser",res.superUser);
            sessionStorage.setItem('currentUser', JSON.stringify({ username: username, token: res.token}));
          }
        }
        return res;
      });
  }
  send(data: Object): Observable<any> {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers:headers});
    return this.http.post(
      'https://your-app-backend-hostname.your-domain.com/your-app-backend-path',
      data, options);
  }


  forgotPassword(email:string){
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers:headers});
    let params = new URLSearchParams();
    params.set('email', email);
    options.params = params;
    return this.http.get(this.appConfig.getConfig('URL_API_FORGOT'),options).map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }
  validateToken(token:string){
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers:headers});
    let params = new URLSearchParams();
    params.set('token', token);
    options.params = params;
    return this.http.get(this.appConfig.getConfig('URL_API_VALIDATE_TOKEN'),options).map((this.extractData.bind(this)))
      .catch(this.handleError.bind(this))
  }

  getTokenByCompany(selectedCompany: EmpresaShortDto, pinSms:string): Observable<any>{
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});
    let params = new URLSearchParams();
    params.set('id', selectedCompany.idcompanyPk.toString());
    params.set('pin',pinSms);
    options.params = params;
    return this.http.get(this.appConfig.getConfig('URL_TOKEN_COMPANY'),options)
      .map(res => res.json())
      .map((res) => {
        if (res.token) {
          this.token = res.token;
          sessionStorage.setItem("currentCompany",JSON.stringify( {currentCompany:selectedCompany}));
          this.selectedCompany = selectedCompany;
          sessionStorage.setItem('auth_token', res.token);
          sessionStorage.setItem('currentUser', JSON.stringify({ username: this.username, token: res.token}));
        }
        return res;
      })
  }
  get selectedCompany(){
    return this._selectedCompany;
  }
  set selectedCompany(company: EmpresaShortDto){
    this._selectedCompany = company;
  }
  get companies(){
    return this._companies;
  }

  set companies(companies: EmpresaShortDto[]){
    this._companies = companies;
  }

  get isSuperUser(){
    return this._isSuperUser;
  }

  set isSuperUser(isSuperUser: boolean){
    this._isSuperUser = isSuperUser;
  }

  logout() {
    this._loggedIn.next(false);
    this._isSelectedCompany.next(false);
    sessionStorage.removeItem('auth_token');
    sessionStorage.removeItem('currentUser');
    sessionStorage.clear();
    setTimeout(() => this._router.navigate(['/login']),1);
  }

  get isLoggedIn() {
    return this._loggedIn.asObservable();
  }

  get isSelectedCompany(){
    return this._isSelectedCompany.asObservable();
  }


  getUserLogged(): Usuario {
    return this.usuario;
  }
  setIsSelectedCompany(ban : boolean){
    this._isSelectedCompany.next(ban);
  }

  getUsernameLogged(): string{
    return this.username;
  }


  loggedInFalse() {
    this._loggedIn.next(false);
  }

  private extractData(res: any) {
    let token = res.headers.get('Authorization');
    if(token){
      sessionStorage.setItem('auth_token', token);
    }
    const body = res.json();
    return body;
  }

  handleError(error: any) {
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    if(error.status === 0){
      console.error(errMsg);
      errMsg = SIN_CONEXION_INTERNET;
    }else if(error.status === 403){
      console.error(errMsg);
    }else{
      console.error(errMsg);
      errMsg = SERVER_ERROR;
    }

    return Observable.throw(errMsg);
  }
}
