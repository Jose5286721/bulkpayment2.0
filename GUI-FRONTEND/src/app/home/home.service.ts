import {Injectable} from '@angular/core';
import {BaseService} from "../utils/base.service";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {Router} from '@angular/router';
import {Http, RequestOptions, Headers} from '@angular/http';
import {SaldoCuentaRequestDto} from "../dto/saldo-cuenta-dto/saldocuenta-request";
import {AppConfig} from "../app.config";

@Injectable()
export class HomeService extends BaseService {
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
  }
  getSaldoActual(request: SaldoCuentaRequestDto){
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let options = new RequestOptions({headers: headers});
    return this.http.post(this.appConfig.getConfig('URL_HOME') + '/getSaldo',request,options)
      .map(this.extractData.bind(this))
      .catch(this.handleError.bind(this))
  }

}
