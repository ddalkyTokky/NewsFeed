# [발표자료.pdf](https://github.com/user-attachments/files/15512637/NewsFeed.PPT.pdf)              
# [시연영상](https://www.youtube.com/watch?v=L8JOhpif_mQ)         
# [협업 노션](https://www.notion.so/teamsparta/4-738be2ef42814995ace23e6298db6d1e)         

# 1. 기획문서
## 1-1. 와이어프레임
### 메인 페이지
![image](https://github.com/Kids-of-StrawberryRabbit/NewsFeed/assets/47583083/2553654e-94f2-4c53-891c-a958d46aa75a)
### 상세 페이지
![image](https://github.com/Kids-of-StrawberryRabbit/NewsFeed/assets/47583083/53c34069-f106-4334-86af-e4ae11a94e66)
### 로그인 페이지
![image](https://github.com/Kids-of-StrawberryRabbit/NewsFeed/assets/47583083/2149e3ea-a45d-4614-8b98-91e3ec62fdb7)
### 회원가입 및 마이페이지
![image](https://github.com/Kids-of-StrawberryRabbit/NewsFeed/assets/47583083/78f77064-6a37-48da-8504-95db5a19669d)
## 1-2. API 설계
![image](https://github.com/Kids-of-StrawberryRabbit/NewsFeed/assets/47583083/0e1858b0-f29d-466e-b7a7-fc3351e9fb54)
## 1-3. DBMS (PostgreSQL)
### ERD
![image](https://github.com/Kids-of-StrawberryRabbit/NewsFeed/assets/47583083/93c53040-44a1-4fa6-9332-7578f36095fb)
### DDL
```
CREATE TABLE feed (
	id	BIGSERIAL PRIMARY KEY	NOT NULL,
	member_id	BIGINT	NOT NULL,
	music_id	BIGINT	NOT NULL,
	title	VARCHAR(200)	NOT NULL,
	content	VARCHAR(1000)	NOT NULL,
	created_at	TIMESTAMP	NOT NULL
);

CREATE TABLE comment (
	id	BIGSERIAL PRIMARY KEY	NOT NULL,
	feed_id	BIGINT	NOT NULL,
	member_id	BIGINT	NOT NULL,
	content	VARCHAR(200)	NOT NULL,
	created_at	TIMESTAMP	NOT NULL
);

CREATE TYPE signup AS ENUM ('NAVER', 'KAKAO', 'EMAIL');

CREATE TABLE member (
	id	BIGSERIAL PRIMARY KEY	NOT NULL,
	nickname	VARCHAR(32)	NOT NULL,
	email	VARCHAR(100)	UNIQUE NOT NULL,
	password	VARCHAR(64)	NOT NULL,
	signup_type	signup	NOT NULL,
	image	VARCHAR(1000)
);

CREATE TABLE feedlike (
	feed_id	BIGINT	NOT NULL,
	member_id	BIGINT	NOT NULL,
	PRIMARY KEY (feed_id, member_id)
);

CREATE TABLE follow (
	follower	BIGINT	NOT NULL,
	followee	BIGINT	NOT NULL,
	PRIMARY KEY (follower, followee)
);

CREATE TABLE music (
	id	BIGSERIAL PRIMARY KEY	NOT NULL,
	singer	VARCHAR(100)	NOT NULL,
	title	VARCHAR(100)	NOT NULL,
	cover	VARCHAR(1000)
);

ALTER TABLE feed ADD CONSTRAINT FK_member_TO_feed_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	id
)
ON DELETE CASCADE;

ALTER TABLE feed ADD CONSTRAINT FK_music_TO_feed_1 FOREIGN KEY (
	music_id
)
REFERENCES music (
	id
)
ON DELETE CASCADE;

ALTER TABLE comment ADD CONSTRAINT FK_feed_TO_comment_1 FOREIGN KEY (
	feed_id
)
REFERENCES feed (
	id
)
ON DELETE CASCADE;

ALTER TABLE comment ADD CONSTRAINT FK_member_TO_comment_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	id
)
ON DELETE CASCADE;

ALTER TABLE feedlike ADD CONSTRAINT FK_feed_TO_feedlike_1 FOREIGN KEY (
	feed_id
)
REFERENCES feed (
	id
)
ON DELETE CASCADE;

ALTER TABLE feedlike ADD CONSTRAINT FK_member_TO_feedlike_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	id
)
ON DELETE CASCADE;

ALTER TABLE follow ADD CONSTRAINT FK_member_TO_follow_1 FOREIGN KEY (
	follower
)
REFERENCES member (
	id
)
ON DELETE CASCADE;

ALTER TABLE follow ADD CONSTRAINT FK_member_TO_follow_2 FOREIGN KEY (
	followee
)
REFERENCES member (
	id
)
ON DELETE CASCADE;
```
