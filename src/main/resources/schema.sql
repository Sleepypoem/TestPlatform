DROP SCHEMA IF EXISTS `test-platform`;
CREATE SCHEMA `test-platform`;
USE `test-platform`;

-- -----------------------------------------------------
-- Table `test-platform`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test-platform`.`roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB ROW_FORMAT = COMPRESSED;

-- -----------------------------------------------------
-- Table `test-platform`.`images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test-platform`.`images` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `format` VARCHAR(45) NOT NULL,
  `size` INT NOT NULL,
  `width` INT NOT NULL,
  `height` INT NOT NULL,
  `path` VARCHAR(1000) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB ROW_FORMAT = COMPRESSED;

-- -----------------------------------------------------
-- Table `test-platform`.`teachers`
-- -----------------------------------------------------
CREATE TABLE teachers (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `teacher_code` BIGINT NOT NULL UNIQUE,
  `image_id` BIGINT,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
  PRIMARY KEY (id),
  INDEX (first_name),
  INDEX (last_name),
  INDEX (teacher_code),
  CONSTRAINT `fk_teachers_images` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`)
) ENGINE=InnoDB ROW_FORMAT=COMPRESSED;

-- -----------------------------------------------------
-- Table `test-platform`.`students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test-platform`.`students` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `image_id` BIGINT ,
  `student_code` BIGINT NOT NULL UNIQUE,
  `grade_level` INT NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
   PRIMARY KEY (`id`),
   INDEX (first_name),
   INDEX (last_name),
   INDEX (student_code),
   CONSTRAINT `fk_students_images`
   FOREIGN KEY (`image_id`) REFERENCES `images` (`id`))
   ENGINE = InnoDB ROW_FORMAT = COMPRESSED;

 -- -----------------------------------------------------
 -- Table `test-platform`.`subjects`
 -- -----------------------------------------------------
 CREATE TABLE IF NOT EXISTS `test-platform`.`subjects` (
   `id` BIGINT NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(45) NOT NULL,
   `description` VARCHAR(255) NULL,
   `created_at` DATETIME NOT NULL,
   `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB ROW_FORMAT = COMPRESSED;

-- -----------------------------------------------------
-- Table `test-platform`.`tests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test-platform`.`tests` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `teacher_id` BIGINT NOT NULL,
  `subject_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `content` LONGTEXT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `fk_tests_teachers`
   FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`),
   CONSTRAINT  `fk_tests_subjects`
   FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`))
   ENGINE = InnoDB ROW_FORMAT = COMPRESSED;

-- -----------------------------------------------------
-- Table `test-platform`.`student_test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test-platform`.`student_tests` (
  `id`BIGINT NOT NULL AUTO_INCREMENT,
  `student_id` BIGINT NOT NULL,
  `test_id` BIGINT NOT NULL,
  `answers` LONGTEXT NULL,
  `score` BIGINT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
   `updated_at` DATETIME NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `fk_student_tests_students`
   FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
   CONSTRAINT `fk_student_tests_tests`
   FOREIGN KEY (`test_id`) REFERENCES `tests` (`id`))
   ENGINE = InnoDB ROW_FORMAT = COMPRESSED;

-- -----------------------------------------------------
-- Table `test-platform`.`roles_teachers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test-platform`.`roles_teachers` (
  `teacher_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
   CONSTRAINT `fk_roles_teachers_teachers`
   FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`),
   CONSTRAINT `fk_roles_teachers_roles`
   FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`))
   ENGINE = InnoDB ROW_FORMAT = COMPRESSED;