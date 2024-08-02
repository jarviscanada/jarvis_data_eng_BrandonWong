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
