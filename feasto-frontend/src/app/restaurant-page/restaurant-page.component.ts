import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RestaurantService} from "../service/restaurant-service/restaurant.service";
import {RestaurantResponse} from "../model/response/RestaurantResponse";
import {ActivatedRoute} from "@angular/router";
import {RatingResponse} from "../model/response/RatingResponse";
import {RatingService} from "../service/rating-service/rating.service";
import {MenuItemResponse} from "../model/response/MenuItemResponse";
import {ModalManagerService} from "../service/modal-service/modal-manager.service";
import {MenuItemService} from "../service/menu-item/menu-item.service";
import {CustomMenuItemResponse} from "../model/response/CustomMenuItemResponse";
import {MenuItemOrderModel} from "../model/MenuItemOrderModel";
import {OrderRequest} from "../model/request/OrderRequest";
@Component({
  selector: 'app-restaurant-page',
  templateUrl: './restaurant-page.component.html',
  styleUrls: ['./restaurant-page.component.css'],
})
export class RestaurantPageComponent implements OnInit{
    @ViewChild('nextSection') nextSection: ElementRef;

    heartStates: boolean[] = [false, false, false];
    stars: string[] = [];
    restaurant: RestaurantResponse;
    id: number;
    isCommentVisible = false;
    ratingResponse: RatingResponse;
    uniqueFoodCategoriesSet = new Set<String>();
    foodListByCategory: MenuItemResponse[] = [];
    customMenuItem: CustomMenuItemResponse;
    CommentsByRestaurant: RatingResponse[] = [];
    menuItemOrderModel: MenuItemOrderModel[] = [];
    orderRequest: OrderRequest;
    currentMenuItemModel: MenuItemOrderModel = {
      menuItemId: 0,
      foodAdditivePrices: {},
      selectedSize: { size: '', price: 0 },
      generalPrice: 0,
      quantity: 1
    };

  constructor(private restaurantService: RestaurantService,
              private route: ActivatedRoute,
              private ratingService: RatingService,
              private modalManager: ModalManagerService,
              private menuItemService: MenuItemService
              ) {
  }
  ngOnInit(): void {

    this.id = +this.route.snapshot.paramMap.get('id')!;

    this.restaurantService.getRestaurantById(this.id).subscribe({
      next: (response: RestaurantResponse) => {
        this.restaurant = response;
        this.updateStars(this.restaurant.rating);
        this.selectFoodCategories();
      },
      error: (error) => {
        console.error('Error fetching restaurants:', error);
      },
    });
  }
  selectFoodCategories(){
    this.restaurant.menuItems.forEach((menuItem: MenuItemResponse)=>{
      if(!this.uniqueFoodCategoriesSet.has(menuItem.category)){
        this.uniqueFoodCategoriesSet.add(menuItem.category);
      }
    })

  }
  fetchAllComments(){

      if (this.restaurant && this.restaurant.restaurantId) {
        this.ratingService.getRestaurantComments(this.restaurant.restaurantId).subscribe({
          next: (response) => {
            this.CommentsByRestaurant = response;
            console.log('Comments:', response);
          },
          error: (error) => {
            console.error('Error fetching comments:', error);

          }
        });
      } else {
        console.error('Restaurant ID is not defined.');
      }

  }
  selectFoodCategory(category: String) {

    if(this.restaurant){
      this.foodListByCategory = [];
      this.restaurant.menuItems.forEach((menuItem: MenuItemResponse)=>{
        if(menuItem.category.toLowerCase() === category.toLowerCase()){
          this.foodListByCategory.push(menuItem);
        }
      })
    }
  }
  toggleHeart(index: number): void {
    this.heartStates[index] = !this.heartStates[index];
    this.triggerAnimation(index);
  }
  triggerAnimation(index: number): void {
    const element = document.querySelector('.heart-button i');
    if (element) {
      element.classList.add('heart-pop');
      setTimeout(() => element.classList.remove('heart-pop'), 400); // Match the duration of the animation
    }
  }
  scrollToNextSection() {
    window.scrollBy({
      top: window.innerHeight,
      left: 0,
      behavior: 'smooth'
    });
  }
  updateStars(rating: number): void {
    this.stars = [];
    let hasHalfStarBeenAdded = false;

    for (let i = 1; i <= 5; i++) {
      if (i <= rating) {
        this.stars.push('star');
      } else if (!hasHalfStarBeenAdded && rating % 1 !== 0 && i - 1 < rating && i > rating) {
        this.stars.push('star_half');
        hasHalfStarBeenAdded = true;
      } else {
        this.stars.push('star_outline');
      }
    }
  }


  toggleCommentVisibility() {
    this.getAverageComment(this.restaurant.restaurantId, this.restaurant.rating)
    this.isCommentVisible = !this.isCommentVisible;
  }

  getAverageComment(restaurantId: number, averageRating: number){
    this.ratingService.getAverageComment(restaurantId, averageRating)
      .subscribe({
        next: (rating) => {
          this.ratingResponse = rating;
          console.log(rating);
        },
        error: (err) => {
          console.error('Failed to load average rating:', err);
        }
      });
  }

  checkingRatingCreateDate(date: string): string {
    const today = new Date();
    const commentDate = new Date(date);
    let diffInDays = Math.floor((today.getTime() - commentDate.getTime()) / (1000 * 60 * 60 * 24));

    if (diffInDays <= 7) {
      return diffInDays === 0 ? `today` : `${diffInDays} days ago`; // Adding handling for 'today'
    } else {
      // For dates more than 7 days ago, format the date
      return commentDate.toLocaleDateString('pl-PL', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      });
    }
    }
  fetchCustomMenuItemResponse(menuItemId: number){
    this.menuItemService.getCustomMenuItem(menuItemId)
      .subscribe({
        next:(menuItem)=>{
          this.customMenuItem = menuItem;
          console.log(menuItem);
        },
        error:(err)=>{
          console.error('Failed to load menu item:', err);
        }
      })
  }

  updateSelectedSize(sizeKey: string) {
    const price = this.customMenuItem.sizesWithPrices[sizeKey];
    this.currentMenuItemModel.selectedSize = { size: sizeKey, price };
    this.calculateGeneralPrice();
  }

  updateAdditives(additiveKey: string, isChecked: boolean) {
    if (isChecked) {
      const price = this.customMenuItem.foodAdditivePrices[additiveKey];
      this.currentMenuItemModel.foodAdditivePrices[additiveKey] = price;
    } else {
      delete this.currentMenuItemModel.foodAdditivePrices[additiveKey];
    }
    this.calculateGeneralPrice();
  }
  calculateGeneralPrice() {
    let price = this.currentMenuItemModel.selectedSize.price;
    Object.values(this.currentMenuItemModel.foodAdditivePrices).forEach(additivePrice => {
      price += additivePrice;
    });

    this.currentMenuItemModel.generalPrice = price * this.currentMenuItemModel.quantity;
  }

  updateOrder(menuItemId: number){
    this.currentMenuItemModel.menuItemId = menuItemId;
    this.orderRequest.items.push(this.currentMenuItemModel);
  }
  passOrder(){
    this.orderRequest.items.push(this.currentMenuItemModel);
  }


  // openMenuItemModal() {
  //   this.modalManager.open();
  // }

}
