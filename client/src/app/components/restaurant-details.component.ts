import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RestaurantService } from '../restaurant-service';
import { Restaurant, Comment } from '../models'

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent {
	
  form! : FormGroup
  comment!: Comment

  constructor(private activatedRoute: ActivatedRoute, private restaurantService:RestaurantService, private fb:FormBuilder ) { }

  restaurantDetail!: Restaurant
  mapurl: string = ""
  restaurantId: string = ""

  // model : Restaurant = new Restaurant ("Singapore", 0,0,0,"",0,0);

	// TODO Task 4 and Task 5
	// For View 3
  ngOnInit(){
    let restaurantString = this.activatedRoute.snapshot.params['restaurant'];
    this.restaurantService.getRestaurant(restaurantString).then(result => {
      this.restaurantDetail = result
      this.restaurantId = result.restaurantId
      console.info(this.restaurantId)
      
    })
    .catch(error => {
      console.error('>> error: ', error)
    })

    this.restaurantService.getMapURL(restaurantString).then(result => {
      console.info(result.map)
      console.info(result.restaurant_id)
      this.mapurl = result.map
      // this.restaurantDetail.name = result.name
      // console.info('>>> mapurl: ', result)
    })
    .catch(error => {
      console.error('>> error: ', error)
    })

    this.form = this.createForm();
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [ Validators.required, Validators.minLength(4)]),
      rating: this.fb.control<number>(3, [Validators.required]),
      comments: this.fb.control<string>('', [Validators.required])
    })
  }

  postComment(){
    this.comment = this.form.value
    this.comment.restaurantId = this.restaurantId
    // let restaurantString = this.activatedRoute.snapshot.params['restaurant'];
    // this.restaurantService.getRestaurant(restaurantString).then(result => {
    //   this.comment.restaurantId = result.restaurantId
    //   console.info(result.restaurantId)
    // }) 
    // .catch(error => {
    //   console.error('>> error: ', error)
    // })
    console.info(this.comment)
    this.restaurantService.postComment(this.comment)
  }

}
