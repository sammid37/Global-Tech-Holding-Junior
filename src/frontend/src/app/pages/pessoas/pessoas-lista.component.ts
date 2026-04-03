import { Component, ElementRef, inject, OnInit, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DatePipe, DecimalPipe } from '@angular/common';
import { Router } from '@angular/router';
import { PessoaService } from '../../core/services/pessoa.service';
import { Pessoa } from '../../shared/models/pessoa.model';

@Component({
    selector: 'app-pessoas-lista',
    standalone: true,
    imports: [FormsModule, DatePipe, DecimalPipe],
    templateUrl: './pessoas-lista.component.html',
})
export class PessoasListaComponent implements OnInit {
    private pessoaService = inject(PessoaService);
    private router = inject(Router);

    @ViewChild('visualizarModal') visualizarModal!: ElementRef<HTMLDialogElement>;
    @ViewChild('deletarModal') deletarModal!: ElementRef<HTMLDialogElement>;

    pessoas = signal<Pessoa[]>([]);
    carregando = signal(false);
    alertMessage = signal<string | null>(null);

    pessoaSelecionada = signal<Pessoa | null>(null);
    pesoIdeal = signal<number | null>(null);
    carregandoPesoIdeal = signal(false);

    pessoaParaDeletar = signal<Pessoa | null>(null);
    deletando = signal(false);

    filtroNome = '';
    filtroCpf = '';
    filtroId: number | null = null;

    ngOnInit(): void {
        this.carregarTodos();
    }

    private carregarTodos(): void {
        this.carregando.set(true);
        this.pessoaService.listar().subscribe({
            next: (data) => {
                this.pessoas.set(data);
                this.carregando.set(false);
            },
            error: () => this.carregando.set(false),
        });
    }

    filtrar(): void {
        const filtrosAtivos = [
            this.filtroNome.trim(),
            this.filtroCpf.trim(),
            this.filtroId,
        ].filter((v) => v !== '' && v !== null && v !== undefined);

        if (filtrosAtivos.length > 1) {
            this.alertMessage.set(
                'Filtros combinados ainda não foram implementados. Utilize apenas um filtro por vez.'
            );
            return;
        }

        this.alertMessage.set(null);

        if (this.filtroId) {
            this.carregando.set(true);
            this.pessoaService.buscarPorId(this.filtroId).subscribe({
                next: (p) => {
                    this.pessoas.set([p]);
                    this.carregando.set(false);
                },
                error: () => {
                    this.pessoas.set([]);
                    this.carregando.set(false);
                },
            });
        } else if (this.filtroNome.trim()) {
            this.carregando.set(true);
            this.pessoaService.pesquisar(this.filtroNome.trim()).subscribe({
                next: (data) => {
                    this.pessoas.set(data);
                    this.carregando.set(false);
                },
                error: () => this.carregando.set(false),
            });
        } else if (this.filtroCpf.trim()) {
            this.carregando.set(true);
            this.pessoaService.buscarPorCpf(this.filtroCpf.trim()).subscribe({
                next: (p) => {
                    this.pessoas.set([p]);
                    this.carregando.set(false);
                },
                error: () => {
                    this.pessoas.set([]);
                    this.carregando.set(false);
                },
            });
        } else {
            this.carregarTodos();
        }
    }

    limparFiltros(): void {
        this.filtroNome = '';
        this.filtroCpf = '';
        this.filtroId = null;
        this.alertMessage.set(null);
        this.carregarTodos();
    }

    fecharAlert(): void {
        this.alertMessage.set(null);
    }

    irParaCadastro(): void {
        this.router.navigate(['/cadastrar']);
    }

    irParaEdicao(id: number): void {
        this.router.navigate(['/editar', id]);
    }

    visualizar(pessoa: Pessoa): void {
        this.pessoaSelecionada.set(pessoa);
        this.pesoIdeal.set(null);
        this.visualizarModal.nativeElement.showModal();
    }

    confirmarDeletar(pessoa: Pessoa): void {
        this.pessoaParaDeletar.set(pessoa);
        this.deletarModal.nativeElement.showModal();
    }

    deletar(): void {
        const id = this.pessoaParaDeletar()?.id;
        if (!id) return;

        this.deletando.set(true);
        this.pessoaService.excluir(id).subscribe({
            next: () => {
                this.deletarModal.nativeElement.close();
                this.pessoaParaDeletar.set(null);
                this.deletando.set(false);
                this.carregarTodos();
            },
            error: () => {
                this.deletando.set(false);
            },
        });
    }

    calcularPesoIdeal(): void {
        const id = this.pessoaSelecionada()?.id;
        if (!id) return;

        this.carregandoPesoIdeal.set(true);
        this.pessoaService.calcularPesoIdeal(id).subscribe({
            next: (peso) => {
                this.pesoIdeal.set(peso);
                this.carregandoPesoIdeal.set(false);
            },
            error: () => {
                this.carregandoPesoIdeal.set(false);
            },
        });
    }
}
