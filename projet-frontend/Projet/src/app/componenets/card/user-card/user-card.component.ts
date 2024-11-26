import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-user-card',
  standalone: true,
  imports: [  MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    CommonModule,
  ],
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.scss']
})
export class UserCardComponent {
  @Input() user: any; 
  @Input() openDialog!: (user?: any) => void;

  onEditClick() {
    if (this.openDialog) {
      this.openDialog(this.user);
    }
  }
}
