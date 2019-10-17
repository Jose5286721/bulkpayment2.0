import {NgModule} from "@angular/core";
import {
  DataTableModule,
  GrowlModule,
  MenubarModule,
  PaginatorModule,
  PanelModule,
  InputTextModule,
  ButtonModule,
  FieldsetModule,
  PasswordModule,
  InputMaskModule,
  InputTextareaModule,
  AutoCompleteModule,
  DropdownModule,
  CheckboxModule,
  TreeModule,
  PickListModule,
  ConfirmDialogModule,
  TooltipModule,
  DialogModule, FileUploadModule, ChipsModule, OrderListModule, SelectButtonModule
} from "primeng/primeng";
@NgModule({
  imports: [
    DataTableModule, GrowlModule, MenubarModule, PaginatorModule, PanelModule, InputTextModule, ButtonModule,OrderListModule,
    FieldsetModule, PasswordModule, InputMaskModule, InputTextareaModule, AutoCompleteModule, DropdownModule,
    CheckboxModule, TreeModule, PickListModule, ConfirmDialogModule, TooltipModule, DialogModule, FileUploadModule, ChipsModule,SelectButtonModule],
  exports: [
    DataTableModule, GrowlModule, MenubarModule, PaginatorModule, PanelModule, ButtonModule, FieldsetModule, OrderListModule,
    PasswordModule, InputMaskModule, InputTextareaModule, AutoCompleteModule, DropdownModule,SelectButtonModule,
    CheckboxModule, InputTextModule, TreeModule, PickListModule, ConfirmDialogModule, TooltipModule, DialogModule, FileUploadModule, ChipsModule, ConfirmDialogModule]
})
export class PrimengModule {
}
