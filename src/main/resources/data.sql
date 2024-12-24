-- 상위 카테고리 삽입
INSERT INTO categories (category_id, parent_id, name) VALUES (1, NULL, 'Outer');
INSERT INTO categories (category_id, parent_id, name) VALUES (2, NULL, 'Top');
INSERT INTO categories (category_id, parent_id, name) VALUES (3, NULL, 'Bottom');
INSERT INTO categories (category_id, parent_id, name) VALUES (4, NULL, 'ACC');
INSERT INTO categories (category_id, parent_id, name) VALUES (5, NULL, '미분류');

-- 하위 카테고리 삽입
INSERT INTO categories (category_id, parent_id, name) VALUES (6, 1, '코트');
INSERT INTO categories (category_id, parent_id, name) VALUES (7, 1, '자켓');
INSERT INTO categories (category_id, parent_id, name) VALUES (8, 1, '가디건');

INSERT INTO categories (category_id, parent_id, name) VALUES (9, 2, '티셔츠');
INSERT INTO categories (category_id, parent_id, name) VALUES (10, 2, '맨투맨');

INSERT INTO categories (category_id, parent_id, name) VALUES (11, 3, '데님');
INSERT INTO categories (category_id, parent_id, name) VALUES (12, 3, '슬랙스');

INSERT INTO categories (category_id, parent_id, name) VALUES (13, 4, '신발');
