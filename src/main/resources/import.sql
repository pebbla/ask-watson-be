INSERT INTO admin(admin_account,admin_name,admin_password) VALUES ('hssarah','이한슬','qwer1234');
INSERT INTO admin(admin_account,admin_name,admin_password) VALUES ('arock1998','최아록','qwer1234');

INSERT INTO cafe(cafe_name,cafe_phone_num,location_sort,company) VALUES ('포인트나인 강남점','02-1919-1919','강남', '포인트나인');
INSERT INTO cafe(cafe_name,cafe_phone_num,location_sort,company) VALUES ('방탈출고고싱','02-2020-1124','건대', '기타');

INSERT INTO theme(theme_name,theme_explanation,category,difficulty,time_limit,cafe_id) VALUES('포인트나인나인','포인트 아홉개를 연결함으로써 별자리를 완성해보세요!','ROMANCE',3.5,60,1);
INSERT INTO theme(theme_name,theme_explanation,category,difficulty,time_limit,cafe_id) VALUES('포인트에잇','포인트 여덟개를 연결해서 별자리를 완성해봅시다','FEAR',4.0,70,1);