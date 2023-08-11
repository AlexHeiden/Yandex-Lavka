--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-05-13 16:07:04

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
-- TOC entry 3362 (class 1262 OID 5)
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

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
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 3362
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 222 (class 1259 OID 16652)
-- Name: assignments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assignments (
    id bigint NOT NULL,
    courier_id bigint NOT NULL,
    order_id bigint NOT NULL,
    creation_date date NOT NULL
);


ALTER TABLE public.assignments OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16651)
-- Name: assignments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.assignments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assignments_id_seq OWNER TO postgres;

--
-- TOC entry 3365 (class 0 OID 0)
-- Dependencies: 221
-- Name: assignments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.assignments_id_seq OWNED BY public.assignments.id;


--
-- TOC entry 216 (class 1259 OID 16409)
-- Name: courier_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.courier_types (
    id bigint NOT NULL,
    name text NOT NULL,
    max_weight real NOT NULL,
    max_order_number integer NOT NULL,
    max_region_number integer NOT NULL,
    first_order_minutes integer NOT NULL,
    other_order_minutes integer NOT NULL,
    stat_salary_coef integer NOT NULL,
    first_order_salary_coef real NOT NULL,
    other_order_salary_coef real NOT NULL,
    rating_coef integer NOT NULL
);


ALTER TABLE public.courier_types OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16408)
-- Name: courier_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.courier_type_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.courier_type_id_seq OWNER TO postgres;

--
-- TOC entry 3366 (class 0 OID 0)
-- Dependencies: 215
-- Name: courier_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.courier_type_id_seq OWNED BY public.courier_types.id;


--
-- TOC entry 218 (class 1259 OID 16586)
-- Name: couriers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.couriers (
    id bigint NOT NULL,
    courier_type text NOT NULL,
    regions integer[] NOT NULL,
    working_hours character(11)[] NOT NULL
);


ALTER TABLE public.couriers OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16585)
-- Name: couriers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.couriers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.couriers_id_seq OWNER TO postgres;

--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 217
-- Name: couriers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.couriers_id_seq OWNED BY public.couriers.id;


--
-- TOC entry 220 (class 1259 OID 16643)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    weight real NOT NULL,
    regions integer NOT NULL,
    delivery_hours character(11)[] NOT NULL,
    cost integer NOT NULL,
    completed_time timestamp without time zone
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16642)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_id_seq OWNER TO postgres;

--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 219
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 3192 (class 2604 OID 16655)
-- Name: assignments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assignments ALTER COLUMN id SET DEFAULT nextval('public.assignments_id_seq'::regclass);


--
-- TOC entry 3189 (class 2604 OID 16492)
-- Name: courier_types id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courier_types ALTER COLUMN id SET DEFAULT nextval('public.courier_type_id_seq'::regclass);


--
-- TOC entry 3190 (class 2604 OID 16589)
-- Name: couriers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.couriers ALTER COLUMN id SET DEFAULT nextval('public.couriers_id_seq'::regclass);


--
-- TOC entry 3191 (class 2604 OID 16646)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);

--
-- TOC entry 3350 (class 0 OID 16409)
-- Dependencies: 216
-- Data for Name: courier_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.courier_types VALUES (1, 'FOOT', 10, 2, 1, 25, 10, 2, 1, 0.8, 3);
INSERT INTO public.courier_types VALUES (2, 'BIKE', 20, 4, 2, 12, 8, 3, 1, 0.8, 2);
INSERT INTO public.courier_types VALUES (3, 'AUTO', 40, 7, 3, 8, 4, 4, 1, 0.8, 1);

--
-- TOC entry 3354 (class 0 OID 16643)
-- Dependencies: 220
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 221
-- Name: assignments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.assignments_id_seq', 18, true);


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 215
-- Name: courier_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courier_type_id_seq', 3, true);


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 217
-- Name: couriers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.couriers_id_seq', 21, true);


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 219
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 16, true);


--
-- TOC entry 3202 (class 2606 OID 16659)
-- Name: assignments assignments_order_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assignments
    ADD CONSTRAINT assignments_order_id_key UNIQUE (order_id);


--
-- TOC entry 3204 (class 2606 OID 16657)
-- Name: assignments assignments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assignments
    ADD CONSTRAINT assignments_pkey PRIMARY KEY (id);


--
-- TOC entry 3194 (class 2606 OID 16418)
-- Name: courier_types courier_type_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courier_types
    ADD CONSTRAINT courier_type_name_key UNIQUE (name);


--
-- TOC entry 3196 (class 2606 OID 16494)
-- Name: courier_types courier_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courier_types
    ADD CONSTRAINT courier_type_pkey PRIMARY KEY (id);


--
-- TOC entry 3198 (class 2606 OID 16593)
-- Name: couriers couriers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.couriers
    ADD CONSTRAINT couriers_pkey PRIMARY KEY (id);


--
-- TOC entry 3200 (class 2606 OID 16650)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3205 (class 2606 OID 16660)
-- Name: assignments assignments_courier_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assignments
    ADD CONSTRAINT assignments_courier_id_fkey FOREIGN KEY (courier_id) REFERENCES public.couriers(id);


--
-- TOC entry 3206 (class 2606 OID 16665)
-- Name: assignments assignments_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.assignments
    ADD CONSTRAINT assignments_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


-- Completed on 2023-05-13 16:07:04

--
-- PostgreSQL database dump complete
--

