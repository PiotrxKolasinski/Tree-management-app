import {Component, Input} from '@angular/core';
import {TreeNode} from "../../../api/models/treeNode";
import {TreeNodeService} from "../../../services/tree-node.service";

@Component({
  selector: 'app-tree-node-list',
  templateUrl: './tree-node-list.component.html',
  styleUrls: ['./tree-node-list.component.css']
})
export class TreeNodeListComponent {
  @Input() nodes: TreeNode[];
  @Input() treeLevel: number;

  constructor(private treeNodeService: TreeNodeService) {
  }

  hasChildren(node: TreeNode): boolean {
    return this.treeNodeService.hasChildren(node);
  }
}
