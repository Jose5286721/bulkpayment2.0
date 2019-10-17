import { Component, OnInit } from '@angular/core';
import { Message } from "primeng/primeng";
import { FormBuilder, FormGroup, Validators} from "@angular/forms";
import { Perfil } from "../model/Perfil";
import { PerfilService } from "./perfil.service";
import {MESSAGEERROR} from "../utils/gop-constantes";
import { Router} from "@angular/router";
import { UsuarioService } from "../administracion/usuario/usuario.service";
import { AutenticacionService } from "../autenticacion/autenticacion.service";
import {Observable} from "rxjs";


@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})


export class PerfilComponent implements OnInit {
  loading = true;
  accionTitle:string;
  msgs: Message[] = [];
  perfilFormGroup: FormGroup;
  isSelectedCompany$ : Observable<boolean>;
  isFirmante: boolean;
  constructor(private _formBuilder: FormBuilder, private _perfilService:PerfilService,
              private _usuarioService: UsuarioService, private _router:Router,
              private _authService:AutenticacionService) { }

  ngOnInit() {
    this.isSelectedCompany$ = this._authService.isSelectedCompany;
    this.isSelectedCompany$.subscribe(value => {
      if(!value) this.showInfo('','Por favor cambie su contraseña');

    });
    this.buildForm(new Perfil());
    this.getBasicProfile();

  }

  buildForm(perfilForm: Perfil){
    this.accionTitle = 'Perfil de Usuario';
    if(perfilForm == null){
      perfilForm = new Perfil();
    }
    this.perfilFormGroup = this._formBuilder.group({
      usernameChr: [perfilForm.userName],
      userlastnameChr: [perfilForm.userLastName],
      userjobtitleChr: [perfilForm.userJobTitle],
      password: [null],
      retrypasswordChr:[null],
      smspinChr: [null],
      retrysmspinChr:[null]
    }, {
      validator: this.matchFields
    });
    this.isFirmante = perfilForm.firmante;
    this.loading = false;

  }

  updateValidators(){
    const passControl = this.perfilFormGroup.controls['password'];
    const pinControl = this.perfilFormGroup.controls['smspinChr'];
    if(passControl.value)
      passControl.setValidators([Validators.required,Validators.pattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$")]);
    else
      passControl.clearValidators();

    if (pinControl.value )
      pinControl.setValidators(Validators.required);
    else
      pinControl.clearValidators();

    passControl.updateValueAndValidity();
    pinControl.updateValueAndValidity();
  }


  getBasicProfile(){
    return this._perfilService.viewBasicProfile().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.perfil);
        } else {
          console.log(res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error,MESSAGEERROR);
      }

    )
  }


  /**
   * metodo para determinar si los campos de contraseña coinciden
   * @param {FormGroup} group
   * @returns {{mismatch: boolean}}
   */
  matchFields(group: FormGroup){

    if(group.get('password').value && !group.get('smspinChr').value)
      return group.get('password').value === group.get('retrypasswordChr').value ? null : { mismatch: true };

    else if(!group.get('password').value && group.get('smspinChr').value)
      return group.get('smspinChr').value === group.get('retrysmspinChr').value ? null : { mismatch: true };

    else if(group.get('password').value && group.get('smspinChr').value)
      return group.get('password').value === group.get('retrypasswordChr').value &&  group.get('smspinChr').value === group.get('retrysmspinChr').value  ? null : { mismatch: true };
    else
      return null;


  }


  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
  }


  /**
   * Metodo para cambiar de contraseña
   * @param {Perfil} perfil
   * @returns {Subscription}
   */
  editUserProfile(perfil: Perfil){
    this.loading = true;
    return this._perfilService.editBasicProfile(perfil).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.showSuccess(res.data.message.detail, res.data.message.message);
          setTimeout(() =>{
            this._authService.logout();
          },2000)
        } else {
          setTimeout(() => {
            this.loading = false;
            this.showWarn(res.errors.message.detail,res.errors.message.message);
          },500);
        }
      },
      error => {
        this.loading = false;
        console.log(error);
        this.showError(error,MESSAGEERROR);
      });


  }

  /**
   * Navega al inicio
   */
  navigateHome() {
    this._router.navigate(['/home']);
  }

  /**
   * Guarda perfil del usuario
   */
  saveProfile(){
    if(this.perfilFormGroup.valid){
      this.editUserProfile(this.cargarPerfilForNew());
    }
  }

  cargarPerfilForNew() : Perfil{
    let perfilTemp = new Perfil();
    perfilTemp.userName = this.perfilFormGroup.value.usernameChr;
    perfilTemp.userLastName = this.perfilFormGroup.value.userlastnameChr;
    perfilTemp.password = this.perfilFormGroup.value.password;
    perfilTemp.smspinChr = this.perfilFormGroup.value.smspinChr;
    return perfilTemp;
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
