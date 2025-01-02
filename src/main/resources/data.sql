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


-- 코트 6
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (1, '트랜치 롱코트', 120000, '따뜻한 겨울용 롱코트 코트', 100, NOW(), NOW(), 6),
    (2, '오버핏 더블 코트', 198000, '따뜻한 겨울용 오버핏 더블 코트', 30, NOW(), NOW(), 6),
    (3, '센트 오버핏 더블 롱 트렌치 코트', 59900, '가을용 센트 오버핏 더블 롱 트렌치 코트', 50, NOW(), NOW(), 6),
    (4, '오버핏 롱코트', 57000, '멋있는 겨울용 오버핏 롱코트', 33, NOW(), NOW(), 6),
    (5, '오버핏 스테디 더플 코트', 262400, '겨울용 오버핏 스테디 더플 코트 (차콜)', 63, NOW(), NOW(), 6);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (1, 1, 'https://m.saeneul.co.kr/web/product/big/202212/87bc0d2345bd1aa4dfdf01be3d8fff0e.jpg', 'MAIN'),
                                                                              (2, 1, 'https://m.saeneul.co.kr/web/product/big/202212/87bc0d2345bd1aa4dfdf01be3d8fff0e.jpg', 'DESCRIPTION'),
                                                                              (3, 2, 'https://m.bylegacy.co.kr/web/product/big/202111/5437a58a81fe0e813a504011a00fd89e.jpg', 'MAIN'),
                                                                              (4, 2, 'https://m.bylegacy.co.kr/web/product/big/202111/5437a58a81fe0e813a504011a00fd89e.jpg', 'DESCRIPTION'),
                                                                              (5, 3, 'https://cafe24.poxo.com/ec01/evecompany0321/Kz2CAnT6o1ru4a+ciSQRaGxQqbHIFYw9wKOy9rjjkVoFVz+eK7kj5cak4uebDxtZXUnx5d1wZzg3A38wZjMIAA==/_/web/product/big/202310/31db088afc1b2d8b4b77ee66bf4edadb.png', 'MAIN'),
                                                                              (6, 3, 'https://cafe24.poxo.com/ec01/evecompany0321/Kz2CAnT6o1ru4a+ciSQRaGxQqbHIFYw9wKOy9rjjkVoFVz+eK7kj5cak4uebDxtZXUnx5d1wZzg3A38wZjMIAA==/_/web/product/big/202310/31db088afc1b2d8b4b77ee66bf4edadb.png', 'DESCRIPTION'),
                                                                              (7, 4, 'https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/vendor_inventory/5edd/06a7daf507dd2860c8f2587dc7e5db659186f9f1bb476f7e9c489e797d7b.jpg', 'MAIN'),
                                                                              (8, 4, 'https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/vendor_inventory/5edd/06a7daf507dd2860c8f2587dc7e5db659186f9f1bb476f7e9c489e797d7b.jpg', 'DESCRIPTION'),
                                                                              (9, 5, 'https://cafe24.poxo.com/ec01/svillage/EjglQcnyYl9oLKpqUS6wZkoYUGMHYECaO/k6nSbbnpdlkt8wLjwDqgoq5TLeq2/NgMDiZn303tNUnRexZVRbLA==/_/web/product/big/202401/dbc545e0d9912f2141c64694ed018404.jpg', 'MAIN'),
                                                                              (10, 5, 'https://cafe24.poxo.com/ec01/svillage/EjglQcnyYl9oLKpqUS6wZkoYUGMHYECaO/k6nSbbnpdlkt8wLjwDqgoq5TLeq2/NgMDiZn303tNUnRexZVRbLA==/_/web/product/big/202401/dbc545e0d9912f2141c64694ed018404.jpg', 'DESCRIPTION');

-- 자켓 7
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (6, '투포켓 면자켓', 47900, '코디하기 좋은 투포켓 면자', 50, NOW(), NOW(), 7),
    (7, '가죽 자켓', 390000, '남자 싱글 가죽 자켓', 40, NOW(), NOW(), 7),
    (8, '블레이저 자켓', 237300, '남성 테크 블레이저 자켓', 100, NOW(), NOW(), 7),
    (9, '모렐로 워크 자켓', 128800, '모렐로 워크 자켓 (베이지)', 33, NOW(), NOW(), 7),
    (10, '트렌디 바시티 자켓', 64900, '트렌디 바시티 자켓 - 올니드', 63, NOW(), NOW(), 7);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (11, 6, 'https://cafe24img.poxo.com/meetkmi0/web/product/big/202302/50b4d0de15cfbaea41f4e98db49cc191.jpg', 'MAIN'),
                                                                              (12, 6, 'https://cafe24img.poxo.com/meetkmi0/web/product/big/202302/50b4d0de15cfbaea41f4e98db49cc191.jpg', 'DESCRIPTION'),
                                                                              (13, 7, 'https://m.northbeach.co.kr/web/product/big/202206/c544fd27e0b77617a887a032db1c970b.jpg', 'MAIN'),
                                                                              (14, 7, 'https://m.northbeach.co.kr/web/product/big/202206/c544fd27e0b77617a887a032db1c970b.jpg', 'DESCRIPTION'),
                                                                              (15, 8, 'https://www.calvinklein.co.kr/dw/image/v2/BGLQ_PRD/on/demandware.static/-/Sites-ck-sea-master-catalog/default/dw9a4af6e8/images/CK/KR/C28_01_40QM514BAE_FL-TP-F1.jpg?sw=1024&sh=1348&q=90', 'MAIN'),
                                                                              (16, 8, 'https://www.calvinklein.co.kr/dw/image/v2/BGLQ_PRD/on/demandware.static/-/Sites-ck-sea-master-catalog/default/dw9a4af6e8/images/CK/KR/C28_01_40QM514BAE_FL-TP-F1.jpg?sw=1024&sh=1348&q=90', 'DESCRIPTION'),
                                                                              (17, 9, 'https://cafe24.poxo.com/ec01/svillage/EjglQcnyYl9oLKpqUS6wZkoYUGMHYECaO/k6nSbbnpdlkt8wLjwDqgoq5TLeq2/NgMDiZn303tNUnRexZVRbLA==/_/web/product/big/202409/01d0c94692923fa6ed6a539c4b48c1c0.jpg', 'MAIN'),
                                                                              (18, 9, 'https://cafe24.poxo.com/ec01/svillage/EjglQcnyYl9oLKpqUS6wZkoYUGMHYECaO/k6nSbbnpdlkt8wLjwDqgoq5TLeq2/NgMDiZn303tNUnRexZVRbLA==/_/web/product/big/202409/01d0c94692923fa6ed6a539c4b48c1c0.jpg', 'DESCRIPTION'),
                                                                              (19, 10, 'https://m.all-need.com/web/product/big/202308/5b2b776a03e35b9ed55bf6d51e58b4a3.jpg', 'MAIN'),
                                                                              (20, 10, 'https://m.all-need.com/web/product/big/202308/5b2b776a03e35b9ed55bf6d51e58b4a3.jpg', 'DESCRIPTION');

-- 가디건 8
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (11, '다카르 브이넥 가디건', 38900, '부드러운 터치감의 다카르 가디건', 70, NOW(), NOW(), 8),
    (12, '프롬 니트 가디건', 39000, '여름을 제외한 3계절 착용이 가능한 니트웨어', 40, NOW(), NOW(), 8),
    (13, '아테네 가디건', 10000, '여성 탑다운으로 뜨는 아란 가디건', 100, NOW(), NOW(), 8),
    (14, '라운드 가디건', 53510, '남자라운드가디건 남자크롭가디건', 33, NOW(), NOW(), 8),
    (15, '에펠 오버핏 가디건', 64900, '트렌디한 에펠 오버핏 가디건', 63, NOW(), NOW(), 8);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (21, 11, 'https://m.wooilsin.kr/web/product/big/202409/aa440c547be9e56c369426d15ece6f62.jpg', 'MAIN'),
                                                                              (22, 11, 'https://m.wooilsin.kr/web/product/big/202409/aa440c547be9e56c369426d15ece6f62.jpg', 'DESCRIPTION'),
                                                                              (23, 12, 'https://m.scenerity.co.kr/web/product/big/20200327/94edcae99664d3d71b83c55fe2140115.jpg', 'MAIN'),
                                                                              (24, 12, 'https://m.scenerity.co.kr/web/product/big/20200327/94edcae99664d3d71b83c55fe2140115.jpg', 'DESCRIPTION'),
                                                                              (25, 13, 'https://cdn.imweb.me/thumbnail/20240505/69632d951f8ad.jpeg', 'MAIN'),
                                                                              (26, 13, 'https://cdn.imweb.me/thumbnail/20240505/69632d951f8ad.jpeg', 'DESCRIPTION'),
                                                                              (27, 14, 'https://sitem.ssgcdn.com/85/42/21/item/1000563214285_i1_1200.jpg', 'MAIN'),
                                                                              (28, 14, 'https://sitem.ssgcdn.com/85/42/21/item/1000563214285_i1_1200.jpg', 'DESCRIPTION'),
                                                                              (29, 15, 'https://m.page27.co.kr/web/product/big/201909/b64b0e8432b6d4bd12da749dd405b31c.jpg', 'MAIN'),
                                                                              (30, 15, 'https://m.page27.co.kr/web/product/big/201909/b64b0e8432b6d4bd12da749dd405b31c.jpg', 'DESCRIPTION');

-- 티셔츠 9
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (16, '에센셜 티셔', 110000, '부드러운 에센셜 티셔', 70, NOW(), NOW(), 9),
    (17, '브루클린 프린팅 티셔츠', 22000, '부드러운 브루클린 프린팅 티셔츠', 40, NOW(), NOW(), 9),
    (18, '네오디 스몰 로고 반팔 티셔츠', 10000, '내셔널지오그래픽 N215UTS910 네오디 스몰 로고 반팔 티셔츠', 100, NOW(), NOW(), 9),
    (19, 'AGUCCIM 티셔츠 티', 14900, '특이한 재밌는 옷 AGUCCIM 티셔츠', 33, NOW(), NOW(), 9),
    (20, '스투시 티셔츠', 124704, '스투시 베이직 스투시 티셔츠 블랙 검정 반팔티', 63, NOW(), NOW(), 9);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (31, 16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHZ3Dd7OtKCDQG-Sh9L82_JTkH8zDhSNRdIQ&s', 'MAIN'),
                                                                              (32, 16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHZ3Dd7OtKCDQG-Sh9L82_JTkH8zDhSNRdIQ&s', 'DESCRIPTION'),
                                                                              (33, 17, 'https://nareum.co.kr/web/product/big/202206/37826b0d5d33d806626da852a70cd014.jpg', 'MAIN'),
                                                                              (34, 17, 'https://nareum.co.kr/web/product/big/202206/37826b0d5d33d806626da852a70cd014.jpg', 'DESCRIPTION'),
                                                                              (35, 18, 'https://m.naturestore.co.kr/data/goods/21/03/11/1000004600/1000004600_detail_052.jpg', 'MAIN'),
                                                                              (36, 18, 'https://m.naturestore.co.kr/data/goods/21/03/11/1000004600/1000004600_detail_052.jpg', 'DESCRIPTION'),
                                                                              (37, 19, 'https://godshop.co.kr/web/product/big/202201/8f55c019846a4a4856f6dd337fa6ca82.jpg', 'MAIN'),
                                                                              (38, 19, 'https://godshop.co.kr/web/product/big/202201/8f55c019846a4a4856f6dd337fa6ca82.jpg', 'DESCRIPTION'),
                                                                              (39, 20, 'https://sitem.ssgcdn.com/83/53/00/item/1000549005383_i1_1200.jpg', 'MAIN'),
                                                                              (40, 20, 'https://sitem.ssgcdn.com/83/53/00/item/1000549005383_i1_1200.jpg', 'DESCRIPTION');

-- 맨투맨 10
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (21, '크랙나염 맨투맨', 45800, 'MNMP 크랙나염 맨투맨', 70, NOW(), NOW(), 10),
    (22, '맨투맨 블랙', 22000, '기본 맨투밴 블랙', 40, NOW(), NOW(), 10),
    (23, '스토리 로고 맨투맨 블랙', 73000, '팔렛 스토리 로고 맨투맨', 100, NOW(), NOW(), 10),
    (24, '특양면 헤리 맨투맨', 21900, '손쉽게 만드는 특양면 헤리 맨투맨', 33, NOW(), NOW(), 10),
    (25, '오버핏 맨투맨[헤비웨이트]', 23100, '트렌디한 느낌의 오버핏 맨투맨', 63, NOW(), NOW(), 10);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (41, 21, 'https://cafe24.poxo.com/ec01/mnp7/HOvhRhvOk+Cp2KY4JuusAnwQW+ucEeAikzB6Rzosi0JVLzCJ3Sgz+ZyDCGmi4haKzUw8F3DoTk2b/DU3iwpUsA==/_/web/product/medium/202408/f6e18d10660335c604059be0d36b14bc.jpg', 'MAIN'),
                                                                              (42, 21, 'https://cafe24.poxo.com/ec01/mnp7/HOvhRhvOk+Cp2KY4JuusAnwQW+ucEeAikzB6Rzosi0JVLzCJ3Sgz+ZyDCGmi4haKzUw8F3DoTk2b/DU3iwpUsA==/_/web/product/medium/202408/f6e18d10660335c604059be0d36b14bc.jpg', 'DESCRIPTION'),
                                                                              (43, 22, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjEtRKOIGb0CSqt-lRfwjOczoJiIUNvVxLBQ&s', 'MAIN'),
                                                                              (44, 22, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjEtRKOIGb0CSqt-lRfwjOczoJiIUNvVxLBQ&s', 'DESCRIPTION'),
                                                                              (45, 23, 'https://cafe24.poxo.com/ec01/fallett20/qG7W5YG4u15UcFpJqE8Uf0tYrHh0naLBpjOcLT8FTYaTDuqopMqKRMzxaxE79JVclh+XQTQSznFoc3ZUpwHf1g==/_/web/product/big/202404/486abd03927d12f5f78a221f085c9827.jpg', 'MAIN'),
                                                                              (46, 23, 'https://cafe24.poxo.com/ec01/fallett20/qG7W5YG4u15UcFpJqE8Uf0tYrHh0naLBpjOcLT8FTYaTDuqopMqKRMzxaxE79JVclh+XQTQSznFoc3ZUpwHf1g==/_/web/product/big/202404/486abd03927d12f5f78a221f085c9827.jpg', 'DESCRIPTION'),
                                                                              (47, 24, 'https://s3.marpple.co/files/u_1364732/2022/9/original/b5a50a2a8a12a272890e605d8df1e631697734701.jpg', 'MAIN'),
                                                                              (48, 24, 'https://s3.marpple.co/files/u_1364732/2022/9/original/b5a50a2a8a12a272890e605d8df1e631697734701.jpg', 'DESCRIPTION'),
                                                                              (49, 25, 'https://m.byslim.com/web/product/big/202408/e00c4d9c10ec5b196133ace9cb47a805.jpg', 'MAIN'),
                                                                              (50, 25, 'https://m.byslim.com/web/product/big/202408/e00c4d9c10ec5b196133ace9cb47a805.jpg', 'DESCRIPTION');

-- 데님 11
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (26, '프리미엄 데님 아이스진', 219000, '편안한 착용감 프리미엄 데님 아이스진', 70, NOW(), NOW(), 11),
    (27, '생지 와이드 데님 팬츠', 37000, '생지 와이드핏 데님 팬츠', 40, NOW(), NOW(), 11),
    (28, '롱 데님 팬츠 와이드', 37500, '브라운 빈티지 워싱 여자 청바지 롱 데님 팬츠 와이드', 100, NOW(), NOW(), 11),
    (29, '데님 라인스톤 팬츠', 151200, '아디다스 데님 라인스톤 팬츠', 33, NOW(), NOW(), 11),
    (30, '빈티지 워싱 컷팅 와이드 데님', 23100, '빈티지 워싱 컷팅 와이드 데님 청 롱 루즈핏 팬츠 남자 코디', 63, NOW(), NOW(), 11);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (51, 26, 'https://fibreno.com/web/product/extra/small/202407/0c441e4d3ed96500537fc576b0e4379c.jpg', 'MAIN'),
                                                                              (52, 26, 'https://fibreno.com/web/product/extra/small/202407/0c441e4d3ed96500537fc576b0e4379c.jpg', 'DESCRIPTION'),
                                                                              (53, 27, 'https://nareum.co.kr/web/product/big/202101/09173b1b70f310f5d496ed1ad68c9f9a.jpg', 'MAIN'),
                                                                              (54, 27, 'https://nareum.co.kr/web/product/big/202101/09173b1b70f310f5d496ed1ad68c9f9a.jpg', 'DESCRIPTION'),
                                                                              (55, 28, 'https://contents.kyobobook.co.kr/sih/fit-in/400x0/gift/pdt/1806/S1717322870965.jpg', 'MAIN'),
                                                                              (56, 28, 'https://contents.kyobobook.co.kr/sih/fit-in/400x0/gift/pdt/1806/S1717322870965.jpg', 'DESCRIPTION'),
                                                                              (57, 29, 'https://assets.adidas.com/images/w_600,f_auto,q_auto/aabc00a38294430eb6b2798316d722b0_9366/Blue_JN3020_21_model.jpg', 'MAIN'),
                                                                              (58, 29, 'https://assets.adidas.com/images/w_600,f_auto,q_auto/aabc00a38294430eb6b2798316d722b0_9366/Blue_JN3020_21_model.jpg', 'DESCRIPTION'),
                                                                              (59, 30, 'https://image.brandi.co.kr/cproduct/BRANDI/2024/02/14/9f47ed26-493a-433e-9305-e957226f4007/SB000000000091938033_1707877232_image1_M.webp', 'MAIN'),
                                                                              (60, 30, 'https://image.brandi.co.kr/cproduct/BRANDI/2024/02/14/9f47ed26-493a-433e-9305-e957226f4007/SB000000000091938033_1707877232_image1_M.webp', 'DESCRIPTION');

-- 슬랙스 12
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (31, '링클프리 테이퍼드 슬랙스', 20900, '사계절 구김없는 링클프리 테이퍼드 슬랙스', 70, NOW(), NOW(), 12),
    (32, 'TR 세미 와이드핏 슬랙스', 32000, '편안한 TR 세미 와이드핏 슬랙스', 40, NOW(), NOW(), 12),
    (33, '벨티드 세미 와이드 슬랙스', 32000, '깔끔한 벨티드 세미 와이드 슬랙스', 100, NOW(), NOW(), 12),
    (34, '클리어 하프밴딩 와이드 슬랙스', 39900, '편안한 클리어 하프밴딩 와이드 슬랙스', 33, NOW(), NOW(), 12),
    (35, '카키브라운 슬랙스', 35800, '카키브라운 슬랙스 (슬림와이드)', 63, NOW(), NOW(), 12);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (61, 31, 'https://hander1212.openhost.cafe24.com/web/item/29479/29479_15.jpg?215477', 'MAIN'),
                                                                              (62, 31, 'https://hander1212.openhost.cafe24.com/web/item/29479/29479_15.jpg?215477', 'DESCRIPTION'),
                                                                              (63, 32, 'https://m.the-otherside.co.kr/web/product/big/202108/14defd023d2a13a0fd87226faf0d454b.jpg', 'MAIN'),
                                                                              (64, 32, 'https://m.the-otherside.co.kr/web/product/big/202108/14defd023d2a13a0fd87226faf0d454b.jpg', 'DESCRIPTION'),
                                                                              (65, 33, 'https://haokan.co.kr/web/product/big/202302/fae30f5aa0a00f1f5a4bf80642c6430e.jpg', 'MAIN'),
                                                                              (66, 33, 'https://haokan.co.kr/web/product/big/202302/fae30f5aa0a00f1f5a4bf80642c6430e.jpg', 'DESCRIPTION'),
                                                                              (67, 34, 'https://m.haloshop.kr/web/product/big/202302/114ce53154f6c354e1aed7fcb2629a3f.jpg', 'MAIN'),
                                                                              (68, 34, 'https://m.haloshop.kr/web/product/big/202302/114ce53154f6c354e1aed7fcb2629a3f.jpg', 'DESCRIPTION'),
                                                                              (69, 35, 'https://m.gentleseoul.com/web/product/big/202010/6892c16899b2e303cf0a7f3bb31d6a94.jpg', 'MAIN'),
                                                                              (70, 35, 'https://m.gentleseoul.com/web/product/big/202010/6892c16899b2e303cf0a7f3bb31d6a94.jpg', 'DESCRIPTION');

-- 신발 13
INSERT INTO products (product_id, name, price, description, stock_quantity, created_at, updated_at, category_id)
VALUES
    (36, '트리핀 베이직 스니커즈', 109000, '내셔널지오그래픽 트리핀 베이직 스니커즈 IVORY', 50, NOW(), NOW(), 13),
    (37, '캐주얼 스니커즈', 26123, '학생스니커즈 대학생 캠퍼스 패션 하이탑 운동화 키높이신발 캐주얼', 40, NOW(), NOW(), 13),
    (38, '퍼피렛 오프 화이트 패딩 신발', 142000, '아디다스 퍼피렛 오프 화이트 패딩 신발 GY1593', 100, NOW(), NOW(), 13);


INSERT INTO products_images (image_id, product_id, image_url, image_type) VALUES
                                                                              (71, 36, 'https://m.naturestore.co.kr/data/goods/21/03/09/1000004381/1000004381_detail_057.jpg', 'MAIN'),
                                                                              (72, 36, 'https://m.naturestore.co.kr/data/goods/21/03/09/1000004381/1000004381_detail_057.jpg', 'DESCRIPTION'),
                                                                              (73, 37, 'https://sitem.ssgcdn.com/09/97/17/item/1000442179709_i1_1200.jpg', 'MAIN'),
                                                                              (74, 37, 'https://sitem.ssgcdn.com/09/97/17/item/1000442179709_i1_1200.jpg', 'DESCRIPTION'),
                                                                              (75, 38, 'https://image-cdn.trenbe.com/productmain/1667136171922-987ea42c-31c2-4ab7-8ed4-200b791a11ab.jpeg', 'MAIN'),
                                                                              (76, 38, 'https://image-cdn.trenbe.com/productmain/1667136171922-987ea42c-31c2-4ab7-8ed4-200b791a11ab.jpeg', 'DESCRIPTION');