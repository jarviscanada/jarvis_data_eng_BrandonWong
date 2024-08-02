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

