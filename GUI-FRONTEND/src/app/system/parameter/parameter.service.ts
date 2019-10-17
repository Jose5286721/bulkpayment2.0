import {Injectable} from "@angular/core";
import {Http, RequestOptions, URLSearchParams, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {BaseService} from "../../utils/base.service";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";

@Injectable()
export class ParameterService extends BaseService {
  URL_SYSTEM_PARAMETER: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_SYSTEM_PARAMETER = this.appConfig.getConfig('URL_SYSTEM_PARAMETER');
    console.log(this.URL_SYSTEM_PARAMETER);
  }

  editarSystemParameter(parameterRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post( this.URL_SYSTEM_PARAMETER+ '/edit',
      JSON.stringify(parameterRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
  eliminarSystemParameter(parameterRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post( this.URL_SYSTEM_PARAMETER+ '/delete',
      JSON.stringify(parameterRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
  getSystemParameterByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_SYSTEM_PARAMETER + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
  }

  addSystemParameter(parameterRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post( this.URL_SYSTEM_PARAMETER+ '/add',
      JSON.stringify(parameterRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  // deleteSystemParameter(parameterRequest: any): Observable<any> {
  //   let headers = new Headers({'Content-Type': 'application/json'});
  //   headers.append('Authorization', sessionStorage.getItem('auth_token'));
  //   let options = new RequestOptions({headers: headers});
  //
  //   return this.http.post( URL_SYSTEM_PARAMETER+ '/delete',
  //     JSON.stringify(parameterRequest),
  //     options)
  //     .map(this.extractData.bind(this))
  //     .catch(this.handleError.bind(this));
  // }
  getSystemParameterById(parameterRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_SYSTEM_PARAMETER + '/getById',
      JSON.stringify(parameterRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
}
