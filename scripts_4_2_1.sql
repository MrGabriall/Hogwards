alter table students
    add check ( age >= 16 ),
    alter column name set not null,
    add constraint unique_name unique (name),
    alter column age set default 20;

alter table faculties
    add constraint unique_name_and_color unique (name, color);