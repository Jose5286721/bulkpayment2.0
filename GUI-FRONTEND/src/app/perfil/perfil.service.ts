import { Injectable } from '@angular/core';
import { BaseService } from "../utils/base.service";
import { Headers, Http, RequestOptions } from "@angular/http";
import { Router } from "@angular/router";
import { AutenticacionService } from "../autenticacion/autenticacion.service";
import { Observable } from "rxjs/Observable";
import {Perfil} from "../model/Perfil";
import {AppConfig} from "../app.config";

@Injectable()
export class PerfilService extends BaseService{

  URL_PERFIL: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_PERFIL = this.appConfig.getConfig('URL_PERFIL');
  }

  changePassword(userRequest:any): Observable<any>{
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_PERFIL + '/password/change',
      JSON.stringify(userRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));

  }

  viewBasicProfile(): Observable<any>{
    let header = new Headers({'Content-Type': 'application/json'});
    header.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: header});
    return this.http.get(this.URL_PERFIL + "/view", options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));

  }

  editBasicProfile(perfilReq: Perfil): Observable<any>{
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_PERFIL + '/edit',
      JSON.stringify(perfilReq),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));

  }

}
