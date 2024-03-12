import { Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import * as bootstrap from 'bootstrap';
import {OrderRequest} from "../model/request/OrderRequest";

import {MenuItemOrderModel} from "../model/MenuItemOrderModel";
import {Subscription} from "rxjs";
import {SharedDataService} from "../service/shared-data/shared-data.service";

@Component({
  selector: 'app-off-canvas-cart',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './off-canvas-cart.component.html',
  styleUrl: './off-canvas-cart.component.css'
})
export class OffCanvasCartComponent implements OnInit{


  @ViewChild('modal') modalElementRef: ElementRef;

  private subscription: Subscription;

  menuItemOrder: MenuItemOrderModel | null={
    menuItemId: 0,
    name: '',
    description: '',
    available: true,
    category: '',
    foodAdditivePrices: {},
    selectedSize: { size: '', price: 0 },
    generalPrice: 0,
    quantity: 0,
    note: ''
  };

  orderRequest: OrderRequest = {
    items: [],
    totalPrice: 0,
    restaurantId: 0,
    orderNote: ''
  };

  constructor(private sharedDataService: SharedDataService) {}
  ngOnInit(): void {
    this.subscription = this.sharedDataService.currentMenuItemOrder$.subscribe((order) => {
      this.menuItemOrder = order;
      if(this.menuItemOrder && this.menuItemOrder.generalPrice){
        this.orderRequest.items.push(this.menuItemOrder);
        this.orderRequest.totalPrice += this.menuItemOrder.generalPrice;
      console.log(this.orderRequest);
      }

    });

  }

  showModal(): void {

    setTimeout(() => {
      if (this.modalElementRef.nativeElement) {
        const offcanvasElement = new bootstrap.Offcanvas(this.modalElementRef.nativeElement);
        offcanvasElement.show();
      }
    }, 0);

  }
  sendOrderRequestToCheckout(){
    this.sharedDataService.updateCheckOutOrder(this.orderRequest);
  }



}
