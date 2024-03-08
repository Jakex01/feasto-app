import {Component, OnInit} from '@angular/core';
import {CurrentMenuItemService} from "../service/currentMenuItem-service/current-menu-item.service";
import {MenuItemOrderModel} from "../model/MenuItemOrderModel";

@Component({
  selector: 'app-main-navbar',
  templateUrl: './main-navbar.component.html',
  styleUrls: ['./main-navbar.component.css']
})
export class MainNavbarComponent implements OnInit{


  currentMenuItem: MenuItemOrderModel | null = null;

  constructor(private menuItemService: CurrentMenuItemService) {}

  ngOnInit() {
    this.menuItemService.currentMenuItem.subscribe(item => {
      this.currentMenuItem = item;
      console.log(item);
    });

}
}
