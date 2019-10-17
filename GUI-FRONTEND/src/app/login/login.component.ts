import { Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {AutenticacionService} from "../autenticacion/autenticacion.service";
import {PermisosService} from "../security/permiso.service";
import {MatDialog} from "@angular/material";
import {CompanyDialogComponent} from "./company-dialog/company-dialog.component";
import {Message} from "primeng/primeng";
import {EmpresaShortDto} from "../dto/empresa-dto/empresa-short.dto";
import {CaptchaService} from "./captcha-service";
import {DomSanitizer} from "@angular/platform-browser";



@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @ViewChild('captchaValue') inputCaptcha: any;

  isCaptchaLoading = true;
  superCompany:boolean;
  badCredentials: boolean;
  hide = true;
  msgs: Message[] = [];
  captchaImage: any;
  captchaEncode:string;
  constructor(private _autenticacionService: AutenticacionService, private _router: Router, private _permisoService: PermisosService,
              public companyDialog: MatDialog, private _captchaService: CaptchaService, private domSanitizer: DomSanitizer) {
  }
  ngOnInit() {
    this._autenticacionService.logout();
    this.getCaptcha();
  }

  login(username: any, password: any, captchaValue:any) {
    this.badCredentials = undefined;
      let redirect = this._autenticacionService.redirectUrl ? this._autenticacionService.redirectUrl : '/home';
      this._autenticacionService.login(username, password, captchaValue, this.captchaEncode).subscribe(
        (res) => {
          if (res.token) {
            this._permisoService.permissions = sessionStorage.getItem("permissions");

            if(res.newUser)
              this._router.navigate(['/perfil']);
            else {
              let isSuperCompany = sessionStorage.getItem("isSuperCompany") =="true";

              if(this._autenticacionService.companies.length>1 && !isSuperCompany)
                this.openDialog(redirect,res.sendSms,this._autenticacionService.companies);
              else{
                if(res.sendSms)
                  this.openDialog(redirect,res.sendSms, [this._autenticacionService.companies[0]])
                else
                  this.getNewToken(redirect,this._autenticacionService.companies[0],null);
              }
              this._permisoService.superCompany= isSuperCompany;
            }
          }
        },
        (err) => {
          this.getCaptcha();
          this.inputCaptcha.nativeElement.value = "";
          let body = JSON.parse(err._body);
          this.showMessage(body.errors.message.detail,body.errors.message.message,'warn');
          this.badCredentials = body.errors.errorCode ==="0064";
        }
      );
  }
  getNewToken(redirect : string, selectedCompany: EmpresaShortDto, pinSms:string){
    this._autenticacionService.getTokenByCompany(selectedCompany,pinSms).subscribe(
      (data) => {
        if (!data) {
          this.showMessage("Problemas en el acceso","WARN","error");
        }else
          this.redirectTo(redirect);
      },
      (err) => {
        console.log(err);
      }
    );

  }
  redirectTo(url:string){
    this._autenticacionService.setIsSelectedCompany(true);
    this._router.navigate([url]);
  }

  errorMessageEmail(): any {
    if (!this.badCredentials) {
      return false;
    } else if (this.badCredentials) {
      return 'Verifique el email y la contraseña';
    }
  }

  openDialog(redirect:string, sendSms: boolean, companies:EmpresaShortDto[]): void {
    const dialogRef = this.companyDialog.open(CompanyDialogComponent, {
      disableClose: false,
      width: '250px',
      data: {companies: companies, sendSms:sendSms },
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result)
        this.redirectTo(redirect);

    });
  }
  passwordRecover(email:string){
    this._autenticacionService.forgotPassword(email).subscribe(
      (res:any)=>{
        if(res.data ){
          this.showMessage(res.data.message.detail,res.data.message.message,'info');
        }else {
          this.showMessage(res.errors.message.detail,res.errors.message.message,'warn');
        }
      }, error => {
        console.log(error)
      }
    );
  }

  showMessage(detail: string, mensaje: string, severity:string) {
    this.msgs = [];
    this.msgs.push({severity: severity, summary: mensaje, detail: detail});
  }
  getImageCaptcha(){
    return this.domSanitizer.bypassSecurityTrustUrl(this.captchaImage);
  }

  getCaptcha(){
    this.isCaptchaLoading = true;
    this._captchaService.getImage().subscribe(
      (response : any) =>{
        this.captchaEncode = response.headers.get("captchaencode");
        this.createImageFromBlob(response.blob());
        this.isCaptchaLoading = false;
      },error => {
        this.isCaptchaLoading = false;
        console.log(error);
      }
    )
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.captchaImage = reader.result;
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

}


