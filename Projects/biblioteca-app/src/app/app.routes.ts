import { Routes } from '@angular/router';
import { LibrosComponent } from './pages/libros/libros.component';
import { PrestamosComponent } from './pages/prestamos/prestamos.component';

export const routes: Routes = [
  { path: 'libros', component: LibrosComponent },
  { path: 'prestamos', component: PrestamosComponent }
];
