import { Component } from '@angular/core';

@Component({
  selector: 'app-prestamos',
  standalone: true, // ✅ Esto es clave
  imports: [],
  templateUrl: './prestamos.component.html',
  styleUrls: ['./prestamos.component.css'] // ❌ era 'styleUrl'
})
export class PrestamosComponent {}
