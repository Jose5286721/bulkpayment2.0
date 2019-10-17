import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {Rol} from "../../model/rol";
import {RolFiltroDto} from "../../dto/rol-dto/rol-filtro.dto";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {RolService} from "./rol.service";
import {Empresa} from "../../model/empresa";
import {RolListResponseDto} from "../../dto/rol-dto/rol-list-response.dto";
import {EmpresaService} from "../empresa/empresa.service";
import {RolFiltroRequestDto} from "../../dto/rol-dto/rol-filtro-request.dto";
import {PermisosService} from "../../security/permiso.service";
import {isUndefined} from "util";

@Component({
  selector: 'app-rol-list',
  templateUrl: './rol-list.component.html',
  styleUrls: ['./rol-list.component.css']
})
export class RolListComponent implements OnInit {

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
  rolesDt: RolListResponseDto[] = [];
  filtroBusquedaRol: RolFiltroDto;
  defectoList: any[] = [{ label: "SI", value: true },{ label: "NO", value: false } ];
  filteredCompany: any[];
  superCompany: boolean;

  constructor(private _router: Router, private _activedRoute: ActivatedRoute, private _empresaService: EmpresaService,
              private _messageUtilService: MessageUtilService, private _rolService: RolService, private _permisoService: PermisosService)
  { }

  ngOnInit() {
    this.loading = false;
    this.filtroBusquedaRol = new RolFiltroDto();
    this.rendererCompany();
    this.onBusqueda();
    this.mostrarMensaje();

  }

  mostrarMensaje(){
    if(this._messageUtilService.getMsgs() !== null && this._messageUtilService.getMsgs().length > 0){
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  rendererCompany(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
    }
  }



  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusquedaRol != null) {
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
    if (this.filtroBusquedaRol != null) {
      this._rolService.getRolByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.rolesDt = res.data.body.roles;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.rolesDt = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }


  cargarCriterio(): RolFiltroRequestDto{
    let filtroRolTemp = new RolFiltroRequestDto();
    filtroRolTemp.defaultrolNum = this.filtroBusquedaRol.defaultrolNum;
    filtroRolTemp.rolnameChr = this.filtroBusquedaRol.rolnameChr;
    return filtroRolTemp;
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
   * Metodo que realiza una busqueda de roles de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._rolService.getRolByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.rolesDt = res.data.body.roles;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.rolesDt = null;
          this.totalRecords = 0;
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  reset(){
    this.filtroBusquedaRol = new RolFiltroDto();
    this.rolesDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  navigateRolEdit(rol: Rol){
    if(rol != null && rol.idrolPk != null){
      this._router.navigate(['/rolEdit/rolId', rol.idrolPk]);
    }else{
      this._router.navigate(['/rolEdit']);
    }
  }

  isSuperCompany(): boolean{
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
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

  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
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

  modificarRolPerm(): boolean {
    return this._permisoService.modificarRol;
  }

  consultarRolPerm(): boolean {
    return this._permisoService.consultarRol;
  }

}
