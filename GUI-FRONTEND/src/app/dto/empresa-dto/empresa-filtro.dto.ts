export class EmpresaFiltroDto{
  companynameChr: string;
  mtsfield5Chr: string;
  stateChr: string;

  constructor() {
    this.companynameChr = null;
    this.mtsfield5Chr = null;
    this.stateChr = "AC";
  }
}
