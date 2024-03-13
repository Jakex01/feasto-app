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
  paymentMethodSelected: boolean = false;
  selectedTip: number | null = null;
  selectPaymentMethod(method: string) {

    this.selectedPaymentMethod = method;
  }
  forwardToSummary(){
    this.paymentMethodSelected = true;
  }
  constructor(private paymentService: PaymentService,
              private sharedDataService: SharedDataService) {
  }
  finalOrderRequest: OrderRequest | null = {
    items: [{
      menuItemId: 0,
      name: '',
      description: '',
      available: true,
      category: '',
      foodAdditivePrices: {},
      selectedSize: { size: '', price: 0 },
      generalPrice: 0,
      quantity: 1,
      note: ''
    }],
    totalPrice: 0,
    restaurantId: 0,
    orderNote: ''
  };

  foodAdditivePricesArray: {name: string, price: number}[];
  ngOnInit(): void {
        this.subscription = this.sharedDataService.currentOrderRequest$.subscribe((order)=>{
          this.finalOrderRequest = order;
          if(this.finalOrderRequest){
          this.finalOrderRequest.items.forEach(item => {
            this.foodAdditivePricesArray = Object.entries(item.foodAdditivePrices).map(([name, price]) => ({
              name, price
            }));
          });
          }
        })
    }
    testFunc(){
    this.paymentService.test().subscribe({
      next: (response) => {
        console.log(response);
      }
    })
    }

  placeOrder(){



    let payment=0;

    if(this.finalOrderRequest?.totalPrice){

      if(this.selectedTip){
         payment = this.finalOrderRequest.totalPrice + this.finalOrderRequest.totalPrice*this.selectedTip;
      }else{
         payment = this.finalOrderRequest.totalPrice;
      }
      const paymentRequest = {
        amount: payment,
        currency: "PLN",
        method: "paypal",
        description: "Payment"
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




}
