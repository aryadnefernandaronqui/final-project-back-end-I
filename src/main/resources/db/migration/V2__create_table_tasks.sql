create table tasks(
task_id UUID primary key not null,
user_id UUID not null references users(user_id),
task_name varchar(20) not null,
description text not null,
favorite boolean,
archived boolean,
finished boolean,
"date" date not null,
created_at timestamp not null default now(),
updated_at timestamp
);