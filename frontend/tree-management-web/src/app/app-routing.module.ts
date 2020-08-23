import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TreeManagementComponent} from "./pages/tree-management/tree-management.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";


const routes: Routes = [
  {path: '', component: TreeManagementComponent},
  {path: 'not-found', component: PageNotFoundComponent},
  {path: '**', redirectTo: '/not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
