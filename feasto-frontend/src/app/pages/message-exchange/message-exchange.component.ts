import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgClass, NgForOf, CommonModule, NgIf, DatePipe} from "@angular/common";

interface Restaurant {
  id: string;
  name: string;
}
interface Message {
  type: 'customer' | 'restaurant';
  content: string;
}
interface ChatArchive {
  id: string;
  date: Date;
}
@Component({
  imports: [
    FormsModule,
    NgClass,
    NgForOf,
    NgIf,
    DatePipe
  ],
  selector: 'app-message-exchange',
  standalone: true,
  styleUrl: './message-exchange.component.css',
  templateUrl: './message-exchange.component.html'
})

export class MessageExchangeComponent {
  selectedRestaurant: string | null = null;
  selectedArchivedChat: string | null = null;
  newMessage = '';

  // Existing restaurants array
  restaurants: Restaurant[] = [
    { id: 'rest1', name: 'Restauracja A' },
    { id: 'rest2', name: 'Restauracja B' }
  ];

  // Messages currently being sent/received
  messages: {[key: string]: Message[]} = {
    'rest1': [
      { type: 'customer', content: 'Czy mogę zamówić stolik na 19:00?' },
      { type: 'restaurant', content: 'Tak, stolik na 19:00 jest dostępny. Na ile osób?' },
      // More messages...
    ],
    'rest2': [
      { type: 'customer', content: 'Czy macie dostępne miejsca na dzisiejszy wieczór?' },
      // More messages...
    ]
  };

  // Simulated archived chats
  archivedChats: ChatArchive[] = [
    { id: 'arch1', date: new Date(2023, 0, 1) },
    { id: 'arch2', date: new Date(2023, 1, 1) }
  ];

  // Archived messages
  archivedMessages: {[key: string]: Message[]} = {
    'arch1': [
      { type: 'customer', content: 'Czy macie otwarte w Nowy Rok?' },
      { type: 'restaurant', content: 'Nie, jesteśmy zamknięci.' }
    ],
    'arch2': [
      { type: 'customer', content: 'Jakie są godziny otwarcia?' },
      { type: 'restaurant', content: 'Otwarte od 10 do 22.' }
    ]
  };

  constructor() {}

  onRestaurantChange(): void {
    this.selectedArchivedChat = null;  // Clear archived chat selection when changing restaurants
  }

  onArchivedChatChange(): void {
    // Additional logic can be implemented if needed when changing archived chats
  }

  sendMessage(): void {
    if (this.newMessage.trim() && this.selectedRestaurant) {
      this.messages[this.selectedRestaurant].push({ type: 'customer', content: this.newMessage });
      this.newMessage = '';  // Clear the input after sending
    }
  }
}
