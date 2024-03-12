import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {CustomMenuItemResponse} from "../model/response/CustomMenuItemResponse";
import {MenuItemOrderModel} from "../model/MenuItemOrderModel";
import {SharedDataService} from "../service/shared-data/shared-data.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-test-comp',
  templateUrl: './test-comp.component.html',
  styleUrls: ['./test-comp.component.css']
})
export class TestCompComponent implements OnInit{

  @ViewChild('modal') modal:ElementRef;

  @Output() sendOrderToParent: EventEmitter<MenuItemOrderModel> = new EventEmitter();
  restaurantId: number | null = null;
  constructor(private sharedDataService: SharedDataService,
              private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('restaurantId');
      this.restaurantId = id ? +id : null;
    });
    }
  customMenuItem: CustomMenuItemResponse;
  selectedSizeKey: string;

  menuItemOrder: MenuItemOrderModel={
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
  showModal1 = false;


  selectedAdditives: { [key: string]: boolean } = {};
  updateSelectedSize(sizeKey: string): void {
    if (this.customMenuItem.sizesWithPrices.hasOwnProperty(sizeKey)) {
      this.menuItemOrder.selectedSize.size = sizeKey;
      this.menuItemOrder.selectedSize.price = this.customMenuItem.sizesWithPrices[sizeKey];
    }

  }
  calculateTotalPrice(): number {
    let total = this.customMenuItem.sizesWithPrices[this.menuItemOrder.selectedSize.size];

    Object.keys(this.selectedAdditives).forEach(key => {
      if (this.selectedAdditives[key]) {
        total += this.customMenuItem.foodAdditivePrices[key];
        this.menuItemOrder.foodAdditivePrices[key] = this.customMenuItem.foodAdditivePrices[key];
      }
    });
    this.menuItemOrder.generalPrice = total*this.menuItemOrder.quantity;
    return total;
  }
  showModal(): void {
    this.showModal1 = true;
  }
  closeModal(){
    this.showModal1 = false;
  }
  sendOrder(){

    this.menuItemOrder.menuItemId = this.customMenuItem.menuItemId;
    this.menuItemOrder.name = this.customMenuItem.name;
    this.menuItemOrder.category = this.customMenuItem.category;
    this.menuItemOrder.description = this.customMenuItem.description;
    this.menuItemOrder.available = this.customMenuItem.available;

      this.sharedDataService.updateMenuItemOrder(this.menuItemOrder);

    this.closeModal();
  }

}
