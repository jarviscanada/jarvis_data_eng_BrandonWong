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

