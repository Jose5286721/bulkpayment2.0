import {Injectable} from '@angular/core';
import {BaseService} from '../../utils/base.service';
import {AutenticacionService} from '../../autenticacion/autenticacion.service';
import {Router} from '@angular/router';
import {Http, RequestOptions, Headers, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {AppConfig} from "../../app.config";

@Injectable()
export class BeneficiariosService extends BaseService {
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
  }

  getBeneficiariosByCriPag(criterios: any, direction: string, properties: string, page: string, size: string): Observable<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let params = new URLSearchParams();
    params.set('direction', direction);
    params.set('properties', properties);
    params.set('page', page);
    params.set('size', size);
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let options = new RequestOptions({headers: headers});
    options.search = params;

    return this.http.post(this.appConfig.getConfig('URL_BENEFICIARIOS') + '/list',
      JSON.stringify(criterios), options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }
}
