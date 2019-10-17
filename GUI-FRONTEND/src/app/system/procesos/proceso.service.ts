import {Injectable} from "@angular/core";
import {Http, RequestOptions, URLSearchParams, Headers} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {BaseService} from "../../utils/base.service";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";

@Injectable()
export class ProcesoService extends BaseService {
  URL_PROCESO: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_PROCESO = this.appConfig.getConfig('URL_PROCESO');
  }

  getProcessById(processId: number): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_PROCESO + '/get/' + processId, options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getProcessByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.URL_PROCESO + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
  }

  editarProceso(procesoRequest: any): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});

    return this.http.post(this.URL_PROCESO + '/edit',
      JSON.stringify(procesoRequest),
      options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }

  getAllProcesos(): Observable<any[]> {
    const headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    const options = new RequestOptions({headers: headers});

    return this.http.get(this.URL_PROCESO + '/get/all', options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this));
  }
}
