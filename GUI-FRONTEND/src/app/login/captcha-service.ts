import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Http, RequestOptions, Headers, URLSearchParams, ResponseContentType, Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {BaseService} from "../utils/base.service";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {AppConfig} from "../app.config";

@Injectable()
export class CaptchaService extends BaseService {

  URL_CAPTCHA: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_CAPTCHA = appConfig.getConfig('URL_CAPTCHA');
  }

  getImage(): Observable<Response> {
    return this.http
      .get(this.URL_CAPTCHA, { responseType: ResponseContentType.Blob })
      .map((res: Response) => res).catch(this.handleError.bind(this))
  }





}
