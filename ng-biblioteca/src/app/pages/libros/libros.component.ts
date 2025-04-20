import { Component } from '@angular/core';

@Component({
  selector: 'app-libros',
  standalone: true, // ✅ Esto es clave
  imports: [],
  templateUrl: './libros.component.html',
  styleUrls: ['./libros.component.css'] // ❌ era 'styleUrl'
})
export class LibrosComponent {}
