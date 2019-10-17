import {Injectable} from "@angular/core";

@Injectable()
export class DateUtilService {

  constructor() {

  }

  /**
   * Conversion de un string de formato aaaa-MM-dd a dd/MM/aaaa
   * @param dateString
   * @returns {string}
   */
  convertStringDate(dateString: string): string{
    if(dateString && dateString.length >= 10){
      let dateSeparator = dateString.split("-");
      let dia = dateSeparator[2].substring(0,2);
      let mes = dateSeparator[1];
      let anho = dateSeparator[0];

      let format = dia + "/" + mes + "/" + anho;
      return format;
    }
    return null;
  }

  /**
   * Conversion de un string date de tipo dd/MM/AAAA a AAAA-MM-dd
   * @param dateString
   * @returns {string}
   */
  static convertStringDateUsToEs(dateString: string): string{
    if(dateString && dateString.length === 10){
      let dateSeparator = dateString.split("/");
      let dia = dateSeparator[0];
      let mes = dateSeparator[1];
      let anho = dateSeparator[2];

      return  anho + "-" + mes + "-" + dia;
    }
    return null;
  }

  /**
   * Conversion de un string date de tipo dd/MM/AAAA a AAAA-MM-dd
   * @param dateString
   * @returns {string}
   */
  static convertStringDateUs(dateString: string): string{
    if(dateString && dateString.length === 10){
      let dateSeparator = dateString.split("/");
      let dia = dateSeparator[0];
      let mes = dateSeparator[1];
      let anho = dateSeparator[2];

      return  mes + "-" + dia + "-" + anho;
    }
    return null;
  }

}
