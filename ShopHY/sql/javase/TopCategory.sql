--22.12.05
--topcategory 만들기
create table TopCategory(
	topcategory_idx number primary key
	, topcategory_name varchar2(20)
);

--시퀀스 생성
create sequence seq_topcategory
increment by 1
start with 1;

--상위 카테고리 중 '상의' 등록
insert into topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '상의');

insert into topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '하의');

insert into topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '액서세리');

insert into topcategory(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '신발');

select * from topcategory; --테이블 조회하기

delete from topcategory; --테이블 삭제하기

drop table topcategory;
drop table seq_topcategory;

--재생성하기
create table TopCategory(
	topcategory_idx number primary key
	, topcategory_name varchar2(20)
);

--조회하기
select * from topcategory;

delete from topcategory; --자식이 있기 때문에 삭제 불가능