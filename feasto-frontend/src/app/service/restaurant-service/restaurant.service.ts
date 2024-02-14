import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {RestaurantDtoModel} from "../../model/RestaurantDtoModel";
import {Observable} from "rxjs";
import {RestaurantFilterModel} from "../../model/RestaurantFilterModel";
import {CreateRestaurantModel} from "../../model/CreateRestaurantModel";
import {RestaurantResponse} from "../../model/response/RestaurantResponse";

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }


  getAllRestaurants(): Observable<RestaurantDtoModel[]> {

    return this.http.get<RestaurantDtoModel[]>(`${this.baseUrl}/api/restaurant`);
  }
  getFilteredRestaurants(filters: RestaurantFilterModel): Observable<RestaurantDtoModel[]>{

    const params = new HttpParams()
      .set('foodType', filters.foodType)
      .set('rating', filters.rating)
      .set('priceRange', filters.priceRange)

    return this.http.get<RestaurantDtoModel[]>(`${this.baseUrl}/api/restaurant/filter`, {params})
  }
  postRestaurant(restaurant: CreateRestaurantModel){
    return this.http.post(`${this.baseUrl}/api/restaurant`, restaurant);
  }
  getRestaurantById(restaurantId: number){
    return this.http.get<RestaurantResponse>(`${this.baseUrl}/api/restaurant/${restaurantId}`);
  }


}

