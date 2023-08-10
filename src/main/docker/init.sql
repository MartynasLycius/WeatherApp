CREATE SEQUENCE docker_test_table_id_seq;
CREATE TABLE public.test_table
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);
INSERT INTO public.test_table (id, title)
VALUES (DEFAULT, 'title_1');
INSERT INTO public.test_table (id, title)
VALUES (DEFAULT, 'title_2');
