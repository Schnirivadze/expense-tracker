import { Component, computed, input, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../../model/user.type';
import { FormsModule } from '@angular/forms'
import { NgClass } from '@angular/common';

@Component({
	selector: 'app-register',
	imports: [RouterLink, FormsModule, NgClass],
	templateUrl: './register.component.html'
})
export class RegisterComponent {
	constructor(private userService: UserService) { }

	username = signal<string>('');
	password = signal<string>('');
	email = signal<string>('');
	user = computed<User>(() => ({
		username: this.username(),
		password: this.password(),
		email: this.email(),
		currentBalance: 0
	}));

	isValidUsername = signal<boolean>(true);
	isValidPassword = signal<boolean>(true);
	isValidEmail = signal<boolean>(true);

	validateForm() {
		this.validateUsername();
		this.validatePassword();
		this.validateEmail();
		return this.isValidEmail() && this.isValidPassword() && this.isValidUsername();
	}

	validateUsername() {
		this.isValidUsername.set(this.username() != '');
	}

	validatePassword() {
		this.isValidPassword.set(this.password() != '');
	}

	validateEmail() {
		this.isValidEmail.set(
			this.email() != "" && this.email().includes('@') &&
			this.email().substring(this.email().indexOf('@')).includes('.') &&
			this.email().substring(this.email().indexOf('.')).length > 1
		);
	}

	registerUser() {
		var isFormValid = this.validateForm()
		if (isFormValid) {
			this.userService.registerUser(this.user());
		}
	}
}
