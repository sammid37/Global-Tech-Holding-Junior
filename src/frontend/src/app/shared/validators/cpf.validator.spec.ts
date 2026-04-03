import { FormControl } from '@angular/forms';
import { cpfValidator, validarCPF } from './cpf.validator';

describe('validarCPF', () => {
    describe('CPFs válidos', () => {
        it('deve aceitar CPF formatado válido', () => {
            expect(validarCPF('529.982.247-25')).toBe(true);
        });

        it('deve aceitar CPF apenas com dígitos válido', () => {
            expect(validarCPF('52998224725')).toBe(true);
        });

        it('deve aceitar outro CPF válido', () => {
            expect(validarCPF('111.444.777-35')).toBe(true);
        });

        it('deve aceitar CPF com dígito verificador zero', () => {
            expect(validarCPF('935.411.347-80')).toBe(true);
        });
    });

    describe('CPFs inválidos — dígitos verificadores incorretos', () => {
        it('deve rejeitar CPF com primeiro dígito verificador errado', () => {
            expect(validarCPF('529.982.247-35')).toBe(false);
        });

        it('deve rejeitar CPF com segundo dígito verificador errado', () => {
            expect(validarCPF('529.982.247-26')).toBe(false);
        });

        it('deve rejeitar CPF com ambos os dígitos verificadores errados', () => {
            expect(validarCPF('529.982.247-00')).toBe(false);
        });
    });

    describe('CPFs inválidos — sequências conhecidas', () => {
        const sequencias = [
            '000.000.000-00',
            '111.111.111-11',
            '222.222.222-22',
            '333.333.333-33',
            '444.444.444-44',
            '555.555.555-55',
            '666.666.666-66',
            '777.777.777-77',
            '888.888.888-88',
            '999.999.999-99',
        ];

        sequencias.forEach((cpf) => {
            it(`deve rejeitar sequência inválida: ${cpf}`, () => {
                expect(validarCPF(cpf)).toBe(false);
            });
        });
    });

    describe('entradas malformadas', () => {
        it('deve rejeitar string vazia', () => {
            expect(validarCPF('')).toBe(false);
        });

        it('deve rejeitar CPF com menos de 11 dígitos', () => {
            expect(validarCPF('529.982.247')).toBe(false);
        });

        it('deve rejeitar CPF com mais de 11 dígitos', () => {
            expect(validarCPF('529.982.247-255')).toBe(false);
        });

        it('deve rejeitar texto aleatório', () => {
            expect(validarCPF('abc.def.ghi-jk')).toBe(false);
        });
    });
});

describe('cpfValidator', () => {
    const validator = cpfValidator();

    it('deve retornar null para controle vazio (campo não preenchido)', () => {
        const control = new FormControl('');
        expect(validator(control)).toBeNull();
    });

    it('deve retornar null para CPF válido', () => {
        const control = new FormControl('529.982.247-25');
        expect(validator(control)).toBeNull();
    });

    it('deve retornar erro cpfInvalido para CPF inválido', () => {
        const control = new FormControl('529.982.247-99');
        expect(validator(control)).toEqual({ cpfInvalido: true });
    });

    it('deve retornar erro cpfInvalido para sequência repetida', () => {
        const control = new FormControl('111.111.111-11');
        expect(validator(control)).toEqual({ cpfInvalido: true });
    });
});
