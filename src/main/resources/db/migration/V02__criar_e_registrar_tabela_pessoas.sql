CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(150) NOT NULL,
    ativo boolean,
    logradouro VARCHAR(100),
    numero VARCHAR(5),
    complemento VARCHAR(100),
    bairro VARCHAR(50),
    cep VARCHAR(8),
    cidade VARCHAR(50),
    estado VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values 
('Jorge Eduardo', true, 'Rua Gerson de Barros Pinangé', '205', 'Próx a Subway do Arruda', 'Ponto de Parada', '52041370', 'Recife', 'PE'),
('Marina Laurênio', true, 'Rua Costa Pinto','285', 'Próx a Coca-Cola', 'Hipodrómo', '52041605', 'Recife', 'PE'),
('João Carlos', true, null, null, null, null, null, null, null),
('João Pedro', true, null, null, null, null, null, null, null),
('Jane Maria', true, null, null, null, null, null, null, null),
('João Primo', true, null, null, null, null, null, null, null),
('Elizabete Patricio', true, null, null, null, null, null, null, null),
('Isabella Primo', true, null, null, null, null, null, null, null),
('Wellington Fabiano', true, null, null, null, null, null, null, null),
('João da Costa', true, null, null, null, null, null, null, null),
('Cleiton Martins', true, null, null, null, null, null, null, null);