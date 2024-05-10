import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8081/api'; // Adjust if your backend URL is different

  constructor(private http: HttpClient) { }

  createPayment(paymentRequest: any): Observable<any> {
    console.log(`${this.baseUrl}/payment/create`);
    return this.http.post(`${this.baseUrl}/payment/create`, paymentRequest);
  }
  test(){
    return this.http.get(`${this.baseUrl}/`);
  }

}
