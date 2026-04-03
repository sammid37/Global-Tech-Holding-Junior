export interface Pessoa {
    id?: number;
    nome: string;
    cpf: string;
    sexo: "M" | "F";
    dataNasc: string;
    altura: number;
    peso: number;
}