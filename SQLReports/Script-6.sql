SELECT 
    a.id AS account_id,
    AVG(monthly_sums.january_sum) AS avg_january_sum,
    AVG(monthly_sums.february_sum) AS avg_february_sum,
    AVG(monthly_sums.march_sum) AS avg_march_sum,
    AVG(monthly_sums.april_sum) AS avg_april_sum,
    AVG(monthly_sums.may_sum) AS avg_may_sum,
    AVG(monthly_sums.june_sum) AS avg_june_sum,
    AVG(monthly_sums.july_sum) AS avg_july_sum,
    AVG(monthly_sums.august_sum) AS avg_august_sum,
    AVG(monthly_sums.september_sum) AS avg_september_sum,
    AVG(monthly_sums.october_sum) AS avg_october_sum,
    AVG(monthly_sums.november_sum) AS avg_november_sum,
    AVG(monthly_sums.december_sum) AS avg_december_sum
FROM 
    accounts a
LEFT JOIN (
    SELECT 
        d.account_id,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 1 THEN d.deposit_value ELSE 0 END) AS january_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 2 THEN d.deposit_value ELSE 0 END) AS february_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 3 THEN d.deposit_value ELSE 0 END) AS march_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 4 THEN d.deposit_value ELSE 0 END) AS april_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 5 THEN d.deposit_value ELSE 0 END) AS may_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 6 THEN d.deposit_value ELSE 0 END) AS june_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 7 THEN d.deposit_value ELSE 0 END) AS july_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 8 THEN d.deposit_value ELSE 0 END) AS august_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 9 THEN d.deposit_value ELSE 0 END) AS september_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 10 THEN d.deposit_value ELSE 0 END) AS october_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 11 THEN d.deposit_value ELSE 0 END) AS november_sum,
        SUM(CASE WHEN EXTRACT(MONTH FROM d.time_stamp) = 12 THEN d.deposit_value ELSE 0 END) AS december_sum,
        EXTRACT(YEAR FROM d.time_stamp) AS year
    FROM 
        deposits d
    WHERE 
        EXTRACT(YEAR FROM d.time_stamp) BETWEEN EXTRACT(YEAR FROM CURRENT_DATE) - 2 AND EXTRACT(YEAR FROM CURRENT_DATE)
    GROUP BY 
        d.account_id, EXTRACT(YEAR FROM d.time_stamp)
) AS monthly_sums ON a.id = monthly_sums.account_id
GROUP BY 
    a.id
ORDER BY 
    a.id;
