import {Component, OnInit} from '@angular/core';
import {TreeNodeService} from "../../services/tree-node.service";
import {TreeNode} from "../../api/models/treeNode";

@Component({
  selector: 'app-tree-management',
  templateUrl: './tree-management.component.html',
  styleUrls: ['./tree-management.component.css']
})
export class TreeManagementComponent implements OnInit {

  constructor(private treeNodeService: TreeNodeService) {}

  ngOnInit(): void {
    this.treeNodeService.initRoot();
  }

  getRootTreeNode(): TreeNode {
    return this.treeNodeService.rootTreeNode;
  }
}
