import {AbstractControl, ValidatorFn} from "@angular/forms";
import {DateUtilService} from "./date-util.service";

export class DateValidators {
  static dateGreaterToday(): ValidatorFn {
    return (c: AbstractControl): { [key: string]: any } | null => {
      const dateString = DateUtilService.convertStringDateUs(c.value);
      const today = new Date();
      today.setHours(0,0,0,0);
      return dateString ===null || new Date(dateString)>=today ? null:  {'forbiddenDate': {value: c.value}};
    };
  }
}
