import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'autores',
    loadComponent: () =>
      import('./autores/lista-autores/lista-autores.component').then(
        (m) => m.ListaAutoresComponent
      ),
  },
  {
    path: 'libros',
    loadComponent: () =>
      import('./pages/libros/libros.component').then((m) => m.LibrosComponent),
  },
  {
    path: 'prestamos',
    loadComponent: () =>
      import('./pages/prestamos/prestamos.component').then(
        (m) => m.PrestamosComponent
      ),
  },
  { path: '', redirectTo: '/libros', pathMatch: 'full' },
];
