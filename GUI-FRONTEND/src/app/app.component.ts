import {Component, OnInit,ViewChild, ViewEncapsulation} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {AutenticacionService} from "./autenticacion/autenticacion.service";
import {MatMenuTrigger} from "@angular/material";
import {PermisosService} from "./security/permiso.service";
import {MatIconRegistry} from "@angular/material";
import {DomSanitizer} from "@angular/platform-browser";
import {EmpresaService} from "./administracion/empresa/empresa.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None,
  preserveWhitespaces: false,

})
export class AppComponent implements OnInit {
  screenWidth: number;
  isLoggedIn$: Observable<boolean>;
  isSelectedCompany$ : Observable<boolean>;
  permissions : string;
  @ViewChild('MT_ADMINISTRACION') triggerAdministracion: MatMenuTrigger;
  @ViewChild('MT_ORDEN_PAGO') triggerOrdenPago: MatMenuTrigger;
  @ViewChild('MT_SISTEMA') triggerSistema: MatMenuTrigger;
  @ViewChild('drawer') sidenav: any;

  constructor(private _autenticacionService: AutenticacionService, private _permisoService: PermisosService,private matIconRegistry: MatIconRegistry, private domSanitizer: DomSanitizer, private _empresaService : EmpresaService) {
    this.matIconRegistry.addSvgIcon('csv',this.domSanitizer.bypassSecurityTrustResourceUrl("assets/images/icons/icon-csv.svg"));
    this.matIconRegistry.addSvgIcon('excel',this.domSanitizer.bypassSecurityTrustResourceUrl("assets/images/icons/icon-excel.svg"));
    this.matIconRegistry.addSvgIcon('pdf',this.domSanitizer.bypassSecurityTrustResourceUrl("assets/images/icons/icon-pdf.svg"));
  }

  ngOnInit(): void {
    this.isSuperCompany();
    this.isSelectedCompany$ = this._autenticacionService.isSelectedCompany;
    this.isLoggedIn$ = this._autenticacionService.isLoggedIn;
    this._permisoService.permissions = sessionStorage.getItem("permissions");
    this.screenWidth = window.innerWidth;
    window.onresize = () => {
      this.screenWidth = window.innerWidth;
    };
  }
  onActivate() {
    window.scroll(0,0);
    const mainDiv = document.getElementById('drawer-container');
    mainDiv.scroll(0,0);
  }
  toggleSidevar()
  {
     if(!this.isResponsive()){
      this.sidenav.toggle();

     }
  }
  toggleSidevarButton()
  {
      this.sidenav.toggle();
  }

  isResponsive():boolean{
    return (this.screenWidth > 960);

  }

  openMenuAdministracion() {
    this.triggerAdministracion.openMenu();
  }

  closeMenuAdministracion() {
    this.triggerAdministracion.closeMenu();
  }

  openMenuOrdenPago() {
    this.triggerOrdenPago.openMenu();
  }

  closeMenuOrdenPago() {
    this.triggerOrdenPago.closeMenu();
  }

  openMenuSistema() {
    this.triggerSistema.openMenu();
  }

  closeMenuSistema() {
    this.triggerSistema.closeMenu();
  }


  consultarEmpresaPerm(): boolean {
    return this._permisoService.consultarEmpresa;
  }
  consultarBeneficiario(): boolean {
    return this._permisoService.consultarBeneficiario;
  }

  generarReportesPerm():boolean{
    return this._permisoService.generarReporte;
  }

  consultarRolPerm(): boolean {
    return this._permisoService.consultarRol;
  }

  consultarUsuarioPerm(): boolean {
    return this._permisoService.consultarUsuario;
  }

  consutarWorkflowPerm(): boolean {
    return this._permisoService.consutarWorkflow;
  }

  consutarTipoOrdenPagoPerm(): boolean {
    return this._permisoService.consultarTipoOrdenPago;
  }

  consultarProceso(): boolean {
    return this._permisoService.consultarProceso;
  }
  consultarSystemParameter():boolean{
    return this._permisoService.consultarSystemParameter;
  }
  consultarCodigoEvento():boolean{
  return this._permisoService.consultarCodigoEvento;
  }
  consultarOrdenDePago():boolean{
    return this._permisoService.consultarOrdenDePago;
  }

  verMenuAdministracion(): boolean {
    return this.consultarEmpresaPerm() || this.consultarRolPerm() || this.consultarUsuarioPerm() || this.isSuperCompany();
  }

  verMenuOrdenPago(): boolean {
    return this.consutarTipoOrdenPagoPerm() || this.consutarWorkflowPerm() || this.modificarBorradoresPerm()
      || this.consutarBorradoresPerm() || this.consultarOrdenDePago() || this.consultarBeneficiario();
  }

  verMenuSistema(): boolean {
    return this.consultarSystemParameter() || this.consultarProceso() || this.consultarCodigoEvento() || this.modificarEjemploPlantillaPerm()
  }

  verMenuConsultas():boolean {
    return this.consultarGenOrdenPagoPerm() || this.consultarRegistroPagoPerm() || this.consultarRegistroNotifPerm()
      || this.consultarRegistroMtsPerm() || this.consultarRegistroSesionPerm() || this.consultarRegistroMsjReceivedPerm()
      || this.consultarComprobantePagosPerm();
  }

  consutarBorradoresPerm(): boolean {
    return this._permisoService.consultarBorradores;
  }

  modificarBorradoresPerm(): boolean{
    return this._permisoService.modificarBorrador;
  }
  consultarGenOrdenPagoPerm() : boolean{
    return this._permisoService.consultarGenOrdenPago;
  }
  consultarRegistroPagoPerm() : boolean{
    return this._permisoService.consultarRegistroPago;
  }

  consultarRegistroNotifPerm() : boolean{
    return this._permisoService.consultarRegistroNotificaciones;
  }

  consultarRegistroMtsPerm() : boolean{
    return this._permisoService.consultarRegistroMts;
  }

  consultarRegistroSesionPerm() : boolean{
    return this._permisoService.consultarRegistroSesion;
  }

  consultarRegistroMsjReceivedPerm() : boolean{
    return this._permisoService.consultarRegistroMsjReceived;
  }
  consultarComprobantePagosPerm() : boolean{
    return this._permisoService.consultarComprobantePagos;
  }
  modificarEjemploPlantillaPerm() : boolean{
    return this._permisoService.modificarEjemploPlantilla;
  }
  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
        }
      },
      error => {
        console.log(error);
        this._permisoService.superCompany = false;
      });
  }
  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }


  getPermissions() {
    this.permissions = sessionStorage.getItem('permissions');
  }
}
