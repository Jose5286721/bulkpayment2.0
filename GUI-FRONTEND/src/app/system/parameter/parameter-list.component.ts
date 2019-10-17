

import {Component, OnInit, ViewChild} from "@angular/core";
import {DataTable, Message} from "primeng/primeng";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {ParameterService} from "./parameter.service";
import {SystemparameterFiltroDto} from "../../dto/systemparameter-dto/systemparameter-filtro.dto";
import {SystemparameterResponseDto} from "../../dto/systemparameter-dto/systemparameter-response.dto";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {ProcesoService} from "../procesos/proceso.service";
import {Proceso} from "../../model/Proceso";
import {Systemparameter} from "../../model/systemparameter";
import {SystemparameterId} from "../../model/systemParameterId";
import {PermisosService} from '../../security/permiso.service';

@Component({
  selector: 'app-systemparameter-list',
  templateUrl: './systemparameter-list.component.html',
  styleUrls: ['./systemparameter-list.component.css']
})
export class SystemParameterListComponent implements OnInit {
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

  listaProcesos:Proceso[];

  filteredProcesos: any[];

  systemparameterDt: SystemparameterResponseDto[];

 filtroBusquedaSystemparameter:SystemparameterFiltroDto;

  constructor(private _router: Router, private _activedRoute: ActivatedRoute,
              private _messageUtilService: MessageUtilService, private _systemparameterService: ParameterService,
              private _procesoService:ProcesoService, private  _permisoService:PermisosService) {
  }

  ngOnInit() {
    this.loading = false;
    this.filtroBusquedaSystemparameter = new SystemparameterFiltroDto();
    this.onBusqueda();
    this.mostrarMensaje();
    this.obtenerProcesos();
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  cargarCriterio(): SystemparameterFiltroDto {
    let filtroTemp = new SystemparameterFiltroDto();
    if(this.filtroBusquedaSystemparameter.proceso) {
      filtroTemp.idProceso = this.filtroBusquedaSystemparameter.proceso.idprocessPk;
    }
    filtroTemp.parametro = this.filtroBusquedaSystemparameter.parametro;
    filtroTemp.valor = this.filtroBusquedaSystemparameter.valor;
    if (filtroTemp.idProceso == null && filtroTemp.parametro == null && filtroTemp.valor==null) {
      filtroTemp.allByPage = true;
    } else {
      filtroTemp.allByPage = false;
    }
    return filtroTemp;
  }
  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusquedaSystemparameter != null) {
      return this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
    }
  }

  /**
   * Metodo que realiza una busqueda de SystemParameters de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._systemparameterService.getSystemParameterByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.systemparameterDt = res.data.body.systemparameters;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.systemparameterDt = null;
          this.totalRecords = 0;
        }
        this.loading = false;
      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }
  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusquedaSystemparameter != null) {
      this._systemparameterService.getSystemParameterByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.systemparameterDt = res.data.body.systemparameters;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.systemparameterDt = null;
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
   * Obtiene lista de todos los Procesos
   * @returns {Subscription}
   */
  obtenerProcesos(){
    return this._procesoService.getAllProcesos().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.listaProcesos = res.data.body.procesos;
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error, MESSAGEERROR);
      });
  }

  filterProcesos(event) {
    this.filteredProcesos = [];
    if(this.listaProcesos){
      for(let i = 0; i < this.listaProcesos.length; i++) {
        let proceso = this.listaProcesos[i];
        if(proceso.processnameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredProcesos.push(proceso);
        }
      }
    }
  }
  reset() {
    this.filtroBusquedaSystemparameter = new SystemparameterFiltroDto();
    this.systemparameterDt = null;
    this.totalRecords = 0;
    this.msgs = null;
  }


  navigateParameterEdit(systemparameter: SystemparameterResponseDto){
    if(systemparameter != null && systemparameter.idProcess!= null){
      this._router.navigate(['/systemparameterEdit/systemparameterId',systemparameter.idProcess,systemparameter.parametro]);
    }else{
      this._router.navigate(['/systemparameterEdit']);
    }
  }
  modificarParameterPerm(): boolean {
    return this._permisoService.modificarParameter;
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
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

}
