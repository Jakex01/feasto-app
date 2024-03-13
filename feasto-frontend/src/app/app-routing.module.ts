import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RestaurantsListComponent} from "./restaurants-list/restaurants-list.component";
import {MainPageComponent} from "./main-page/main-page.component";
import {CreateRestaurantComponent} from "./create-restaurant/create-restaurant.component";
import {RestaurantPageComponent} from "./restaurant-page/restaurant-page.component";
import {TestCompComponent} from "./test-comp/test-comp.component";
import {LoginPageComponent} from "./authentication/login-page/login-page.component";
import {RegisterPageComponent} from "./authentication/register-page/register-page.component";
import {OrderCheckoutPageComponent} from "./order-checkout-page/order-checkout-page.component";

import {TestkubaComponent} from "./testkuba/testkuba.component";

const routes: Routes = [

  { path: 'restaurants',
    component: RestaurantsListComponent,
    data: { animation: 'RestaurantsPage' }
  },
  {
    path: 'main',
    component: MainPageComponent
  },
  {
    path:'test',
    component:CreateRestaurantComponent
  },
  {
    path: 'restaurants/:id',
    component: RestaurantPageComponent,
    data: { animation: 'RestaurantPage' }
  },
  {
    path: 'testing',
    component: TestCompComponent
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'register',
    component: RegisterPageComponent
  },
  {
    path: 'checkout',
    component: OrderCheckoutPageComponent
  },
  {
    path: 'test1',
    component:TestkubaComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
