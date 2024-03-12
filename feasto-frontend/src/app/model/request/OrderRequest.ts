import {MenuItemOrderModel} from "../MenuItemOrderModel";

export interface OrderRequest{
  items: MenuItemOrderModel[];
  totalPrice: number;
  restaurantId: number;
  orderNote: string;
}
