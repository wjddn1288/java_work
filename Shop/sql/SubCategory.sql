create table subcategory(
	subcategory_idx number primary key,
	topcategory_idx number, --부모의 pk
	subcategory_name varchar2(20),
	constraint fk_topcategory_subcategory foreign key (topcategory_idx) references topcategory(topcategory_idx)
)

create sequence seq_subcategory
increment by 1
start with 1


select * from subCATEGORY

drop table subcategory;
drop sequence seq_subcategory


insert into SUBCATEGORY values(seq_subcategory.nextval,1,'티셔츠')
insert into SUBCATEGORY values(seq_subcategory.nextval,1,'가디건');
insert into SUBCATEGORY values(seq_subcategory.nextval,1,'니트');
insert into SUBCATEGORY values(seq_subcategory.nextval,1,'점퍼');


insert into SUBCATEGORY values(seq_subcategory.nextval,2,'면바지');
insert into SUBCATEGORY values(seq_subcategory.nextval,2,'반바지');
insert into SUBCATEGORY values(seq_subcategory.nextval,2,'청바지');
insert into SUBCATEGORY values(seq_subcategory.nextval,2,'레깅스');


insert into SUBCATEGORY values(seq_subcategory.nextval,3,'귀걸이');
insert into SUBCATEGORY values(seq_subcategory.nextval,3,'목걸이');
insert into SUBCATEGORY values(seq_subcategory.nextval,3,'팔찌');
insert into SUBCATEGORY values(seq_subcategory.nextval,3,'반지');


insert into SUBCATEGORY values(seq_subcategory.nextval,4,'운동화');
insert into SUBCATEGORY values(seq_subcategory.nextval,4,'구두');
insert into SUBCATEGORY values(seq_subcategory.nextval,4,'샌들');
insert into SUBCATEGORY values(seq_subcategory.nextval,4,'슬리퍼');

