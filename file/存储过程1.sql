drop PROCEDURE in_bymonthdetail;
create PROCEDURE in_bymonthdetail (in d1 date ,in d2 date ,in h int ,in z1 int ,in z2 DOUBLE )
begin 

select *, round((zf-kpzf),2),round((nextzf),2) ,round((zf-kpzf+nextzf),2) from jingjia where hslpm<h and kpzf>z1 and date>=d1 and date<d2 and kpzf<z2 order by zf-kpzf+nextzf  ;
end 

call in_bymonthdetail(20200701,20200801,6,2,9.5) ;
call in_bymonthdetail(20200801,20200901,6,2,9.5) ;
call in_bymonthdetail(20200901,20201001,6,2,9.5) ;
call in_bymonthdetail(20201001,20201101,6,2,9.5) ;
call in_bymonthdetail(20201101,20201201,6,2,9.5) ;
call in_bymonthdetail(20201201,20210101,6,2,9.5) ;
call in_bymonthdetail(20210101,20210201,6,2,9.5) ;
call in_bymonthdetail(20210201,20210301,6,2,9.5) ;
call in_bymonthdetail(20210301,20210401,6,2,9.5) ;
call in_bymonthdetail(20210401,20210501,6,2,9.5) ;
call in_bymonthdetail(20210501,20210601,6,2,9.5) ;


create PROCEDURE in_bymonth (in d1 date ,in d2 date )
begin 

select '前1' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<2 and kpzf>2 and date>=d1 and date<d2 and kpzf<9.5 union
select '前2' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<3 and kpzf>2 and date>=d1 and date<d2 and kpzf<9.5 union
select '前3' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<4 and kpzf>2 and date>=d1 and date<d2 and kpzf<9.5 union
select '前4' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<5 and kpzf>2 and date>=d1 and date<d2 and kpzf<9.5 union
select '前5' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<6 and kpzf>2 and date>=d1 and date<d2 and kpzf<9.5 union
-- 目测设置开盘涨幅>1 换手率前2最佳，前3也行
select '前1 ' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<2 and kpzf>1 and date>=d1 and date<d2 and kpzf<9.5 union
select '前2 ' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<3 and kpzf>1 and date>=d1 and date<d2 and kpzf<9.5 union
select '前3 ' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<4 and kpzf>1 and date>=d1 and date<d2 and kpzf<9.5 union
select '前4 ' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<5 and kpzf>1 and date>=d1 and date<d2 and kpzf<9.5 union
select '前5 ' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<6 and kpzf>1 and date>=d1 and date<d2 and kpzf<9.5 ;
end 

call in_bymonth(20200701,20200801) ;
call in_bymonth(20200801,20200901) ;
call in_bymonth(20200901,20201001) ;
call in_bymonth(20201001,20201101) ;
call in_bymonth(20201101,20201201) ;
call in_bymonth(20201201,20210101) ;
call in_bymonth(20210101,20210201) ;
call in_bymonth(20210201,20210301) ;
call in_bymonth(20210301,20210401) ;
call in_bymonth(20210401,20210501) ;
call in_bymonth(20210501,20210601) ;



drop PROCEDURE in_bymonth
show create PROCEDURE in_sel
show create procedure in_sela;
show PROCEDURE status







create PROCEDURE in_test (in d1 date ,in d2 date )
begin 

select '前1' ,d1 , d2,count(*), round(avg(zf-kpzf),2),round(avg(nextzf),2) ,round(avg(zf-kpzf+nextzf),2) from jingjia where hslpm<2 and kpzf>2 and date>=d1 and date<d2 and kpzf<9.5 ;
end 

call in_test(20210101,20210201) ;

DROP PROCEDURE in_test;
