import {Component, Input} from '@angular/core';
import {TreeNode} from "../../../../api/models/treeNode";
import {TreeNodeService} from "../../../../services/tree-node.service";
import {TreeNodeItemManagementDialogComponent} from "./tree-node-item-management-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-tree-node-item',
  templateUrl: './tree-node-item.component.html',
  styleUrls: ['./tree-node-item.component.css']
})
export class TreeNodeItemComponent {
  @Input() node: TreeNode;
  @Input() index: number;
  @Input() treeLevel: number;

  constructor(private treeNodeService: TreeNodeService, private dialog: MatDialog) {
  }

  showManagementNodeDialog() {
    let valueBefore = this.node.value;
    const dialogRef = this.dialog.open(TreeNodeItemManagementDialogComponent, {
      width: '300px',
      data: this.node
    });

    dialogRef.afterClosed().subscribe(() => {
      if (valueBefore != this.node.value) {
        this.treeNodeService.updateNode(this.node).subscribe(() => {
          this.treeNodeService.getRoot().subscribe((response: TreeNode) => {
            this.treeNodeService.rootTreeNode = response;
          })
        });
      }
    });
  }

  addChild() {
    this.treeNodeService.addNode(this.node).subscribe((response: TreeNode) => this.node.children.push(response));
  }

  remove() {
    this.treeNodeService.removeNode(this.node).subscribe(() => {
      this.treeNodeService.initRoot();
    });
  }

  getNodeColor() {
    if (this.isRootNode()) return '#800000';
    else if (this.isLeafNode()) return '#006400';
    else return '#654321';
  }

  private isRootNode() {
    return this.treeLevel === 0;
  }

  private isLeafNode() {
    return !this.treeNodeService.hasChildren(this.node);
  }
}
