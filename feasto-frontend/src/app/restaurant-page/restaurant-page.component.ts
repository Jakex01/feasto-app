import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RestaurantService} from "../service/restaurant-service/restaurant.service";
import {RestaurantResponse} from "../model/response/RestaurantResponse";
import {RestaurantDtoModel} from "../model/RestaurantDtoModel";
import {ActivatedRoute} from "@angular/router";
import {fadeInOut} from "../animations";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {RatingResponse} from "../model/response/RatingResponse";
@Component({
  selector: 'app-restaurant-page',
  templateUrl: './restaurant-page.component.html',
  styleUrls: ['./restaurant-page.component.css'],
  animations: [
    trigger('fadeIn', [
      // stan początkowy jest niewidoczny (opacity: 0)
      state('void', style({ opacity: 0 })),
      // przejście z niewidocznego do widocznego (fade in)
      transition('void => *', [
        animate('0.5s ease-in')
      ]),
      // Możesz też dodać przejście od widocznego do niewidocznego (fade out) jeśli potrzebujesz
      transition('* => void', [
        animate('0.5s ease-out', style({ opacity: 0 }))
      ])
    ])
  ]
})
export class RestaurantPageComponent implements OnInit{
  @ViewChild('nextSection') nextSection: ElementRef;
  categories = ['Popularne', 'Śniadania', 'Kanapki', 'Obiady', 'Kolacja'];
  heartStates: boolean[] = [false, false, false];
  stars: string[] = [];
  restaurant: RestaurantResponse;
  id: number;
  ratingResponse: RatingResponse;

  constructor(private restaurantService: RestaurantService,
              private route: ActivatedRoute,) {
  }
  ngOnInit(): void {

    this.id = +this.route.snapshot.paramMap.get('id')!;

    this.restaurantService.getRestaurantById(this.id).subscribe({
      next: (response: RestaurantResponse) => {
        this.restaurant = response;
        this.updateStars(this.restaurant.rating);
        console.log(response);
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
      top: window.innerHeight, // Change this value to adjust the scroll amount
      left: 0,
      behavior: 'smooth'
    });
  }
  updateStars(rating: number): void {
    this.stars = [];
    let hasHalfStarBeenAdded = false;

    for (let i = 1; i <= 5; i++) {
      if (i <= rating) {
        this.stars.push('star'); // Pełna gwiazdka
      } else if (!hasHalfStarBeenAdded && rating % 1 !== 0 && i - 1 < rating && i > rating) {
        this.stars.push('star_half'); // Półpełna gwiazdka
        hasHalfStarBeenAdded = true; // Zapobiega dodaniu więcej niż jednej pół gwiazdki
      } else {
        this.stars.push('star_outline'); // Pusta gwiazdka
      }
    }
  }
  isCommentVisible = false; // Domyślnie komentarz jest ukryty

  toggleCommentVisibility() {


    this.isCommentVisible = !this.isCommentVisible; // Zmienia stan widoczności
  }

}
