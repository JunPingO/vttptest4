import { NgModule } from "@angular/core";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';


const matModules: any[] = [
    MatFormFieldModule,
    MatCardModule
]

@NgModule ({
    imports: matModules,
    exports: matModules
})

export class MaterialModule {

}