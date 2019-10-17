import {Injectable} from "@angular/core";
import {Message} from "primeng/primeng";

@Injectable()
export class MessageUtilService {

  msgs: Message[] = [];

  constructor() {

  }

  setMessagesComponent(severity: string, summary: string, detail: string){
    this.msgs = [];
    this.msgs.push({severity: severity, summary: summary, detail: detail});
  }

  getMsgs(): Message[]{
    return this.msgs;
  }

  setMsgs(mensaje: Message[]){
    this.msgs = mensaje;
  }

}
