CREATE SEQUENCE IF NOT EXISTS tb_maquinas_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS tb_sensores_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE gerador_matricula
(
    ano               INTEGER NOT NULL,
    ultimo_sequencial BIGINT,
    CONSTRAINT pk_gerador_matricula PRIMARY KEY (ano)
);

CREATE TABLE tb_empresas
(
    id       UUID NOT NULL,
    nome     VARCHAR(255),
    email    VARCHAR(255),
    telefone VARCHAR(255),
    cnpj     VARCHAR(255),
    CONSTRAINT pk_tb_empresas PRIMARY KEY (id)
);

CREATE TABLE tb_maquinas
(
    maquina_id  BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    empresa_id  UUID,
    CONSTRAINT pk_tb_maquinas PRIMARY KEY (maquina_id)
);

CREATE TABLE tb_maquinas_sensores
(
    maquina_id BIGINT NOT NULL,
    sensor_id  BIGINT NOT NULL,
    CONSTRAINT pk_tb_maquinas_sensores PRIMARY KEY (maquina_id, sensor_id)
);

CREATE TABLE tb_roles
(
    role_id BIGINT NOT NULL,
    name    VARCHAR(255),
    CONSTRAINT pk_tb_roles PRIMARY KEY (role_id)
);

CREATE TABLE tb_sensores
(
    sensor_id   BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    types       SMALLINT[],  -- Troque de SMALLINT para SMALLINT[]
    CONSTRAINT pk_tb_sensores PRIMARY KEY (sensor_id)
);

CREATE TABLE tb_users
(
    user_id              UUID        NOT NULL,
    cargo                VARCHAR(31) NOT NULL,
    name                 VARCHAR(255),
    cpf                  VARCHAR(255),
    nascimento           date,
    matricula            VARCHAR(255),
    empresa_id           UUID,
    email                VARCHAR(255),
    password             VARCHAR(255),
    must_change_password BOOLEAN     NOT NULL,
    active               BOOLEAN     NOT NULL,
    CONSTRAINT pk_tb_users PRIMARY KEY (user_id)
);

CREATE TABLE tb_users_roles
(
    role_id BIGINT NOT NULL,
    user_id UUID   NOT NULL,
    CONSTRAINT pk_tb_users_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE tb_users
    ADD CONSTRAINT uc_tb_users_matricula UNIQUE (matricula);

ALTER TABLE tb_maquinas
    ADD CONSTRAINT FK_TB_MAQUINAS_ON_EMPRESA FOREIGN KEY (empresa_id) REFERENCES tb_empresas (id);

ALTER TABLE tb_users
    ADD CONSTRAINT FK_TB_USERS_ON_EMPRESA FOREIGN KEY (empresa_id) REFERENCES tb_empresas (id);

ALTER TABLE tb_maquinas_sensores
    ADD CONSTRAINT fk_tbmaqsen_on_maquina_entity FOREIGN KEY (maquina_id) REFERENCES tb_maquinas (maquina_id);

ALTER TABLE tb_maquinas_sensores
    ADD CONSTRAINT fk_tbmaqsen_on_sensor_entity FOREIGN KEY (sensor_id) REFERENCES tb_sensores (sensor_id);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT fk_tbuserol_on_role_entity FOREIGN KEY (role_id) REFERENCES tb_roles (role_id);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT fk_tbuserol_on_user_entity FOREIGN KEY (user_id) REFERENCES tb_users (user_id);