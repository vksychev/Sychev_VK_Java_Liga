drop table if exists ORDERS;
drop table if exists CUSTOMERS;

create table CUSTOMERS
(
    id    integer primary key,
    name  text,
    email text
);

create table ORDERS
(
    id          integer primary key auto_increment,
    name        text,
    price       integer,
    customer_id integer references CUSTOMERS (id)
);

INSERT INTO CUSTOMERS(id, name, email) VALUES
(1 , 'Ivan', 'i@ya.ru'),
(2 , 'Vit', 'v@ya.ru'),
(3 , 'Sam', 's@ya.ru'),
(4 , 'Lon', 'l@ya.ru');
