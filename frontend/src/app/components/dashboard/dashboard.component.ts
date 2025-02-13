import { CurrencyPipe, DatePipe, NgClass } from '@angular/common';
import { Component, computed, effect, inject, signal } from '@angular/core';
import { TransactionsService } from '../../services/transactions.service';
import { Transaction } from '../../../model/transaction.type';
import { Chart, CategoryScale, LinearScale, Title, Tooltip, Legend, LineElement, PointElement, ArcElement, LineController } from 'chart.js';
import { HeaderComponent } from '../header/header.component';
import { UserService } from '../../services/user.service';

Chart.register(
	CategoryScale,
	LinearScale,
	Title,
	Tooltip,
	Legend,
	LineElement,
	PointElement,
	ArcElement,
	LineController
);

@Component({
	selector: 'app-dashboard',
	imports: [CurrencyPipe, NgClass, DatePipe,HeaderComponent],
	templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
	transactionsService = inject(TransactionsService)
	userService = inject(UserService)

	transactions = signal<Transaction[]>([]);

	totalBalance = computed(() => {
		const value =this.transactions().reduce((sum, transaction) => sum + transaction.amount, 0);
		return value;
	})

	ngOnInit() {
		this.transactionsService.getAllTransactions().subscribe({
			next: (data) => {
				const sortedTransactions = data.sort((a: Transaction, b: Transaction) =>
					new Date(b.date).getTime() - new Date(a.date).getTime()
				);
				this.transactions.set(sortedTransactions);

				const now = new Date();

				const dailyBalance: number[] = [];
				const labels: string[] = [];

				let cumulativeBalance = 0;
				for (let day = 1; day <= now.getDate(); day++) {
					cumulativeBalance=0;
					const graphDayDate=new Date(`${now.getFullYear()}-${now.getMonth()+1}-${day}`)
					const transactionsForTheDay = sortedTransactions.filter((transaction: Transaction) => {
						const transactionDate = new Date(transaction.date);
						return transactionDate.getTime() < graphDayDate.getTime()
					});

					transactionsForTheDay.forEach((transaction: Transaction) => {
						cumulativeBalance += transaction.amount;
					});

					dailyBalance.push(cumulativeBalance);
				}
				var daysInCurrenMonth=new Date(now.getFullYear(), now.getMonth()+1, 0).getDate();
				for (let i = 0; i < daysInCurrenMonth; i++) { labels.push("") }

				const ctx = (document.getElementById('myChart') as HTMLCanvasElement).getContext('2d');
				if (ctx) {
					const chartData = {
						labels: labels,
						datasets: [{
							label: 'Balance',
							data: dailyBalance,
							borderColor: '#a6adbb',
							fill: false,
							stepped: true,
							pointRadius: 0,
						}]
					};

					new Chart(ctx, {
						type: 'line',
						data: chartData,
						options: {
							layout: {
								padding: 2
							},
							plugins: {
								legend: {
									display: false,
								},
								tooltip: {
									enabled: false,
								}
							},
							scales: {
								x: {
									grid: {
										display: false,
									},
									ticks: {
										display: false,
									},
									border: {
										width: 0
									}
								},
								y: {
									beginAtZero: true,
									min: 0,
									grid: {
										display: false,
									}, ticks: {
										font: {
											family: 'ui-sans-serif, system-ui, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji"',
											weight: 'bolder'
										}
									},
									border: {
										width: 0
									}
								}
							}
						}
					});
				}
			},
			error: (error) => console.error('Error fetching transactions:', error),
			complete: () => console.info('Completed fetching transactions')
		});
	}
	constructor() {
		effect(() => {
		  this.userService.currentBalance.set(this.totalBalance());
		});
	  }
}
