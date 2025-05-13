USE schedule;

-- 테이블 생성 (schedules)
CREATE TABLE schedules
(
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 ID',
    task VARCHAR(255) NOT NULL COMMENT '할 일',
    author VARCHAR(100) NOT NULL COMMENT '작성자명',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
);

-- 테이블 생성 (authors)
CREATE TABLE authors
(
    author_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 ID',
    name VARCHAR(100) NOT NULL COMMENT '이름',
    email VARCHAR(100) NOT NULL COMMENT '이메일',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
);

-- schedules의 author를 author_id(외래키)로 교체
-- -- 기존 author 컬럼 삭제
ALTER TABLE schedules DROP COLUMN author;
-- -- author_id 추가
ALTER TABLE schedules
    ADD COLUMN author_id INT COMMENT '작성자 ID',
    ADD CONSTRAINT fk_schedules_author_id FOREIGN KEY (author_id) REFERENCES authors(author_id);