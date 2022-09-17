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

INSERT INTO user(user_nickname,user_phone_num,user_birth,user_gender,marketing_agree_yn) VALUES ('우영우', '010-1511-2662', '1995-04-02', 'F', 1);
INSERT INTO user(user_nickname,user_phone_num,user_birth,user_gender,marketing_agree_yn) VALUES ('홍길동', '010-1111-2222', '1900-01-01', 'M', 1);

INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,geography,website,address,image_url) VALUES ('포인트나인 강남점','02-1919-1919',1, POINT(127.127730, 38.439801),"http://www.pointnine.com","서울시 서초구 서초3동 어쩌구 주소","https://storage.cloud.google.com/ask_watson/test/front/IMG_9823.jpg");
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,geography,website,address,image_url) VALUES ('더클루 강남점','02-2323-1111',1, POINT(127.127730, 38.439801),"http://www.theclue.com","서울시 어쩌구 주소","https://storage.cloud.google.com/ask_watson/test/front/IMG_9823.jpg");
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,geography,website,address,image_url) VALUES ('룰루랄라라','02-6653-1624',5, POINT(127.127730, 38.439801),"http://www.lululalala.com","어쩌구 주소","https://storage.cloud.google.com/ask_watson/test/front/IMG_9823.jpg");
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,geography,website,address,image_url) VALUES ('방탈출고고싱','031-2020-1124',12,POINT(127.127730, 38.439801),"http://www.gogosing.com","주소주소입니다주소","https://storage.cloud.google.com/ask_watson/test/front/IMG_9823.jpg");
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,geography,website,address,image_url) VALUES ('ㅂㅐㄱ설공주','02-6653-1624',5, POINT(127.127730, 38.439801),"http://www.snowwhite.com","어쩌구 주소","https://storage.cloud.google.com/ask_watson/test/front/IMG_9823.jpg");
INSERT INTO cafe(cafe_name,cafe_phone_num,location_id,geography,website,address,image_url) VALUES ('사과 맛있어욤','031-2020-1124',12, POINT(127.127730, 38.439801),"http://www.appleapple.com","주소주소입니다주소","https://storage.cloud.google.com/ask_watson/test/front/IMG_9823.jpg");

-- INSERT INTO `review` (`activity`, `content`, `device_ratio`, `difficulty`, `rating`, `time_taken`, `used_hint_num`, `theme_id`) VALUES ('1', '내용내용!!', '1', '1', '1', '1', '1', '1', '1');

INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('포인트나인나인','포인트 아홉개를 연결함으로써 별자리를 완성해보세요!',3,3.5,60,1,2,24000,"http://www.pointnine-reservation.com","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('포인트에잇','포인트 여덟개를 연결해서 별자리를 완성해봅시다',1,4.0,70,1,1,22000,"http://www.pointeight-reservation.com","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('포인트세븐','럭키세븐',3,2.0,50,1,4,18000,"https://www.pointseven-reservation.co.kr","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('더클루1','더클루테마1입니당',6,3.5,60,2,2,24000,"http://www.theclue1-reservation.ac.kr","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('더클루2','더클루테마2입니당',1,4.3,70,2,1,21000,"http://www.theclue2-reservation.ac.kr","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('더클루3','더클루테마3입니당',1,4.2,70,2,1,30000,"http://www.theclue3-reservation.ac.kr","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('더클루4','더클루테마4입니당',1,4.7,70,2,1,40000,"http://www.theclue4-reservation.ac.kr","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");
INSERT INTO theme(theme_name,theme_explanation,category_id,difficulty,time_limit,cafe_id,min_num_people,price,reservation_url,image_url) VALUES('더클루5','더클루테마5입니당더클루테마5입니당더클루테마5입니당더클루테마5입니당더클루테마5입니당더클루테마5입니당더클루테테마5입니당더클루테마5입니당더클루테마5입니당',1,4.7,70,2,1,40000,"http://www.theclue4-reservation.ac.kr","https://storage.cloud.google.com/ask_watson/test/front/%E1%84%87%E1%85%AE%E1%84%82%E1%85%A9%E1%86%BC2.png");

INSERT INTO notice(created_at,title,content) VALUES('2022-09-18T02:29:45','공지공지1','공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용');
INSERT INTO notice(created_at,title,content) VALUES('2022-04-18T02:29:45','공지공지2','공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공ㄹㄹㄹㄹㄹㄹ 지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용 지내용임공지내용공지내용임공지내용공지내용임공지내용ㅇ');
INSERT INTO notice(created_at,title,content) VALUES('2022-09-18T02:29:45','공지공지3','공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용');
INSERT INTO notice(created_at,title,content) VALUES('2022-09-18T02:29:45','공지공지4','공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내ㄹㄹㄹㄹㄹ용임공지내용공지내용임공지내용공지내용임공지내용');
INSERT INTO notice(created_at,title,content) VALUES('2022-09-18T02:29:45','공지공지5','공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용');
INSERT INTO notice(created_at,title,content) VALUES('2022-09-18T02:29:45','공지공지6','공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용공지내용임공지내용');

INSERT INTO faq(title,content) VALUES('자주묻는질문1','자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 ');
INSERT INTO faq(title,content) VALUES('자주묻는질문2','자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 ');
INSERT INTO faq(title,content) VALUES('자주묻는질문3','자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 ');
INSERT INTO faq(title,content) VALUES('자주묻는질문4','자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 ');
INSERT INTO faq(title,content) VALUES('자주묻는질문5','자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 ');
INSERT INTO faq(title,content) VALUES('자주묻는질문ㅇ6','자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 자주자주 묻는 질문들 ');

INSERT INTO review(user_id,difficulty,time_taken,used_hint_num,rating,device_ratio,activity,content,theme_id) VALUES(1,3,45,7,2.3,4,4,"리뷰예시입니다ㅏㅏㅏㅏㅏㅏ",1);
INSERT INTO review(user_id,difficulty,time_taken,used_hint_num,rating,device_ratio,activity,content,theme_id) VALUES(2,3,45,7,2.3,4,4,"리뷰예시~~~~입니다ㅏㅏㅏㅏㅏㅏ",2);

INSERT INTO report(reporter_id,reported_user_id,content,review_id) VALUES(2,1,"리뷰가 너무 성의가 없습니다...",1);
INSERT INTO report(reporter_id,reported_user_id,content,review_id) VALUES(1,2,"리뷰가 너무 재미가 없습니다...",2);
