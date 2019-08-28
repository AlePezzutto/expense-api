CREATE TABLE pessoa (
    pessoa_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(50) NOT NULL,
    logradouro  VARCHAR(100) NULL,
    numero      VARCHAR(10)  NULL,
    complemento VARCHAR(20) NULL,
    bairro      VARCHAR(50) NULL,
    cidade      VARCHAR(30) NULL,
    estado      VARCHAR(2)  NULL,
    cep         VARCHAR(9)  NULL,
    ativo       BOOLEAN NOT NULL
);
INSERT INTO pessoa (
    nome,
    logradouro,
    numero,
    complemento,
    bairro,
    cidade,
    estado,
    cep,
    ativo
)
VALUES (
    'Jimmy James',
    'Rua do Osso',
    '123',
    'Toca 2',
    'Vila Canina',
    'Osasco',
    'SP',
    '06000-111',
    true
);

