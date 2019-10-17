import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions, URLSearchParams} from "@angular/http";
import {BaseService} from "../../utils/base.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import 'rxjs/Rx';
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";


@Injectable()
export class BorradorService extends BaseService{

  URL_BORRADORES : string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_BORRADORES = appConfig.getConfig('URL_BORRADORES');
  }

  procesarArchivo(file: File, idCompany: string, idWorkflow: string): Observable<any> {
    let headers = new Headers();
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let params = new URLSearchParams();
    params.set('idCompany', idCompany);
    params.set('idWorkflow', idWorkflow);

    let options = new RequestOptions({headers: headers});
    options.params = params;

    let formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post(this.URL_BORRADORES + '/beneficiaries/validfile',formData, options).timeout(3600000)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  crearBorrador(request: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_BORRADORES + '/',
      JSON.stringify(request),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  editarBorrador(iddraftPk: number, request: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_BORRADORES + '/' + iddraftPk,
      JSON.stringify(request),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getDraftByCriPag(criterios: any, direction:string, properties:string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_BORRADORES + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  getBeneficiariesByCriPag(idDraftPk: any, direction:string, properties:string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.get(this.URL_BORRADORES + '/beneficiaries/'+ idDraftPk , options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  // getActualTotalAmount(idDraftPk: any): Observable<any> {
  //   let headers = new Headers({'Content-Type': 'application/json'});
  //   headers.append('Authorization', sessionStorage.getItem('auth_token'));
  //   let options = new RequestOptions({headers: headers});
  //
  //   return this.http.get(this.URL_BORRADORES + '/beneficiaries/totalAmount/'+ idDraftPk , options)
  //     .map(this.extractData.bind(this))
  //     .catch(this.handleError.bind(this))
  // }

  getDraftById(id: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_BORRADORES + '/' + id, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  deleteDraftDetail(iddraftPk: number, idbeneficiaryPk): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.delete(this.URL_BORRADORES + '/details/draft/' + iddraftPk + '/beneficiary/' + idbeneficiaryPk, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }


}
