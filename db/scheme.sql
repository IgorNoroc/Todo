create table item (
    id serial primary key,
    user_id int not null references users(id)
    description text,
    created timestamp,
    done boolean default false
)

create table users (
    id serial primary key ,
    name text,
    email text,
    password text;
)