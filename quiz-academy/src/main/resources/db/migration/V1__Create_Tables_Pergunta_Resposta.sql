create table pergunta(
      id bigint not null auto_increment,
      descricao varchar(250) not null,
      nivel varchar(50),
      categoria varchar(50),
      autor varchar(50),
      data_hora_cadastro timestamp,
      primary key (id)
);

create table resposta(
      id bigint not null auto_increment,
      resposta varchar (250) not null,
      fonte varchar(70),
      data_hora_cadastro timestamp,
      pergunta_id bigint not null,
      primary key (id),
      foreign key (pergunta_id) references pergunta(id)
);

--INSERT INTO pergunta (id, descricao, nivel, categoria, autor, data_hora_cadastro) values (1, 'Quem descobriu o Brasil?', 'BASICO', 'GERAL', 'Desconhecido', CURRENT_TIMESTAMP);
--INSERT INTO pergunta (id, descricao, nivel, categoria, autor, data_hora_cadastro) values (2, 'Qual é a cor do cavalo branco de Napoleão?', 'BASICO', 'GERAL', 'Desconhecido', CURRENT_TIMESTAMP);
--INSERT INTO pergunta (id, descricao, nivel, categoria, autor, data_hora_cadastro) values (3, 'Em que ano foi declarado a independencia dos EUA?', 'AVANCADO', 'GERAL', 'Desconhecido', CURRENT_TIMESTAMP);



