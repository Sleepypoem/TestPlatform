USE `test-platform`;

-- -------------------------------------------------------
-- ROLES
-- -------------------------------------------------------
INSERT INTO `test-platform`.`roles` (
`id`, `name`, `description`, `created_at`, `updated_at`)
 VALUES (
 1, 'ADMIN', 'A role with full users management.', '2023-11-02 07:55:41', NOW()
 );
INSERT INTO `test-platform`.`roles` (
`id`, `name`, `description`, `created_at`, `updated_at`)
 VALUES (
 2, 'USER', 'A role with limited users management.', '2023-11-02 02:55:41', NOW()
 );

-- -------------------------------------------------------
-- TEACHERS
-- -------------------------------------------------------
INSERT INTO `test-platform`.`teachers` (
`id`, `first_name`,`last_name`, `teacher_code`, `created_at`, `updated_at`)
 VALUES (
 1, 'John ', 'Doe', 77885522, '2023-11-05 14:55:41', NOW()
 );
 INSERT INTO `test-platform`.`teachers` (
`id`, `first_name`,`last_name`, `teacher_code`, `created_at`, `updated_at`)
 VALUES (
 2, 'Jane ', 'Doe', 77446622, '2023-11-05 14:55:41', NOW()
 );

-- -------------------------------------------------------
-- STUDENTS
-- -------------------------------------------------------
INSERT INTO `test-platform`.`students` (
`id`, `first_name`,`last_name`, `student_code`, `created_at`, `updated_at`)
 VALUES (
 1, 'Monika', 'White', 01254789, '2023-11-05 14:55:41', NOW()
 );
 INSERT INTO `test-platform`.`students` (
`id`, `first_name`,`last_name`, `student_code`, `created_at`, `updated_at`)
 VALUES (
 2, 'Sayori', 'Daiba', 02547789, '2023-11-05 14:55:41', NOW()
 );
 INSERT INTO `test-platform`.`students` (
`id`, `first_name`,`last_name`, `student_code`, `created_at`, `updated_at`)
 VALUES (
 3, 'Yuri', 'Hoshino', 03547789, '2023-11-05 14:55:41', NOW()
 );
 INSERT INTO `test-platform`.`students` (
`id`, `first_name`,`last_name`, `student_code`, `created_at`, `updated_at`)
 VALUES (
 4, 'Natsuki', 'Amai', 04547789, '2023-11-05 14:55:41', NOW()
 );

 -- -------------------------------------------------------
  -- SUBJECTS
  -- -------------------------------------------------------
  INSERT INTO `test-platform`.`subjects` (
 `id`, `name`, `description`, `created_at`, `updated_at`)
  VALUES (
  1, 'Math', 'A subject with math tests.', '2023-11-05 14:55:41', NOW()
  );

   INSERT INTO `test-platform`.`subjects` (
 `id`, `name`, `description`, `created_at`, `updated_at`)
  VALUES (
   2, 'English', 'A subject with english tests.', '2023-11-05 14:55:41', NOW()
  );

 -- -------------------------------------------------------
 -- TESTS
 -- -------------------------------------------------------
 INSERT INTO `test-platform`.`tests` (
`id`, `name`, `teacher_id`, `subject_id` , `content`, `created_at`, `updated_at`)
 VALUES (
 1, 1, 'Math test', 2, '{
                         "questions": [
                             {
                                 "description": "what is the result of 6 + 6",
                                 "type": "multi-choice",
                                 "options": [
                                     "4",
                                     "8",
                                     "12",
                                     "22"
                                 ],
                                 "answer": "3"
                             },
                             {
                                 "description": "what is the result of 3 x 3",
                                 "type": "multi-choice",
                                 "options": [
                                     "15",
                                     "8",
                                     "12",
                                     "9"
                                 ],
                                 "answer": "4"
                             }
                         ]
                     }', '2023-11-05 14:55:41', NOW()
 );
 INSERT INTO `test-platform`.`tests` (
`id`, `name`, `teacher_id`, `subject_id` , `content`, `created_at`, `updated_at`)
 VALUES (
 2, 2, 'English test', 1, '{
                         "questions": [
                             {
                              "description": "what is the capital of France?",
                               "type": "multi-choice",
                                "options": [
                                "Paris",
                                 "London",
                                  "Berlin",
                                   "Madrid"
                                   ],
                                    "answer": "1"
                              },
                             { "description": "what is the capital of Germany?", "type": "multi-choice", "options": ["Paris", "London", "Berlin", "Madrid"], "answer": "2" },
                             { "description": "what is the capital of Italy?", "type": "multi-choice", "options": ["Paris", "London", "Berlin", "Madrid"], "answer": "3" },]
                             }', '2023-11-05 14:55:41', NOW()
 );

 -- -------------------------------------------------------
 STUDENT TESTS
 -- -------------------------------------------------------
 INSERT INTO `test-platform`.`student_tests` (
`id`, `student_id`, `test_id`, `answers`, `score`, `status`, `created_at`, `updated_at`)
 VALUES (
 1, 1, 1, '[1,1]', 0, 1, '2023-11-05 14:55:41', NOW()
 );
 INSERT INTO `test-platform`.`student_tests` (
`id`, `student_id`, `test_id`, `answers`, `score`, `status`, `created_at`, `updated_at`)
 VALUES (
 2, 2, 2, '[1,2]', 0, 1, '2023-11-05 14:55:41', NOW()
 );

 -- --------------------------------------------------------
 -- TEACHERS ROLES
 -- --------------------------------------------------------
 INSERT INTO `test-platform`.`teacher_roles` (
`teacher_id`, `role_id`, `created_at`, `updated_at`)
 VALUES (
 1, 1, NOW(), NOW()
 );
 INSERT INTO `test-platform`.`teacher_roles` (
`teacher_id`, `role_id`, `created_at`, `updated_at`)
 VALUES (
 2, 1, NOW(), NOW()
 );