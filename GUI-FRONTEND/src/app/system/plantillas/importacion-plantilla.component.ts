import {Component, OnInit, ViewChild} from '@angular/core';
import {FileUpload, Message} from "primeng/primeng";
import {TemplatesService} from "./templates.service";
import {COMPLETE_EXAMPLE_TEMPLATE, SHORT_EXAMPLE_TEMPLATE} from "../../utils/gop-constantes";
import {isComponentView} from "@angular/core/src/view/util";

@Component({
  selector: 'app-log-mts',
  templateUrl: './importacion-plantilla.component.html',
  styleUrls: ['./importacion-plantilla.component.css']
})
export class ImportacionPlantillaComponent implements OnInit {
  @ViewChild('fileUpload') fileUpload: FileUpload;
  //spinner
  loading = false;
  uploadedFiles: File[] = [];
  csvmimetype = 'application/csv, application/x-csv,text/csv, text/comma-separated-values, text/x-comma-separated-values';
  // Message
  msgs: Message[] = [];
  selectedExample: string;
  selectedFile: File;
  canLoad:boolean = false;
  examples:any = [{label:'Corta', value: SHORT_EXAMPLE_TEMPLATE},{label:'Completa', value: COMPLETE_EXAMPLE_TEMPLATE}]

  constructor( private _templateService : TemplatesService
  ) { }

  ngOnInit() {
  }
  selectFile(event) {
    console.log(event.files);
    let files = event.files;
    if (files && files.length > 0) {
      this.selectedFile = <File>(files[0]).valueOf();
      if(this.isValidType(this.selectedFile.type))
        this.canLoad = true;
      else
        this.selectedFile = null;
    }
  }

  isValidType(type:string){
    return !!(this.csvmimetype && this.csvmimetype.search(type) != -1);
  }

  loadFile() {
    this.loading = true;
    this._templateService.importExampleCsv(this.selectedFile,this.selectedExample).subscribe((res:any) =>{
      if(res && res.data){
        this.showMsg('info',res.data.message.detail, res.data.message.message);
      }else{
        this.showMsg('error',res.errors.message.detail, res.errors.message.message);
      }
        this.loading = false;
      }, error => {
        console.log(error);
        this.loading = false;
      }
    );
  }


  showMsg(severity: string,detail: string, mensaje: string) {
    this.msgs = [];
    this.msgs.push({severity:severity, summary:mensaje, detail:detail});
  }



}
