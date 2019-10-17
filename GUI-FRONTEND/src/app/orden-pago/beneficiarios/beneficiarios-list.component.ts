import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTable, Message} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";

import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA, STATES} from "../../utils/gop-constantes";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";
import {BeneficiariosService} from './beneficiarios-service';
import {EmpresaService} from '../../administracion/empresa/empresa.service';
import {BeneficiarioFiltroDto} from '../../dto/beneficiario-dto/beneficiario-filtro.dto';
import {BeneficiarioDto} from '../../dto/beneficiario-dto/beneficiario.dto';
import {Empresa} from '../../model/empresa';
import {BeneficiarioFiltroRequestDto} from '../../dto/beneficiario-dto/beneficiario-filtro-request.dto';


@Component({
  selector: 'app-beneficiarios-list',
  templateUrl: './beneficiarios-list.component.html',
  styleUrls: ['./beneficiarios-list.component.css']
})
export class BeneficiariosListComponent implements OnInit {
  //Referencia a componentes
  @ViewChild('dt') dt: DataTable;

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
  estadoList = STATES;
  filtroBusquedaBeneficiario: BeneficiarioFiltroDto;
  beneficiarioDt: BeneficiarioDto[] = [];

  filteredCompany: any[];
  listaEmpresa: Empresa[];

  constructor(private _router: Router, private _activedRoute: ActivatedRoute, private _permisoService: PermisosService,
              private _messageUtilService: MessageUtilService, private _beneficiarioservice: BeneficiariosService,
              private _empresaService: EmpresaService) {
  }

  ngOnInit() {
    this.loading = false;
    this.filtroBusquedaBeneficiario = new BeneficiarioFiltroDto();
    this.onBusqueda();
    this.mostrarMensaje();
    this.callSuperCompanyService();
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  cargarCriterio(): BeneficiarioFiltroRequestDto {
    let filtroBeneficiarioTemp = new BeneficiarioFiltroRequestDto();
    if(this.filtroBusquedaBeneficiario.empresa) {
      filtroBeneficiarioTemp.idCompany = this.filtroBusquedaBeneficiario.empresa.idcompanyPk;
    }
    filtroBeneficiarioTemp.phonenumberChr = this.filtroBusquedaBeneficiario.phonenumberChr;
    filtroBeneficiarioTemp.stateChr = this.filtroBusquedaBeneficiario.stateChr;
    return filtroBeneficiarioTemp;
  }

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusquedaBeneficiario != null) {
      this._beneficiarioservice.getBeneficiariosByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.beneficiarioDt = res.data.body.beneficiarios;

              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.beneficiarioDt = null;
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
   * Metodo que hace una peticion para obtener otra pagina de la tabla
   * @param event
   */
  paginate(event) {
    this.page = this.size = 0;
    this.page = event.page + 1;
    this.size = event.rows;
    this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
  }

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
    this._beneficiarioservice.getBeneficiariosByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.beneficiarioDt = res.data.body.beneficiarios;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.beneficiarioDt = null;
          this.totalRecords = 0;
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  reset() {
    this.filtroBusquedaBeneficiario = new BeneficiarioFiltroDto();
    this.beneficiarioDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }

  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusquedaBeneficiario != null) {
      return this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
    }
  }

  showSuccess(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'success', summary: mensaje, detail: detail});
  }

  showInfo(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'info', summary: mensaje, detail: detail});
  }

  showWarn(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'warn', summary: mensaje, detail: detail});
  }

  showError(detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity: 'error', summary: mensaje, detail: detail});
  }

  isSuperCompany(): boolean{
    return this._permisoService.superCompany;
  }

  callSuperCompanyService(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
    }else if(this.isSuperCompany()){
      this.obtenerEmpresas();
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
        if(this._permisoService.superCompany)
          this.obtenerEmpresas()
      },
      error => {
        console.log(error);
        this._permisoService.superCompany = false;
      });
  }

}
