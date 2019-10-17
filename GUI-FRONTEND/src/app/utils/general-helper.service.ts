import {Injectable} from "@angular/core";

@Injectable()
export class GeneralHelperService {

  constructor() {

  }

  /**
   * Convierte un numero de tipo (09xx)xxx-xxx a 09xxxxxxxxx
   * @param {string} b
   * @returns {any}
   */
  convertMaskToNumber(b : string){
    if(b && b.length==13) {
      let parte1 = b.substring(1, 5);
      let parte2 = b.substring(6, 9);
      let parte3 = b.substring(10, 13);

      let billetera = parte1 + parte2 + parte3;
      return billetera;
    }
    return null;
  }

  /**
   * Conversion de un string a formato (09xx)xxx-xxx
   * @param {string} b
   * @returns {any}
   */
  convertNumberToMask(b: string){
    if(b && b.length==10) {
      let parte1 = b.substring(0, 4);
      let parte2 = b.substring(4, 7);
      let parte3 = b.substring(7, 10);

      return "(" + parte1 + ")" + parte2 + "-" + parte3;

    }
    return null;
  }

  isNullOrUndefined(value: any){
    return value === undefined || value === null;

  }

}
