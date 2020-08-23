import {Injectable} from '@angular/core';
import {ApiService} from "../api.service";
import {ENDPOINTS} from "../config/endpoints";

@Injectable({
  providedIn: 'root'
})
export class TreeNodeApiService {

  constructor(private apiService: ApiService) {
  }

  getRoot() {
    return this.apiService.get(ENDPOINTS.TREE.NODE.GET_ROOT);
  }

  save(treeNode) {
    return this.apiService.post(ENDPOINTS.TREE.NODE.SAVE, treeNode);
  }

  update(id, treeNode) {
    return this.apiService.put(ENDPOINTS.TREE.NODE.UPDATE + '/' + id, treeNode);
  }

  remove(id) {
    return this.apiService.delete(ENDPOINTS.TREE.NODE.REMOVE + "/" + id);
  }

}
