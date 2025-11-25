CREATE SEQUENCE IF NOT EXISTS tb_maquinas_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS tb_sensores_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE gerador_matricula
(
    ano               INTEGER NOT NULL,
    ultimo_sequencial BIGINT,
    CONSTRAINT pk_gerador_matricula PRIMARY KEY (ano)
);

CREATE TABLE tb_maquinas
(
    maquina_id  BIGINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    tensao_padrao      DOUBLE PRECISION,
    tensao_variacao     DOUBLE PRECISION,
    temperatura_padrao DOUBLE PRECISION,
    temperatura_variacao       DOUBLE PRECISION,
    pressao_padrao     DOUBLE PRECISION,
    pressao_variacao       DOUBLE PRECISION,
    humidade_padrao    DOUBLE PRECISION,
    humidade_variacao       DOUBLE PRECISION,
    ativo           Boolean,
    setor           VARCHAR(10),
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
    type       VARCHAR(20),
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

ALTER TABLE tb_maquinas_sensores
    ADD CONSTRAINT fk_tbmaqsen_on_maquina_entity FOREIGN KEY (maquina_id) REFERENCES tb_maquinas (maquina_id);

ALTER TABLE tb_maquinas_sensores
    ADD CONSTRAINT fk_tbmaqsen_on_sensor_entity FOREIGN KEY (sensor_id) REFERENCES tb_sensores (sensor_id);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT fk_tbuserol_on_role_entity FOREIGN KEY (role_id) REFERENCES tb_roles (role_id);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT fk_tbuserol_on_user_entity FOREIGN KEY (user_id) REFERENCES tb_users (user_id);

CREATE TABLE tb_leituras (
    id BIGSERIAL PRIMARY KEY,
    valor DOUBLE PRECISION NOT NULL,
    sensor_id BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL,

    CONSTRAINT fk_leitura_sensor
        FOREIGN KEY (sensor_id)
        REFERENCES tb_sensores(sensor_id)
        ON DELETE CASCADE
);

-- Índices para otimizar consultas por sensor e timestamp
CREATE INDEX idx_sensor_timestamp ON tb_leituras(sensor_id, timestamp);
CREATE INDEX idx_timestamp ON tb_leituras(timestamp);

-- Comentários para documentação
COMMENT ON TABLE tb_leituras IS 'Armazena as leituras dos sensores';
COMMENT ON COLUMN tb_leituras.id IS 'Identificador único da leitura';
COMMENT ON COLUMN tb_leituras.valor IS 'Valor da leitura do sensor';
COMMENT ON COLUMN tb_leituras.sensor_id IS 'Referência ao sensor que gerou a leitura';
COMMENT ON COLUMN tb_leituras.timestamp IS 'Data e hora da leitura';