import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RatingResponse} from "../../model/response/RatingResponse";

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAverageComment(restaurantId: number, averageRating?: number){
    let queryParams = '';
    if (averageRating !== undefined) {
      queryParams = `?averageRating=${averageRating}`;
    }

    return this.http.get<RatingResponse>(`${this.baseUrl}/api/rating/average/${restaurantId}${queryParams}`);
  }
  getRestaurantComments(restaurantId: number){
    return this.http.get<RatingResponse[]>(`${this.baseUrl}/api/rating/${restaurantId}`);
  }

}
