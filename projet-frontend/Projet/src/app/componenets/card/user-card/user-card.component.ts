import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-card',
  standalone: true,
  imports: [CommonModule],
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
