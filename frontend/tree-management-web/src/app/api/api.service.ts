import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) {
  }

  get<T = any>(endpoint: string, options?: { params: HttpParams, headers: HttpHeaders }) {
    return this.http.get(this.getUrl(endpoint), options);
  }

  post<T = any>(endpoint: string, data ?: any, options?: { params: HttpParams, headers: HttpHeaders }) {
    return this.http.post(this.getUrl(endpoint), data, options);
  }

  put<T = any>(endpoint: string, data ?: any, options?: { params: HttpParams, headers: HttpHeaders }) {
    return this.http.put(this.getUrl(endpoint), data, options);
  }

  delete(endpoint: string, options?: { params: HttpParams, headers: HttpHeaders }) {
    return this.http.delete(this.getUrl(endpoint), options);
  }

  private getUrl(endpoint: string) {
    return environment.url + endpoint;
  }
}
