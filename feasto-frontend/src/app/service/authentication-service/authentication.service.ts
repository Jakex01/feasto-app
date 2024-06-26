import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterRequest} from "../../model/request/RegisterRequest";
import {AuthenticationRequest} from "../../model/request/AuthenticationRequest";
import {AuthenticationResponse} from "../../model/response/AuthenticationResponse";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private baseUrl = 'http://localhost:8083/api/auth';

  constructor(private http: HttpClient) { }

  register(
    registerRequest: RegisterRequest
  ){
    return this.http.post<AuthenticationResponse>
    (`${this.baseUrl}/register`, registerRequest);
  }
  login(
    authRequest: AuthenticationRequest
  ) {
    return this.http.post<AuthenticationResponse>
    (`${this.baseUrl}/authenticate`, authRequest);
  }
}
