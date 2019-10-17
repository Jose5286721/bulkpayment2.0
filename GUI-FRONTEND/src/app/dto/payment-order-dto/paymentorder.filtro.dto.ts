import {Empresa} from '../../model/empresa';
import {Workflow} from '../../model/workflow';
import {TipoOrdenPago} from '../../model/tipo-orden-pago';

export class PaymentOrderFilterDto {
  tipoOrdenPago: TipoOrdenPago;
  workflow: Workflow;
  empresa: Empresa;
  idOrdenPago: number;
  fechaDesde: string;
  fechaHasta: string;
  estado:string;
}
