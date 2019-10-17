import {Injectable} from '@angular/core';
import {BaseService} from '../../utils/base.service';
import {AutenticacionService} from '../../autenticacion/autenticacion.service';
import {Router} from '@angular/router';
import {Http, RequestOptions, Headers, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {AppConfig} from "../../app.config";

@Injectable()
export class PaymentOrderService extends BaseService {

  URL_PAYMENT_ORDERS: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_PAYMENT_ORDERS = appConfig.getConfig('URL_PAYMENT_ORDERS');
  }

  getPaymentOrdersByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_PAYMENT_ORDERS + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  signPaymentOrder(id: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let params = new URLSearchParams();
    params.set('id', id);
    let options = new RequestOptions({headers: headers});
    options.search = params;
    return this.http.post(this.URL_PAYMENT_ORDERS + '/sign',
      JSON.stringify(null), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  cancelPaymentOrder(body: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.URL_PAYMENT_ORDERS + '/cancel',
      JSON.stringify(body), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  retryPaymentOrder(idPaymentOrder:string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});
    let params = new URLSearchParams();
    params.set('id', idPaymentOrder);
    options.search = params;
    return this.http.post(this.URL_PAYMENT_ORDERS + '/retry',JSON.stringify(null), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

  getPaymentOrderById(paymentOrderId: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_PAYMENT_ORDERS + '/' + paymentOrderId, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
  getInfoCompany(idCompany:number): Observable<any>{
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});
    return this.http.get(this.URL_PAYMENT_ORDERS+'/infoCompany/'+idCompany,options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  /**
   * Cuenta la cantidad de ordenes de pago del usuario logueado, siempre y cuando sea firmante
   */
  countPaymentOrderByState(stateChr: string): Observable<any[]>{
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_PAYMENT_ORDERS + '/count/' + stateChr, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));

  }
}
