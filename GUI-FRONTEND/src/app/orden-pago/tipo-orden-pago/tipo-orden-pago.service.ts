import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions, URLSearchParams} from "@angular/http";
import {BaseService} from "../../utils/base.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import 'rxjs/Rx';
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";


@Injectable()
export class TipoOrdenPagoService extends BaseService{

  URL_TIPO_ORDEN_PAGO: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_TIPO_ORDEN_PAGO = appConfig.getConfig('URL_TIPO_ORDEN_PAGO');
  }


  crearTipoOrdenPago(topRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_TIPO_ORDEN_PAGO + '/add',
      JSON.stringify(topRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  editarTipoOrdenPago(topRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_TIPO_ORDEN_PAGO + '/edit',
      JSON.stringify(topRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getTipoOrdenPagoByCriPag(criterios: any, direction:string, properties:string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_TIPO_ORDEN_PAGO + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  getTipoOrdenPagoById(topId: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_TIPO_ORDEN_PAGO + '/get/' + topId, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllTipoOrdenPago(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_TIPO_ORDEN_PAGO + '/get/all', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }


}
