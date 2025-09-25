CREATE TABLE monitoramento (
    id SERIAL PRIMARY KEY,
    moto_id INT REFERENCES moto(id),
    data TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE manutencao (
    id SERIAL PRIMARY KEY,
    moto_id INT REFERENCES moto(id),
    descricao VARCHAR(255) NOT NULL,
    data TIMESTAMP NOT NULL
);
