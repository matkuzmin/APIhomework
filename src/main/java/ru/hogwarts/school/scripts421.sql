alter table student add constraint age_constrain check (age>20);
alter table student add primary key (name);
alter table faculty add constraint color_name_constrain unique (name , color);
alter table student alter column age set default 20;
