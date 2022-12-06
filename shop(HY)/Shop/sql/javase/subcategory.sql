--topcategory의 하위 카테고리 만들기
create table subcategory(
	subcategory_idx number primary key
	, topcategory_idx number --부모의 pk
	, subcategory_name varchar2(20)
);

--시퀀스 생성
create sequence seq_subcategory
increment by 1
start with 1;

insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,1,'티셔츠');
update subcategory set subcategory_name='가디건' where subcategory_idx=2;
update subcategory set subcategory_name='니트' where subcategory_idx=3;
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,1,'점퍼');

insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,2,'면바지');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,2,'반바지');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,2,'청바지');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,2,'레깅스');

insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,3,'귀걸이');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,3,'목걸이');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,3,'팔찌');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,3,'반지');

insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,4,'운동화');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,4,'구두');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,4,'샌들');
insert into subcategory(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval,4,'슬리퍼');


delete * from subcategory; --테이블 삭제

drop table subcategory;
drop table seq_subcategory;

--제약조건 주기
create table subcategory(
	subcategory_idx number primary key
	, topcategory_idx number --부모의 pk
	, subcategory_name varchar2(20)
	, constraint fk_topcategory_subcategory --제약조건 하나 제시할거야
	 foreign key (topcategory_idx) -- 대상은 이거야
	 references topcategory (topcategory_idx) --topcategory_idx가 참조대상이야 
);

--시퀀스 생성
create sequence seq_subcategory
increment by 1
start with 1;

--조회하기
select * from subcategory;

select * from subcategory where topcategory_idx=3

