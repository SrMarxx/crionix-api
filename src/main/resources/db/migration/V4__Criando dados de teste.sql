-- ============================================================================
-- SCRIPT DE GERAÇÃO DE DADOS (SEM ALTERAR tb_roles)
-- ============================================================================

BEGIN;

-------------------------------------------------------------------------------
-- 1. GERADOR DE MATRÍCULA
-------------------------------------------------------------------------------
INSERT INTO gerador_matricula (ano, ultimo_sequencial)
VALUES (EXTRACT(YEAR FROM CURRENT_DATE), 1000)
ON CONFLICT DO NOTHING;

-- OBS: Pulamos a tb_roles pois você já possui os dados (1='CRIAR', 2='VISUALIZAR')

-------------------------------------------------------------------------------
-- 2. MÁQUINAS (20 MÁQUINAS)
-------------------------------------------------------------------------------
INSERT INTO tb_maquinas (
    maquina_id, name, description,
    tensao_padrao, tensao_variacao,
    temperatura_padrao, temperatura_variacao,
    pressao_padrao, pressao_variacao,
    humidade_padrao, humidade_variacao,
    ativo, setor
)
SELECT
    s,
    'Maquina Industrial ' || s,
    CASE WHEN random() > 0.5 THEN 'Freezer' ELSE 'Câmara Fria' END,
    220.0, 10.0,
    -20.0, 5.0,
    100.0, 20.0,
    90.0, 10.0,
    true,
    CASE
        WHEN s % 4 = 0 THEN 'ACOUGUE'
        WHEN s % 4 = 1 THEN 'PADARIA'
        WHEN s % 4 = 2 THEN 'FRUTAS'
        ELSE 'LATICINIOS'
        END
FROM generate_series(1, 20) as s
ON CONFLICT DO NOTHING;

-------------------------------------------------------------------------------
-- 3. SENSORES (50 SENSORES)
-------------------------------------------------------------------------------
INSERT INTO tb_sensores (sensor_id, name, description, type)
SELECT
    s,
    'Sensor ' || CASE
                     WHEN s % 4 = 0 THEN 'Temp'
                     WHEN s % 4 = 1 THEN 'Press'
                     WHEN s % 4 = 2 THEN 'Volt'
                     ELSE 'Umid'
        END || '-' || s,
    'Monitoramento de Variáveis',
    CASE
        WHEN s % 4 = 0 THEN 'TEMPERATURE'
        WHEN s % 4 = 1 THEN 'PRESSURE'
        WHEN s % 4 = 2 THEN 'VOLTAGE'
        ELSE 'HUMIDITY'
        END
FROM generate_series(1, 50) as s
ON CONFLICT DO NOTHING;

-------------------------------------------------------------------------------
-- 4. VINCULAR SENSORES ÀS MÁQUINAS
-------------------------------------------------------------------------------
INSERT INTO tb_maquinas_sensores (maquina_id, sensor_id)
SELECT
    m.maquina_id,
    s.sensor_id
FROM tb_maquinas m
         CROSS JOIN tb_sensores s
WHERE random() < 0.08
ON CONFLICT DO NOTHING;

-------------------------------------------------------------------------------
-- 5. USUÁRIOS (50 USUÁRIOS)
-------------------------------------------------------------------------------
INSERT INTO tb_users (
    user_id, cargo, name, cpf, nascimento, matricula, email, password, must_change_password, active
)
SELECT
    gen_random_uuid(),
    'COLABORADOR',
    'Usuario Teste ' || s,
    lpad(s::text, 11, '0'),
    '1985-01-01'::date + (random() * 10000)::int,
    'MAT-' || (20240000 + s),
    'usuario' || s || '@empresa.com',
    '$2a$10$DummyHashForTestOnly....................', -- Senha fake
    false,
    true
FROM generate_series(1, 50) as s
ON CONFLICT DO NOTHING;

-------------------------------------------------------------------------------
-- 6. VINCULAR USUÁRIOS A ROLES (Ajustado para ID 1 ou 2)
-------------------------------------------------------------------------------
-- Aqui garantimos que só vinculamos aos IDs 1 ou 2 que você já tem
INSERT INTO tb_users_roles (user_id, role_id)
SELECT
    u.user_id,
    (floor(random() * 2) + 1)::int -- Gera apenas 1 ou 2
FROM tb_users u
ON CONFLICT DO NOTHING;

-------------------------------------------------------------------------------
-- 7. MANUTENÇÕES (HISTÓRICO)
-------------------------------------------------------------------------------
INSERT INTO tb_manutencoes (
    maquina_id, user_id, data_criacao, data_limite, ativo,
    descricao, prioridade, tipo, conclusao, data_conclusao, relatorio
)
SELECT
    (SELECT maquina_id FROM tb_maquinas ORDER BY random() LIMIT 1),
    (SELECT user_id FROM tb_users ORDER BY random() LIMIT 1),
    NOW() - (random() * 30 || ' days')::interval,
    NOW() + (random() * 10 || ' days')::interval,
    CASE WHEN random() > 0.5 THEN true ELSE false END,
    'Manutenção Teste #' || s,
    CASE
        WHEN s % 4 = 0 THEN 'URGENTE'
        WHEN s % 4 = 1 THEN 'ALTA'
        WHEN s % 4 = 2 THEN 'MEDIA'
        ELSE 'BAIXA' END,
    CASE
        WHEN s % 3 = 0 THEN 'PREVENTIVA'
        WHEN s % 3 = 1 THEN 'PREDITIVA'
        ELSE 'CORRETIVA' END,
    CASE
        WHEN s % 4 = 0 THEN 'SUCESSO'
        WHEN s % 4 = 1 THEN 'FALHA'
        WHEN s % 4 = 2 THEN 'ADIAMENTO'
        ELSE NULL END,
    CASE WHEN s % 4 = 3 THEN NULL ELSE NOW() END,
    CASE WHEN s % 4 = 3 THEN NULL ELSE 'Relatório automático.' END
FROM generate_series(1, 100) as s;

-------------------------------------------------------------------------------
-- 8. LEITURAS (CARGA MASSIVA)
-------------------------------------------------------------------------------
-- Gera leituras a cada 5 min para todos os sensores nos últimos 90 dias
INSERT INTO tb_leituras (sensor_id, valor, timestamp)
SELECT
    s.sensor_id,
    (random() * 100)::numeric(10,2),
    series_timestamp
FROM tb_sensores s
         CROSS JOIN generate_series(
        NOW() - interval '90 days',
        NOW(),
        interval '5 minutes'
                    ) as series_timestamp;

-------------------------------------------------------------------------------
-- 9. REGISTROS (ESTATÍSTICAS)
-------------------------------------------------------------------------------
INSERT INTO tb_registros (
    data_registro, quantidade_maquinas, quantidade_pessoas,
    quantidade_manutencoes_abertas, quantidade_manutencoes_fechadas,
    quantidade_sucesso, quantidade_falha, quantidade_adiamento
)
VALUES (
           NOW(),
           (SELECT count(*) FROM tb_maquinas),
           (SELECT count(*) FROM tb_users),
           (SELECT count(*) FROM tb_manutencoes WHERE ativo = true),
           (SELECT count(*) FROM tb_manutencoes WHERE ativo = false),
           0, 0, 0
       );

COMMIT;

ANALYZE tb_leituras;