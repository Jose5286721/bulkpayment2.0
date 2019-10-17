import { Component, OnInit, ViewChild } from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {UsuarioFiltroDto} from "../../dto/usuario-dto/usuario-filtro.dto";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Empresa} from "../../model/empresa";
import {EmpresaService} from "../empresa/empresa.service";
import {UsuarioFiltroRequestDto} from "../../dto/usuario-dto/usuario-filtro-request.dto";
import {Usuario} from "../../model/usuario";
import {UsuarioListResponseDto} from "../../dto/usuario-dto/usuario-list-response.dto";
import {UsuarioService} from "./usuario.service";
import {PermisosService} from "../../security/permiso.service";
import {isUndefined} from "util";

@Component({
  selector: 'app-usuario-list',
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {

  //Referencia a componentes
  @ViewChild('dt') dt : DataTable;

  //spinner
  loading = true;

  //DataTable
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';

  // Message
  msgs: Message[] = [];

  //
  userDt: UsuarioListResponseDto[] = [];
  filtroBusqueda: UsuarioFiltroDto;
  stateList: any[] = [{ label: "Activo", value: "AC" },{ label: "Inactivo", value: "IN" } ];
  filteredCompany: any[];
  listaEmpresa: Empresa[];

  constructor(private _router: Router, private _empresaService: EmpresaService, private _permisoService: PermisosService,
              private _messageUtilService: MessageUtilService, private _usuarioService: UsuarioService)
  { }

  ngOnInit() {
    this.loading = false;
    this.filtroBusqueda = new UsuarioFiltroDto();
    this.rendererCompany();
    this.onBusqueda();
    this.mostrarMensaje();
  }

  mostrarMensaje(){
    if(this._messageUtilService.getMsgs()){
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  /**
   * Obtiene lista de todas las empresas
   * @returns {Subscription}
   */
  obtenerEmpresas(){
    return this._empresaService.getAllEmpresas().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaEmpresa = res.data.body.empresas;
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }


  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusqueda != null) {
      return this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
    }
  }

  /**
   * Metodo que hace una peticion para obtener otra pagina de la tabla
   * @param event
   */
  paginate(event) {
    this.page = this.size = 0;
    this.page = event.page + 1;
    this.size = event.rows;
    this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
  }

  /**
   * Metodo que hace una peticion al servicio de listado de empresas, con criterios de busqueda
   */
  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusqueda != null) {
      this._usuarioService.getUsuarioByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.userDt = res.data.body.usuarios;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.userDt = null;
              this.totalRecords = 0;
              this.showWarn(res.errors.message.detail, res.errors.message.message);
              this.loading = false;
            }
          },
          error => {
            console.log(error);
            this.loading = false;
          });
    }
  }


  cargarCriterio(): UsuarioFiltroRequestDto{
    let filtroTemp = new UsuarioFiltroRequestDto();
    if(this.filtroBusqueda.company) {
      filtroTemp.companyId = this.filtroBusqueda.company.idcompanyPk;
    }
    filtroTemp.emailChr = this.filtroBusqueda.emailChr;
    filtroTemp.stateChr = this.filtroBusqueda.stateChr;
    filtroTemp.usernameChr = this.filtroBusqueda.usernameChr;
    return filtroTemp;
  }

  /**
   * event.order = 1 cuando el ordenamiento es ascendente.
   * Es -1 en descendente.
   */
  mysort(event) {
    this.properties = event.field;
    if (event.order === 1) {
      this.direction = 'ASC';
    } else {
      this.direction = 'DESC';
    }
    //   Si ordena en la primera página, si ordena en las siguientes, ya
    //   se debe haber cambiado el valor de las variables page y size.
    if (this.page === 0) {
      this.page = 1;
    }
    if (this.size === 0) {
      this.size = 10;
    }
  }

  /**
   * Metodo que realiza una busqueda de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._usuarioService.getUsuarioByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.userDt = res.data.body.usuarios;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.userDt = null;
          this.totalRecords = 0;
          this.showWarn(res.errors.message.detail, res.errors.message.message);
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  reset(){
    this.filtroBusqueda = new UsuarioFiltroDto();
    this.userDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  navigateUsuarioEdit(usuario: Usuario){
    if(usuario != null && usuario.iduserPk != null){
      this._router.navigate(['/userEdit/userId', usuario.iduserPk]);
    }else{
      this._router.navigate(['/userEdit']);
    }
  }

  filterCompany(event) {
    this.filteredCompany = [];
    if(this.listaEmpresa){
      for(let i = 0; i < this.listaEmpresa.length; i++) {
        let empresa = this.listaEmpresa[i];
        if(empresa.companynameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredCompany.push(empresa);
        }
      }
    }
  }

  showSuccess(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'success', summary:mensaje, detail:detail});
  }

  showInfo(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'info', summary:mensaje, detail:detail});
  }

  showWarn(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'warn', summary:mensaje, detail:detail});
  }

  showError(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:'error', summary:mensaje, detail:detail});
  }

  modificarUsuarioPerm(): boolean {
    return this._permisoService.modificarUsuario;
  }

  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }

  rendererCompany(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
    }else if(this.isSuperCompany()){
      this.obtenerEmpresas();
    }
  }

  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
          if(res.data.body.superCompany){
            this.obtenerEmpresas();
          }
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

