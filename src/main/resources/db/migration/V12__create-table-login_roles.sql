create table login_roles(
    logins_id bigint not null,
    roles_id bigint not null,
    primary key(logins_id, roles_id),
    foreign key(logins_id) references logins(id),
    foreign key(roles_id) references roles(id)
)