import { Injectable } from '@angular/core';
import {BaseService} from "../../utils/base.service";
import {Headers, Http, RequestOptions, ResponseContentType, URLSearchParams, Response} from "@angular/http";
import {Router} from "@angular/router";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {Observable} from "rxjs";
import {AppConfig} from "../../app.config";

@Injectable()
export class ComprobantePagoService extends BaseService{

  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
  }
  getComprobantePagoByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('paymentOrderId',criterios.paymentOrderId);
    params.set('beneficiaryCI',criterios.beneficiaryCI);
    params.set('phonenumberChr',criterios.phonenumberChr);
    params.set('sinceDate',criterios.sinceDate);
    params.set('toDate',criterios.toDate);
    params.set('companyId',criterios.companyId);
    params.set('direction', direction);
    params.set('orderBy', properties);
    params.set('page', page);
    params.set('linesPerPage', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.params = params;

    return this.http.get(this.appConfig.getConfig('URL_PAYMENT_VOUCHER') + '/list',
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  getReportPdf(criterios: any, direction: string, properties: string){
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    headers.append('Accept', 'application/pdf');
    let params = new URLSearchParams();
    params.set('paymentOrderId',criterios.paymentOrderId);
    params.set('beneficiaryCI',criterios.beneficiaryCI);
    params.set('phonenumberChr',criterios.phonenumberChr);
    params.set('companyId',criterios.companyId);
    params.set('sinceDate',criterios.sinceDate);
    params.set('toDate',criterios.toDate);
    params.set('direction', direction);
    params.set('orderBy', properties);
    let options = new RequestOptions({headers: headers, responseType: ResponseContentType.Blob});
    options.params =params;
    return this.http.get(this.appConfig.getConfig('URL_REPORTS')+'/paymentVoucher/download/pdf',options).map((res: Response) => res).catch(this.handleError.bind(this))
  }

}
