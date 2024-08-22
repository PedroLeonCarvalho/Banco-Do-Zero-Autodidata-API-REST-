SELECT 
    a.id AS account_id, 
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 1 THEN d.deposit_value ELSE 0 END) AS total_deposits_january,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 2 THEN d.deposit_value ELSE 0 END) AS total_deposits_february,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 3 THEN d.deposit_value ELSE 0 END) AS total_deposits_march,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 4 THEN d.deposit_value ELSE 0 END) AS total_deposits_april,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 5 THEN d.deposit_value ELSE 0 END) AS total_deposits_may,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 6 THEN d.deposit_value ELSE 0 END) AS total_deposits_june,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 7 THEN d.deposit_value ELSE 0 END) AS total_deposits_july,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 8 THEN d.deposit_value ELSE 0 END) AS total_deposits_august,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 9 THEN d.deposit_value ELSE 0 END) AS total_deposits_september,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 10 THEN d.deposit_value ELSE 0 END) AS total_deposits_october,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 11 THEN d.deposit_value ELSE 0 END) AS total_deposits_november,
    SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 12 THEN d.deposit_value ELSE 0 END) AS total_deposits_december
FROM 
	accounts a
LEFT JOIN 
    deposits d ON a.id = d.account_id 
AND EXTRACT(YEAR FROM d.time_stamp) = EXTRACT(YEAR FROM CURRENT_DATE)
GROUP BY 
    a.id
ORDER BY 
    a.id;

