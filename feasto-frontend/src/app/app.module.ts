import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CommonModule, NgOptimizedImage} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MainNavbarComponent} from "./main-navbar/main-navbar.component";
import {RestaurantPageComponent} from "./restaurant-page/restaurant-page.component";
import {CreateRestaurantComponent} from "./create-restaurant/create-restaurant.component";
import {TestCompComponent} from "./test-comp/test-comp.component";
import {RestaurantsListComponent} from "./restaurants-list/restaurants-list.component";
import {MainPageComponent} from "./main-page/main-page.component";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ObjectToArrayPipe} from "./pipes/ObjectToArrayPipe";
import {OffCanvasCartComponent} from "./off-canvas-cart/off-canvas-cart.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MessageExchangeComponent} from "./pages/message-exchange/message-exchange.component";
@NgModule({
  declarations: [
    AppComponent,
    MainNavbarComponent,
    MainPageComponent,
    RestaurantsListComponent,
    TestCompComponent,
    CreateRestaurantComponent,
    ObjectToArrayPipe,
    RestaurantPageComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    NgOptimizedImage,
    OffCanvasCartComponent,
    MessageExchangeComponent,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
