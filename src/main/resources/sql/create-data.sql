
-- delete old data
delete from task_employee;
delete from timesheet;
delete from task;
delete from employee;
delete from manager;
delete from department;

-- add few departments
insert into department values(1,'management');
insert into department values(2,'engineering');


-- add few employees
insert into employee values(1, 'Steve Jobs',1);
insert into employee values(2, 'Bill Gates',1);
insert into employee values(3, 'Steve Wozniak',2);
insert into employee values(4, 'Paul Allen',2);

-- add few managers
insert into manager values(1, 'Eric Schmidt');
insert into manager values(2, 'Steve Ballmer');

-- add some tasks
insert into task values(1, 0, 'task 1', 1);
insert into task values(2, 0, 'task 2', 2);

-- connect tasks to some employees
insert into task_employee values (1, 1);
insert into task_employee values (1, 3);
insert into task_employee values (1, 4);
insert into task_employee values (2, 2);
insert into task_employee values (2, 1);

-- create some timesheets on tasks
insert into timesheet values(1,
 5, -- hours
 1, -- first task
 1 -- employee steve jobs
);

insert into timesheet values(2,
 8, -- hours
 2, -- second task
 3 -- employee bill gates
);