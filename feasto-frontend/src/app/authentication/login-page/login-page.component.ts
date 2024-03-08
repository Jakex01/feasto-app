import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../model/request/AuthenticationRequest";
import {AuthenticationResponse} from "../../model/response/AuthenticationResponse";
import {AuthenticationService} from "../../service/authentication-service/authentication.service";
import {Router} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {RegisterRequest} from "../../model/request/RegisterRequest";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

  authRequest: AuthenticationRequest = {
  email: '',
    password: ''
  };
  authResponse: AuthenticationResponse = {
    accessToken: '',
    refreshToken: ''
  };

  registerRequest: RegisterRequest = {
    firstname: "",
    lastname: "",
    email: "",
    password: "",
    role: "USER"
  }

  rightPanelActive = false;


  constructor(
    private authService: AuthenticationService,
    private router: Router,

  ) { }

  togglePanel(active: boolean): void {
    this.rightPanelActive = active;
  }
  authenticate() {


    this.authService.login(this.authRequest)
      .subscribe({
        next: (response) => {
          this.authResponse = response;
            localStorage.setItem('token', response.accessToken as string);
            this.router.navigate(['main']);
        }
      });
  }
  registerUser() {

    console.log(this.registerRequest)
    this.authService.register(this.registerRequest)
      .subscribe(
        {
          next: (response)=>{
            if(response){
              this.authResponse = response;
            }else{
              setTimeout(()=>{
                this.router.navigate(['login'])
              }, 3000)
            }
          }
        }
      )

  }


}
