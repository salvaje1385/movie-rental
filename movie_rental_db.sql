--
-- PostgreSQL database dump
--

-- Dumped from database version 10.13
-- Dumped by pg_dump version 10.15

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
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: likes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.likes (
    movie_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.likes OWNER TO postgres;

--
-- Name: movie; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movie (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    available boolean DEFAULT true NOT NULL,
    description character varying(2000),
    image character varying(2000),
    rental_price double precision NOT NULL,
    sale_price double precision NOT NULL,
    stock integer NOT NULL,
    title character varying(500) NOT NULL,
    likes integer DEFAULT 0
);


ALTER TABLE public.movie OWNER TO postgres;

--
-- Name: movie_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movie_sequence
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movie_sequence OWNER TO postgres;

--
-- Name: movie_updates; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movie_updates (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    new_value character varying(255) NOT NULL,
    old_value character varying(255),
    update_type integer NOT NULL,
    movie_id bigint NOT NULL
);


ALTER TABLE public.movie_updates OWNER TO postgres;

--
-- Name: purchase; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    price double precision NOT NULL,
    movie_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.purchase OWNER TO postgres;

--
-- Name: rental; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rental (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    due_date timestamp without time zone NOT NULL,
    penalty double precision,
    price double precision NOT NULL,
    return_date timestamp without time zone,
    movie_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.rental OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    address character varying(2000),
    email character varying(100),
    username character varying(100) NOT NULL,
    password character varying(150),
    phone character varying(80)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Data for Name: likes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.likes (movie_id, user_id) FROM stdin;
4101	6
4101	13
4101	14
4101	15
4003	6
4003	13
4003	14
4005	14
4005	15
4005	6
3955	6
3955	13
4001	13
3953	13
4201	47
\.


--
-- Data for Name: movie; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movie (id, created_at, updated_at, available, description, image, rental_price, sale_price, stock, title, likes) FROM stdin;
3951	2020-11-14 16:01:57.707	2020-11-14 16:57:09.496	t	I'm Batman!	\N	6.45000000000000018	20.4499999999999993	14	Batman Returns	0
4002	2020-11-14 17:59:50.091	2020-11-14 17:59:50.091	t	Extraterrestres	\N	5.45000000000000018	18.4800000000000004	17	Avatar	0
4004	2020-11-14 18:01:02.514	2020-11-14 18:01:02.514	t	Robots	\N	4.91999999999999993	17.379999999999999	8	Transformers	0
3952	2020-11-14 16:05:47.284	2020-11-14 22:27:51.138	f	MINIONS	\N	4.45000000000000018	15.4499999999999993	10	Minions	0
3954	2020-11-14 16:06:32.284	2020-11-14 16:56:28.484	f	El Lorax	\N	4.78000000000000025	15.2300000000000004	9	The Lorax	0
4051	2020-11-14 23:43:00.1	2020-11-14 23:43:00.1	t	El Exterminador	\N	4.78000000000000025	16.4499999999999993	8	Terminator	0
4101	2020-11-14 23:44:25.335	2020-11-14 23:58:05.21	t	El Exterminador 2	\N	5.74000000000000021	17.1499999999999986	10	Terminator 2	4
3955	2020-11-14 16:06:57.994	2020-11-14 23:59:50.834	f		\N	4.09999999999999964	15.7400000000000002	9	Paw Patrol	2
4001	2020-11-14 17:59:09.336	2020-11-15 00:00:06.136	f	Un barco	\N	4.45000000000000018	17.4499999999999993	13	Titanic	1
3953	2020-11-14 16:06:10.49	2020-11-15 00:00:21.416	t	The Grinch	\N	4.20000000000000018	13.8499999999999996	10	Grinch	1
3901	2020-11-14 16:01:01.863	2020-11-15 00:10:22.134	t	It's about toys!	\N	6	21	20	Toy Story	0
4151	2020-11-15 12:50:50.566	2020-11-15 12:50:50.566	t	Superman!	https://www.images.com/45d4f5	5.45000000000000018	15.4800000000000004	13	Superman	0
4251	2020-11-15 21:41:13.558	2020-11-15 21:45:47.542	t	Square	https://www.images.com/5d4f5	4.55999999999999961	15.8900000000000006	1	Bob Sponge	0
4201	2020-11-15 17:28:50.89	2020-11-16 00:23:42.522	t	Hombre Araña 2	https://www.images.com/4fd5f45d	4.78000000000000025	16.4499999999999993	13	Spiderman 2	1
4003	2020-11-14 18:00:30.531	2020-11-16 00:31:34.143	t	Vengadores	\N	5.67999999999999972	19.870000000000001	15	Avengers	3
4005	2020-11-14 18:01:51.817	2020-11-16 00:37:25.473	t	Comedia	\N	6.48000000000000043	18.4499999999999993	15	Que paso ayer?	3
\.


--
-- Data for Name: movie_updates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movie_updates (id, created_at, updated_at, new_value, old_value, update_type, movie_id) FROM stdin;
36	2020-11-14 16:52:49.578	2020-11-14 16:52:49.578	Toy Story	Toy Story 4	0	3901
37	2020-11-14 16:52:49.594	2020-11-14 16:52:49.594	6.0	5.9	1	3901
38	2020-11-14 16:52:49.602	2020-11-14 16:52:49.602	21.0	20.9	2	3901
39	2020-11-14 16:53:33.761	2020-11-14 16:53:33.761	4.2	4.48	1	3953
40	2020-11-14 16:53:33.768	2020-11-14 16:53:33.768	13.85	13.85	2	3953
41	2020-11-14 16:55:12.438	2020-11-14 16:55:12.438	4.1	4.45	1	3955
42	2020-11-14 16:56:28.488	2020-11-14 16:56:28.488	The Lorax	Lorax	0	3954
43	2020-11-14 16:56:28.494	2020-11-14 16:56:28.494	15.23	15.78	2	3954
84	2020-11-15 17:29:51.057	2020-11-15 17:29:51.057	Spiderman 2	Spiderman	0	4201
\.


--
-- Data for Name: purchase; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchase (id, created_at, updated_at, price, movie_id, user_id) FROM stdin;
31	2020-11-14 16:08:39.773	2020-11-14 16:08:39.773	13.8499999999999996	3953	13
32	2020-11-14 16:08:54.365	2020-11-14 16:08:54.365	20.4499999999999993	3951	14
33	2020-11-14 16:09:04.512	2020-11-14 16:09:04.512	15.7799999999999994	3954	6
86	2020-11-16 00:23:42.547	2020-11-16 00:23:42.547	16.4499999999999993	4201	47
87	2020-11-16 00:31:34.168	2020-11-16 00:31:34.168	19.870000000000001	4003	47
\.


--
-- Data for Name: rental; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rental (id, created_at, updated_at, due_date, penalty, price, return_date, movie_id, user_id) FROM stdin;
28	2020-11-14 16:07:36.924	2020-11-14 16:07:36.924	2020-11-13 22:01:02.89	\N	4.48000000000000043	\N	3953	13
29	2020-11-14 16:07:47.305	2020-11-14 16:07:47.305	2020-11-13 22:01:02.89	\N	4.45000000000000018	\N	3955	14
30	2020-11-14 16:08:10.062	2020-11-14 16:08:10.062	2020-11-13 22:01:02.89	\N	5.86000000000000032	\N	3901	6
85	2020-11-15 21:43:57.274	2020-11-15 21:45:47.547	2020-11-13 22:01:02.89	\N	4.55999999999999961	2020-11-10 05:42:28.703	4251	47
88	2020-11-16 00:37:25.508	2020-11-16 00:37:25.508	2020-11-13 22:01:02.89	\N	6.48000000000000043	\N	4005	47
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
47	1
48	2
49	1
50	1
51	1
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_at, updated_at, address, email, username, password, phone) FROM stdin;
6	2020-11-11 00:43:06.995	2020-11-11 02:25:08.466	en mi casa	jperez@test.com	Juana Perez	4321	485646
13	2020-11-13 22:04:09.975	2020-11-13 22:04:09.975	colonia los robles	hperez@test.com	Humberto Perez	abcd	8451564564
14	2020-11-13 22:04:45.783	2020-11-13 22:04:45.783	la esperanza	lpaladino@test.com	Ligia Paladino	password	484511556
15	2020-11-13 22:05:54.484	2020-11-13 22:05:54.484	barrio	mquezada@test.com	Martha Quezada	pass	84545125
47	2020-11-15 11:43:01.076	2020-11-15 11:43:01.076	\N	user@movierental.com	user	$2a$10$aKV5umIGj62eDGuKIiJL5u/q6SPzmKQMo/CKyZu6KbuqVoBpxv3Re	\N
48	2020-11-15 11:43:29.969	2020-11-15 11:43:29.969	\N	admin@movierental.com	admin	$2a$10$AYj5koRAhQYO8E32H4bpCO6.gwUfADrgahSnSNdPTZQNR7ZmH2n1u	\N
49	2020-11-15 12:13:57.095	2020-11-15 12:13:57.095	\N	aperez@test.com	aperez	$2a$10$.wMpIEjpC5mwrWu4LYJ09uXy60ktrjxXk.tb9N8pbBrFuSBbvwduG	\N
50	2020-11-15 12:21:37.4	2020-11-15 12:21:37.4	\N	jolivarez@test.com	jolivarez	$2a$10$SMDYmiEAiEHE/Zu8kAELDuWfvRhouovDKKI33dEhSnOYCkCxcCu6O	\N
51	2020-11-15 13:14:38.22	2020-11-15 13:14:38.22	\N	sruiz@test.com	sruiz	$2a$10$avUg3czQjWtvpAfcuum.Vem5GmGnIOJMeABtFwcesd/cm89h5kJ.i	\N
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 88, true);


--
-- Name: movie_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movie_sequence', 4300, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- Name: likes likes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (movie_id, user_id);


--
-- Name: movie movie_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie
    ADD CONSTRAINT movie_pkey PRIMARY KEY (id);


--
-- Name: movie_updates movie_updates_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie_updates
    ADD CONSTRAINT movie_updates_pkey PRIMARY KEY (id);


--
-- Name: purchase purchase_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT purchase_pkey PRIMARY KEY (id);


--
-- Name: rental rental_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rental
    ADD CONSTRAINT rental_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: rental fk2xk70r11s5vcm9e2djkaipv2u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rental
    ADD CONSTRAINT fk2xk70r11s5vcm9e2djkaipv2u FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- Name: likes fk6icmn2p3ba7o92whtf5aumuo1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fk6icmn2p3ba7o92whtf5aumuo1 FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- Name: movie_updates fk9t8jqb1w6c6km7tm4cewo9smi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movie_updates
    ADD CONSTRAINT fk9t8jqb1w6c6km7tm4cewo9smi FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: purchase fkhgsvttia7xfb172bja3mb1jrr; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT fkhgsvttia7xfb172bja3mb1jrr FOREIGN KEY (movie_id) REFERENCES public.movie(id);


--
-- Name: rental fkj1ty59tjbwlmx7p4uotyto7lp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rental
    ADD CONSTRAINT fkj1ty59tjbwlmx7p4uotyto7lp FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: likes fknvx9seeqqyy71bij291pwiwrg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT fknvx9seeqqyy71bij291pwiwrg FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: purchase fkoj7ky1v8cf4ibkk0s7alikp52; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase
    ADD CONSTRAINT fkoj7ky1v8cf4ibkk0s7alikp52 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

