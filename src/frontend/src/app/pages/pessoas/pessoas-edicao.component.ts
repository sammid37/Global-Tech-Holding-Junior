import { Component, inject, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PessoaService } from '../../core/services/pessoa.service';
import { Pessoa } from '../../shared/models/pessoa.model';
import { cpfValidator } from '../../shared/validators/cpf.validator';
import { dataNascValidator } from '../../shared/validators/data-nasc.validator';

@Component({
    selector: 'app-pessoas-edicao',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './pessoas-edicao.component.html',
})
export class PessoasEdicaoComponent implements OnInit {
    private pessoaService = inject(PessoaService);
    private router = inject(Router);
    private route = inject(ActivatedRoute);

    readonly today = new Date().toISOString().split('T')[0];

    pessoaId!: number;
    carregando = signal(true);
    enviando = signal(false);
    alertErro = signal<string | null>(null);
    alertSucesso = signal(false);

    form = new FormGroup({
        nome: new FormControl('', [Validators.required]),
        cpf: new FormControl('', [
            Validators.required,
            Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/),
            cpfValidator(),
        ]),
        sexo: new FormControl<'M' | 'F' | ''>('', [Validators.required]),
        dataNasc: new FormControl('', [Validators.required, dataNascValidator()]),
        peso: new FormControl<number | null>(null, [
            Validators.required,
            Validators.min(0.01),
        ]),
        altura: new FormControl<number | null>(null, [
            Validators.required,
            Validators.min(0.01),
        ]),
    });

    get f() {
        return this.form.controls;
    }

    isInvalid(control: FormControl): boolean {
        return control.invalid && control.touched;
    }

    formatarCPF(event: Event): void {
        const input = event.target as HTMLInputElement;
        let value = input.value.replace(/\D/g, '');
        if (value.length > 11) value = value.slice(0, 11);
        if (value.length > 9) {
            value = value.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, '$1.$2.$3-$4');
        } else if (value.length > 6) {
            value = value.replace(/(\d{3})(\d{3})(\d{1,3})/, '$1.$2.$3');
        } else if (value.length > 3) {
            value = value.replace(/(\d{3})(\d{1,3})/, '$1.$2');
        }
        input.value = value;
        this.f.cpf.setValue(value, { emitEvent: false });
    }

    ngOnInit(): void {
        const id = Number(this.route.snapshot.paramMap.get('id'));
        this.pessoaId = id;

        this.pessoaService.buscarPorId(id).subscribe({
            next: (pessoa) => {
                this.form.setValue({
                    nome: pessoa.nome,
                    cpf: pessoa.cpf,
                    sexo: pessoa.sexo,
                    dataNasc: pessoa.dataNasc,
                    peso: pessoa.peso,
                    altura: pessoa.altura,
                });
                this.carregando.set(false);
            },
            error: () => {
                this.alertErro.set('Não foi possível carregar os dados da pessoa.');
                this.carregando.set(false);
            },
        });
    }

    salvar(): void {
        this.form.markAllAsTouched();

        if (this.form.invalid) {
            this.alertErro.set(
                'Preencha todos os campos obrigatórios corretamente antes de continuar.'
            );
            this.alertSucesso.set(false);
            return;
        }

        this.alertErro.set(null);
        this.enviando.set(true);

        const pessoa: Pessoa = {
            nome: this.form.value.nome!,
            cpf: this.form.value.cpf!,
            sexo: this.form.value.sexo as 'M' | 'F',
            dataNasc: this.form.value.dataNasc!,
            peso: this.form.value.peso!,
            altura: this.form.value.altura!,
        };

        this.pessoaService.alterar(this.pessoaId, pessoa).subscribe({
            next: () => {
                this.alertSucesso.set(true);
                this.alertErro.set(null);
                this.enviando.set(false);
            },
            error: () => {
                this.alertErro.set('Ocorreu um erro ao salvar as alterações. Tente novamente.');
                this.enviando.set(false);
            },
        });
    }

    cancelar(): void {
        this.router.navigate(['/']);
    }

    irParaLista(): void {
        this.router.navigate(['/']);
    }
}
