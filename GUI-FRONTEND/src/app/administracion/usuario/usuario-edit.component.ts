import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Message, TreeNode} from "primeng/primeng";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {EmpresaService} from "../empresa/empresa.service";
import {MessageUtilService} from "../../utils/message-util.service";
import {CANT_FILAS, MESSAGEERROR, PRIMERA_PAGINA} from "../../utils/gop-constantes";
import {Empresa} from "../../model/empresa";
import {Usuario} from '../../model/usuario';
import { Rol } from '../../model/rol';
import {UsuarioService} from "./usuario.service";
import {UserDto} from "../../dto/usuario-dto/UserDto";
import {RolListResponseDto} from "../../dto/rol-dto/rol-list-response.dto";
import {RolService} from "../roles/rol.service";
import {RolFiltroRequestDto} from "../../dto/rol-dto/rol-filtro-request.dto";
import {DateUtilService} from "../../utils/date-util.service";
import {isUndefined} from "util";
import {PermisosService} from "../../security/permiso.service";
import {GeneralHelperService} from "../../utils/general-helper.service";
import {EmpresaShortDto} from "../../dto/empresa-dto/empresa-short.dto";
import {AutenticacionService} from "../../autenticacion/autenticacion.service";

@Component({
  selector: 'app-usuario-edit',
  templateUrl: './usuario-edit.component.html',
  styleUrls: ['./usuario-edit.component.css']
})
export class UsuarioEditComponent implements OnInit {

  //spinner
  loading = true;

  // Message
  msgs: Message[] = [];
  accionTitle: string = '';

  //DataTable
  totalRecords = 0;
  size = 0;
  page = 0;
  direction = '';
  properties = '';

  //
  isNewUsuario: boolean;
  isSuperUser: boolean;
  superCompany: boolean;
  userFormGroup: FormGroup;
  filteredCompany: any[];
  listaEmpresa: Empresa[];
  empresaSeleccionada: EmpresaShortDto[];
  hiddenCompanies: EmpresaShortDto[];
  rendererSmspinChr: boolean;
  rolesDt: RolListResponseDto[];
  rolSelected: Rol;
  filtroBusqueda: RolFiltroRequestDto ={ stateNum : true, defaultrolNum:null, rolnameChr:null};
  stateUser: any[] = [{ label: "Activo", value: "AC" },{ label: "Inactivo", value: "IN" } ];
  usuario: Usuario;


  constructor(private _activatedRoute: ActivatedRoute, private _router: Router, private _generalHelper: GeneralHelperService,
              private _formBuilder: FormBuilder, private _empresaService: EmpresaService, private _rolService: RolService,
              private _messageUtilService: MessageUtilService, private _usuarioService: UsuarioService,
              private  _dateUtil: DateUtilService, private _permisoService: PermisosService,
              private _authService: AutenticacionService)
  { }

  ngOnInit() {
    this.rolSelected = null;
    this.isNewUsuario = true;
    this.rendererSmspinChr = true;
    this.empresaSeleccionada = [];
    this.listaEmpresa = [];
    this.hiddenCompanies = [];
    this.loadRol();
    this.isEditOrNew();
    this.isSuperUserService();
    this.buildForm(null);
  }

  isSuperUserService(){
    this.isSuperUser = this._authService.isSuperUser;
  }
  onMoveToSource(event:any){
      if(this.usuario && this.usuario.emailChr == this._authService.getUsernameLogged()){
        let selectedCompanyName = this._authService.selectedCompany.companynameChr;
        if(this.objectContains(event.items,selectedCompanyName)){
          let empresaToDelete: Empresa = null;
          this.empresaSeleccionada.push(this._authService.selectedCompany);
          this.listaEmpresa.forEach(value => {if(value.companynameChr === selectedCompanyName)empresaToDelete=value});
          const index: number = this.listaEmpresa.indexOf(empresaToDelete);
          this.listaEmpresa.splice(index,1);
        }
      }
  }

  /**
   * Verifica la url, si tiene id significa que es una edicion de un usuario,
   * sino un nuevo autenticacion
   */
  isEditOrNew(){
    this._activatedRoute.params.subscribe( params => {
      if (params['id']) {
        this.accionTitle = 'Editar Usuario';
        this.isNewUsuario = false;
        this.getUsuarioById(params['id']);
      }else{
        this.accionTitle = 'Agregar Usuario';
        this.isNewUsuario = true;
        this.rendererCompany();
      }
    });
  }

  /**
   * Obtiene lista de todas las empresas
   * @returns {Subscription}
   */
  obtenerEmpresas(){
    this.loading=true;
    return this._empresaService.getAllEmpresas().subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          if(!this.isNewUsuario) {
            this.getCompaniesForUser(res.data.body.empresas);
          }else{
            this.listaEmpresa = res.data.body.empresas;
            this.loading=false;
          }
        } else {
          console.log(res.errors.message.message);
          this.loading=false;
        }
      },
      error => {
        console.log(error);
        this.loading=false;
        this.showError(error, MESSAGEERROR);
      });
  }
  isJustOneCompany(){
    return this._authService.companies.length<2 && !this.superCompany;
  }
  getCompaniesForUser(companiesAll : any) {
      for (let company of companiesAll) {
        if (!this.objectContains(this.empresaSeleccionada, company.companynameChr)) {
          this.listaEmpresa.push(company);
        }
      }
  }

  objectContains(obj, term: string): boolean {
    for (let key in obj){
      if(obj[key].companynameChr.indexOf(term) != -1) return true;
    }
    return false;
  }

  loadRol() {
      this.onBusquedaRoles(this.filtroBusqueda);
  }

  /**
   * Obtiene roles
   */
  onBusquedaRoles(criterio: any) {
    this.loading=true;
    this.msgs = null;
      this._rolService.getRolByCriPag(criterio, '', '', PRIMERA_PAGINA, CANT_FILAS)
        .subscribe((res: any) => {
            if (res.data != null && res.data.body != null) {
              this.rolesDt = res.data.body.roles;
              this.totalRecords = res.meta.totalCount;
              this.loading = false;
            } else {
              this.rolesDt = null;
              this.totalRecords = 0;
              this.loading = false;
            }
          },
          error => {
            console.log(error);
            this.loading=false;
          });
  }

  /**
   * Metodo que hace una peticion para traer datos ordenados
   * @param event
   * @returns {any}
   */
  onSort(event) {
    if (this.filtroBusqueda != null) {
      return this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
    }
  }

  /**
   * Metodo que hace una peticion para obtener otra pagina de la tabla
   * @param event
   */
  paginate(event) {
    this.page = this.size = 10;
    this.page = event.page + 1;
    this.size = event.rows;
    this.onBusquedaByCriPag(this.cargarCriterio(), this.direction, this.properties, this.page.toString(), this.size.toString());
  }


  cargarCriterio(){
    //verificar si es super compania luego
    if(this.userFormGroup.value.company && this.userFormGroup.value.company != null){
      this.onBusquedaRoles(this.filtroBusqueda);
    }else{
      this.onBusquedaRoles(this.filtroBusqueda);
    }
  }

  /**
   * Metodo que realiza una busqueda de roles de acuerdo al criterio introducido
   * @param c
   * @param {string} direction
   * @param {string} properties
   * @param {string} page
   * @param {string} size
   */
  onBusquedaByCriPag(criterio: any, direction: string, properties: string, page: string, size: string) {
    this.loading = true;
    this._rolService.getRolByCriPag(criterio, direction, properties, page, size).subscribe((res: any) => {
        if (res.data != null) {
          if (res.data.body != null) {
            this.rolesDt = res.data.body.roles;
            this.totalRecords = res.meta.totalCount;
            this.loading = false;
          }
        } else {
          this.rolesDt = null;
          this.totalRecords = 0;
          this.loading = false;
        }

      },
      error => {
        console.log(error);
        this.loading = false;
      });
  }

  /**
   * Navega al listado de usuarios
   */
  navigateUserList() {
    this._router.navigate(['/userList']);
  }

  /**
   * Arma e inicializa la estructura del formulario para creacion o edicion de
   * usuarios
   */
  buildForm(usuarioForm: Usuario){
    if(usuarioForm == null){
      usuarioForm = new Usuario();
    }
    //Se pone requerido los campos de acuerdo a si es nuevo o una edicion
    if (!this.isNewUsuario) {
      this.rendererSmspinChr = usuarioForm.smssignNum;
      this.userFormGroup = this._formBuilder.group({
        iduserPk: [usuarioForm.iduserPk],
        usernameChr: [usuarioForm.usernameChr, Validators.required],
        userlastnameChr: [usuarioForm.userlastnameChr, Validators.required],
        emailChr: [usuarioForm.emailChr, [Validators.required, Validators.email]],
        userjobtitleChr: [usuarioForm.userjobtitleChr, Validators.required],
        phonenumberChr: [usuarioForm.phonenumberChr, Validators.required],
        smspinChr: [null],
        smssignNum: [usuarioForm.smssignNum],
        notifysmsNum: [usuarioForm.notifysmsNum],
        notifyemailNum: [usuarioForm.notifyemailNum],
        codigoequipohumanoNum: [usuarioForm.codigoequipohumanoNum],
        stateChr: [usuarioForm.stateChr, Validators.required],
        creationdateTim: [{value: this._dateUtil.convertStringDate(usuarioForm.creationdateTim), disabled: true}],
        firmante:[usuarioForm.esFirmante]
      });
      if(this.isSuperUser)
        this.userFormGroup.addControl(
        'temporaryPasswordChr',
          new FormControl(null,[Validators.pattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$")])
        )
    } else {
      this.userFormGroup = this._formBuilder.group({
        iduserPk: [null],
        usernameChr: [null, Validators.required],
        userlastnameChr: [null, Validators.required],
        emailChr: [null, [Validators.required, Validators.email]],
        userjobtitleChr: [null, Validators.required],
        phonenumberChr: [null, Validators.required],
        temporaryPasswordChr: [null,[Validators.required ,Validators.pattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$")]],
        smspinChr: [null],
        smssignNum: [true],
        notifysmsNum: [true],
        notifyemailNum: [true],
        canviewcompanycreditNum: [false],
        codigoequipohumanoNum: [null],
        stateChr: [null],
        creationdateTim: [null],
        firmante:[false]
      });
    }
    this.userFormGroup.valueChanges
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
   * Obtiene un usuario por su id
   * @returns {Subscription}
   */
  getUsuarioById(usuarioId: number) {
    this.loading = true;
    return this._usuarioService.getUsuarioById(usuarioId).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this.buildForm(res.data.body.usuario);
          this.usuario = res.data.body.usuario;
          this.empresaSeleccionada = this.usuario.companies;
          this.rendererCompany();
          this.onBusquedaRoles(this.filtroBusqueda);
          this.rolSelected = this.usuario.rol;
          this.loading = false;
        } else {
          console.log(res.errors.message.message);
          this.loading = false;
        }
      },
      error => {
        console.log(error);
        this.loading = false;
        this.showError(error,MESSAGEERROR);
      });
  }

  /**
   *
   * @returns {UserAddDto}
   */
  cargarUsuarioForNew() : UserDto{
    let usuarioTemp = new UserDto();
    usuarioTemp.usernameChr = this.userFormGroup.value.usernameChr;
    usuarioTemp.userlastnameChr = this.userFormGroup.value.userlastnameChr;
    usuarioTemp.emailChr = this.userFormGroup.value.emailChr;
    usuarioTemp.idcompany = this.toIdArray(this.empresaSeleccionada);
    usuarioTemp.temporaryPasswordChr = this.userFormGroup.value.temporaryPasswordChr;
    usuarioTemp.userjobtitleChr = this.userFormGroup.value.userjobtitleChr;
    usuarioTemp.phonenumberChr = this.userFormGroup.value.phonenumberChr;
    usuarioTemp.smssignNum = this.userFormGroup.value.smssignNum;
    if(this.userFormGroup.value.smssignNum){
      usuarioTemp.smspinChr = this.userFormGroup.value.smspinChr;
    }
    usuarioTemp.notifysmsNum = this.userFormGroup.value.notifysmsNum;
    usuarioTemp.notifyemailNum = this.userFormGroup.value.notifyemailNum;
    usuarioTemp.codigoequipohumanoNum = this.userFormGroup.value.codigoequipohumanoNum;
    usuarioTemp.rolId = this.rolSelected.idrolPk;
    usuarioTemp.esFirmante = this.userFormGroup.value.firmante;
    return usuarioTemp;
  }

  /**
   * Carga campos para la edicion de un autenticacion
   * @returns {Empresa}
   */
  cargarUsuarioForEdit() : UserDto{
    let usuarioTemp = new UserDto();
    usuarioTemp.iduserPk = this.userFormGroup.value.iduserPk;
    usuarioTemp.stateChr = this.userFormGroup.value.stateChr;
    usuarioTemp.usernameChr = this.userFormGroup.value.usernameChr;
    usuarioTemp.userlastnameChr = this.userFormGroup.value.userlastnameChr;
    usuarioTemp.emailChr = this.userFormGroup.value.emailChr;
    let companiesToSave : EmpresaShortDto[] = [];
    this.empresaSeleccionada.forEach(empresa => companiesToSave.push(empresa));
    if(this.hiddenCompanies.length>1){
      this.hiddenCompanies.forEach( hidden => companiesToSave.push(hidden));
    }
    usuarioTemp.idcompany = this.toIdArray(companiesToSave);
    usuarioTemp.userjobtitleChr = this.userFormGroup.value.userjobtitleChr;
    usuarioTemp.phonenumberChr = this.userFormGroup.value.phonenumberChr;
    usuarioTemp.smssignNum = this.userFormGroup.value.smssignNum;
    if(this.userFormGroup.value.smssignNum){
      usuarioTemp.smspinChr = this.userFormGroup.value.smspinChr;
    }
    if(this.userFormGroup.value.temporaryPasswordChr){
      usuarioTemp.temporaryPasswordChr = this.userFormGroup.value.temporaryPasswordChr;
    }
    usuarioTemp.notifysmsNum = this.userFormGroup.value.notifysmsNum;
    usuarioTemp.notifyemailNum = this.userFormGroup.value.notifyemailNum;
    usuarioTemp.codigoequipohumanoNum = this.userFormGroup.value.codigoequipohumanoNum;
    usuarioTemp.rolId = this.rolSelected.idrolPk;
    usuarioTemp.esFirmante = this.userFormGroup.value.firmante;
    return usuarioTemp;
  }

  toIdArray(companies: EmpresaShortDto[]){
    let idCompanies : number[] = [];
    companies.forEach(value => idCompanies.push(value.idcompanyPk));
    return idCompanies;
  }

  /**
   * Submit, se encarga de redirigir al metodo correcto para la creacion o edicion de un usuario
   */
  saveUser(){
    if(this.userFormGroup.valid && this.isNewUsuario && this.rolSelected){
      this.crearUsuario(this.cargarUsuarioForNew());
    }else if(this.userFormGroup.valid && !this.isNewUsuario && this.rolSelected){
      this.editarUsuario(this.cargarUsuarioForEdit());
    }
  }

  /**
   * Crea nuevo usuario
   * @param {UserAddDto} usuario
   * @returns {Subscription}
   */
  crearUsuario(usuario: UserDto){
    this.loading = true;
    return this._usuarioService.crearUsuario(usuario).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/userList']);
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
   * Procesar la edicion de los campos de un usuario.
   * @param {Usuario} usuario
   * @returns {Subscription}
   */
  editarUsuario(usuario: UserDto) {
    this.loading = true;
    return this._usuarioService.editarUsuario(usuario).subscribe(
      (res: any) => {
        if (res.data != null && res.data.body != null) {
          this._messageUtilService.setMessagesComponent('info',res.data.body.mensaje,res.data.message.detail);
          this._router.navigate(['/userList']);
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

  filterCompany(event) {
    this.filteredCompany = [];
    if(this.listaEmpresa){
      for(let i = 0; i < this.listaEmpresa.length; i++) {
        let empresa = this.listaEmpresa[i];
        if(empresa.companynameChr.toLowerCase().indexOf(event.query.toLowerCase()) >= 0) {
          this.filteredCompany.push(empresa);
        }
      }
    }
  }

  onChanceFirmarSms(event: any){
    this.userFormGroup.patchValue({notifysmsNum:this.rendererSmspinChr = event});
  }
  onChangeNotifysmsNum(event: any){
    this.userFormGroup.patchValue({smssignNum:this.rendererSmspinChr=event});
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

  isSuperCompany(): boolean{
    this.superCompany = this._permisoService.superCompany;
    return this.superCompany;
  }

  /**
   * Verifica si es super empresa, en caso de no serlo ya trae los roles de la empresa
   * del usuario logueado.
   */
  rendererCompany(){
    if(isUndefined(this.isSuperCompany())){
      this.isSuperCompanyService()
    }else if(this.isSuperCompany()){
      this.obtenerEmpresas();
    }else{
      this.obtenerEmpresasPorUsuario();
      this.onBusquedaRoles(this.filtroBusqueda);
    }
  }
  obtenerEmpresasPorUsuario(){
    if(this._authService.companies.length<2){
      let userLoggedCompanies = this._authService.companies;
      this.empresaSeleccionada.forEach( company =>{
        if(!this.objectContains(userLoggedCompanies,company.companynameChr)){
          this.hiddenCompanies.push(company);
        }
      });
      this.empresaSeleccionada = userLoggedCompanies;
    }else{
      this.listaEmpresa = this.toEmpresa(this._authService.companies);
      let companiesAux : EmpresaShortDto[] =[];
      this.empresaSeleccionada.forEach(company => {
        if(this.objectContains(this.listaEmpresa,company.companynameChr)) {
          companiesAux.push(company);
        }else
          this.hiddenCompanies.push(company);
      });
      this.empresaSeleccionada = companiesAux;
      companiesAux =[];
      this.listaEmpresa.forEach(company => {
        if(!this.objectContains(this.empresaSeleccionada,company.companynameChr)) {
          companiesAux.push(company);
        }
      });
      this.listaEmpresa = this.toEmpresa(companiesAux);

    }
  }

  toEmpresa(empresaShort: EmpresaShortDto[]) : Empresa[]{
    let empresaArray : Empresa[] = [];
    for(let empresaS of empresaShort){
      let empresa = new Empresa();
      empresa.idcompanyPk=empresaS.idcompanyPk;
      empresa.companynameChr = empresaS.companynameChr;
      empresaArray.push(empresa);
    }
    return empresaArray;
  }

  /***
   * Llamada a servicio para obtener si es de una super compañia
   * @returns {Subscription}
   */
  isSuperCompanyService(){
    this.loading = true;
    return this._empresaService.isSuperCompany().subscribe((res: any) => {
        if (res.data != null && res.data.body != null) {
          this._permisoService.superCompany = res.data.body.superCompany;
          if(res.data.body.superCompany){
            this.obtenerEmpresas();
          }else{
            this.obtenerEmpresasPorUsuario();
            this.onBusquedaRoles(this.filtroBusqueda);
          }
        } else {
          this._permisoService.superCompany = false;
          this.loading=false;
        }
      },
      error => {
        this.loading = false;
        console.log(error);
        this._permisoService.superCompany = false;
      });
  }

}
