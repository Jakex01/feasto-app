export interface MenuItemOrderModel{
  menuItemId: number;
  name: string;
  description: string;
  available: boolean;
  category: string;
  foodAdditivePrices: { [key: string]: number };
  selectedSize: { size: string; price: number };
  generalPrice: number;
  quantity: number;
   note: string;
}
