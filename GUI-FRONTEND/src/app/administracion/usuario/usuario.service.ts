import { Injectable } from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {BaseService} from "../../utils/base.service";
import 'rxjs/Rx';
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";

@Injectable()
export class UsuarioService extends BaseService{

  URL_USUARIO: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_USUARIO = appConfig.getConfig('URL_USUARIO');
  }

  crearUsuario(usuarioRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_USUARIO + '/add',
      JSON.stringify(usuarioRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  editarUsuario(usuarioRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_USUARIO + '/edit',
      JSON.stringify(usuarioRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getUsuarioByCriPag(criterios: any, direction:string, properties:string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_USUARIO + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  getUsuarioById(usuarioId: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_USUARIO + '/get/' + usuarioId, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllUsuarios(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_USUARIO + '/all/autenticacion', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllUsuariosByCompany(companyId: number): Observable<any[]> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('idCompany', companyId ? companyId.toString() : null);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.get(this.URL_USUARIO + '/all', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllFirmantes(companyId?:number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    let params =new URLSearchParams();
    params.set('companyId', companyId ? companyId.toString() : null);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.get(this.URL_USUARIO + '/usuariosFirmantes' , options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
  changeUserPassword(request:any, token:string){
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', token);
    const options = new RequestOptions({headers: headers});
    return this.http.post(this.URL_USUARIO + '/changePassword',JSON.stringify(request),options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  isSuperUser(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_USUARIO + '/superUser', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
}
