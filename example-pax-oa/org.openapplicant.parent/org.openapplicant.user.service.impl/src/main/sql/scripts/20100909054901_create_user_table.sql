--// create_user_table
create table user (
	user_id bigint not null auto_increment,
	email varchar(255) not null,
	password varchar(255),
	family_name varchar(255),
	given_name varchar(255),
	middle_name varchar(255),
	username varchar(255) not null unique,
	uuid varchar(255) not null unique,
	primary key(user_id)
) ENGINE=InnoDB;


--//@UNDO
drop table if exists user;

