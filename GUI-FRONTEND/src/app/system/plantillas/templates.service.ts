import { Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions, URLSearchParams, ResponseContentType} from "@angular/http";
import {BaseService} from "../../utils/base.service";
import {Router} from "@angular/router";
import 'rxjs/Rx';
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {AppConfig} from "../../app.config";


@Injectable()
export class TemplatesService extends BaseService{

  URL_EXAMPLE_TEMPLATE: string;
  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService, private appConfig: AppConfig) {
    super(_autenticacionService, _router);
    this.URL_EXAMPLE_TEMPLATE = this.appConfig.getConfig('URL_EXAMPLE_TEMPLATE');
  }

  getCsvExample(fileNameparameter: string){
    let headers = new Headers({'Content-Type': 'application/json'});
    headers.append('Authorization', sessionStorage.getItem('auth_token'));
    let params = new URLSearchParams();
    params.set('fileNameParameter', fileNameparameter);
    let options = new RequestOptions({headers: headers, responseType: ResponseContentType.Blob});
    options.params =params;
    return this.http.get(this.URL_EXAMPLE_TEMPLATE + "/downloadfile",options).map((res : Response) => res)
    .catch(this.handleError.bind(this))
  }

  importExampleCsv(file:File, fileNameParameter: string){
    let headers = new Headers();
    headers.append('Authorization', sessionStorage.getItem('auth_token'));

    let params = new URLSearchParams();
    params.set('fileNameParameter', fileNameParameter);

    let options = new RequestOptions({headers:headers, params: params});

    let formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post(this.URL_EXAMPLE_TEMPLATE+ '/uploadfile',formData,options).
    map(this.extractData.bind(this)).
      catch(this.handleError.bind(this));
  }

}
