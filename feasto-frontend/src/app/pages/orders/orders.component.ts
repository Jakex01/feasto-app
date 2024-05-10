import { Component } from '@angular/core';
import {CommonModule} from "@angular/common";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Order} from "../../model/Order";

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css',
  animations: [
    trigger('fadeSlideInOut', [
      state('in', style({ opacity: 1, transform: 'translateX(0)' })),
      transition(':enter', [
        style({ opacity: 0, transform: 'translateX(-50%)' }),
        animate('300ms ease-out')
      ]),
      transition(':leave', [
        animate('300ms ease-out', style({ opacity: 0, transform: 'translateX(50%)' }))
      ])
    ])
  ]
})
export class OrdersComponent {
  selectedTab = 'history';

  historyOrders: Order[] = [
    {
      id: 1,
      items: [
        { foodItem: 'Pizza Margherita', size: 'large', addOns: ['extra cheese'], price: 5.0, quantity: 1 },
        { foodItem: 'Coca Cola', size: '0.5L', addOns: [], price: 1.5, quantity: 1 }
      ],
      orderDate: '2023-04-21',
      deliveryDate: '2023-04-21',
      totalPrice: 6.5
    },
    {
      id: 2,
      items: [
        { foodItem: 'Burger wołowy', size: 'standard', addOns: ['frytki', 'sos'], price: 7.0, quantity: 1 },
        { foodItem: 'Lemoniada', size: '0.5L', addOns: [], price: 2.0, quantity: 1 }
      ],
      orderDate: '2023-04-22',
      deliveryDate: '2023-04-22',
      totalPrice: 9.0
    }
  ];

  currentOrders = [
    {
      id: '001',
      restaurantName: 'Restauracja Italiano',
      details: '2x Pizza Margherita, 1x Tiramisu',
      orderDate: new Date(),
      expectedDelivery: new Date(new Date().getTime() + 45 * 60000), // Oczekiwany czas dostawy +45 minut
      deliveryAddress: 'ul. Marszałkowska 58, Warszawa',
      status: 'W trakcie realizacji',
      amount: 75,
      progress: 50
    },
    {
      id: '002',
      restaurantName: 'Burger King',
      details: '1x Whopper, 1x Fries Large, 1x Coke',
      orderDate: new Date(),
      expectedDelivery: new Date(new Date().getTime() + 30 * 60000),
      deliveryAddress: 'ul. Piotrkowska 120, Łódź',
      status: 'Przygotowywany',
      amount: 45,
      progress: 75
    }
  ];

  selectTab(tab: string) {
    this.selectedTab = tab;
  }
}
