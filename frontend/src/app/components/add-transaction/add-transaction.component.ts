import { Component, computed, inject, signal } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe, NgClass } from '@angular/common';
import { UserService } from '../../services/user.service';
import { TransactionsService } from '../../services/transactions.service';
import { Transaction } from '../../../model/transaction.type';
import { RouterLink } from '@angular/router';

@Component({
	selector: 'app-add-transaction',
	imports: [HeaderComponent, FormsModule, CurrencyPipe, NgClass, RouterLink],
	templateUrl: './add-transaction.component.html',
})
export class AddTransactionComponent {
	userService = inject(UserService)
	transactionsService = inject(TransactionsService)

	amount = signal<number>(0)
	description = signal<string>('')
	category = signal<string>('Category')
	currentBalance = computed(() => this.userService.currentBalance())

	amountDisplayed = computed(() => {
		const balance = parseFloat(this.userService.currentBalance().toString());
		const value = parseFloat(this.amount().toString());
		const result = value ? balance + value : balance;
		return parseFloat(result.toFixed(2));
	});

	transaction = computed<Transaction>(() => {
		var now = new Date();
		return {
			amount: this.amount(),
			description: this.description(),
			category: this.category(),
			date: `${now.getFullYear()}-${now.getMonth() + 1 < 10 ? '0' : ''}${now.getMonth() + 1}-${now.getDate() < 10 ? '0' : ''}${now.getDate()}`,
		}
	});

	isAmountValid = signal<boolean>(true)
	isDescriptionValid = signal<boolean>(true)
	isCategoryValid = signal<boolean>(true)

	isFormValod = signal<boolean>(false)
	validateAmount() {
		this.isAmountValid.set(!isNaN(parseFloat(this.amount().toString())) && this.amount() != 0)
	}
	validateDescription() {
		this.isDescriptionValid.set(this.description() != "")
	}
	validateCategory() {
		this.isCategoryValid.set(this.category() != "" && this.category() != "Category")
	}
	validateForm() {
		this.validateAmount()
		this.validateDescription()
		this.validateCategory()
		this.isFormValod.set(this.isAmountValid() && this.isDescriptionValid() && this.isCategoryValid())
	}


	createTransaction() {
		this.validateForm()
		console.info(this.transaction().date)
		if (this.isFormValod()) {
			this.transactionsService.postTransaction(this.transaction());
		}
	}
}
