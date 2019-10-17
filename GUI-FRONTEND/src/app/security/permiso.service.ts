import {Injectable} from '@angular/core';
import {BaseService} from "../utils/base.service";
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {PermisoList} from "./permisoList";


@Injectable()
export class PermisosService extends BaseService {
  private _permisos: String[];
  private _superCompany: boolean;
  private _superUser: boolean;
  private _canviewcompanycredit: boolean;
  private _permissions: string;
  //PERMISOS DE ACCIONES
  readonly CONSULTAR_EMPRESA: string = PermisoList.CONSULTAR_EMPRESA;
  readonly CONSULTAR_BENEFICIARIO: string = PermisoList.CONSULTAR_BENEFICIARIO;
  readonly CONSULTAR_ROL: string = PermisoList.CONSULTAR_ROL;
  readonly CONSULTAR_USUARIO: string = PermisoList.CONSULTAR_USUARIO;
  readonly CONSULTAR_WORKFLOW: string = PermisoList.CONSULTAR_WORKFLOW;
  readonly CONSULTAR_ORDEN_DE_PAGO: string = PermisoList.CONSULTAR_ORDEN_DE_PAGO;
  readonly CONSULTAR_TIPO_ORDEN_PAGO: string = PermisoList.CONSULTAR_TIPO_ORDEN_DE_PAGO;
  readonly CONSULTAR_PROCESO: string= PermisoList.CONSULTAR_PROCESO;
  readonly CONSULTAR_SYSTEMPARAMETER: string= PermisoList.CONSULTAR_SYSTEMPARAMETER;
  readonly CONSULTAR_CODIGO_EVENTOS: string=PermisoList.CONSULTAR_COD_EVENTO;
  readonly MODIFICAR_ROL: string = PermisoList.MODIFICAR_ROL;
  readonly MODIFICAR_USER: string = PermisoList.MODIFICAR_USUARIO;
  readonly MODIFICAR_EMPRESA: string = PermisoList.MODIFICAR_EMPRESA;
  readonly MODIFICAR_WORKFLOW: string = PermisoList.MODIFICAR_WORKFLOW;
  readonly MODIFICAR_TIPO_ORDEN_DE_PAGO: string = PermisoList.MODIFICAR_TIPO_ORDEN_DE_PAGO;
  readonly CONSULTAR_BORRADOR: string = PermisoList.CONSULTAR_BORRADOR;
  readonly MODIFICAR_ORDEN_DE_PAGO: string=PermisoList.MODIFICAR_ORDEN_DE_PAGO;
  readonly MODIFICAR_BORRADOR:string= PermisoList.MODIFICAR_BORRADOR;
  readonly MODIFICAR_COD_EVENTO: string=PermisoList.MODIFICAR_TIPO_ORDEN_DE_PAGO;
  readonly MODIFICAR_PROCESO: string=PermisoList.MODIFICAR_PROCESO;
  readonly MODIFICAR_PARAMETER: string= PermisoList.MODIFICAR_SYSTEMPARAMETER;
  readonly GENERAR_REPORTE : string = PermisoList.GENERAR_REPORTE;
  //PERMISOS PARA VER MENUS
  readonly VER_MENU_ADMINISTRACION: string = PermisoList.MENU_ADMINISTRACION;
  readonly VER_MENU_SISTEMA: string = PermisoList.MENU_SISTEMA;
  readonly VER_MENU_ORDEN_PAGO: string = PermisoList.MENU_ORDEN_PAGO;
  readonly VER_MENU_CONSULTA: string = PermisoList.MENU_CONSULTA;
  readonly VER_SALDO_CUENTA : string = PermisoList.VER_SALDO_CUENTA;
  readonly CONSULTAR_REGISTRO_DE_GEN_OP : string = PermisoList.CONSULTAR_REGISTRO_DE_GEN_OP;
  readonly CONSULTAR_REGISTRO_DE_PAGO : string = PermisoList.CONSULTAR_REGISTRO_DE_PAGO;
  readonly CONSULTAR_NOTIFICACIONES : string = PermisoList.CONSULTAR_NOTIFICACIONES;
  readonly CONSULTAR_REGISTRO_MTS : string = PermisoList.CONSULTAR_REGISTRO_MTS;
  readonly CONSULTAR_REGISTRO_SESION : string = PermisoList.CONSULTAR_REGISTRO_SESION;
  readonly CONSULTAR_REGISTRO_DE_MSJ_RECEIVED : string = PermisoList.CONSULTAR_REGISTRO_DE_MSJ_RECEIVED;
  readonly CONSULTAR_COMPROBANTE_DE_PAGOS : string = PermisoList.CONSULTAR_COMPROBANTE_DE_PAGOS;
  readonly MODIFICAR_EJEMPLO_DE_PLANTILLA : string = PermisoList.MODIFICAR_EJEMPLO_DE_PLANTILLA;







  constructor(private http: Http, _router: Router, _autenticacionService: AutenticacionService) {
    super(_autenticacionService, _router);
  }

  get permissions(): string {
    return this._permissions;

  }

  set permissions(value: string){
    this._permissions = value;
  }

  get permisos(): String[] {
    return this._permisos;
  }

  set permisos(value: String[]) {
    this._permisos = value;
  }


  get superCompany(): boolean {
    return this._superCompany;
  }

  set superCompany(value: boolean) {
    this._superCompany = value;
  }

  get superUser(): boolean {
    return this._superUser;
  }

  set superUser(value: boolean) {
    this._superUser= value;
  }
  get generarReporte() :boolean {
    return this.hasPermissions(this.GENERAR_REPORTE);
  }

//Permiso para ver empresas
  get consultarEmpresa(): boolean {
    return this.hasPermissions(this.CONSULTAR_EMPRESA);
  }

  get consultarBeneficiario():boolean {
    return this.hasPermissions(this.CONSULTAR_BENEFICIARIO);
  }

  //Permiso para ver roles
  get consultarRol(): boolean {
    return this.hasPermissions(this.CONSULTAR_ROL);
  }

  //Permiso para ver usuarios
  get consultarUsuario(): boolean {
    return this.hasPermissions(this.CONSULTAR_USUARIO);
  }

  //Permiso para ver workflows
  get consutarWorkflow(): boolean {
    return this.hasPermissions(this.CONSULTAR_WORKFLOW);
  }

  //Permiso para ver usuarios
  get consultarTipoOrdenPago(): boolean {
    return this.hasPermissions(this.CONSULTAR_TIPO_ORDEN_PAGO);
  }

  //Permiso para ver Procesos
  get consultarProceso():boolean{
    return this.hasPermissions(this.CONSULTAR_PROCESO);
  }

  get consultarOrdenDePago(): boolean {
    return this.hasPermissions(this.CONSULTAR_ORDEN_DE_PAGO);
  }

  //Permiso para ver menu administracion
  get verMenuAdministracion(): boolean {
    return this.hasPermissions(this.VER_MENU_ADMINISTRACION)
  }


  //Permiso para consultar parametros de sistema
  get consultarSystemParameter():boolean{
    return this.hasPermissions(this.CONSULTAR_SYSTEMPARAMETER);
  }

  get consultarCodigoEvento():boolean{
    return this.hasPermissions(this.CONSULTAR_CODIGO_EVENTOS);
  }

  get modificarRol(): boolean {
    return this.hasPermissions(this.MODIFICAR_ROL)
  }


  get modificarUsuario(): boolean {
    return this.hasPermissions(this.MODIFICAR_USER)
  }

  get modificarEmpresa(): boolean {
    return this.hasPermissions(this.MODIFICAR_EMPRESA)
  }

  get modificarWorkflow(): boolean {
    return this.hasPermissions(this.MODIFICAR_WORKFLOW)
  }

  get modificarTipoOrdenPago(): boolean {
    return this.hasPermissions(this.MODIFICAR_TIPO_ORDEN_DE_PAGO)
  }

  get consultarBorradores():boolean{
    return this.hasPermissions(this.CONSULTAR_BORRADOR);
  }
  get modificarOrdenPago():boolean{
    return this.hasPermissions(this.MODIFICAR_ORDEN_DE_PAGO);
  }
  get modificarBorrador():boolean{
    return this.hasPermissions(this.MODIFICAR_BORRADOR);
  }
  get modificarCodEvento():boolean{
    return this.hasPermissions(this.MODIFICAR_COD_EVENTO);
  }
  get modificarProceso():boolean{
    return this.hasPermissions(this.MODIFICAR_PROCESO);
  }
  get modificarParameter(): boolean{
    return this.hasPermissions(this.MODIFICAR_PARAMETER);
  }
  get canviewcompanycredit(): boolean {
    return this.hasPermissions(this.VER_SALDO_CUENTA);
  }

  get consultarGenOrdenPago(): boolean{
    return this.hasPermissions(this.CONSULTAR_REGISTRO_DE_GEN_OP);
  }
  get consultarRegistroPago(): boolean{
    return this.hasPermissions(this.CONSULTAR_REGISTRO_DE_PAGO);
  }

  get consultarRegistroNotificaciones(): boolean{
    return this.hasPermissions(this.CONSULTAR_NOTIFICACIONES);
  }

  get consultarRegistroMts(): boolean{
    return this.hasPermissions(this.CONSULTAR_REGISTRO_MTS);
  }

  get consultarRegistroSesion(): boolean{
    return this.hasPermissions(this.CONSULTAR_REGISTRO_SESION);
  }

  get consultarRegistroMsjReceived(): boolean{
    return this.hasPermissions(this.CONSULTAR_REGISTRO_DE_MSJ_RECEIVED);
  }

  get consultarComprobantePagos(): boolean{
    return this.hasPermissions(this.CONSULTAR_COMPROBANTE_DE_PAGOS);
  }


  get modificarEjemploPlantilla() : boolean{
    return this.hasPermissions(this.MODIFICAR_EJEMPLO_DE_PLANTILLA);

  }
  hasPermissions(permission: string): boolean {
    // return this.permisos && permissions.every((permiso) => this.permisos.indexOf(permiso) >= 0);
    return !!(this.permissions && this.permissions.search(permission) != -1);

  }
}
