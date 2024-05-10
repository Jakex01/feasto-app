export interface CustomMenuItemResponse {
  menuItemId: number;
  name: string;
  description: string;
  available: boolean;
  category: string;
  foodAdditivePrices: { [key: string]: number };
  sizesWithPrices: { [size: string]: number };
}
