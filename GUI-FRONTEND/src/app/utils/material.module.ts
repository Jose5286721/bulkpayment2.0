import {
  MatButtonModule, MatCheckboxModule, MatAutocompleteModule,
  MatInputModule, MatRadioModule, MatSelectModule, MatMenuModule, MatSidenavModule,
  MatToolbarModule, MatCardModule, MatTabsModule, MatChipsModule, MatIconModule,
  MatProgressSpinnerModule, MatDialogModule, MatTooltipModule, MatListModule, MatDatepickerModule, MatNativeDateModule,
  MatGridListModule, MatSlideToggleModule, MatTableModule, MatSortModule, MatPaginatorModule, MatFormFieldModule,
  MatStepperModule, MatExpansionModule, MatProgressBarModule
} from '@angular/material';

import {NgModule} from "@angular/core";

@NgModule({
  imports: [MatButtonModule, MatCheckboxModule, MatAutocompleteModule,
    MatInputModule, MatRadioModule, MatSelectModule, MatMenuModule,MatSidenavModule,
    MatToolbarModule,MatCardModule, MatTabsModule, MatChipsModule, MatIconModule, MatProgressBarModule,
    MatProgressSpinnerModule, MatDialogModule, MatTooltipModule, MatListModule, MatDatepickerModule, MatNativeDateModule,
    MatGridListModule, MatSlideToggleModule, MatTableModule, MatSortModule, MatPaginatorModule, MatFormFieldModule, MatStepperModule, MatExpansionModule],
  exports: [MatButtonModule, MatCheckboxModule, MatAutocompleteModule,
    MatInputModule, MatRadioModule, MatSelectModule, MatMenuModule,MatSidenavModule,
    MatToolbarModule,MatCardModule, MatTabsModule, MatChipsModule, MatIconModule, MatProgressBarModule,
    MatProgressSpinnerModule, MatDialogModule, MatTooltipModule, MatListModule, MatDatepickerModule, MatNativeDateModule,
    MatGridListModule, MatSlideToggleModule, MatTableModule, MatSortModule, MatPaginatorModule, MatFormFieldModule, MatStepperModule, MatExpansionModule],
})
export class MaterialModule { }
