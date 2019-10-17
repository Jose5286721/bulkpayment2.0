import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions, URLSearchParams} from "@angular/http";
import {BaseService} from "../../utils/base.service";
import {Router} from "@angular/router";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {Observable} from "rxjs/Observable";
import 'rxjs/Rx';
import {AppConfig} from "../../app.config";


@Injectable()
export class EmpresaService extends BaseService{

  URL_EMPRESA:string;
    constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_EMPRESA = appConfig.getConfig('URL_EMPRESA');
  }


  crearEmpresa(empresaRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_EMPRESA + '/add',
      JSON.stringify(empresaRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  editarEmpresa(empresaRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_EMPRESA + '/edit',
      JSON.stringify(empresaRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getEmpresasByCriPag(criterios: any, direction:string, properties:string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_EMPRESA + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }
  getEmpresaById(empresaId: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_EMPRESA + '/get/' + empresaId, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllEmpresas(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_EMPRESA + '/get/all', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  isSuperCompany(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_EMPRESA + '/supercompany', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }


}
