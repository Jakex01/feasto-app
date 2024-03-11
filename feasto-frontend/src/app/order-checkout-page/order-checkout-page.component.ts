import {Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PaymentService} from "../service/payment-service/payment.service";
import {PaymentRequest} from "../model/request/PaymentRequest";

@Component({
  selector: 'app-order-checkout-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './order-checkout-page.component.html',
  styleUrl: './order-checkout-page.component.css'
})
export class OrderCheckoutPageComponent{

  paymentRequest: PaymentRequest;
  selectedPaymentMethod: string = '';

  selectPaymentMethod(method: string) {
    this.selectedPaymentMethod = method;
  }
  constructor(private paymentService: PaymentService) {
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
