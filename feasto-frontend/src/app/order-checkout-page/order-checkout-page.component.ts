import {Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PaymentService} from "../service/payment-service/payment.service";
import {PaymentRequest} from "../model/request/PaymentRequest";
import {Subscription} from "rxjs";
import {SharedDataService} from "../service/shared-data/shared-data.service";
import {OrderRequest} from "../model/request/OrderRequest";

@Component({
  selector: 'app-order-checkout-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-checkout-page.component.html',
  styleUrl: './order-checkout-page.component.css'
})
export class OrderCheckoutPageComponent implements OnInit{

  paymentRequest: PaymentRequest;
  selectedPaymentMethod: string = '';
  private subscription: Subscription;

  selectPaymentMethod(method: string) {
    this.selectedPaymentMethod = method;
  }
  constructor(private paymentService: PaymentService,
              private sharedDataService: SharedDataService) {
  }
  finalOrderRequest: OrderRequest  | null= {
    items: [],
    totalPrice: 0,
    restaurantId: 0,
    orderNote: ''
  };
  ngOnInit(): void {
        this.subscription = this.sharedDataService.currentOrderRequest$.subscribe((order)=>{
          this.finalOrderRequest = order;
        })
    }

  placeOrder(){
    console.log("here")
    const paymentRequest = {
      amount: "10.00",
      currency: "USD",
      method: "paypal",
      description: "Payment description"
    };
    this.selectPaymentMethod('paypal');

    if(this.selectedPaymentMethod === 'paypal') {
      this.paymentService.createPayment(paymentRequest).subscribe({
        next: (response) => {
          window.location.href = response.url;
        },
        error: (error) => {
          console.error('Payment creation failed', error);
        }
      });
    }
  }

}
