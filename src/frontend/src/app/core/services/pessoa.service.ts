// Comunicação HTTP com a API
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pessoa } from '../../shared/models/pessoa.model';

@Injectable({
    providedIn: 'root'
})
export class PessoaService {
    private readonly apiUrl = 'http://localhost:8080/api/pessoas';

    constructor(private http: HttpClient) {}

    incluir(pessoa: Pessoa): Observable<Pessoa> {
        return this.http.post<Pessoa>(this.apiUrl, pessoa);
    }

    alterar(id: number, pessoa: Pessoa): Observable<Pessoa> {
        return this.http.put<Pessoa>(`${this.apiUrl}/editar/${id}`, pessoa);
    }

    excluir(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/deletar/${id}`);
    }

    pesquisar(nome: string): Observable<Pessoa[]> {
        const params = new HttpParams().set('nome', nome);
        return this.http.get<Pessoa[]>(this.apiUrl, { params });
    }

    listar(): Observable<Pessoa[]> {
        return this.http.get<Pessoa[]>(`${this.apiUrl}/listar`);
    }

    buscarPorId(id: number): Observable<Pessoa> {
        return this.http.get<Pessoa>(`${this.apiUrl}/${id}`);
    }

    buscarPorCpf(cpf: string): Observable<Pessoa> {
        return this.http.get<Pessoa>(`${this.apiUrl}/cpf/${encodeURIComponent(cpf)}`);
    }

    calcularPesoIdeal(id: number): Observable<number> {
        return this.http.get<number>(`${this.apiUrl}/${id}/peso-ideal`);
    }
}
