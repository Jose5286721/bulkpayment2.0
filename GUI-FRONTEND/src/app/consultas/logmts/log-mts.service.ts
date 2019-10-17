import { Injectable } from '@angular/core';
import {BaseService} from "../../utils/base.service";
import {Headers, Http, RequestOptions, ResponseContentType, URLSearchParams, Response} from "@angular/http";
import {Router} from "@angular/router";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {Observable} from "rxjs";
import {AppConfig} from "../../app.config";

@Injectable()
export class LogMtsService extends BaseService{

  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
  }
  getLogMtsByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.appConfig.getConfig('URL_LOG_MTS') + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }
  getReportPdf(criterios:any,direction: any, properties:any){
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    headers.append('Accept', 'application/pdf');
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    let options = new RequestOptions({headers: headers, responseType: ResponseContentType.Blob});
    options.params =params;
    return this.http.post(this.appConfig.getConfig('URL_REPORTS') + "/logMts/download/pdf",JSON.stringify(criterios),options).map((res: Response) => res).catch(this.handleError.bind(this))
  }

  getReportXlsx(criterios:any,direction: any, properties:any){
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    let options = new RequestOptions({headers: headers, responseType: ResponseContentType.Blob});
    options.params =params;
    return this.http.post(this.appConfig.getConfig('URL_REPORTS') + "/logMts/download/xlsx",JSON.stringify(criterios),options).map((res: Response) => res).catch(this.handleError.bind(this))
  }

  getReportCsv(criterios:any,direction: any, properties:any){
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    let options = new RequestOptions({headers: headers, responseType: ResponseContentType.Blob});
    options.params =params;
    return this.http.post(this.appConfig.getConfig('URL_REPORTS') + "/logMts/download/csv",JSON.stringify(criterios),options).map((res: Response) => res).catch(this.handleError.bind(this))
  }

}
