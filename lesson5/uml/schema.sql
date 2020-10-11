drop table if exists student_subject;
drop table if exists teacher_subject;
drop table if exists student;
drop table if exists teacher;
drop table if exists subject;
drop table if exists school;


create table school
(
    id      integer not null primary key,
    name    varchar(255) not null,
    address varchar(255) not null
);

create table teacher
(
    id        integer not null primary key,
    fio       varchar(255) not null,
    age       integer,
    sex       varchar(10),
    school_id integer not null references school (id)
);

create table student
(
    id        integer not null primary key,
    fio       varchar(255) not null,
    age       integer,
    sex       varchar(10),
    school_id integer not null references school (id)
);

create table subject
(
    id   integer not null primary key,
    name varchar(255) not null
);

create table student_subject
(
    student_id integer not null,
    subject_id integer not null,
    foreign key (student_id) references student (id),
    foreign key (subject_id) references subject (id),
    unique (student_id, subject_id)
);

create table teacher_subject
(
    teacher_id integer not null,
    subject_id integer not null,
    foreign key (teacher_id) references teacher (id),
    foreign key (subject_id) references subject (id),
    unique (teacher_id, subject_id)
);