import { Injectable } from '@angular/core';
import {Headers, Http, RequestOptions} from "@angular/http";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {BaseService} from "./base.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {AppConfig} from "../app.config";

@Injectable()
export class UtilService extends BaseService{

  constructor(private _http: Http,_router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
  }

  /**
   * Metodo para obtener la expresion regular de numeros telefonicos
   * @returns {Observable<string[]>}
   */
  getPhoneNumberRegExpr(): Observable<string[]>{
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});
    return this._http.get(this.appConfig.getConfig('URL_UTIL') + '/phoneRegExpr', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  /**
   * Metodo para obtener el mayor tamanho de los archivos CSV a cargar
   * @returns {Observable<string[]>}
   */
  getMaxFileSize(): Observable<any>{
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});
    return this._http.get(this.appConfig.getConfig('URL_UTIL') + '/csvMaxSize', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  /**
   * Retorna el nombre del archivo del response
   * @param res Headers
   */
  getFileNameFromResponse(res: Response){
    const contentDisposition = res.headers.get('content-disposition') || '';
    const matches = /filename=([^;]+)/ig.exec(contentDisposition);
    return (matches[1] || 'untitled').trim();
  }

  static objectContains( obj: any,term: string): string {
    for (let key in obj){
      if(obj[key].value.indexOf(term) != -1) return obj[key].label;
    }
    return null;
  }
}
