import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function dataNascValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value as string;
        if (!value) return null;

        const dataNasc = new Date(value);
        const hoje = new Date();
        hoje.setHours(0, 0, 0, 0);

        return dataNasc > hoje ? { dataNascFutura: true } : null;
    };
}
