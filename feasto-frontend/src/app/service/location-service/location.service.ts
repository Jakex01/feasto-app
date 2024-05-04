import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LocationModel} from "../../model/LocationModel";
import {Observable} from "rxjs";
import {Location} from "@angular/common";
import {LocationRequest} from "../../model/request/LocationRequest";

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  private baseUrl = 'http://localhost:8083/api/location';

  constructor(private http: HttpClient) { }


  getAllLocationsByUser(): Observable<LocationModel[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<LocationModel[]>(this.baseUrl, { headers });
  }
  postLocation(location: LocationRequest){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.post(this.baseUrl, location, {headers});
  }
  updateLocation(location: LocationModel[]): Observable<any> {
    return this.http.put(this.baseUrl, location);
  }


}
