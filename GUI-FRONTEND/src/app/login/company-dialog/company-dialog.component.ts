import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {EmpresaShortDto} from "../../dto/empresa-dto/empresa-short.dto";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'company-dialog',
  templateUrl: './company-dialog.component.html'
})
export class CompanyDialogComponent implements OnInit {
  dialogFormGroup: FormGroup;
  companies : EmpresaShortDto[];
  errorPin = false;
  errorMessage:string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<CompanyDialogComponent>,
    private _authService: AutenticacionService ) {}

    ngOnInit() {
      this.companies = this.data.companies;
      this.buildForm();
    }
    buildForm(){
      this.dialogFormGroup = this._formBuilder.group({
        company: [this.companies[0], Validators.required],
        pinSms: [null, Validators.required]
      });

      const pinSmsControl = this.dialogFormGroup.get('pinSms');
      if(!this.data.sendSms)
        pinSmsControl.setValidators(null);
    }

    close(){
      this._authService.getTokenByCompany(this.dialogFormGroup.value.company,this.dialogFormGroup.value.pinSms).subscribe(
        (res) => {
          if (!res.token) {
           this.errorMessage=res.errors.message.message;
           this.errorPin=true;
          }else{
            this.dialogRef.close(true);
          }
        },
        (err) => {
          console.log(err);
        }
      );
    }


}
