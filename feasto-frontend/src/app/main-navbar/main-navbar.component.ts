import {Component, OnInit, ViewChild} from '@angular/core';
import {CurrentMenuItemService} from "../service/currentMenuItem-service/current-menu-item.service";
import {MenuItemOrderModel} from "../model/MenuItemOrderModel";
import {OffCanvasCartComponent} from "../off-canvas-cart/off-canvas-cart.component";

@Component({
  selector: 'app-main-navbar',
  templateUrl: './main-navbar.component.html',
  styleUrls: ['./main-navbar.component.css']
})
export class MainNavbarComponent implements OnInit{


  currentMenuItem: MenuItemOrderModel | null = null;
  @ViewChild(OffCanvasCartComponent) childComponent: OffCanvasCartComponent;
  constructor(private menuItemService: CurrentMenuItemService) {}

  ngOnInit() {
    this.menuItemService.currentMenuItem.subscribe(item => {
      this.currentMenuItem = item;
    });

}
  parentToChild(){
  this.childComponent.showModal();
  }

}
