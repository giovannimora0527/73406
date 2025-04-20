// src/app/autores/autores.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AutoresService {

  private apiUrl = 'http://localhost:4200/autores'; // Cambia esta URL por la URL de tu API

  constructor(private http: HttpClient) { }

  // Obtener todos los autores
  getAutores(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  // Crear un nuevo autor
  createAutor(autor: any): Observable<any> {
    return this.http.post(this.apiUrl, autor);
  }

  // Editar un autor
  editAutor(id: number, autor: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, autor);
  }
}
