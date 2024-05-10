import { Component } from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from "@angular/router";
import {filter} from "rxjs";

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
  showNavbar = true;

  constructor(private router: Router) {
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      this.showNavbar = !event.urlAfterRedirects.startsWith('/login');
    });
  }

}
