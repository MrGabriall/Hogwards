select s.name, s.age, f.name
from students s
left join faculties f on f.id = s.faculty_id;

select s.name, s.age
from students s
inner join avatars a on a.id = s.id