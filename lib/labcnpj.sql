SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS labcnpj;

SET FOREIGN_KEY_CHECKS = 1;

CREATE DATABASE labcnpj
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE labcnpj;

-- =========================================
-- TABELA usuario
-- =========================================

CREATE TABLE usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(200),
    cpf VARCHAR(11),
    email VARCHAR(50),
    senha VARCHAR(255),
    data_criacao DATETIME,
    ativo TINYINT(1),

    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuario_email (email)
) ENGINE=InnoDB;

-- =========================================
-- TABELA empresa
-- =========================================

CREATE TABLE empresa (
    id_empresa INT NOT NULL AUTO_INCREMENT,
    cnpj VARCHAR(14),
    nome VARCHAR(200),
    tipo_empresa VARCHAR(200),
    municipio VARCHAR(200),
    data_abertura DATE,
    data_encerramento DATE,
    situacao VARCHAR(50),

    PRIMARY KEY (id_empresa)
) ENGINE=InnoDB;

-- =========================================
-- TABELA categoria
-- =========================================

CREATE TABLE categoria (
    id_categoria INT NOT NULL AUTO_INCREMENT,
    id_usuario INT,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    data_criacao DATETIME,

    PRIMARY KEY (id_categoria)
) ENGINE=InnoDB;

-- =========================================
-- TABELA consulta
-- =========================================

CREATE TABLE consulta (
    id_consulta INT NOT NULL AUTO_INCREMENT,
    id_usuario INT,
    id_empresa INT,
    data_hora DATETIME,
    sucesso TINYINT(1),
    msg_erro TEXT,
    tempo_resposta_ms INT,

    PRIMARY KEY (id_consulta)
) ENGINE=InnoDB;

-- =========================================
-- TABELA favorito
-- =========================================

CREATE TABLE favorito (
    id_favorito INT NOT NULL AUTO_INCREMENT,
    id_usuario INT,
    id_empresa INT,
    id_categoria INT,
    nome_favorito VARCHAR(150),
    data_criacao DATETIME,

    PRIMARY KEY (id_favorito)
) ENGINE=InnoDB;

-- =========================================
-- FOREIGN KEYS
-- =========================================

ALTER TABLE categoria
ADD CONSTRAINT fk_categoria_usuario
FOREIGN KEY (id_usuario)
REFERENCES usuario(id_usuario);

ALTER TABLE consulta
ADD CONSTRAINT fk_consulta_usuario
FOREIGN KEY (id_usuario)
REFERENCES usuario(id_usuario);

ALTER TABLE consulta
ADD CONSTRAINT fk_consulta_empresa
FOREIGN KEY (id_empresa)
REFERENCES empresa(id_empresa);

ALTER TABLE favorito
ADD CONSTRAINT fk_favorito_usuario
FOREIGN KEY (id_usuario)
REFERENCES usuario(id_usuario);

ALTER TABLE favorito
ADD CONSTRAINT fk_favorito_empresa
FOREIGN KEY (id_empresa)
REFERENCES empresa(id_empresa);

ALTER TABLE favorito
ADD CONSTRAINT fk_favorito_categoria
FOREIGN KEY (id_categoria)
REFERENCES categoria(id_categoria);