import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PessoaService } from '../../core/services/pessoa.service';
import { Pessoa } from '../../shared/models/pessoa.model';
import { cpfValidator } from '../../shared/validators/cpf.validator';
import { dataNascValidator } from '../../shared/validators/data-nasc.validator';

@Component({
    selector: 'app-pessoas-cadastro',
    standalone: true,
    imports: [ReactiveFormsModule],
    templateUrl: './pessoas-cadastro.component.html',
})
export class PessoasCadastroComponent {
    private pessoaService = inject(PessoaService);
    private router = inject(Router);

    readonly today = new Date().toISOString().split('T')[0];

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

    alertErro = signal<string | null>(null);
    alertSucesso = signal(false);
    enviando = signal(false);

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

    cadastrar(): void {
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

        this.pessoaService.incluir(pessoa).subscribe({
            next: () => {
                this.alertSucesso.set(true);
                this.alertErro.set(null);
                this.enviando.set(false);
                this.form.reset();
            },
            error: (error) => {
                const mensagem = error?.error?.erro ?? error?.error?.message ?? 'Ocorreu um erro ao cadastrar a pessoa. Tente novamente.';
                this.alertErro.set(mensagem);
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
