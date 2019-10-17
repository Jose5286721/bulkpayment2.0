import {Component, OnInit} from '@angular/core';
import {Message,} from "primeng/primeng";
import {Rol} from "../../model/rol";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {EmpresaService} from "../empresa/empresa.service";
import {MessageUtilService} from "../../utils/message-util.service";
import {RolService} from "./rol.service";
import {MESSAGEERROR} from "../../utils/gop-constantes";
import {Empresa} from "../../model/empresa";
import {RolRequestDto} from "../../dto/rol-dto/rol-request.dto";
import {Permiso} from "../../model/permiso";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";

@Component({
  selector: 'app-rol-edit',
  templateUrl: './rol-edit.component.html',
  styleUrls: ['./rol-edit.component.css']
})
export class RolEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';

  //
  isNewRol: boolean;
  rolFormGroup: FormGroup;
  filteredCompany: any[];
  idRolUrl: number;
  listaPermisos: Permiso[];
  permisosSelecionados: Permiso[];
  superCompany: boolean;




  constructor(private _activatedRoute: ActivatedRoute, private _router: Router,
              private _formBuilder: FormBuilder, private _empresaService: EmpresaService, private _permisoService: PermisosService,
              private _messageUtilService: MessageUtilService, private _rolService: RolService)
  { }

  ngOnInit() {
    this.isNewRol = true;
    this.listaPermisos = [];
    this.permisosSelecionados = [];
    this.getAllPermisos();
    this.rendererCompany();
    this.buildForm(null);
    this.isEditOrNew();
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un rol,
   * sino un nuevo rol
   */
  isEditOrNew(){
    this._activatedRoute.params.subscribe( params => {
      if (params['id']) {
        this.idRolUrl = params['id'];
        this.accionTitle = 'Editar Rol';
        this.isNewRol = false;
        this.getRolById(params['id']);
      }else{
        this.accionTitle = 'Agregar Rol';
        this.isNewRol = true;
      }
    });
  }


  /**
   * Todos los permisos disponibles
   * @returns {Subscription}
   */
  getAllPermisos() {
    this.loading = true;
    return this._rolService.getAllPermissions().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          if(!this.isNewRol) {
            this.getPermissionsForRolSelected(res.data.body.permisos);
          }else{
            this.listaPermisos = res.data.body.permisos;
          }
        } else {
          console.log(res.errors.message.message);
          this.loading = false;
        }
      },
      error => {
        console.log(error);
        this.showError(error,MESSAGEERROR);
        this.loading = false;
      });
  }


  getPermissionsForRolSelected(permisosAll : any) {
    this.loading = true;
    this._activatedRoute.params.subscribe( params => {
      return this._rolService.getPermissionsForRolSelected(params['id']).subscribe(
        (res: any) => {
          if (res.data != null && res.data.body != null) {
            let permisosForUser = res.data.body.permisos;
            for(let permiso of permisosAll){
              if(!this.objectContains(permisosForUser, permiso.nameChr)){
                this.listaPermisos.push(permiso);
              }
            }
            this.permisosSelecionados = permisosForUser;
            this.loading = false;
          } else {
            console.log(res.errors.message.message);
            this.loading = false;
          }
        },
        error => {
          console.log(error);
          this.showError(error,MESSAGEERROR);
          this.loading = false;
        });
    });

  }

  objectContains(obj, term: string): boolean {
    for (let key in obj){
      if(obj[key].nameChr.indexOf(term) != -1) return true;
    }
    return false;
  }

  /**
   * Navega al listado de roles
   */
  navigateRolList() {
    this._router.navigate(['/rolList']);
  }

  /**
   * Arma e inicializa la estructura del formulario para creacion o edicion de
   * roles
   */
  buildForm(rolForm: Rol){
    if(rolForm == null){
      rolForm = new Rol();
    }
    //Se pone requerido los campos de acuerdo a si es un nuevo rol o una edicion
    if(this.isNewRol){
      this.rolFormGroup = this._formBuilder.group({
        idrolPk: [null],
        rolnameChr: [null, Validators.required],
        descriptionChr: [null],
        company: [{value: null, disabled: false}],
        defaultrolNum: [false],
        stateNum:[{value:null, disabled:false}]
      });
    }else{
      this.rolFormGroup = this._formBuilder.group({
        idrolPk: [rolForm.idrolPk],
        rolnameChr: [rolForm.rolnameChr, Validators.required],
        descriptionChr: [rolForm.descriptionChr],
        company: [rolForm.company],
        defaultrolNum: false,
        stateNum:[{value:rolForm.stateNum, disabled:!this.isNewRol && !this.superCompany}]
      });
    }
    this.loading = false;
    this.rolFormGroup.valueChanges
      .subscribe(data => this.onValueChanged(1));
  }

  /**
   * Metodo al que se accede con cada cambio en el formulario
   * @param data
   */
  onValueChanged(data?: any) {
    this.msgs = null;
  }

  /**
   * Obtiene un rol por su id
   * @returns {Subscription}
   */
  getRolById(rolId: number) {
    return this._rolService.getRolById(rolId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.rol);
        } else {
          this.showWarn(res.errors.message.detail,res.errors.message.message);
        }
      },
      error => {
        console.log(error);
        this.showError(error,MESSAGEERROR);
      });
  }

  /**
   * Carga campos requeridos para la creacion de un rol
   * @returns {Empresa}
   */
  cargarRolForNew() : RolRequestDto{
    let rolTemp = new RolRequestDto();
    rolTemp.rolnameChr = this.rolFormGroup.value.rolnameChr;
    rolTemp.descriptionChr = this.rolFormGroup.value.descriptionChr;
    if(this.rolFormGroup.value.company){
      rolTemp.companyId = this.rolFormGroup.value.company.idcompanyPk;
    }
    rolTemp.defaultrolNum = this.rolFormGroup.value.defaultrolNum;
    for(let idPerm of this.permisosSelecionados){
      rolTemp.listPermisos[idPerm.nameChr]= idPerm.idpermissionPk;
    }
    return rolTemp;
  }

  /**
   * Carga campos para la edicion de un rol
   * @returns {Empresa}
   */
  cargarRolForEdit() : RolRequestDto{
    let rolTemp = new RolRequestDto();
    rolTemp.idrolPk = this.rolFormGroup.value.idrolPk;
    rolTemp.rolnameChr = this.rolFormGroup.value.rolnameChr;
    rolTemp.defaultrolNum = this.rolFormGroup.value.defaultrolNum;
    if(this.rolFormGroup.value.company){
      rolTemp.companyId = this.rolFormGroup.value.company.idcompanyPk;
    }
    rolTemp.stateNum = this.rolFormGroup.value.stateNum;
    rolTemp.descriptionChr = this.rolFormGroup.value.descriptionChr;
    for(let idPerm of this.permisosSelecionados){
      rolTemp.listPermisos[idPerm.nameChr]= idPerm.idpermissionPk;
    }
    return rolTemp;
  }

  /**
   * Submit, se encarga de redirigir al metodo correcto para la creacion o edicion de un rol
   */
  saveRol(){
    if(this.rolFormGroup.valid && this.isNewRol){
      this.crearRol(this.cargarRolForNew());
    }else if(this.rolFormGroup.valid && !this.isNewRol){
      this.editarRol(this.cargarRolForEdit());
    }
  }

  /**
   * Creacion de un nuevo rol
   * @param {RolRequestDto} rol
   * @returns {Subscription}
   */
  crearRol(rol: RolRequestDto){
    this.loading = true;
    return this._rolService.crearRol(rol).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/rolList']);
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
   * Procesar la edicion de los campos de un rol.
   * @param {Rol} rol
   * @returns {Subscription}
   */
  editarRol(rol: RolRequestDto){
    this.loading = true;
    return this._rolService.editarRol(rol).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/rolList']);
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


  // expandAll(){
  //   this.rolTree.forEach( node => {
  //     this.expandRecursive(node, true);
  //   } );
  // }
  //
  // collapseAll(){
  //   this.rolTree.forEach( node => {
  //     this.expandRecursive(node, false);
  //   } );
  // }

  // private expandRecursive(node:TreeNode, isExpand:boolean){
  //   node.expanded = isExpand;
  //   if(node.children){
  //     node.children.forEach( childNode => {
  //       this.expandRecursive(childNode, isExpand);
  //     } );
  //   }
  // }


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

  isSuperCompany(): boolean{
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
  }

  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
        } else {
          this._permisoService.superCompany = false;
        }
      },
      error => {
        console.log(error);
        this._permisoService.superCompany = false;
      });
  }

  rendererCompany(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService();
    }
  }


}
