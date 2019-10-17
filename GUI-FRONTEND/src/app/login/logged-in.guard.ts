import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import 'rxjs/add/operator/take';
import {Observable} from "rxjs/Observable";
import {RolService} from "../administracion/roles/rol.service";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {PermisosService} from "../security/permiso.service";
import {EmpresaService} from "../administracion/empresa/empresa.service";


@Injectable()
export class LoggedInGuard implements CanActivate {

  constructor(private _autenticacionService: AutenticacionService, private _permisoService: PermisosService,
              private _router: Router, private _rolService: RolService, private _empresaService: EmpresaService) {}

  /**
   * Metodo que chequea a donde debera redirir una url
   * @param {ActivatedRouteSnapshot} route
   * @param {RouterStateSnapshot} state
   * @returns {Observable<boolean>}
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    let url: string = state.url;
    if(url.includes('changePassword'))
      return true;
    return this.checkLogin(url,route);
  }

  /**
   * Metodo que verifica si el autenticacion esta logueado o no, verifica si el autenticacion logueado tiene permisos para ir a una
   * url especifica, en caso de no tener permisos lo redirige a /** que es 'Page not found'.Cada url tiene configurado que permisos
   * debe tener para ser accedido a el en el archivo {@link app.routing}
   * @param {string} url
   * @param {ActivatedRouteSnapshot} route
   * @returns {Observable<boolean>}
   */
  checkLogin(url: string, route: ActivatedRouteSnapshot): boolean {
    let estaLogueado = null;
    let valor;
    let str: String[];
    valor = this._autenticacionService.isLoggedIn
      .take(1)
      .map((isLoggedIn: boolean) => {
        if (!isLoggedIn){
          this._autenticacionService.redirectUrl = url;
          this._router.navigate(['/login']);
          return false;
        }
        return true;
      });
    valor.subscribe(res => {estaLogueado = res});
    if(!estaLogueado){
      return valor;
    }
    this.isSuperCompany();
    str = JSON.parse(this._permisoService.permissions);
    if (str!= null) {
      if (str === undefined || str.length === 0 || ((!str || str.length == 0) && estaLogueado)) {
        this._autenticacionService.logout();
        this._permisoService.permissions = sessionStorage.getItem("permissions");
      }
      let permisosRequeridos = route && route.data["permisos"] && route.data["permisos"].length > 0 ? route.data["permisos"].map(xx => xx) : null;
      if (permisosRequeridos && !(this._permisoService.permissions.search(permisosRequeridos) != -1)) {
        this._router.navigate(['/home']);
        return false;

      } else {
        return true; //Tiene permisos para ir a la url

      }
    }else{
      return false;

    }
  }

  isSuperCompany(){
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
            this._permisoService.superCompany = res.data.body.superCompany;
        } else {
          this._permisoService.superCompany = false;
        }
      },
      error => {
        console.log(error);
        this._permisoService.superCompany = false;
      });
  }

}
