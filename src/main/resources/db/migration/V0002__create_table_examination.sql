create table if not exists examination
(
    id int auto_increment primary key,
    patientId int not null references patient,
    diastolicPressure int not null,
    systolicPressure int not null,
    height int not null,
    weight int not null,
    examinationDate timestamp not null
)
