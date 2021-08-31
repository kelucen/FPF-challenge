import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';
import { DatePipe } from '@angular/common'

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  product: Product = {
    name:'',
    description: '',
    date: '',
    price: 0.0,
    category: '',
    image:''
  }
  submitted = false;
  constructor(private productService: ProductService, public datepipe: DatePipe) { }
  ngOnInit(): void {
  }

  saveProduct(): void {
    const data = {
      name: this.product.name,
      description: this.product.description,
      date: this.datepipe.transform(this.product.date,'dd/MM/yyyy')?.toString(),
      price: this.product.price,
      category: this.product.category,
      image:this.product.image
    };
    this.productService.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.submitted = true;
        },
        error => {
          console.log(error);
        });
  }

  newProduct(): void {
    this.submitted = false;
    this.product = {
      name:'',
      description: '',
      date: '',
      price: 0.0,
      category: '',
      image:''
    };
  }
}
