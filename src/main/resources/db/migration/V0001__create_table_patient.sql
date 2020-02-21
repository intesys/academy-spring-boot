create table if not exists patient
(
    id int auto_increment
        primary key,
    firstName varchar(255) null,
    lastName varchar(255) null,
    birthDate date not null,
    fiscalCode char(16) not null
);
