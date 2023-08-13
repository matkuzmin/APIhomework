create table person (
    id serial primary key ,
    nickName char(100) not null ,
    age integer not null check ( age>20 ),
    driver_license boolean,
    car_id integer references car(id)
);
create table car (
    id serial primary key ,
    brand char(100) not null ,
    model char(100) not null,
    price numeric not null
);
select s.name , s.age , f.name
from student s inner join faculty f on s.faculty_id = f.id;
select  s.name, s.age
from student s left join avatar a on s.id = a.student_id where a.data notnull ;