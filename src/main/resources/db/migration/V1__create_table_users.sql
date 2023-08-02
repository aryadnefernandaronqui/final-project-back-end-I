create table users(
user_id UUID primary key not null,
user_name varchar(20) not null,
email varchar(30) not null,
password varchar(15) not null,
created_at timestamp not null default now(),
updated_at timestamp
);
