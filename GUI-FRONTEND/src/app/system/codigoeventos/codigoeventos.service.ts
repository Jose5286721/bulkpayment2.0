import {Injectable} from "@angular/core";
import {Http, RequestOptions, URLSearchParams, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {BaseService} from "../../utils/base.service";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";

@Injectable()
export class CodigoeventosService extends BaseService {
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
  }

  editarCodigoEvento(parameterRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post( this.appConfig.getConfig('URL_CODIGO_EVENTOS')+ '/edit',
      JSON.stringify(parameterRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
  getCodigoEventosByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.appConfig.getConfig('URL_CODIGO_EVENTOS') + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
  }

  getCodigoEventosById(parameterRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.appConfig.getConfig('URL_CODIGO_EVENTOS') + '/getById',
      JSON.stringify(parameterRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
}
