/* create database dwalinadventure */

create table scene(
	id int auto_increment primary key,
	name varchar(100) not null,
	description text not null
);

create table itens(
	id int auto_increment primary key,
	name varchar(100) not null,
	description text not null,
	scene_id int,
	positiveResult_id int,
	negativeResult_id int,
	is_in_inventory boolean default false,
	foreign key (scene_id) references scene(id),
	foreign key (positiveResult_id) references scene(id),
	foreign key (negativeResult_id) references scene(id)
);

alter table itens
add nextScene_id int;

create table actions(
	id int auto_increment primary key,
	item_id int,
	scene_id int,
	is_sucess boolean,
	message text not null,
	nextScene_id int,
	foreign key(item_id) references items(id),
	foreign key(scene_id) references scene(id),
	foreign key(nextScene_id) references scene(id)
); 