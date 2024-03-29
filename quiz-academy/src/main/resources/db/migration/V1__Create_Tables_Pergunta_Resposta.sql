create table pergunta(
      id serial primary key,
      descricao varchar(250) not null,
      nivel varchar(50),
      categoria varchar(50),
      autor varchar(50),
      data_hora_cadastro timestamp not null DEFAULT NOW()
);

create table resposta(
      id serial primary key,
      resposta varchar (250) not null,
      fonte varchar(70),
      data_hora_cadastro timestamp not null DEFAULT NOW(),
      pergunta_id bigint not null,
      foreign key (pergunta_id) references pergunta(id)
);
-- SCRIPT H2
--create table pergunta(
--      id bigint not null auto_increment,
--      descricao varchar(250) not null,
--      nivel varchar(50),
--      categoria varchar(50),
--      autor varchar(50),
--      data_hora_cadastro timestamp,
--      primary key (id)
--);

--create table resposta(
--      id bigint not null auto_increment,
--      resposta varchar (250) not null,
--      fonte varchar(70),
--      data_hora_cadastro timestamp,
--      pergunta_id bigint not null,
--      primary key (id),
--      foreign key (pergunta_id) references pergunta(id)
--);



