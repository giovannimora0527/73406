import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-lista-autores',
  standalone: true, // ✅ Esto es necesario
  imports: [],
  templateUrl: './lista-autores.component.html',
  styleUrls: ['./lista-autores.component.css']
})
export class ListaAutoresComponent implements OnInit {
  constructor() { }

  ngOnInit(): void {}
}
