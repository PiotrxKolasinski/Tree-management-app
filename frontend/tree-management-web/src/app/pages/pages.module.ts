import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TreeManagementComponent} from "./tree-management/tree-management.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {TreeNodeItemComponent} from './tree-management/tree-node-list/tree-node-item/tree-node-item.component';
import {TreeNodeListComponent} from './tree-management/tree-node-list/tree-node-list.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatTreeModule} from "@angular/material/tree";
import {MatIconModule} from "@angular/material/icon";
import {TreeNodeItemManagementDialogComponent} from "./tree-management/tree-node-list/tree-node-item/tree-node-item-management-dialog.component";


@NgModule({
  declarations: [
    TreeManagementComponent,
    PageNotFoundComponent,
    TreeNodeItemComponent,
    TreeNodeListComponent,
    TreeNodeItemManagementDialogComponent
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    FormsModule,
    MatDialogModule,
    MatInputModule,
    MatTreeModule,
    MatIconModule,
    MatButtonModule
  ],
})
export class PagesModule {
}
