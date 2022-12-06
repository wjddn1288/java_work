create table product(
	product_idx number primary key
	, subcategory_idx number
	, product_name varchar2(30)
	, brand varchar2(30) --브랜드명
	, price number default 0 --default값을 주지 않으면 null+5 연산불가능
	, filename varchar2(20)
	, constraint fk_subcategory_product --이름만 준 것
		foreign key(subcategory_idx)
		references subcategory(subcategory_idx) --참조 대상
);

create sequence seq_product
increment by 1
start WITH 1;

--쿼리문 검증!
insert into product(product_idx, subcategory_idx, product_name, brand, price, filename) values(seq_product.nextval,4,'카키점퍼','JP',109000,'1670296635051.jpg')

--조인문 : subcategory와 product 조인문 작성하기
--inner join을 명시하지 않은 조인문
--shop 테이블 영역에 정보띄우기 위해 작성
select s.subcategory_idx as subcategory_idx, subcategory_name,
 product_idx, product_name, brand, price, filename
 from subcategory s, product p
 where s.subcategory_idx=p.subcategory_idx;
 