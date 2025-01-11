import { Component } from '@angular/core';
import { MenuComponent } from '../shared/menu/menu.component';

@Component({
  selector: 'app-modules',
  standalone: true,
  imports: [
      MenuComponent,
    ],
  templateUrl: './modules.component.html',
  styleUrl: './modules.component.scss'
})
export class ModulesComponent {

}
