-- Recommendation: Execute script in order.

-- Persons:
INSERT INTO persons (dni, email, first_name, last_name, role, legajo) VALUES ('11111111', 'alumno@gmail.com', 'alumno', 'alumno', 1, '34627');
INSERT INTO persons (dni, email, first_name, last_name, role, legajo) VALUES ('22222222', 'director@gmail.com', 'director', 'director', 2, null);

-- Users:
INSERT INTO users(username, person_id) VALUES ('alumno', 1);
INSERT INTO users(username, person_id) VALUES ('director', 2);

-- Degrees:
INSERT INTO degrees(acronym, name) VALUES ('TPI', 'Tecnicatura en Programación Informática');
INSERT INTO degrees(acronym, name) VALUES ('LI', 'Licenciatura en Informática');


-- Subjects:
INSERT INTO subjects(name) VALUES ('Práctica del Desarrollo de Software');
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 1);


INSERT INTO subjects(name) VALUES ('Introducción a la programación');
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 2);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 2);

-- Others:
INSERT INTO semesters(name, is_snd_semester, year) VALUES ('Primer cuatrimestre', false, 2022);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'Prácticas de desarrollo - Comisión 1', 30, 1, 1);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'Prácticas de desarrollo - Comisión 2', 32, 1, 1);
INSERT INTO quote_requests(comment, state, course_id, student_id) VALUES ('No puedo por horario de trabajo', 0, 1, 1);