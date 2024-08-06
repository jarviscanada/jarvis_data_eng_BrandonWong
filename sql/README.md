# Introduction

# SQL Queries
## Table Setup (DDL)

## Modifying data module
### Question 1: Insert a row
Insert a row to table `cd.facilities` with default values. Note that a template was not provided since the order and the values match all columns of the `cd.facilities` table
```sql
INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
```

### Question 2: Insert a row with auto-increment ID
Insert a row to table `cd.facilities` with auto-increment ID. This can be achieved by performing a subquery that fetches the highest ID value from the `cd.facilities` table.
```sql
INSERT INTO cd.facilities (
  facid,
  name, 
  membercost, 
  guestcost, 
  initialoutlay, 
  monthlymaintenance
) 
VALUES 
  ((SELECT max(facid) FROM cd.facilities)+ 1,
    'Spa',
    20, 
    30, 
    100000, 
    800
  );
```

### Question 3: Update a value of a specific row
Update a value of a specific row on table `cd.facilities`. Ensure that we can select the specific row either with the primary key or value that is unique.
```sql
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  name = 'Tennis Court 2';
```

### Question 4: Update a row based on other row(s)
Update a row from values of a different row. To reduce I/Os, use `FROM` to select the rows to compare it with.
```sql
UPDATE 
  cd.facilities facs 
SET 
  membercost = facs2.membercost * 1.1, 
  guestcost = facs2.guestcost * 1.1 
FROM 
  (
    SELECT 
      membercost, 
      guestcost 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) as facs2 
WHERE 
  name = 'Tennis Court 2';
```

### Question 5: Delete an entire table
Delete an entire table using the table's name
```sql
DELETE FROM
  cd.bookings;
```

### Question 6: Delete a row
Delete a row that does not have any foreign keys. In this example, the member does not have any bookings so we can safely remove the row on the table without worrying that the primary key exists as a foreign key on other tables
```sql
DELETE FROM
  cd.members
WHERE
  memid = 37;
```

## Basics Module
### Question 7: Select with conditions
Select rows that satisfy conditions. You can use aliases to refer to each row with a readable name and use values to compare each row.
```sql
SELECT
  facid, 
  name, 
  membercost, 
  monthlymaintenance
FROM
  cd.facilities as facs
WHERE
  facs.membercost > 0
AND
  facs.membercost < facs.monthlymaintenance / 50;
```

### Question 8: Select with string condition
Select rows that satisfy a given substring. You can use `%` to indicate that there can be any characters
```sql
SELECT
  *
FROM
  cd.facilities
WHERE
  name LIKE '%Tennis%';
```

### Question 9: Select with range
Select rows with specific IDs without comparing each ID. You can use the `IN` operator to give a list of values to compare a field to.
```sql
SELECT
  *
FROM
  cd.facilities
WHERE
  facid IN (1,5);
```

### Question 10: Select with date
Select rows that is before a certain date. You can use number comparison to compare dates, and date formats can be wrapped with single quotes using YYYY-MM-DD HH:MM:SS
```sql
SELECT
  memid,
  surname,
  firstname,
  joindate
FROM
  cd.members
WHERE
  joindate >= '2012-09-01';
```

### Question 11: Select two tables using union
Select two rows and place it to a single column. You can use UNION that can have two selections and place them together. Unlike JOIN, it doesnt not require the rows between two tables to match with an identifier.
```sql
SELECT
  surname
FROM
  cd.members
UNION
SELECT
  name as surname
FROM
  cd.facilities;
```

## Join Module
### Question 12: Simple join
Join bookings and members so that we can fetch a list of bookings based on a specific name. If we have access to their ID, then we dont have to use JOIN; however, we are only provided with a name. Thus, a join operation must be made to complete it. Furthermore, if you want to compare a multiple strings to a string, use CONCAT to ensure it becomes one entire string for comparison
```sql
SELECT
  bks.starttime
FROM
  cd.bookings bks
  JOIN cd.members mbs ON bks.memid = mbs.memid
WHERE
  CONCAT(
    mbs.firstname,
    ' ',
    mbs.surname
  ) = 'David Farrell';
```

### Question 13: Join between ordered dates
Join bookings on certain facilities by ordered date. To order dates, use ORDER BY and since we are looking for a range, you can compare two dates or use the BETWEEN keyword. We cannot compare a given date to a specific date using the equal operator.
```sql
SELECT
  bks.starttime as start,
  facs.name
FROM
  cd.bookings bks
  JOIN cd.facilities facs ON bks.facid = facs.facid
WHERE
  facs.name LIKE 'Tennis%'
  AND bks.starttime BETWEEN '2012-09-21 00:00:00'
  AND '2012-09-22 00:00:00'
ORDER BY
  start;
```

### Question 14: Left outer join
Join a table that has reference to itself; however, we display the row whether we have a match or not. If we choose the first table to fetch the information then we can assign it to the left side. Thus, using LEFT OUTER JOIN ensures that any left rows will be displayed whether the right side has a match or not. If it doesn't, a null value will be placed.
```sql
SELECT
  mbs.firstname as memfname,
  mbs.surname as memsname,
  mbs2.firstname as recfname,
  mbs2.surname as recsname
FROM
  cd.members as mbs
  LEFT OUTER JOIN cd.members mbs2 ON mbs.recommendedby = mbs2.memid
ORDER BY
  memsname, memfname;
```

### Question 15: Inner distinct join
Join a table who have recommended another member. In the schema, we have the ID of which member recommended them. So we have to reverse the process by using an inner join. To remove any duplicates, we can use DISTINCT
```sql
SELECT
  DISTINCT(mbs2.firstname) as firstname,
  mbs2.surname as surname
FROM
  cd.members as mbs
  JOIN cd.members as mbs2 ON mbs2.memid = mbs.recommendedby
ORDER BY
  surname,
  firstname;
```

### Question 16: Join without using join
Join tables without the keyword JOIN can be achieved using a subquery. Inside the subquery, we can match the left table with the right table.
```sql
SELECT
  DISTINCT CONCAT(mbs.firstname, ' ', mbs.surname) as member,
  (
    SELECT
      CONCAT(mbs2.firstname, ' ', mbs2.surname) as recommender
    FROM
      cd.members as mbs2
    WHERE
      mbs2.memid = mbs.recommendedby
  )
FROM
  cd.members as mbs
ORDER BY
  members;
```

## Aggregation Module
### Question 17: Count aggregation
Count number of recurring value on a field. Ensure that we group the recurring value together using GROUP BY.
```sql
SELECT
  recommendedby,
  COUNT(recommendedby)
FROM
  cd.members
WHERE
  recommendedby IS NOT NULL
GROUP BY
  recommendedby
ORDER BY
  recommendedby;
```

### Question 18: Sum aggregation
Sum all values of a certain field. Ensure once again that we group the target of the recurring field together.
```sql
SELECT
  facid,
  SUM(slots)
FROM
  cd.bookings
GROUP BY
  facid
ORDER BY
  facid;
```

### Question 19: Filter sum aggregation
Sum all values and filter by certain date. Note that you can sort the rows based on the aggregation value.
```sql
SELECT
  facid,
  SUM(slots) as "Total Slots"
FROM
  cd.bookings
WHERE
  starttime BETWEEN '2012-09-01'
  AND '2012-10-01'
GROUP BY
  facid
ORDER BY
  SUM(slots);
```

### Question 20: Extract date aggregation
Sum all values based on a specific date. To extract a date/month/year, you can use keyword EXTRACT(<prefix> FROM <field>) to extract it.
```sql
SELECT
  facid,
  EXTRACT(MONTH FROM starttime) as month,
  SUM(slots)
FROM
  cd.bookings
WHERE
  EXTRACT(YEAR FROM starttime) = '2012'
GROUP BY
  facid,
  month
ORDER BY
  facid,
  month;
```

### Question 21: Subquery aggregation
Select unique rows for a field. You can use the COUNT keyword on a subquery. In this scenario, we can find the unique rows using DISTINCT.
```sql
SELECT
  COUNT(
    DISTINCT(memid)
  )
FROM
  cd.bookings;
```

### Question 22: Join aggregation
To perform an aggregation on a join, you can simply start the join and group by the field on the desired table. Furthermore, to select the first occurrence on a datetime, you can use the MIN aggregation to get the smallest date.
```sql
SELECT
  mbs.surname,
  mbs.firstname,
  mbs.memid,
  MIN(bks.starttime)
FROM
  cd.members as mbs
  JOIN cd.bookings bks ON mbs.memid = bks.memid
WHERE
  bks.starttime > '2012-09-01'
GROUP BY
  mbs.memid
ORDER BY
  mbs.memid;
```

### Question 23: Window function
To perform a window function, we can use the OVER keyword that will operate on a set of specified rows. In this scenario, we want to select all rows so we can keep it empty.
```sql
SELECT
  COUNT(*) OVER() as count,
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;
```

### Question 24: Row number window function
To perform a mono increasing list, we can use the ROW\_NUMBER. We do not use PARTITION BY since we want to apply ROW\_NUMBER on the entire set. Furthermore, we want to filter in incremental order of joindate so we can use ORDER BY.
```sql
SELECT
  ROW_NUMBER() OVER(ORDER BY joindate),
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;
```

### Question 25: Subquery window function
To perform a subquery on a window function, you can use a temporary view or write the subquery under SELECT. To select the maximum value, you can use MAX if the ddl allows it. Another way is to use the window function RANK. We can create a RANK window function order by the sum of the values in descending order.
```sql
WITH result AS (
  SELECT
    facid,
    SUM(slots) as total,
    RANK() OVER (
      ORDER BY sum(slots) DESC
    ) as rank
  FROM
    cd.bookings
  GROUP BY
    facid
)
SELECT
  facid,
  total
FROM
  result
WHERE
  rank = 1;
```
