import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {Empresa} from "../../model/empresa";
import {EmpresaFiltroDto} from "../../dto/empresa-dto/empresa-filtro.dto";
import {EmpresaService} from "./empresa.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {CANT_FILAS, PRIMERA_PAGINA, STATES} from "../../utils/gop-constantes";
import {PermisosService} from "../../security/permiso.service";
import {isUndefined} from "util";

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa-list.component.html',
  styleUrls: ['./empresa-list.component.css']
})
export class EmpresaListComponent implements OnInit {

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


  empresasDt: Empresa[] = [];
  estadoList = STATES;
  filtroBusquedaEmpresa: EmpresaFiltroDto;

  constructor(private _empresaService: EmpresaService, private _router: Router, private _activedRoute: ActivatedRoute,
              private _messageUtilService: MessageUtilService, private _permisoService: PermisosService) { }

  ngOnInit() {
    this.loading = false;
    this.filtroBusquedaEmpresa = new EmpresaFiltroDto();
    this.callSuperCompanyService();
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
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusquedaEmpresa != null) {
      return this.onBusquedaByCriPag(this.filtroBusquedaEmpresa, this.direction, this.properties, this.page.toString(), this.size.toString());
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
    this.onBusquedaByCriPag(this.filtroBusquedaEmpresa, this.direction, this.properties, this.page.toString(), this.size.toString());
  }

  /**
   * Metodo que hace una peticion al servicio de listado de empresas, con criterios de busqueda
   */
  onBusqueda() {
    this.msgs = null;
    if (this.filtroBusquedaEmpresa != null) {
      this.loading = true;
      this._empresaService.getEmpresasByCriPag(this.filtroBusquedaEmpresa, '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.empresasDt = res.data.body.empresas;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.empresasDt = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
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
   * Metodo que realiza una busqueda de empresas de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._empresaService.getEmpresasByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.empresasDt = res.data.body.empresas;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.empresasDt = null;
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
    this.filtroBusquedaEmpresa = new EmpresaFiltroDto();
    this.empresasDt = null;
    this.totalRecords=0;
    this.msgs = null;
  }

  navigateCompanyEdit(empresa: Empresa){
    if(empresa != null && empresa.idcompanyPk != null){
      this._router.navigate(['/companyEdit/companyId', empresa.idcompanyPk]);
    }else{
      this._router.navigate(['/companyEdit']);
    }
  }

  modificarEmpresaPerm(): boolean {
    return this._permisoService.modificarEmpresa;
  }

  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }

  callSuperCompanyService(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
    }else if(!this.isSuperCompany()){
      this.onBusqueda();
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

  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
          if(!res.data.body.superCompany){
            this.onBusqueda();
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
