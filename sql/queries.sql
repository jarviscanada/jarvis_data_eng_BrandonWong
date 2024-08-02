-- Introduction
-- All SQL module queries will be placed under this file as reference

-- Question 1: Insert a row
INSERT INTO cd.facilities 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800);

-- Question 2: Insert a row with auto-increment ID
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost,
  initialoutlay, monthlymaintenance
)
VALUES
  ((SELECT max(facid) FROM cd.facilities)+ 1,
  'Spa', 20, 30, 100000, 800);

-- Question 3: Update a value on a specific row
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  name = 'Tennis Court 2';

-- Question 4: Update a row based on other row(s)
UPDATE
  cd.facilities facs
SET
  membercost = facs2.membercost * 1.1,
  guestcost = facs2.guestcost * 1.1,
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


