-- Inserção de operações na tabela Operation
INSERT INTO Operation (type, cost) VALUES ('ADDITION', 10.0 );
INSERT INTO Operation (type, cost) VALUES ('SUBTRACTION', 11.0 );
INSERT INTO Operation (type, cost) VALUES ('MULTIPLICATION', 12.0 );
INSERT INTO Operation (type, cost) VALUES ('DIVISION', 13.0 );
INSERT INTO Operation (type, cost) VALUES ('SQUARE_ROOT', 14.0 );
INSERT INTO Operation (type, cost) VALUES ('RANDOM_STRING', 15.0 );

ALTER TABLE PLACE ALTER COLUMN ID RESTART WITH 7;


