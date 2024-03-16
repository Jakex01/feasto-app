import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {CreateRestaurantModel} from "../model/CreateRestaurantModel";
import {RestaurantService} from "../service/restaurant-service/restaurant.service";

@Component({
  selector: 'app-create-restaurant',
  templateUrl: './create-restaurant.component.html',
  styleUrls: ['./create-restaurant.component.css']
})
export class CreateRestaurantComponent {
  formData: CreateRestaurantModel = {
    name: '',
    openingHours: '',
    description: '',
    image: null,
    address: '',
    phoneNumber: '',
    foodType: '',
    prices: 0
  };
  openingHour: string;
  closingHour: string;

  imageFile: File | null;

  constructor(private formBuilder: FormBuilder, private restaurantService: RestaurantService) { }

  ngOnInit() {

  }
  hours: number[] = Array.from({length: 24}, (_, i) => i);
  async submitForm(form: any) {
    if (form.valid) {

      if(this.imageFile){
        this.formData.image = await this.convertFileToBase64(this.imageFile);
      }

      this.restaurantService.postRestaurant(this.formData).subscribe({
        next: (response) =>{
          console.log("Restaurant successfully created", response);
        }
      })

    } else {
      console.error('Form is not valid');
    }
  }
  updateHoursOpened() {
    if (this.openingHour && this.closingHour) {
      this.formData.openingHours = `${this.openingHour}-${this.closingHour}`;
      console.log(this.formData.openingHours);
    }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.imageFile = file;
    }
  }
  convertFileToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = error => reject(error);
    });
  }


}
