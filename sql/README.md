# Introduction

# SQL Queries
## Table Setup (DDL)


## Question 1: Insert a row
Insert a row to table `cd.facilities` with default values. Note that a template was not provided since the order and the values match all columns of the `cd.facilities` table
```
INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);
```

## Question 2: Insert a row with auto-increment ID
Insert a row to table `cd.facilities` with auto-increment ID. This can be achieved by performing a subquery that fetches the highest ID value from the `cd.facilities` table.
```
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  ((SELECT max(facid) FROM cd.facilities)+ 1,
    'Spa', 20, 30, 100000, 800);
```

## Question 3: Update a value of a specific row
Update a value of a specific row on table `cd.facilities`. Ensure that we can select the specific row either with the primary key or value that is unique.
```
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  name = 'Tennis Court 2';
```

## Question 4: Update a row based on other row(s)
Update a row from values of a different row. To reduce I/Os, use `FROM` to select the rows to compare it with.
```
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
  name = 'Tennis Court 2'
```

