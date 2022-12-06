create table product(
	product_idx number primary key
	, subcategory_idx number
	, product_name varchar2(30)
	, brand varchar2(30)
	, price number default 0
	, filename varchar2(20)
	, constraint fk_subcategory_product foreign key (subcategory_idx) references subcategory(subcategory_idx)
)

create sequence seq_product
increment by 1
start with 1


select * from product order by product_idx asc;

select t.topcategory_name as topcategory, s.subcategory_name as subcategory, product_name, brand, price from product p inner join SUBCATEGORY s
on p.subcategory_idx = s.subcategory_idx  inner join topcategory t on t.topcategory_idx = s.topcategory_idx

select t.topcategory_idx as topcategory_idx, t.topcategory_name as topcategory_name, s.subcategory_idx as subcategory_idx, s.subcategory_name as subcategory, 
			product_idx, product_name, brand, price from product p inner join SUBCATEGORY s
			on p.subcategory_idx = s.subcategory_idx  inner join topcategory t on t.topcategory_idx = s.topcategory_idx;