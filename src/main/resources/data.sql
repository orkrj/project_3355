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

-- 코트 상품 임시값 삽입
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (1, '코트 A', 120000, '따뜻한 겨울용 코트 A', 100, NOW(), NOW(), 6),
    (2, '코트 B', 150000, '스타일리시한 코트 B', 50, NOW(), NOW(), 6),
    (3, '코트 C', 100000, '캐주얼한 코트 C', 30, NOW(), NOW(), 6),
    (4, '코트 D', 170000, '프리미엄 코트 D', 20, NOW(), NOW(), 6),
    (5, '코트 E', 130000, '일상적인 코트 E', 10, NOW(), NOW(), 6);