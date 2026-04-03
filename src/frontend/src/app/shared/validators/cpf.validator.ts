import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function validarCPF(cpf: string): boolean {
    const digits = cpf.replace(/\D/g, '');

    if (digits.length !== 11) return false;

    // Rejects known invalid sequences (e.g. 000.000.000-00, 111.111.111-11, ...)
    if (/^(\d)\1{10}$/.test(digits)) return false;

    const calcDigit = (slice: string, factor: number): number => {
        const sum = slice
            .split('')
            .reduce((acc, d, i) => acc + Number(d) * (factor - i), 0);
        const remainder = (sum * 10) % 11;
        return remainder >= 10 ? 0 : remainder;
    };

    const firstDigit = calcDigit(digits.slice(0, 9), 10);
    if (firstDigit !== Number(digits[9])) return false;

    const secondDigit = calcDigit(digits.slice(0, 10), 11);
    return secondDigit === Number(digits[10]);
}

export function cpfValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value as string;
        if (!value) return null;
        return validarCPF(value) ? null : { cpfInvalido: true };
    };
}
