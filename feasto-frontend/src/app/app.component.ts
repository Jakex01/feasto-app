import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'feasto-frontend';

  prepareRoute(outlet: RouterOutlet): string | undefined {
    return outlet.activatedRouteData['animation'] as string;
  }
}
