-- Persons:
INSERT INTO persons VALUES (1, 1, '11111111', 'alumno@gmail.com', 'alumno', 'alumno', '34627')
INSERT INTO persons VALUES (2, 2, '22222222', 'director@gmail.com', 'director', 'director', null)

-- Users:
INSERT INTO users VALUES (1, 'alumno', 1)
INSERT INTO users VALUES (2, 'director', 2)

INSERT INTO subjects VALUES (1, 'Prácticas de desarrollo')
INSERT INTO semesters VALUES (1, 'Primer cuatrimestre', false, 2022)
INSERT INTO courses VALUES (1, 'Gabriela Arévalo, Daniel Palazzo, ...', 30, 'Prácticas de desarrollo - Comisión 1', 1, 30, 1, 1)
INSERT INTO quote_requests VALUES (1, 'No puedo por horario de trabajo', 0, 1, 1)