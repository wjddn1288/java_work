create table TopCategory(
	topcategory_idx number primary key,
	topcategory_name varchar2(20)
)

create sequence seq_topcategory
increment by 1
start with 1

select * from TOPCATEGORY

insert into TopCategory values(seq_topcategory.nextval,'상의');
insert into TopCategory values(seq_topcategory.nextval,'하의');
insert into TopCategory values(seq_topcategory.nextval,'액세서리');
insert into TopCategory values(seq_topcategory.nextval,'신발');