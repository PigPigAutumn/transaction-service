INSERT INTO t_transaction (id, transaction_id, account, amount, balance, type, status, counterparty, description, create_time, update_time)
VALUES
(1, 'TXN202501001', 'ACC001', 100.00, 500.00, 'DEPOSIT', 'COMPLETED', 'Bank of China', 'Initial deposit', '2025-01-15 09:30:45', '2025-01-15 09:30:45'),
(2, 'TXN202501002', 'ACC001', -50.00, 450.00, 'WITHDRAWAL', 'COMPLETED', 'ATM 001', 'Cash withdrawal', '2025-01-16 14:22:18', '2025-01-16 14:22:18'),
(3, 'TXN202501003', 'ACC002', 200.00, 700.00, 'DEPOSIT', 'COMPLETED', 'Bank of America', 'Salary deposit', '2025-01-20 08:45:30', '2025-01-20 08:45:30'),
(4, 'TXN202502001', 'ACC002', -75.50, 624.50, 'WITHDRAWAL', 'COMPLETED', 'Supermarket', 'Grocery shopping', '2025-02-05 16:40:22', '2025-02-05 16:40:22'),
(5, 'TXN202502002', 'ACC003', 1500.00, 1500.00, 'DEPOSIT', 'COMPLETED', 'Investment Co.', 'Dividend payment', '2025-02-14 11:15:33', '2025-02-14 11:15:33'),
(6, 'TXN202502003', 'ACC001', 300.00, 750.00, 'TRANSFER', 'PENDING', 'ACC003', 'Friend repayment', '2025-02-28 13:05:17', '2025-02-28 13:05:17'),
(7, 'TXN202503001', 'ACC003', -200.00, 1300.00, 'WITHDRAWAL', 'COMPLETED', 'Online Store', 'Online purchase', '2025-03-10 20:18:09', '2025-03-10 20:18:09'),
(8, 'TXN202503002', 'ACC002', 125.75, 750.25, 'DEPOSIT', 'COMPLETED', 'Bank of China', 'Interest earned', '2025-03-18 10:30:45', '2025-03-18 10:30:45'),
(9, 'TXN202503003', 'ACC001', -30.00, 720.00, 'WITHDRAWAL', 'COMPLETED', 'Restaurant', 'Dinner payment', '2025-03-25 19:45:22', '2025-03-25 19:45:22'),
(10, 'TXN202504001', 'ACC003', 500.00, 1800.00, 'DEPOSIT', 'COMPLETED', 'Employer', 'Bonus payment', '2025-04-05 12:00:00', '2025-04-05 12:00:00'),
(11, 'TXN202504002', 'ACC002', -45.25, 705.00, 'WITHDRAWAL', 'COMPLETED', 'Gas Station', 'Fuel payment', '2025-04-12 17:30:15', '2025-04-12 17:30:15'),
(12, 'TXN202504003', 'ACC001', 80.50, 800.50, 'DEPOSIT', 'PENDING', 'ACC002', 'Money received', '2025-04-28 09:15:30', '2025-04-28 09:15:30');