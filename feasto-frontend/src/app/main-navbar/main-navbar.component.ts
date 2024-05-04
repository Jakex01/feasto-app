import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CurrentMenuItemService} from "../service/currentMenuItem-service/current-menu-item.service";
import {MenuItemOrderModel} from "../model/MenuItemOrderModel";
import {OffCanvasCartComponent} from "../off-canvas-cart/off-canvas-cart.component";
import {formatDate} from "@angular/common";
import {LocationModel} from "../model/LocationModel";
import {LocationService} from "../service/location-service/location.service";
import {LocationRequest} from "../model/request/LocationRequest";

interface DateInfo {
  day: string;
  date: string;
}
interface TimeSlot {
  time: string;
  selected: boolean;
}
@Component({
  selector: 'app-main-navbar',
  templateUrl: './main-navbar.component.html',
  styleUrls: ['./main-navbar.component.css']
})


export class MainNavbarComponent implements OnInit{


  currentMenuItem: MenuItemOrderModel | null = null;
  locationItem: LocationModel;
  @ViewChild(OffCanvasCartComponent) childComponent: OffCanvasCartComponent;
  constructor(private menuItemService: CurrentMenuItemService,
              private locationService: LocationService
              ) {}
  selectedDeliveryTime: string;
  deliveryTimeOptions: string[] = [];
  selectedDeliveryStreetName: string;
  selectedPostalCode: string;
  displayDeliveryTime: string = 'Pick up now';
  city: string;
  public timeSlots: TimeSlot[] = [];

  locations: LocationModel[] = [];

  public dates: DateInfo[][] = this.generateDateSlides();

  private generateDateSlides(): DateInfo[][] {
    let currentDay = new Date();
    const daysOfWeek = [];
    const daysFormatOptions: Intl.DateTimeFormatOptions = { month: 'short', day: 'numeric' };

    for (let i = 0; i < 6; i++) {
      const newDate = new Date(currentDay);
      newDate.setDate(currentDay.getDate() + i);
      let dayLabel = newDate.toLocaleDateString('en-US', { weekday: 'short' });

      // Custom labels for today and tomorrow
      if (i === 0) {
        dayLabel = 'Today';
      } else if (i === 1) {
        dayLabel = 'Tomorrow';
      }

      daysOfWeek.push({
        day: dayLabel,
        date: newDate.toLocaleDateString('en-US', daysFormatOptions)
      });
    }

    // Group the first 3 days into the first slide, and the next 3 days into the second slide
    return [daysOfWeek.slice(0, 3), daysOfWeek.slice(3, 6)];
  }



  selectedHour: string = '';  // This will store the currently selected hour

  selectHour(hour: string) {
    this.selectedHour = hour;  // Update the selected hour when a button is clicked
  }

  isSelectedHour(hour: string): boolean {
    return this.selectedHour === hour;  // Check if the hour is the selected hour
  }
  selectedDate: string = '';

  selectDate(date: string) {
    this.generateTimeSlots();
    this.selectedDate = date; // Set the selected date
  }

  isSelectedDate(date: string): boolean {
    return this.selectedDate === date; // Check if the date is selected
  }

  ngOnInit() {
    this.initializeTimeSlots();
    this.generateDeliveryTimeOptions();

    this.locationService.getAllLocationsByUser().subscribe({
      next: (response: LocationModel[])=>{
        this.locations = response;
        console.log(response);
    }, error: (error)=>{
        console.error('Error fetching locations:', error);
      },
    });


    this.menuItemService.currentMenuItem.subscribe(item => {
      this.currentMenuItem = item;
    });

}
  parentToChild(){
  this.childComponent.showModal();
  }

  generateDeliveryTimeOptions(): void {
    const now = new Date();
    let hour = now.getHours();
    let minutes = now.getMinutes();

    // Start at the next half hour if the current minutes are past 30
    if (minutes > 30) {
      hour++;
    }

    // Generate times from the current hour until 22:00
    while (hour < 22) {
      this.deliveryTimeOptions.push(this.formatTime(hour, 0));  // e.g., 16:00
      this.deliveryTimeOptions.push(this.formatTime(hour, 30)); // e.g., 16:30
      hour++;
    }
  }

  formatTime(hour: number, minutes: number): string {
    return `${hour.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
  }
  formatPostalCode(event: KeyboardEvent): void {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/[^\d]/g, ''); // Remove non-digits

    // Adding a hyphen after the second digit
    if (value.length >= 2) {
      value = value.substring(0, 2) + '-' + value.substring(2, 5);
    }

    this.selectedPostalCode = value; // Update the bound value
    input.value = value; // Update the input's displayed value
  }
  updateDeliveryDisplay(): void {
    const currentDate = new Date();
    const formattedDate = formatDate(currentDate, 'dd MMM', 'en-US');
    this.displayDeliveryTime = this.selectedDeliveryTime === 'now'
      ? 'Pick up now'
      : `${formattedDate}, ${this.selectedDeliveryTime}`;
  }
  saveLocation() {
    const newLocation: LocationRequest = {
      city: this.city,
      street: this.selectedDeliveryStreetName,
      streetNumber: "30",
      country: "Poland",
      postalCode: this.selectedPostalCode,
      current: true

    };
    this.locationService.postLocation(newLocation)
      .subscribe({
        next: (response: any)=>{
          const locationResponse = response as LocationModel;
          this.locations = [...this.locations, locationResponse];
        },

      });

    this.selectedPostalCode = '';
    this.city = '';
    this.selectedDeliveryStreetName = '';
  }

  selectLocation(location: LocationModel) {
    this.locations.forEach(loc => loc.current = false);
    const foundLocation = this.locations.find(loc => loc.id === location.id);
    if (foundLocation) {
      foundLocation.current = true;
    }
    location.current = true;
    this.locationService.updateLocation(this.locations);
  }
  private initializeTimeSlots(): void {
    this.timeSlots = [];  // Clear existing time slots
    let currentHour = new Date().getHours();
    let startHour = currentHour < 20 ? currentHour + 1 : 8; // If current hour is before 20:00, start from next hour, else reset to 8 next day

    for (let hour = startHour; hour <= 20; hour++) {
      this.timeSlots.push({ time: `${hour}:00`, selected: false });
    }
  }
  private generateTimeSlots(): void {
    const today = new Date().toDateString();
    const isToday = new Date(this.selectedDate).toDateString() === today;

    this.timeSlots = [];
    const startHour = isToday ? new Date().getHours() + 1 : 8;
    const endHour = 20;

    for (let hour = startHour; hour <= endHour; hour++) {
      this.timeSlots.push({ time: `${hour}:00`, selected: false });
    }
  }
}
