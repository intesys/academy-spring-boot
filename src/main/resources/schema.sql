create table patient
(
    id int auto_increment
        primary key,
    firstName varchar(255) null,
    lastName varchar(255) null,
    birthDate date not null,
    fiscalCode char(16) not null
);

create table examination
(
    id int auto_increment primary key,
    patientId int not null references patient,
    diastolicPressure int not null,
    systolicPressure int not null,
    height int not null,
    weight int not null,
    examinationDate timestamp not null
)