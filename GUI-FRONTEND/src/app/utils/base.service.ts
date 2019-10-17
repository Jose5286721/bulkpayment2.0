import {Injectable} from "@angular/core";
import {SERVER_ERROR, SIN_CONEXION_INTERNET} from "./gop-constantes";
import {Observable} from "rxjs/Observable";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {Router} from "@angular/router";

@Injectable()
export class BaseService {

  constructor(private _autenticacionService: AutenticacionService, private _router: Router) {
  }


  updateTokenResponse(res: Response) {
    let token = res.headers.get('Authorization');
    if (token) sessionStorage.setItem('auth_token', token);
  }
  extractData(res: Response) {
    this.updateTokenResponse(res);
    const body = res.json();
    return body;
  }

  handleError(error: any) {
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    if(error.status === 0){
      console.error(errMsg);
      errMsg = SIN_CONEXION_INTERNET;
    }else if(error.status === 401){
      this.isNoAutorizado();
    }else if(error.status === 403){
      console.error(errMsg);
    }else{
      console.error(errMsg);
      errMsg = SERVER_ERROR;
    }

    return Observable.throw(errMsg);
  }

  isNoAutorizado(){
    this._autenticacionService.loggedInFalse();
    sessionStorage.removeItem('auth_token');
    sessionStorage.removeItem('currentUser');
    sessionStorage.clear();
    setTimeout(() => this._router.navigate(['/login']),0);
  }
}
