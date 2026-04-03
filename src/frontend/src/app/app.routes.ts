import { Routes } from '@angular/router';
import { PessoasListaComponent } from './pages/pessoas/pessoas-lista.component';
import { PessoasCadastroComponent } from './pages/pessoas/pessoas-cadastro.component';
import { PessoasEdicaoComponent } from './pages/pessoas/pessoas-edicao.component';

export const routes: Routes = [
    { path: '', component: PessoasListaComponent },
    { path: 'cadastrar', component: PessoasCadastroComponent },
    { path: 'editar/:id', component: PessoasEdicaoComponent },
];
