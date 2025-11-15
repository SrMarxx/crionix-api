-- V1__Criar_tabelas_iniciais.sql

-- Tabela para Roles (Permissões)
-- Corresponde a RoleEntity
CREATE TABLE tb_roles (
    role_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de Usuários
-- Corresponde a UserEntity e todas as suas subclasses
CREATE TABLE tb_users (
    user_id UUID PRIMARY KEY,
    name VARCHAR(255),
    cpf VARCHAR(255) UNIQUE,
    nascimento DATE,
    matricula VARCHAR(255) UNIQUE,
    email VARCHAR(255),
    password VARCHAR(255),
    must_change_password BOOLEAN NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    cargo VARCHAR(255) NOT NULL -- Coluna discriminadora para a herança
);

-- Tabela de Junção para o relacionamento Muitos-para-Muitos entre Usuários e Roles
CREATE TABLE tb_users_roles (
    user_id UUID NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES tb_users(user_id),
    FOREIGN KEY (role_id) REFERENCES tb_roles(role_id)
);

-- Tabela para controle sequencial de matrículas
-- Corresponde a GeradorMatriculaEntity
CREATE TABLE gerador_matricula (
    ano INTEGER PRIMARY KEY,
    ultimo_sequencial BIGINT NOT NULL DEFAULT 0
);