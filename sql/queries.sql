-- Introduction
-- All SQL module queries will be placed under this file as reference

-- MODIFYING DATA MODULE
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
  name = 'Tennis Court 2';

-- Question 5: Delete an entire table
DELETE FROM
  cd.bookings;

-- Question 6: Delete a row
DELETE FROM
  cd.members
WHERE
  memid = 37;

-- BASICS MODULE
-- Question 7: Select with conditions
SELECT
  facid, name, membercost, monthlymaintenance
FROM
  cd.facilities as facs
WHERE
  facs.membercost > 0
AND
  facs.membercost < facs.monthlymaintenance / 50;

-- Question 8: Select with string condition
SELECT
  *
FROM
  cd.facilities
WHERE
  name LIKE '%Tennis%';

-- Question 9: Select with range
SELECT
  *
FROM
  cd.facilities
WHERE
  facid IN (1,5);

-- Question 10: Select with date
SELECT
  memid,
  surname,
  firstname,
  joindate
FROM
  cd.members
WHERE
  joindate >= '2012-09-01';

-- Question 11: Select two tables using union
SELECT
  surname
FROM
  cd.members
UNION
SELECT
  name as surname
FROM
  cd.facilities;

-- Join Module
-- Question 12: Simple join
SELECT
  bks.starttime
FROM
  cd.bookings bks
JOIN
  cd.members mbs
ON
  bks.memid = mbs.memid
WHERE
  CONCAT(
    mbs.firstname,
    ' ',
    mbs.surname
  ) = 'David Farrell';

-- Question 13: 
