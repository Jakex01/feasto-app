import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RestaurantService} from "../service/restaurant-service/restaurant.service";
import {RestaurantResponse} from "../model/response/RestaurantResponse";
import {ActivatedRoute} from "@angular/router";
import {RatingResponse} from "../model/response/RatingResponse";
import {RatingService} from "../service/rating-service/rating.service";
@Component({
  selector: 'app-restaurant-page',
  templateUrl: './restaurant-page.component.html',
  styleUrls: ['./restaurant-page.component.css'],
})
export class RestaurantPageComponent implements OnInit{
  @ViewChild('nextSection') nextSection: ElementRef;
  categories = ['Popularne', 'Śniadania', 'Kanapki', 'Obiady', 'Kolacja'];
  heartStates: boolean[] = [false, false, false];
  stars: string[] = [];
  restaurant: RestaurantResponse;
  id: number;
  isCommentVisible = false;
  ratingResponse: RatingResponse;

  ratingByRestaurant: RatingResponse;

  constructor(private restaurantService: RestaurantService,
              private route: ActivatedRoute,
              private ratingService: RatingService
              ) {
  }
  ngOnInit(): void {

    this.id = +this.route.snapshot.paramMap.get('id')!;

    this.restaurantService.getRestaurantById(this.id).subscribe({
      next: (response: RestaurantResponse) => {
        this.restaurant = response;
        this.updateStars(this.restaurant.rating);
        console.log("my response: " + response.description);
      },
      error: (error) => {
        console.error('Error fetching restaurants:', error);
      },
    });
  }
  selectCategory(category: string) {
    // Logic to display the selected category's items
    console.log(category);
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

        },
        error: (err) => {
          console.error('Failed to load average rating:', err);
        }
      });
  }

}
