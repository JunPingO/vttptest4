import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestaurantList } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent {
	
  constructor(private activatedRoute: ActivatedRoute, private restaurantService:RestaurantService) { }

  restaurantList : RestaurantList[] = []

	// TODO Task 3
	// For View 2
  ngOnInit() :void {
    let cuisineString = this.activatedRoute.snapshot.params['cuisine'];
    console.info("navigated to cuisine >>>>", cuisineString)
    this.restaurantService.getRestaurantsByCuisine(cuisineString)
      .then(result => {
        for ( var restaurant of result ){
          this.restaurantList.push(restaurant)
        }
        console.info('>>> orders: ', this.restaurantList[0])
      })
      .catch(error => {
        console.error('>> error: ', error)
      })
  }
}
