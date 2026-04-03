import { FormControl } from '@angular/forms';
import { dataNascValidator } from './data-nasc.validator';

describe('dataNascValidator', () => {
    const validator = dataNascValidator();

    it('deve retornar null para controle vazio', () => {
        const control = new FormControl('');
        expect(validator(control)).toBeNull();
    });

    it('deve retornar null para data no passado', () => {
        const control = new FormControl('1990-06-15');
        expect(validator(control)).toBeNull();
    });

    it('deve retornar null para a data de hoje', () => {
        const hoje = new Date().toISOString().split('T')[0];
        const control = new FormControl(hoje);
        expect(validator(control)).toBeNull();
    });

    it('deve retornar erro dataNascFutura para data de amanhã', () => {
        const amanha = new Date();
        amanha.setDate(amanha.getDate() + 1);
        const control = new FormControl(amanha.toISOString().split('T')[0]);
        expect(validator(control)).toEqual({ dataNascFutura: true });
    });

    it('deve retornar erro dataNascFutura para data distante no futuro', () => {
        const control = new FormControl('2099-12-31');
        expect(validator(control)).toEqual({ dataNascFutura: true });
    });
});
