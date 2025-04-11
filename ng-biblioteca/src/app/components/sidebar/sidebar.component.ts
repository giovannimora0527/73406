import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgForOf } from '@angular/common';
import { navigation } from '../../theme/layout/admin/navigation/navigation';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, NgForOf],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  navItems = navigation;
}
