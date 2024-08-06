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

-- Question 13: Join between ordered dates
SELECT
  bks.starttime as start,
  facs.name
FROM
  cd.bookings bks
  JOIN cd.facilities facs ON bks.facid = facs.facid
WHERE
  facs.name LIKe 'Tennis%'
  AND bks.starttime BETWEEN '2012-09-21 00:00:00'
  AND '2012-09-22 00:00:00'
ORDER BY
  start;

-- Question 14: Left outer join
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

-- Question 15: Inner distinct join
SELECT
  DISTINCT(mbs2.firstname) as firstname,
  mbs2.surname as surname
FROM
  cd.members as mbs
  JOIN cd.members as mbs2 ON mbs2.memid = mbs.recommendedby
ORDER BY
  surname,
  firstname;

-- Question 16: Join without using join
SELECT
  DISTINCT CONCAT(mbs.firstname, ' ', mbs.surname) as member,
  (
    SELECT
      CONCAT(mbs2.firstname, ' ', mbs2.surname) as recommnder
    FROM
      cd.members as mbs2
    WHERE
      mbs2.memid = mbs.recommendedby
  )
FROM
  cd.members as mbs
ORDER BY
  members;

-- Aggregation Module
-- Question 17: Count aggregation
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

-- Question 18: Sum aggregation
SELECT
  facid,
  SUM(slots)
FROM
  cd.bookings
GROUP BY
  facid
ORDER BY
  facid;

-- Question 19: Filter sum aggregation
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

-- Question 20: Extract date aggregation
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

-- Question 21: Subquery aggregation
SELECT
  COUNT(
    DISTINCT(memid)
  )
FROM
  cd.bookings;

-- Question 22: Join aggregation
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

-- Question 23: Window function
SELECT
  COUNT(*) OVER() as count,
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;

-- Question 24: Row number window function
SELECT
  ROW_NUMBER() OVER(ORDER BY joindate),
  firstname,
  surname
FROM
  cd.members
ORDER BY
  joindate;

-- Question 25: Subquery window function
WITH result AS (
  SELECT
    facid,
    SUM(slots) as total,
    RANK() OVER (
      ORDER BY SUM(slots) DESC
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
