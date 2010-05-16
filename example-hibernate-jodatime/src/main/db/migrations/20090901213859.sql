create table User(
	id bigint not null auto_increment,
	ts datetime,
	tszone varchar(255),
	ts2 datetime,
	cal datetime,
	primary key(id)
)engine=InnoDB;