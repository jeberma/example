--// create_user_authorities_table
create table user_authorities (
	user_id bigint not null,
	authority varchar(255) not null,
	primary key(user_id, authority),
	index(user_id),
	foreign key(user_id) references user(user_id)
) ENGINE=InnoDB;

--//@UNDO
drop table if exists user_authorities;


