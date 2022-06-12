--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2 (Debian 14.2-1.pgdg110+1)
-- Dumped by pg_dump version 14.2 (Debian 14.2-1.pgdg110+1)

-- Started on 2022-05-17 03:28:40 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3421 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 45327)
-- Name: course_student; Type: TABLE; Schema: public; Owner: postgres
--

SET search_path TO public;

CREATE TABLE public.course_student (
                                       course_id bigint NOT NULL,
                                       student_id bigint NOT NULL
);


ALTER TABLE public.course_student OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 45331)
-- Name: courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.courses (
                                id bigint NOT NULL,
                                assigned_teachers character varying(255) NOT NULL,
                                current_quotes integer NOT NULL,
                                name character varying(255) NOT NULL,
                                number integer NOT NULL,
                                total_quotes integer NOT NULL,
                                semester_id bigint,
                                subject_id bigint
);


ALTER TABLE public.courses OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 45330)
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.courses_id_seq OWNER TO postgres;

--
-- TOC entry 3422 (class 0 OID 0)
-- Dependencies: 210
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.courses_id_seq OWNED BY public.courses.id;


--
-- TOC entry 212 (class 1259 OID 45339)
-- Name: degree_subject; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.degree_subject (
                                       degree_id bigint NOT NULL,
                                       subject_id bigint NOT NULL
);


ALTER TABLE public.degree_subject OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 45343)
-- Name: degrees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.degrees (
                                id bigint NOT NULL,
                                acronym character varying(255) NOT NULL,
                                name character varying(255) NOT NULL
);


ALTER TABLE public.degrees OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 45342)
-- Name: degrees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.degrees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.degrees_id_seq OWNER TO postgres;

--
-- TOC entry 3423 (class 0 OID 0)
-- Dependencies: 213
-- Name: degrees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.degrees_id_seq OWNED BY public.degrees.id;


--
-- TOC entry 216 (class 1259 OID 45352)
-- Name: persons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persons (
                                role integer NOT NULL,
                                id bigint NOT NULL,
                                dni character varying(255) NOT NULL,
                                email character varying(255) NOT NULL,
                                first_name character varying(255) NOT NULL,
                                last_name character varying(255) NOT NULL,
                                legajo character varying(255)
);


ALTER TABLE public.persons OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 45351)
-- Name: persons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.persons_id_seq OWNER TO postgres;

--
-- TOC entry 3424 (class 0 OID 0)
-- Dependencies: 215
-- Name: persons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persons_id_seq OWNED BY public.persons.id;


--
-- TOC entry 218 (class 1259 OID 45361)
-- Name: quote_requests; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quote_requests (
                                       id bigint NOT NULL,
                                       comment character varying(255) NOT NULL,
                                       state integer NOT NULL,
                                       course_id bigint,
                                       student_id bigint
);


ALTER TABLE public.quote_requests OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 45360)
-- Name: quote_requests_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.quote_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.quote_requests_id_seq OWNER TO postgres;

--
-- TOC entry 3425 (class 0 OID 0)
-- Dependencies: 217
-- Name: quote_requests_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.quote_requests_id_seq OWNED BY public.quote_requests.id;


--
-- TOC entry 220 (class 1259 OID 45368)
-- Name: semesters; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.semesters (
                                  id bigint NOT NULL,
                                  name character varying(255) NOT NULL,
                                  semester boolean NOT NULL,
                                  year integer NOT NULL
);


ALTER TABLE public.semesters OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 45367)
-- Name: semesters_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.semesters_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.semesters_id_seq OWNER TO postgres;

--
-- TOC entry 3426 (class 0 OID 0)
-- Dependencies: 219
-- Name: semesters_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.semesters_id_seq OWNED BY public.semesters.id;


--
-- TOC entry 222 (class 1259 OID 45375)
-- Name: studied_courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.studied_courses (
                                        id bigint NOT NULL,
                                        mark integer,
                                        status integer NOT NULL,
                                        course_id bigint,
                                        studied_degree_id bigint
);


ALTER TABLE public.studied_courses OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 45374)
-- Name: studied_courses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.studied_courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studied_courses_id_seq OWNER TO postgres;

--
-- TOC entry 3427 (class 0 OID 0)
-- Dependencies: 221
-- Name: studied_courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.studied_courses_id_seq OWNED BY public.studied_courses.id;


--
-- TOC entry 224 (class 1259 OID 45382)
-- Name: studied_degrees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.studied_degrees (
                                        id bigint NOT NULL,
                                        coefficient real NOT NULL,
                                        degree_id bigint,
                                        student_id bigint
);


ALTER TABLE public.studied_degrees OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 45381)
-- Name: studied_degrees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.studied_degrees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.studied_degrees_id_seq OWNER TO postgres;

--
-- TOC entry 3428 (class 0 OID 0)
-- Dependencies: 223
-- Name: studied_degrees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.studied_degrees_id_seq OWNED BY public.studied_degrees.id;


--
-- TOC entry 226 (class 1259 OID 45389)
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subjects (
                                 id bigint NOT NULL,
                                 name character varying(255) NOT NULL
);


ALTER TABLE public.subjects OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 45388)
-- Name: subjects_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subjects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subjects_id_seq OWNER TO postgres;

--
-- TOC entry 3429 (class 0 OID 0)
-- Dependencies: 225
-- Name: subjects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subjects_id_seq OWNED BY public.subjects.id;


--
-- TOC entry 228 (class 1259 OID 45396)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id bigint NOT NULL,
                              username character varying(255) NOT NULL,
                              person_id bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 45395)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 227
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 3215 (class 2604 OID 45334)
-- Name: courses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses ALTER COLUMN id SET DEFAULT nextval('public.courses_id_seq'::regclass);


--
-- TOC entry 3216 (class 2604 OID 45346)
-- Name: degrees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degrees ALTER COLUMN id SET DEFAULT nextval('public.degrees_id_seq'::regclass);


--
-- TOC entry 3217 (class 2604 OID 45355)
-- Name: persons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons ALTER COLUMN id SET DEFAULT nextval('public.persons_id_seq'::regclass);


--
-- TOC entry 3218 (class 2604 OID 45364)
-- Name: quote_requests id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quote_requests ALTER COLUMN id SET DEFAULT nextval('public.quote_requests_id_seq'::regclass);


--
-- TOC entry 3219 (class 2604 OID 45371)
-- Name: semesters id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.semesters ALTER COLUMN id SET DEFAULT nextval('public.semesters_id_seq'::regclass);


--
-- TOC entry 3220 (class 2604 OID 45378)
-- Name: studied_courses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_courses ALTER COLUMN id SET DEFAULT nextval('public.studied_courses_id_seq'::regclass);


--
-- TOC entry 3221 (class 2604 OID 45385)
-- Name: studied_degrees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_degrees ALTER COLUMN id SET DEFAULT nextval('public.studied_degrees_id_seq'::regclass);


--
-- TOC entry 3222 (class 2604 OID 45392)
-- Name: subjects id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects ALTER COLUMN id SET DEFAULT nextval('public.subjects_id_seq'::regclass);


--
-- TOC entry 3223 (class 2604 OID 45399)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3225 (class 2606 OID 45338)
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- TOC entry 3231 (class 2606 OID 45350)
-- Name: degrees degrees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degrees
    ADD CONSTRAINT degrees_pkey PRIMARY KEY (id);


--
-- TOC entry 3237 (class 2606 OID 45359)
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 45366)
-- Name: quote_requests quote_requests_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quote_requests
    ADD CONSTRAINT quote_requests_pkey PRIMARY KEY (id);


--
-- TOC entry 3247 (class 2606 OID 45373)
-- Name: semesters semesters_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.semesters
    ADD CONSTRAINT semesters_pkey PRIMARY KEY (id);


--
-- TOC entry 3253 (class 2606 OID 45380)
-- Name: studied_courses studied_courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_courses
    ADD CONSTRAINT studied_courses_pkey PRIMARY KEY (id);


--
-- TOC entry 3255 (class 2606 OID 45387)
-- Name: studied_degrees studied_degrees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_degrees
    ADD CONSTRAINT studied_degrees_pkey PRIMARY KEY (id);


--
-- TOC entry 3257 (class 2606 OID 45394)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (id);


--
-- TOC entry 3249 (class 2606 OID 45417)
-- Name: semesters uk7irkxk0xtk11rucovph1sjg12; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.semesters
    ADD CONSTRAINT uk7irkxk0xtk11rucovph1sjg12 UNIQUE (semester, year);


--
-- TOC entry 3239 (class 2606 OID 45413)
-- Name: persons uk_1x5aosta48fbss4d5b3kuu0rd; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT uk_1x5aosta48fbss4d5b3kuu0rd UNIQUE (email);


--
-- TOC entry 3227 (class 2606 OID 45405)
-- Name: courses uk_9d84353c7qcexvs5mb2h0tunl; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT uk_9d84353c7qcexvs5mb2h0tunl UNIQUE (number);


--
-- TOC entry 3259 (class 2606 OID 45421)
-- Name: subjects uk_aodt3utnw0lsov4k9ta88dbpr; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT uk_aodt3utnw0lsov4k9ta88dbpr UNIQUE (name);


--
-- TOC entry 3233 (class 2606 OID 45409)
-- Name: degrees uk_aqsotmoewehkbpjo21fpek1oy; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degrees
    ADD CONSTRAINT uk_aqsotmoewehkbpjo21fpek1oy UNIQUE (name);


--
-- TOC entry 3251 (class 2606 OID 45419)
-- Name: semesters uk_ci1s5s8npb7j044md3s0wdhsh; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.semesters
    ADD CONSTRAINT uk_ci1s5s8npb7j044md3s0wdhsh UNIQUE (name);


--
-- TOC entry 3235 (class 2606 OID 45407)
-- Name: degrees uk_jorh1rvqd2gut7ue4p3doqu8p; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degrees
    ADD CONSTRAINT uk_jorh1rvqd2gut7ue4p3doqu8p UNIQUE (acronym);


--
-- TOC entry 3241 (class 2606 OID 45415)
-- Name: persons uk_l6isl31krjcrhq6x77gk2x75w; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT uk_l6isl31krjcrhq6x77gk2x75w UNIQUE (legajo);


--
-- TOC entry 3261 (class 2606 OID 45423)
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- TOC entry 3243 (class 2606 OID 45411)
-- Name: persons uk_t0tma5e5ec4leolu2gaqpc9v7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT uk_t0tma5e5ec4leolu2gaqpc9v7 UNIQUE (dni);


--
-- TOC entry 3229 (class 2606 OID 45403)
-- Name: courses ukpisye137j017huxi9nk5vhmao; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT ukpisye137j017huxi9nk5vhmao UNIQUE (semester_id, subject_id, number);


--
-- TOC entry 3263 (class 2606 OID 45401)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3273 (class 2606 OID 45469)
-- Name: studied_courses fk5m8hjpnxxl1415t4dy39iuq3s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_courses
    ADD CONSTRAINT fk5m8hjpnxxl1415t4dy39iuq3s FOREIGN KEY (studied_degree_id) REFERENCES public.studied_degrees(id);


--
-- TOC entry 3267 (class 2606 OID 45439)
-- Name: courses fk5tckdihu5akp5nkxiacx1gfhi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT fk5tckdihu5akp5nkxiacx1gfhi FOREIGN KEY (subject_id) REFERENCES public.subjects(id);


--
-- TOC entry 3269 (class 2606 OID 45449)
-- Name: degree_subject fk8es9iy4u2b5r3st8ppwi35qa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degree_subject
    ADD CONSTRAINT fk8es9iy4u2b5r3st8ppwi35qa FOREIGN KEY (degree_id) REFERENCES public.degrees(id);


--
-- TOC entry 3274 (class 2606 OID 45474)
-- Name: studied_degrees fk9qpxmsj5ej0rq4a0vl3q210xb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_degrees
    ADD CONSTRAINT fk9qpxmsj5ej0rq4a0vl3q210xb FOREIGN KEY (degree_id) REFERENCES public.degrees(id);


--
-- TOC entry 3268 (class 2606 OID 45444)
-- Name: degree_subject fkg7p9et281ljjx5w60q2el32oj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.degree_subject
    ADD CONSTRAINT fkg7p9et281ljjx5w60q2el32oj FOREIGN KEY (subject_id) REFERENCES public.subjects(id);


--
-- TOC entry 3275 (class 2606 OID 45479)
-- Name: studied_degrees fklaq0ifouafx1yeneyj54s824w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_degrees
    ADD CONSTRAINT fklaq0ifouafx1yeneyj54s824w FOREIGN KEY (student_id) REFERENCES public.persons(id);


--
-- TOC entry 3265 (class 2606 OID 45429)
-- Name: course_student fklmsbddqkv96q4nijkrxuof3ug; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_student
    ADD CONSTRAINT fklmsbddqkv96q4nijkrxuof3ug FOREIGN KEY (course_id) REFERENCES public.courses(id);


--
-- TOC entry 3271 (class 2606 OID 45459)
-- Name: quote_requests fklpcvpd0x7c714ifxdr191f3fd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quote_requests
    ADD CONSTRAINT fklpcvpd0x7c714ifxdr191f3fd FOREIGN KEY (student_id) REFERENCES public.persons(id);


--
-- TOC entry 3266 (class 2606 OID 45434)
-- Name: courses fklr7ck4w266my7691avtikan92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT fklr7ck4w266my7691avtikan92 FOREIGN KEY (semester_id) REFERENCES public.semesters(id);


--
-- TOC entry 3270 (class 2606 OID 45454)
-- Name: quote_requests fkmfpbphl1vp2u3l5pu1vsb1flp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quote_requests
    ADD CONSTRAINT fkmfpbphl1vp2u3l5pu1vsb1flp FOREIGN KEY (course_id) REFERENCES public.courses(id);


--
-- TOC entry 3276 (class 2606 OID 45484)
-- Name: users fkmvbq8q4vpi6csivw9wcnq6ho5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkmvbq8q4vpi6csivw9wcnq6ho5 FOREIGN KEY (person_id) REFERENCES public.persons(id);


--
-- TOC entry 3272 (class 2606 OID 45464)
-- Name: studied_courses fko5bw8dchfke19k9p4tvooyo7w; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studied_courses
    ADD CONSTRAINT fko5bw8dchfke19k9p4tvooyo7w FOREIGN KEY (course_id) REFERENCES public.courses(id);


--
-- TOC entry 3264 (class 2606 OID 45424)
-- Name: course_student fksow7pyajls077dq3b8w01f064; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course_student
    ADD CONSTRAINT fksow7pyajls077dq3b8w01f064 FOREIGN KEY (student_id) REFERENCES public.persons(id);


-- Completed on 2022-05-17 03:28:40 UTC

--
-- PostgreSQL database dump complete
--
