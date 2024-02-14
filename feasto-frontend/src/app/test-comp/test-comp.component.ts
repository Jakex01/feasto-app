import { Component } from '@angular/core';

@Component({
  selector: 'app-test-comp',
  templateUrl: './test-comp.component.html',
  styleUrls: ['./test-comp.component.css']
})
export class TestCompComponent {
  selectedSortOption: string = 'option1';

  // Funkcja obsługi zmiany opcji sortowania
  onSortOptionChange(event: any) {
    this.selectedSortOption = event.target.value;
    // Tutaj możesz dodać kod do sortowania wyników na podstawie wybranej opcji
  }
}
