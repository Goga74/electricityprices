CREATE TABLE price_data (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            date DATE NOT NULL UNIQUE,
                            json_data JSON NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
