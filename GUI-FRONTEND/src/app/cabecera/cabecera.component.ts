import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {ObservableMedia} from "@angular/flex-layout";
import {CompanyDialogComponent} from "../login/company-dialog/company-dialog.component";
import {MatDialog} from "@angular/material";
import {Router} from "@angular/router";
import {PermisosService} from "../security/permiso.service";
import {isUndefined} from "util";

@Component({
  selector: 'cabecera',
  templateUrl: './cabecera.component.html',
  styleUrls: ['./cabecera.component.css']
})
export class CabeceraComponent implements OnInit{

  isLoggedIn$: Observable<boolean>;
  isSelectedCompany$ : Observable<boolean>;
  screenWidth: number;

  @Output() sidenavOpen : EventEmitter<boolean> = new EventEmitter();

  constructor (private _authService: AutenticacionService, private _media: ObservableMedia,
               public companyDialog: MatDialog, private _router: Router, private _permisoService: PermisosService) {
    // set screenWidth on page load
    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      // set screenWidth on screen size change
      this.screenWidth = window.innerWidth;
    };
  }
  see(){
    return this._media.isActive('gt-xs');
  }
  ngOnInit(): void {
    this.isLoggedIn$ = this._authService.isLoggedIn;
    this.isSelectedCompany$ = this._authService.isSelectedCompany;
  }
  toggle(){
    this.sidenavOpen.emit();
  }
  iconProfile = [
    {text: 'Mi Perfil', icon: 'perm_identity', route: '/home'},
    {text: 'Cerrar sesión', icon: 'exit_to_app', route:'/login'}
  ];

  /**
   * Metodo que obtiene el username del autenticacion logueado
   * @returns {string}
   */
  obtenerUsername(): string{
    return this._authService.getUsernameLogged();
  }
  obtenerCompany():string{

    return this._authService.selectedCompany.companynameChr;
  }

  /**
   * Metodo para cerrar sesion
   */
  onLogout(){
    this._authService.logout();
  }
  canChangeCompany(){
    if(this._authService.companies==null )
      return true;
    return (this._authService.companies.length ==1 || this._permisoService.superCompany);
  }


  changeCompany(): void {
    const dialogRef = this.companyDialog.open(CompanyDialogComponent, {
      width: '250px',
      disableClose:false,
      data: {companies: this._authService.companies, sendSms:false }
    });
    dialogRef.afterClosed().subscribe(result => {
      if(!isUndefined(result)) {
        if (result) {
          this._router.navigate(['/home']).catch(reason => console.log(reason));
        } else if (!result) {
          console.log("ERROR AL OBTENER TOKEN");
        }
      }
    });
  }

  /**
   * Metodo para ir a la parte superior de la pantalla
   */
  toTop(){
    let ele = document.getElementsByClassName('mat-sidenav-content');
    let eleArray = <Element[]>Array.prototype.slice.call(ele);
    eleArray.map( val => {
      val.scrollTop = document.documentElement.scrollTop = 0;
    });
  }

}
