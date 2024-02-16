import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {MenuItemResponse} from "../../model/response/MenuItemResponse";

@Injectable({
  providedIn: 'root'
})
export class MenuItemService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }


  getCustomMenuItem(menuItemId: number){
    return this.http.get<MenuItemResponse>(`${this.baseUrl}/api/menu-item/${menuItemId}`);
  }
}
