create table usr
(
    id         uuid          not null,
    first_name varchar(250)  not null,
    last_name  varchar(250)  not null,
    birthday   date          not null,
    city       varchar(250)  not null,
    sex        int2          not null,
    interests  varchar(2500) not null,
    primary key (id)
);

create table usr_relationship
(
    relating_id uuid not null,
    related_id  uuid not null,
    primary key (relating_id, related_id),
    constraint related_usr_fk foreign key (related_id) references usr,
    constraint relating_usr_fk foreign key (relating_id) references usr
);
