-- console
USE schedule;

-- 테이블 생성 (schedules)
CREATE TABLE schedules
(
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 ID',
    task VARCHAR(255) NOT NULL COMMENT '할 일',
    author VARCHAR(100) NOT NULL COMMENT '작성자명',
    password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    created_at DATETIME DEFAULT NOW() COMMENT '작성일',
    updated_at DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '수정일'
);