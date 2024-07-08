
CREATE TABLE account (
    client_id int NOT NULL,
    balance numeric(8,2) NOT NULL,
    state boolean NOT NULL,
    account_number serial NOT NULL,
    account_type character varying(10) NOT NULL
);


CREATE TABLE client (
    age integer NOT NULL,
    gender character varying(1) NOT NULL,
    id serial NOT NULL,
    state boolean NOT NULL,
    identification character varying(10) NOT NULL,
    phone character varying(10) NOT NULL,
    client_id character varying(50) NOT NULL,
    name character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    direction character varying(100) NOT NULL
);



CREATE TABLE transaction (
    final_balance numeric(8,2) NOT NULL,
    initial_balance numeric(8,2) NOT NULL,
    transaction_id serial NOT NULL,
    value numeric(8,2) NOT NULL,
    account_number bigint NOT NULL,
    date timestamp(6) without time zone NOT NULL,
    transaction_description character varying(255) NOT NULL,
    type character varying(255) NOT NULL
);


ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (account_number);


ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


ALTER TABLE ONLY transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id);


ALTER TABLE ONLY transaction
    ADD CONSTRAINT "FKkrjb6f3jrvw9v0qu8n3jdh1ku" FOREIGN KEY (account_number) REFERENCES account(account_number);



ALTER TABLE ONLY account
    ADD CONSTRAINT "FKlobd2m14ups7c9pho6ck2j7ln" FOREIGN KEY (client_id) REFERENCES client(id);

ALTER SEQUENCE client_id_seq
    START WITH 3
    RESTART 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE account_account_number_seq
    START WITH 6
    RESTART 6
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE transaction_transaction_id_seq
    START WITH 11
    RESTART 11
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


INSERT INTO client
(age, gender, id, state, identification, phone, client_id, "name", "password", direction)
VALUES(26, 'M', 1, true, '1719870808', '0960122384', '33d74d1d-e046-4307-92fc-d541c48db70d', 'Brandon Romero', '1234', 'Quito');
INSERT INTO public.client
(age, gender, id, state, identification, phone, client_id, "name", "password", direction)
VALUES(29, 'M', 2, true, '1719870809', '0960872385', '31d74d1d-e054-4307-92fc-e547c48ab82r', 'Pedro Rosales', '12345', 'Guayaquil');

INSERT INTO public.account
(client_id, balance, state, account_number, account_type)
VALUES(2, 1750.00, true, 3, 'Ahorros');
INSERT INTO public.account
(client_id, balance, state, account_number, account_type)
VALUES(1, 240.00, true, 2, 'Corriente');
INSERT INTO public.account
(client_id, balance, state, account_number, account_type)
VALUES(1, 935.00, true, 1, 'Ahorros');
INSERT INTO public.account
(client_id, balance, state, account_number, account_type)
VALUES(2, 75.00, true, 5, 'Corriente');
INSERT INTO public.account
(client_id, balance, state, account_number, account_type)
VALUES(1, 700.00, true, 4, 'Ahorros');

INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(525.00, 500.00, 1, 25.00, 1, '2024-06-27 22:02:00.649', 'Deposito de 25', 'Deposito');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(500.00, 300.00, 2, 200.00, 2, '2024-07-07 19:24:32.301', 'Deposito de 200', 'Deposito');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(250.00, 500.00, 3, -250.00, 2, '2024-07-07 19:26:32.191', 'Retiro de -250', 'Retiro');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(240.00, 500.00, 4, -260.00, 2, '2024-07-07 19:35:40.683', 'Retiro de -260', 'Retiro');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(975.00, 525.00, 5, 450.00, 1, '2024-07-07 19:37:35.218', 'Deposito de 450', 'Deposito');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(935.00, 975.00, 6, -40.00, 1, '2024-07-07 19:38:23.665', 'Retiro de -40', 'Retiro');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(325.00, 200.00, 7, 125.00, 5, '2024-07-07 19:39:30.530', 'Deposito de 125', 'Deposito');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(75.00, 325.00, 8, -250.00, 5, '2024-07-07 19:39:59.312', 'Retiro de -250', 'Retiro');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(1250.00, 900.00, 9, 350.00, 4, '2024-07-07 19:40:38.709', 'Deposito de 350', 'Deposito');
INSERT INTO public."transaction"
(final_balance, initial_balance, transaction_id, value, account_number, "date", transaction_description, "type")
VALUES(700.00, 1250.00, 10, -550.00, 4, '2024-07-07 19:40:53.960', 'Retiro de -550', 'Retiro');