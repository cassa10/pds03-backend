-- Recommendation: Execute script in order.

-- Users:
INSERT INTO users (dni, email, first_name, last_name, role, legajo, username) VALUES ('11111111', 'alumno@gmail.com', 'Henry', 'Rice', 1, '34627', 'alumno');
INSERT INTO users (dni, email, first_name, last_name, role, legajo, username) VALUES ('11111112', 'lio@gmail.com', 'Lionel', 'Messi', 1, '34628', 'messi10');
INSERT INTO users (dni, email, first_name, last_name, role, legajo, username) VALUES ('11111113', 'alanturing@gmail.com', 'Alan', 'Turing', 1, '34629','alan_turing');
INSERT INTO users (dni, email, first_name, last_name, role, legajo, username) VALUES ('99999999', 'pepegrillo@gmail.com', 'Pepe', 'Grillo', 1, '99999','pepe');
INSERT INTO users (dni, email, first_name, last_name, role, legajo, username) VALUES ('22222222', 'director@gmail.com', 'director', 'director', 2, null,'director');

-- Degrees:
INSERT INTO degrees(acronym, name) VALUES ('TPI', 'Tecnicatura en Programación Informática');
INSERT INTO degrees(acronym, name) VALUES ('LI', 'Licenciatura en Informática');

-- Subjects:

INSERT INTO subjects(name) VALUES ('Lectura y escritura académica');
INSERT INTO subjects(name) VALUES ('Matemática');
INSERT INTO subjects(name) VALUES ('Elementos de programación y lógica');
INSERT INTO subjects(name) VALUES ('Matemática I');
INSERT INTO subjects(name) VALUES ('Introducción a la Programación');
INSERT INTO subjects(name) VALUES ('Organización de Computadoras');
INSERT INTO subjects(name) VALUES ('Estructuras de Datos');
INSERT INTO subjects(name) VALUES ('Programación con Objetos I');
INSERT INTO subjects(name) VALUES ('Bases de Datos');
INSERT INTO subjects(name) VALUES ('Matemática II');
INSERT INTO subjects(name) VALUES ('Programación con Objetos II');
INSERT INTO subjects(name) VALUES ('Redes de Computadoras');
INSERT INTO subjects(name) VALUES ('Sistemas Operativos');
INSERT INTO subjects(name) VALUES ('Programación Funcional');
INSERT INTO subjects(name) VALUES ('Construcción de Interfaces de Usuario');
INSERT INTO subjects(name) VALUES ('Algoritmos');
INSERT INTO subjects(name) VALUES ('Estrategias de Persistencia');
INSERT INTO subjects(name) VALUES ('Laboratorio de Sistemas Operativos y Redes');
INSERT INTO subjects(name) VALUES ('Análisis Matemático');
INSERT INTO subjects(name) VALUES ('Lógica y Programación');
INSERT INTO subjects(name) VALUES ('Elementos de Ingeniería de Software');
INSERT INTO subjects(name) VALUES ('Seguridad de la Información');
INSERT INTO subjects(name) VALUES ('Matemática III');
INSERT INTO subjects(name) VALUES ('Programación Concurrente');
INSERT INTO subjects(name) VALUES ('Ingeniería de Requerimientos');
INSERT INTO subjects(name) VALUES ('Práctica del Desarrollo de Software');
INSERT INTO subjects(name) VALUES ('Probabilidad y Estadística');
INSERT INTO subjects(name) VALUES ('Gestión de Proyectos de Software');
INSERT INTO subjects(name) VALUES ('Lenguajes Formales y Autómatas');
INSERT INTO subjects(name) VALUES ('Programación con Objetos III');
INSERT INTO subjects(name) VALUES ('Teoría de la Computación');
INSERT INTO subjects(name) VALUES ('Arquitectura de Software I');
INSERT INTO subjects(name) VALUES ('Sistemas Distribuidos');
INSERT INTO subjects(name) VALUES ('Características de Lenguajes de Programación');
INSERT INTO subjects(name) VALUES ('Arquitectura de Software II');
INSERT INTO subjects(name) VALUES ('Arquitectura de Computadoras');
INSERT INTO subjects(name) VALUES ('Parseo y generación de código');
INSERT INTO subjects(name) VALUES ('Aspectos Legales y Sociales');
INSERT INTO subjects(name) VALUES ('Bases de Datos II');
INSERT INTO subjects(name) VALUES ('Participación y Gestión en Proyectos de Software Libre');
INSERT INTO subjects(name) VALUES ('Introducción a la Bioinformática');
INSERT INTO subjects(name) VALUES ('Políticas Públicas en la Sociedad de la Información y la Era Digital');
INSERT INTO subjects(name) VALUES ('Sistemas de Información Geográfica');
INSERT INTO subjects(name) VALUES ('Herramientas Declarativas en Programación');
INSERT INTO subjects(name) VALUES ('Introducción al Desarrollo de Videojuegos');
INSERT INTO subjects(name) VALUES ('Derechos de Autor y Derechos de Copia en la Era Digital');
INSERT INTO subjects(name) VALUES ('Análisis Estático de Programas y Herramientas Asociadas');
INSERT INTO subjects(name) VALUES ('Seminarios');
INSERT INTO subjects(name) VALUES ('Semántica de Lenguajes de Programación');
INSERT INTO subjects(name) VALUES ('Seminarios sobre Herramientas o Técnicas Puntuales');
INSERT INTO subjects(name) VALUES ('Taller de Trabajo Intelectual');
INSERT INTO subjects(name) VALUES ('Taller de Trabajo Universitario');
INSERT INTO subjects(name) VALUES ('Inglés I');
INSERT INTO subjects(name) VALUES ('Inglés II');
INSERT INTO subjects(name) VALUES ('Desarrollo de Aplicaciones');

INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 1);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 1);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 2);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 2);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 3);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 3);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 4);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 4);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 5);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 5);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 6);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 6);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 7);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 7);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 8);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 8);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 9);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 9);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 10);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 10);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 11);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 11);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 12);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 12);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 13);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 13);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 14);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 14);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 15);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 15);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 16);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 17);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 17);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 18);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 18);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 19);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 20);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 21);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 21);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 22);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 23);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 24);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 24);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 25);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 26);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 27);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 28);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 29);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 30);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 31);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 32);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 33);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 34);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 35);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 36);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 37);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 38);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 39);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 39);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 40);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 40);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 41);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 41);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 42);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 42);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 43);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 43);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 44);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 44);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 45);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 45);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 46);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 46);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 47);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 47);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 48);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 48);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 49);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 49);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 50);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 50);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 51);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 51);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 52);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 52);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 53);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 53);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 54);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (2, 54);
INSERT INTO degree_subject(degree_id, subject_id) VALUES (1, 55);

-- Semesters:
INSERT INTO semesters(name, is_snd_semester, year) VALUES ('Primer cuatrimestre', false, 2022);
INSERT INTO semesters(name, is_snd_semester, year) VALUES ('Segundo cuatrimestre', true, 2022);

-- Courses - Primary semester
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana Grillo', 'C1', 30, 1, 1);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana Grillo', 'C2', 32, 1, 1);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alexander', 'C1', 30, 1, 2);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alexander', 'C2', 32, 1, 2);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaño', 'C1', 30, 1, 3);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaño', 'C2', 32, 1, 3);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C1', 30, 1, 4);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C2', 32, 1, 4);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alan Rodas', 'C1', 30, 1, 5);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alan Rodas', 'C2', 32, 1, 5);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Denise', 'C1', 30, 1, 6);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Denise', 'C2', 32, 1, 6);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C1', 30, 1, 7);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C2', 32, 1, 7);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C1', 30, 1, 8);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C2', 32, 1, 8);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 1, 9);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 1, 9);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 1, 10);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 1, 10);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C1', 30, 1, 11);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C2', 32, 1, 11);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Cesar', 'C1', 30, 1, 12);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Cesar', 'C2', 32, 1, 12);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Mardelo', 'C1', 30, 1, 13);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Mardelo', 'C2', 32, 1, 13);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C1', 30, 1, 14);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C2', 32, 1, 14);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Martin', 'C1', 30, 1, 15);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Martin', 'C2', 32, 1, 15);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Facto', 'C1', 30, 1, 16);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Facto', 'C2', 32, 1, 16);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ronny', 'C1', 30, 1, 17);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ronny', 'C2', 32, 1, 17);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jose Luis', 'C1', 30, 1, 18);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jose Luis', 'C2', 32, 1, 18);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C1', 30, 1, 19);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C2', 32, 1, 19);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C1', 30, 1, 20);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C2', 32, 1, 20);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Suarez', 'C1', 30, 1, 21);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Suarez', 'C2', 32, 1, 21);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede', 'C1', 30, 1, 22);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede', 'C2', 32, 1, 22);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C1', 30, 1, 23);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C2', 32, 1, 23);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Dani', 'C1', 30, 1, 24);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Dani', 'C2', 32, 1, 24);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 1, 25);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 1, 25);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 1, 26);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 1, 26);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 1, 27);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 1, 27);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 1, 28);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 1, 28);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C1', 30, 1, 29);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C2', 32, 1, 29);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C1', 30, 1, 30);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C2', 32, 1, 30);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 1, 31);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 1, 31);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C1', 30, 1, 32);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C2', 32, 1, 32);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C1', 30, 1, 33);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C2', 32, 1, 33);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 1, 34);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 1, 34);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C1', 30, 1, 35);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C2', 32, 1, 35);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C1', 30, 1, 36);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C2', 32, 1, 36);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C1', 30, 1, 37);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C2', 32, 1, 37);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Lucas', 'C1', 30, 1, 38);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Lucas', 'C2', 32, 1, 38);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 1, 39);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 1, 39);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 1, 40);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 1, 40);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juliana', 'C1', 30, 1, 41);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juliana', 'C2', 32, 1, 41);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C1', 30, 1, 42);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C2', 32, 1, 42);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaña', 'C1', 30, 1, 43);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaña', 'C2', 32, 1, 43);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 1, 44);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 1, 44);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C1', 30, 1, 45);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C2', 32, 1, 45);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C1', 30, 1, 46);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C2', 32, 1, 46);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C1', 30, 1, 47);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C2', 32, 1, 47);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 1, 48);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 1, 48);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C1', 30, 1, 49);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C2', 32, 1, 49);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 1, 50);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 1, 50);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 1, 51);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 1, 51);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 1, 52);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 1, 52);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C1', 30, 1, 53);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C2', 32, 1, 53);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C1', 30, 1, 54);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C2', 32, 1, 54);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Feli', 'C1', 30, 1, 55);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Feli', 'C2', 32, 1, 55);

-- Courses - Secondary semester
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana Grillo', 'C1', 30, 2, 1);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana Grillo', 'C2', 32, 2, 1);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alexander', 'C1', 30, 2, 2);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alexander', 'C2', 32, 2, 2);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaño', 'C1', 30, 2, 3);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaño', 'C2', 32, 2, 3);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C1', 30, 2, 4);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C2', 32, 2, 4);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alan Rodas', 'C1', 30, 2, 5);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Alan Rodas', 'C2', 32, 2, 5);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Denise', 'C1', 30, 2, 6);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Denise', 'C2', 32, 2, 6);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C1', 30, 2, 7);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C2', 32, 2, 7);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C1', 30, 2, 8);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C2', 32, 2, 8);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 2, 9);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 2, 9);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 2, 10);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 2, 10);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C1', 30, 2, 11);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C2', 32, 2, 11);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Cesar', 'C1', 30, 2, 12);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Cesar', 'C2', 32, 2, 12);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Mardelo', 'C1', 30, 2, 13);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Mardelo', 'C2', 32, 2, 13);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C1', 30, 2, 14);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fidel', 'C2', 32, 2, 14);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Martin', 'C1', 30, 2, 15);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Martin', 'C2', 32, 2, 15);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Facto', 'C1', 30, 2, 16);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Facto', 'C2', 32, 2, 16);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ronny', 'C1', 30, 2, 17);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ronny', 'C2', 32, 2, 17);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jose Luis', 'C1', 30, 2, 18);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jose Luis', 'C2', 32, 2, 18);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C1', 30, 2, 19);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C2', 32, 2, 19);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C1', 30,2, 20);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C2', 32,2, 20);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Suarez', 'C1', 30, 2, 21);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pablo Suarez', 'C2', 32, 2, 21);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede', 'C1', 30, 2, 22);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede', 'C2', 32, 2, 22);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C1', 30, 2, 23);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C2', 32, 2, 23);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Dani', 'C1', 30, 2, 24);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Dani', 'C2', 32, 2, 24);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 2, 25);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 2, 25);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 2, 26);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 2, 26);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 2, 27);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 2, 27);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 2, 28);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 2, 28);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C1', 30, 2, 29);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Ema Arevalo', 'C2', 32, 2, 29);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C1', 30, 1, 30);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C2', 32, 1, 30);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 2, 31);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 2, 31);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C1', 30, 2, 32);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Claudia', 'C2', 32, 2, 32);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C1', 30, 2, 33);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C2', 32, 2, 33);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 2, 34);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 2, 34);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C1', 30, 2, 35);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C2', 32, 2, 35);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C1', 30, 2, 36);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Susana', 'C2', 32, 2, 36);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C1', 30, 2, 37);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juan Perez', 'C2', 32, 2, 37);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Lucas', 'C1', 30, 2, 38);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Lucas', 'C2', 32, 2, 38);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 2, 39);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 2, 39);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 2, 40);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 2, 40);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juliana', 'C1', 30, 2, 41);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Juliana', 'C2', 32, 2, 41);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C1', 30, 2, 42);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C2', 32, 2, 42);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaña', 'C1', 30, 2, 43);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Flavia Saldaña', 'C2', 32, 2, 43);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C1', 30, 2, 44);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Jano', 'C2', 32, 2, 44);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C1', 30, 2, 45);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Diego T', 'C2', 32, 2, 45);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C1', 30, 2, 46);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Marcelo', 'C2', 32, 2, 46);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C1', 30, 2, 47);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Fede Sawady', 'C2', 32, 2, 47);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C1', 30, 2, 48);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Gabriela Arévalo', 'C2', 32, 2, 48);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C1', 30, 2, 49);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Maximo', 'C2', 32, 2, 49);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 2, 50);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 2, 50);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 2, 51);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 2, 51);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C1', 30, 2, 52);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Pepe Grillo', 'C2', 32, 2, 52);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C1', 30, 2, 53);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C2', 32, 2, 53);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C1', 30, 2, 54);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Liliana', 'C2', 32, 2, 54);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Feli', 'C1', 30, 2, 55);
INSERT INTO courses(assigned_teachers, name, total_quotes, semester_id, subject_id) VALUES ('Feli', 'C2', 32, 2, 55);

-- Quote requests:
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 1, 1, 'Lo voy a pensar con Gabi');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 3, 1, 'Mmmm revisar bien porque ...');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 5, 1, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 7, 1, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 9, 1, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 11, 1, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 1, 2, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 3, 2, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 5, 2, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 7, 2, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 9, 2, '');
INSERT INTO quote_requests(comment, state, course_id, student_id, admin_comment) VALUES ('No puedo por horario de trabajo', 0, 2, 3, '');

-- Studied degrees:
INSERT INTO studied_degrees(coefficient, degree_id, student_id) VALUES (10, 1, 1);

-- Studied subjects:
INSERT INTO studied_subjects(mark, status, studied_degree_id, subject_id) VALUES (10, 2, 1, 1);
INSERT INTO studied_subjects(mark, status, studied_degree_id, subject_id) VALUES (10, 2, 1, 2);
INSERT INTO studied_subjects(mark, status, studied_degree_id, subject_id) VALUES (10, 2, 1, 3);

-- Student enrolled courses:
INSERT INTO student_enrolled_course(student_id, course_id) VALUES (1, 23)

-- Hours:
INSERT INTO hours(_day, _from, _to) VALUES (1, '09:00', '13:00')
INSERT INTO hours(_day, _from, _to) VALUES (1, '14:00', '18:00')
INSERT INTO hours(_day, _from, _to) VALUES (1, '18:00', '21:00')
INSERT INTO hours(_day, _from, _to) VALUES (1, '18:00', '22:00')

-- Course hours:
INSERT INTO courses_hours(course_id, hours_id) VALUES (1, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (1, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (2, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (2, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (3, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (3, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (4, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (4, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (5, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (5, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (6, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (6, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (7, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (7, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (8, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (8, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (9, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (9, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (1, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (10, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (11, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (11, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (12, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (12, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (13, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (13, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (14, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (14, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (15, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (15, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (16, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (16, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (17, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (17, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (18, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (18, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (19, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (19, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (20, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (20, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (21, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (21, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (22, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (22, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (23, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (23, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (24, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (24, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (25, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (25, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (26, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (26, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (27, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (27, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (28, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (28, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (29, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (29, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (30, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (30, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (31, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (31, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (32, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (32, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (33, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (33, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (34, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (34, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (35, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (35, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (36, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (36, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (37, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (37, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (38, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (38, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (39, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (39, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (40, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (40, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (41, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (41, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (42, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (42, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (43, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (43, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (44, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (44, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (45, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (45, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (46, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (46, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (47, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (47, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (48, 1)
INSERT INTO courses_hours(course_id, hours_id) VALUES (48, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (49, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (49, 4)
INSERT INTO courses_hours(course_id, hours_id) VALUES (50, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (50, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (51, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (51, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (52, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (52, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (53, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (53, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (54, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (54, 2)
INSERT INTO courses_hours(course_id, hours_id) VALUES (55, 3)
INSERT INTO courses_hours(course_id, hours_id) VALUES (55, 2)
