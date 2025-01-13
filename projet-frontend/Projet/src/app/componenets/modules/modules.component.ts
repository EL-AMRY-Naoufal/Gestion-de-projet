import { Component, OnInit } from '@angular/core';
import { MenuComponent } from '../shared/menu/menu.component';
import { DepartementService } from '../../services/departement.service';

@Component({
  selector: 'app-modules',
  standalone: true,
  imports: [
      MenuComponent,
    ],
  templateUrl: './modules.component.html',
  styleUrl: './modules.component.scss'
})
export class ModulesComponent implements OnInit {
  departements: any[] = [];

    constructor(private departementService: DepartementService) {
      
    }
  
    ngOnInit(): void {
      this.departementService.getDepartementsByYear(1).subscribe(data => {
        this.departements = data;
        console.log(this.departements);
      });  
    }

}
