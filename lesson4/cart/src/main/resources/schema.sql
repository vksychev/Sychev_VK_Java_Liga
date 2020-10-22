drop table if exists ORDERS;
drop table if exists CUSTOMERS;

create table CUSTOMERS
(
    id    integer primary key,
    name  varchar(50),
    email varchar(100)
);

create table ORDERS
(
    id          integer primary key auto_increment,
    name        varchar(250),
    price       integer,
    customer_id integer references CUSTOMERS (id)
);