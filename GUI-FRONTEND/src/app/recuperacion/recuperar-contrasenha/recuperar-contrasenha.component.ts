import { Component, OnInit } from '@angular/core';
import {Message} from "primeng/primeng";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PerfilService} from "../../perfil/perfil.service";
import {UsuarioService} from "../../administracion/usuario/usuario.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";
import {UserChangePasswordDto} from "../../dto/usuario-dto/UserChangePasswordDto";


@Component({
  selector: 'app-recuperar-contrasenha',
  templateUrl: './recuperar-contrasenha.component.html',
  styleUrls: ['./recuperar-contrasenha.component.css']
})
export class RecuperarContrasenhaComponent implements OnInit {

  loading = true;
  changePassToken : string;
  msgs: Message[] = [];
  changePassGroup: FormGroup;
  constructor(private _formBuilder: FormBuilder, private _perfilService:PerfilService,
              private _usuarioService: UsuarioService, private _router:Router, private _activatedRoute: ActivatedRoute,
              private _authService:AutenticacionService) { }

  ngOnInit() {
    this.getToken();
    this.buildForm();

  }
  getToken(){
    this._activatedRoute.queryParams.subscribe( query=>{
      this.changePassToken = query['token'];
    });
    this._authService.validateToken(this.changePassToken).subscribe(
      (res: boolean) =>{
        console.log(res);
        if(!res){
          this._router.navigate(['/login']);
        }
      },error => {
        console.log(error);
        this._router.navigate(['/login']);
      }
    );
  }

  buildForm(){
    this.changePassGroup = this._formBuilder.group({
      password: [null,[Validators.required ,Validators.pattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$")]],
      retrypasswordChr:[null,Validators.required]
    }, {
      validator: this.matchEmail
    });
    this.loading = false;
    this.changePassGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }


  /**
   * metodo para determinar si los campos de contraseña coinciden
   * @param {FormGroup} group
   * @returns {{mismatch: boolean}}
   */
  matchEmail(group: FormGroup){
    return group.get('password').value === group.get('retrypasswordChr').value ? null : { mismatch: true };

  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
  }

  changeUserPassword(){
    let dto = new UserChangePasswordDto();
    dto.newPassword = this.changePassGroup.value.password;
    this._usuarioService.changeUserPassword(dto,this.changePassToken).subscribe(
      (res: any) =>{
        if(res.data && res.data.message) {
          this.showSuccess(res.data.message.detail, res.data.message.message);
          setTimeout(() => {
            this._router.navigate(['/login']);
          }, 2000);
        }
      else
          this.showError(res.errors.message.detail,res.errors.message.message);
    })

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
