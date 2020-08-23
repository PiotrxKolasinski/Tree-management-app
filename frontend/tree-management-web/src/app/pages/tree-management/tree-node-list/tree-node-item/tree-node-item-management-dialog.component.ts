import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {TreeNodeService} from "../../../../services/tree-node.service";
import {TreeNode} from "../../../../api/models/treeNode";

@Component({
  selector: 'tree-node-item-management-dialog',
  template: `
    <div mat-dialog-content>
      <p>Set new value for node</p>
      <mat-form-field style="width: 100%">
        <mat-label>New value</mat-label>
        <input type="number" matInput [(ngModel)]="data.value">
      </mat-form-field>
    </div>
    <div mat-dialog-actions>
      <button mat-button (click)="copyNodeStructure()" cdkFocusInitial style="width: 30%">Copy</button>
      <button *ngIf="data.parent !== undefined" mat-button (click)="cutNodeStructure()" cdkFocusInitial style="width: 30%">Cut</button>
      <button mat-button (click)="pasteNodeStructure()" cdkFocusInitial style="width: 30%">Paste</button>
    </div>
  `
})
export class TreeNodeItemManagementDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: TreeNode, private treeNodeService: TreeNodeService) {
  }

  copyNodeStructure() {
    this.treeNodeService.copyNodeStructure(this.data);
  }

  cutNodeStructure() {
    this.treeNodeService.cutNodeStructure(this.data);
  }

  pasteNodeStructure() {
    this.treeNodeService.pasteNodeStructure(this.data)
      .subscribe((response: TreeNode) => this.data.children.push(response));
  }
}

