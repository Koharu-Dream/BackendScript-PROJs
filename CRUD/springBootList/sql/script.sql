-- Criação do Schema (Banco de Dados)
CREATE DATABASE IF NOT EXISTS `sales`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `sales`;

-- Criação da Tabela product
CREATE TABLE IF NOT EXISTS `product` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `nome` VARCHAR(60) NOT NULL,
    `marca` VARCHAR(45) NOT NULL,
    `pais_fabricacao` VARCHAR(60) NOT NULL,
    `preco` DECIMAL(10,2) NOT NULL DEFAULT '0.00',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- (Opcional) Dados para teste inicial
INSERT INTO `product` (`nome`, `marca`, `pais_fabricacao`, `preco`)
VALUES
('Teclado Mecânico RGB', 'Redragon', 'China', 249.90),
('Monitor UltraWide 29"', 'LG', 'Brasil', 1150.00),
('Mouse Sem Fio MX Master 3', 'Logitech', 'México', 489.00);
