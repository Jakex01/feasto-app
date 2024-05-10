import {Component, OnInit} from '@angular/core';
import {RestaurantService} from "../service/restaurant-service/restaurant.service";
import {RestaurantDtoModel} from "../model/RestaurantDtoModel";
import {RestaurantFilterModel} from "../model/RestaurantFilterModel";

@Component({
  selector: 'app-restaurants-list',
  templateUrl: './restaurants-list.component.html',
  styleUrls: ['./restaurants-list.component.css']
})
export class RestaurantsListComponent implements OnInit{

  selectedFoodType: string = "";
  selectedRating: string = "";
  selectedPriceRange: string = "";
  selectedSortOption: string = "";
  searchTerm: string = "";

  numberOfResults: number;

  restaurants: RestaurantDtoModel[] = [];
  constructor(private restaurantService: RestaurantService) {
  }
  ngOnInit(): void {
    this.restaurantService.getAllRestaurants().subscribe({
      next: (response: RestaurantDtoModel[]) => {
        this.restaurants = response;
        console.log("here is my response: "+response)
        this.numberOfResults = response.length;

      },
      error: (error) => {
        console.error('Error fetching restaurants:', error);
      },
    });
  }
  resetFilter() {
    this.selectedFoodType = "";
    this.selectedRating = "";
    this.selectedPriceRange = "";
    this.searchTerm = "";

    this.restaurantService.getAllRestaurants().subscribe(
      (response: RestaurantDtoModel[]) => {
        this.restaurants = response;
        console.log(response);
      },
      error => {
        console.error('Error fetching restaurants:', error);
      }
    );

  }
  updateFilters() {

    const filters: RestaurantFilterModel = {
      foodType: this.selectedFoodType,
      rating: this.selectedRating,
      priceRange: this.selectedPriceRange
    };

    this.restaurantService.getFilteredRestaurants(filters).subscribe(restaurants => {
      this.restaurants = restaurants;
      this.numberOfResults = restaurants.length;


      if(this.selectedSortOption){
        this.onSortChanged(this.selectedSortOption);
      }

    });
  }
  resetFoodTypeFilter(){
    this.selectedFoodType = "";
  }
  resetRatingFilter(){
    this.selectedRating = "";
  }
  resetPriceFilter(){
    this.selectedPriceRange = "";
  }
  applyFilter() {
    const searchTerm = this.searchTerm.toLowerCase();
    this.restaurants = this.restaurants.filter(restaurant =>
      restaurant.name.toLowerCase().includes(searchTerm)
    );
    console.log(this.restaurants.length);
  }
  onSortChanged(sortValue: string) {
    switch (sortValue) {
      case 'price':
        this.restaurants.sort((a, b) => a.prices - b.prices);
        break;
      case 'rating':
        this.restaurants.sort((a, b) => a.rating - b.rating);
        break;
      default:
        break;
    }

    this.restaurants = [...this.restaurants];

    console.log(this.selectedSortOption)
  }
  checkIfOpened(openingHours: string): boolean {
    const currentTime = new Date();
    const openingParts = openingHours.split('-')
      .map(part => part
        .split(':'));

    const openingTime = new Date(currentTime);
    openingTime.setHours(parseInt(openingParts[0][0]), parseInt(openingParts[0][1]));
    const closingTime = new Date(currentTime);
    closingTime.setHours(parseInt(openingParts[1][0]), parseInt(openingParts[1][1]));

    return currentTime >= openingTime && currentTime <= closingTime;
  }





}
