<<<<<<< HEAD
CREATE TABLE gallery(
gallery_id NUMBER PRIMARY KEY
,title varchar2(100)
,writer varchar2(30)
,content clob
,filename varchar2(30)
,regdate date DEFAULT sysdate 
);

CREATE SEQUENCE seq_gallery
INCREMENT BY 1
START WITH 1;


select * from gallery;

create table dot(
	dot_id number primary key
	, x number
	, y number
	, gallery_id number
);

create sequence seq_dot
increment by 1
=======
CREATE TABLE gallery(
gallery_id NUMBER PRIMARY KEY
,title varchar2(100)
,writer varchar2(30)
,content clob
,filename varchar2(30)
,regdate date DEFAULT sysdate 
);

CREATE SEQUENCE seq_gallery
INCREMENT BY 1
START WITH 1;


select * from gallery;

create table dot(
	dot_id number primary key
	, x number
	, y number
	, gallery_id number
);

create sequence seq_dot
increment by 1
>>>>>>> e694098889c5759c58b9353358a8554d64883dbd
start with 1;