import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Transaction } from '../../model/transaction.type';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
	providedIn: 'root'
})
export class TransactionsService {
	constructor(private http: HttpClient, private router:Router) { }

	getAllTransactions(): Observable<any> {
		const headers = new HttpHeaders({ 'Authorization': `${sessionStorage.getItem('token')}` });
		return this.http.get<Transaction[]>('http://localhost:8080/expenses', { headers });
	}
	
	postTransaction(transaction: Transaction): void {
		const headers = new HttpHeaders({ 'Authorization': `${sessionStorage.getItem('token')}` });
		this.http.post('http://localhost:8080/expenses', transaction, { headers }).subscribe({
			complete: () => this.router.navigate(['/dashboard']),
			error: (error) => console.error('Transaction creation error:', error)
		});
	}
}
