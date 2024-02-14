export interface CreateRestaurantModel {
  name: string;
  openingHours: string;
  description: string;
  image: string | null;
  address: string;
  phoneNumber: string;
  foodType: string;
  prices: number;
}
