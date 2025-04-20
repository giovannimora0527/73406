import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common'; // Importa CommonModule para usar *ngFor

// Array de elementos para el menú
export const items = [
  { name: 'Libros', route: '/libros' },
  { name: 'Préstamos', route: '/prestamos' },
  { name: 'Autores', route: '/autores' } // ✅ Agrega este objeto
];

@Component({
  selector: 'app-sidebar',
  standalone: true, // Indicamos que es un componente standalone
  imports: [CommonModule, RouterLink, RouterLinkActive], // Importamos CommonModule para *ngFor
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  public items = items;
}
