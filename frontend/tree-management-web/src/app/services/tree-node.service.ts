import {Injectable} from '@angular/core';
import {TreeNode} from "../api/models/treeNode";
import {TreeNodeApiService} from "../api/services/tree-node-api.service";
import {TreeNodeCreation} from "../api/models/treeNodeCreation";
import {TreeNodeUpdate} from "../api/models/treeNodeUpdate";

@Injectable({
  providedIn: 'root'
})
export class TreeNodeService {
  rootTreeNode: TreeNode;
  private copiedTreeNode: TreeNode;

  constructor(private treeNodeApiService: TreeNodeApiService) {
  }

  initRoot() {
    this.createRootTreeNode().subscribe(
      (response: TreeNode) => this.rootTreeNode = response,
      (error) => {
        if (error.status === 409) this.getRoot().subscribe((response: TreeNode) => {
          this.rootTreeNode = response;
        })
      }
    );
  }

  private createRootTreeNode() {
    return this.treeNodeApiService.save(new TreeNodeCreation(0, null, []));
  }

  getRoot() {
    return this.treeNodeApiService.getRoot();
  }

  addNode(parentNode: TreeNode) {
    return this.treeNodeApiService.save(new TreeNodeCreation(0, parentNode.id, []));
  }

  updateNode(node: TreeNode) {
    return this.treeNodeApiService.update(node.id, new TreeNodeUpdate(node.value));
  }

  removeNode(node: TreeNode) {
    return this.treeNodeApiService.remove(node.id);
  }

  copyNodeStructure(currentNode: TreeNode) {
    this.copiedTreeNode = {...currentNode};
  }

  cutNodeStructure(currentNode: TreeNode) {
    this.copyNodeStructure(currentNode);
    this.removeNode(currentNode).subscribe(() => this.initRoot());
  }

  pasteNodeStructure(parentNode: TreeNode) {
    if (this.copiedTreeNode === undefined) return;
    let request = this.prepareTreeNodeCreation(this.copiedTreeNode);
    request.parentId = parentNode.id;
    return this.treeNodeApiService.save(request);
  }

  prepareTreeNodeCreation(node: TreeNode): TreeNodeCreation {
    return this.hasChildren(node) ? new TreeNodeCreation(node.value, null, node.children.map(child => this.prepareTreeNodeCreation(child)))
      : new TreeNodeCreation(node.value, null, []);
  }

  hasChildren(node: TreeNode): boolean {
    return node.children !== undefined && node.children !== null && node.children.length !== 0;
  }
}
