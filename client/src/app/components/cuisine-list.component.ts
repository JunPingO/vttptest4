import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent {

  cuisineList! : string[]

  constructor(private router : Router, private restaurantService:RestaurantService) {}

	// TODO Task 2
	// For View 1
  ngOnInit(){
    this.restaurantService.getCuisineList().then(result => {
        // console.info(orderSummary)
        this.cuisineList = result
      console.info('>>> orders: ', this.cuisineList)
    })
    .catch(error => {
      console.error('>> error: ', error)
    })
  }

}
