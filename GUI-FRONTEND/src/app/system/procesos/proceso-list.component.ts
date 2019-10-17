import {Component, OnInit, ViewChild} from "@angular/core";
import {ProcesoService} from "./proceso.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageUtilService} from "../../utils/message-util.service";
import {DataTable, Message} from "primeng/primeng";
import {ProcesoFiltroDto} from "../../dto/systemparameter-dto/proceso-filtro.dto";
import {ProcesoListResponseDto} from "../../dto/systemparameter-dto/proceso-response.dto";
import {CANT_FILAS, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Proceso} from "../../model/Proceso";
import {PermisosService} from '../../security/permiso.service';

@Component({
  selector: 'app-proceso-list',
  templateUrl: './proceso-list.component.html',
  styleUrls: ['./proceso-list.component.css']
})
export class ProcesoListComponent implements OnInit {
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

  filtroBusquedaProceso: ProcesoFiltroDto;
  processDt: ProcesoListResponseDto[];
  // Message
  msgs: Message[] = [];

  ngOnInit() {
    this.loading = false;
    this.filtroBusquedaProceso = new ProcesoFiltroDto();
    this.onBusqueda();
    this.mostrarMensaje();
  }

  constructor(private _router: Router, private _activedRoute: ActivatedRoute,
              private _messageUtilService: MessageUtilService,
              private _procesoService: ProcesoService, private _permisoService:PermisosService) {
  }

  mostrarMensaje() {
    if (this._messageUtilService.getMsgs() != null && this._messageUtilService.getMsgs().length > 0) {
      this.msgs = this._messageUtilService.getMsgs();
      this._messageUtilService.setMsgs(null);
    }
  }

  cargarCriterio(): ProcesoFiltroDto {
    let filtroTemp = new ProcesoFiltroDto();
    filtroTemp.descripcion = this.filtroBusquedaProceso.descripcion;
    filtroTemp.nombre = this.filtroBusquedaProceso.nombre;
    if (filtroTemp.nombre == null && filtroTemp.descripcion == null) {
      filtroTemp.allByPage = true;
    } else {
      filtroTemp.allByPage = false;
    }
    return filtroTemp;
  }

  navigateProcessEdit(proceso: Proceso) {
    if (proceso != null && proceso.idprocessPk != null) {
      this._router.navigate(['/processEdit/processId', proceso.idprocessPk]);
    } else {
      this._router.navigate(['/processEdit']);
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
    this._procesoService.getProcessByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.processDt = res.data.body.procesos;
            this.totalRecords = res.meta.totalCount;
          }
        } else {
          this.processDt = null;
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
    this.filtroBusquedaProceso = new ProcesoFiltroDto();
    this.processDt = null;
    this.totalRecords = 0;
    this.msgs = null;
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

  onBusqueda() {
    this.msgs = null;
    this.loading = true;
    if (this.filtroBusquedaProceso != null) {
      this._procesoService.getProcessByCriPag(this.cargarCriterio(), '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.processDt = res.data.body.procesos;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.processDt = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
          });
    }
  }
  modificarProcesoPerm(): boolean {
    return this._permisoService.modificarProceso;
  }
}
