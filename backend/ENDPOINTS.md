

| **Method** | **Endpoint**                   | **Description**                                   | **Parameters**                              | **Response**                                  |
| ---------- | ------------------------------ | ------------------------------------------------- | ------------------------------------------- | --------------------------------------------- |
| `POST`     | `/auth/register`               | Register a new user                               | `email`, `username`, `password`             | `JWT token`, user data                        |
| `POST`     | `/auth/login`                  | Log in and return JWT                             | `email`, `password`                         | `JWT token`                                   |
| `GET`      | `/auth/me`                     | Get current user from JWT                         | -                                           | User data                                     |
| `PUT`      | `/auth/update`                 | Update user info (username, password, etc.)       | `username`, `password`                      | Updated user data                             |
| `DELETE`   | `/auth/delete`                 | Delete user account                               | -                                           | Confirmation response                         |
| `POST`     | `/expenses`                    | Add a new expense                                 | `amount`, `category`, `description`, `date` | Created expense data                          |
| `GET`      | `/expenses`                    | Get all expenses for the authenticated user       | -                                           | List of all expenses                          |
| `GET`      | `/expenses/category/:category` | Get expenses filtered by category                 | `category`                                  | List of expenses in the specified category    |
| `GET`      | `/expenses/weekly-summary`     | Get total spending for the last 7 days            | -                                           | Total spending for the last 7 days            |
| `GET`      | `/expenses/daily-summary`      | Get total spending for each day in the last month | -                                           | Total spending for each day in the last month |
| `GET`      | `/expenses/monthly-summary`    | Get total spending for the last month             | -                                           | Total spending for the last month             |
| `GET`      | `/expenses/spent-summary`      | Get total spending for a custom date range        | `from`, `till` (e.g., `YYYY-MM-DD`)         | Total spending between `from` and `till`      |
| `PUT`      | `/expenses/:id`                | Edit an existing expense                          | `amount`, `category`, `description`, `date` | Updated expense data                          |
| `DELETE`   | `/expenses/:id`                | Delete an expense                                 | -                                           | Confirmation response                         |

