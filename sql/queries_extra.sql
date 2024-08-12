-- Introduction
-- To improve SQL queries writing, a classroom database has been created with additional relationships compared to the previous ERD.

-- Aggregation Module
-- Show the average grade for all students
SELECT
  AVG(grade)
FROM
  Grades;

-- Show all information about the youngest teacher on staff
SELECT
  *
FROM
  Teachers
WHERE
  Age = (
    SELECT
      MIN(Age)
    FROM
      Teachers
  );

-- Show the number of students who are currently in a class (all students have a grade for each subject they have enrolled in)
SELECT
  COUNT(*)
FROM
  Students sts
  JOIN Grades grs ON grs.StudentID = sts.StudentID
  JOIN Subjects subs ON grs.SubjectID = subs.SubjectID
  JOIN Classes cls ON cls.ClassID = subs.ClassID;

-- Show the name of all students who have the same grade in multiple subjects
-- TODO

-- Show the average age of all male students
SELECT
  AVG(Age)
FROM
  Students
WHERE
  Gender = 'Male';

-- Window Function
-- Show each teacher's name, the number of students under them, and the average number of students for any teacher
-- TODO

-- Show each student's name, their grade for each subject, and the average grade amongst all students for that same subject
SELECT
  sts.Name,
  grs.Grade,
  grs.SubjectID,
  AVG(grs.Grade) OVER (PARTITION BY grs.SubjectID)
FROM
  Grades grs
  JOIN Students sts ON grs.StudentID = sts.StudentID
GROUP BY
  sts.Name,
  grs.Grade,
  grs.SubjectID;

-- Show each class's name, student count, and the total number of students in the school
-- TODO

-- Show the ranking of students based on their grade (average grade for all their subjects)
SELECT
  StudentID,
  Grade,
  RANK() OVER (ORDER BY AVG(Grade) DESC)
FROM
  Grades
GROUP BY
  StudentID,
  Grade;

-- Show each student's name, the subject, and the grade in which they have 75% or better compared to the maximum grade for that subject (the maximum is considered 100%)
-- TODO
