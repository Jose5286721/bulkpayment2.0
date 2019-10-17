import { Injectable } from '@angular/core';
import {Http, RequestOptions, URLSearchParams, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {Observable} from "rxjs/Observable";
import {BaseService} from "../../utils/base.service";
import 'rxjs/Rx';
import {AppConfig} from "../../app.config";

@Injectable()
export class RolService extends BaseService{

  URL_ROL: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_ROL = appConfig.getConfig('URL_ROL');
  }


  crearRol(rolRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.URL_ROL + '/add',
      JSON.stringify(rolRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  editarRol(rolRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.URL_ROL + '/edit',
      JSON.stringify(rolRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));

  }

  getRolByCriPag(criterios: any, direction:string, properties:string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_ROL + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  getRolById(rolId: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_ROL + '/get/' + rolId, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllPermissions(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_ROL + '/all/permissions', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getPermissionsForRolSelected(idRol: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_ROL + '/' + idRol + '/permissions/', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }



}
