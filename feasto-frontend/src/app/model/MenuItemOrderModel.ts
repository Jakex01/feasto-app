export interface MenuItemOrderModel{
  menuItemId: number;
  foodAdditivePrices: { [key: string]: number };
  selectedSize: { size: string; price: number };
  generalPrice: number;
  quantity: number;
}
