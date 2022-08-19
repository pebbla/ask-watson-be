INSERT INTO location(state, city) VALUES ('서울', '강남');
INSERT INTO location(state, city) VALUES ('서울', '홍대');
INSERT INTO location(state, city) VALUES ('서울', '신촌');
INSERT INTO location(state, city) VALUES ('서울', '건대');
INSERT INTO location(state, city) VALUES ('서울', '대학로');
INSERT INTO location(state, city) VALUES ('서울', '강북');
INSERT INTO location(state, city) VALUES ('서울', '신림');
INSERT INTO location(state, city) VALUES ('서울', '기타');
INSERT INTO location(state, city) VALUES ('경기', '부천');
INSERT INTO location(state, city) VALUES ('경기', '일산');
INSERT INTO location(state, city) VALUES ('경기', '수원');
INSERT INTO location(state, city) VALUES ('경기', '안양');
INSERT INTO location(state, city) VALUES ('경기', '기타');
INSERT INTO location(state, city) VALUES ('인천', '기타');
INSERT INTO location(state, city) VALUES ('충청', '대전');
INSERT INTO location(state, city) VALUES ('충청', '천안');
INSERT INTO location(state, city) VALUES ('충청', '청주');
INSERT INTO location(state, city) VALUES ('충청', '기타');
INSERT INTO location(state, city) VALUES ('경상', '대구');
INSERT INTO location(state, city) VALUES ('경상', '부산');
INSERT INTO location(state, city) VALUES ('경상', '기타');
INSERT INTO location(state, city) VALUES ('전라', '전주');
INSERT INTO location(state, city) VALUES ('전라', '광주');
INSERT INTO location(state, city) VALUES ('전라', '기타');
INSERT INTO location(state, city) VALUES ('강원', '기타');
INSERT INTO location(state, city) VALUES ('제주', '기타');

INSERT INTO company(company_name) VALUES ('포인트나인');
INSERT INTO company(company_name) VALUES ('더클루');
INSERT INTO company(company_name) VALUES ('셜록');

INSERT INTO category(category_name) VALUES ('스릴러');
INSERT INTO category(category_name) VALUES ('SF');
INSERT INTO category(category_name) VALUES ('추리');
INSERT INTO category(category_name) VALUES ('공포');
INSERT INTO category(category_name) VALUES ('코미디');
INSERT INTO category(category_name) VALUES ('로맨스');
INSERT INTO category(category_name) VALUES ('판타지');
INSERT INTO category(category_name) VALUES ('야외');
INSERT INTO category(category_name) VALUES ('19금');
INSERT INTO category(category_name) VALUES ('감성');
INSERT INTO category(category_name) VALUES ('방털기');
INSERT INTO category(category_name) VALUES ('물음표');
INSERT INTO category(category_name) VALUES ('기타');

INSERT INTO admin(admin_account,admin_name,admin_password) VALUES ('hssarah','이한슬','qwer1234');
INSERT INTO admin(admin_account,admin_name,admin_password) VALUES ('arock1998','최아록','qwer1234');

INSERT INTO user(user_password,user_nickname,user_phone_num,user_birth,user_gender,marketing_agree_yn) VALUES ('qwer', '우영우', '010-1511-2662', '1995-04-02', 'F', 1);
INSERT INTO user(user_password,user_nickname,user_phone_num,user_birth,user_gender,marketing_agree_yn) VALUES ('1234', '홍길동', '010-1111-2222', '1900-01-01', 'M', 1);

INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,company_id,geography) VALUES ('포인트나인 강남점','02-1919-1919',1, 1, POINT(127.127730, 38.439801));
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,company_id,geography) VALUES ('더클루 강남점','02-2323-1111',1, 2, POINT(127.127730, 38.439801));
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,company_id,geography) VALUES ('룰루랄라라','02-6653-1624',5, 4, POINT(127.127730, 38.439801));
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,company_id,geography) VALUES ('방탈출고고싱','031-2020-1124',12, 4, POINT(127.127730, 38.439801));

INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('포인트나인나인','포인트 아홉개를 연결함으로써 별자리를 완성해보세요!',3,3.5,60,1,2,24000);
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('포인트에잇','포인트 여덟개를 연결해서 별자리를 완성해봅시다',1,4.0,70,1,1,22000);
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('포인트세븐','럭키세븐',3,2.0,50,1,4,18000);
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('더클루1','더클루테마1입니당',6,3.5,60,2,2,24000);
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('더클루2','더클루테마2입니당',1,4.3,70,2,1,21000);
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('더클루3','더클루테마3입니당',1,4.2,70,2,1,30000);
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price) VALUES('더클루4','더클루테마4입니당',1,4.7,70,2,1,40000);
