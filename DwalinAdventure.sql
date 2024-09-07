create table CENA (
	ID INT primary key AUTO_INCREMENT,
	NOME VARCHAR (100),
	DESCRICAO TEXT 
);

create table OBJETO (
	ID INT primary key AUTO_INCREMENT,
	NOME VARCHAR (100),
	DESCRICAO TEXT
);

create table CENA_OBJETO (
	ID INT primary key AUTO_INCREMENT,
	ID_CENA INT,
	ID_OBJETO INT,
	USAR BOOLEAN default FALSE,
	foreign key (ID_CENA) references CENA (ID),
	foreign key (ID_OBJETO) references OBJETO (ID)
);

create table INVENTORY (
	ID INT primary key auto_increment,
	ID_JOGADOR INT,
	ID_OBJETO INT,
	foreign key (ID_OBJETO) references OBJETO(ID)
);

create table INTERACAO (
	ID INT primary key auto_increment,
	ID_CENA INT,
	ID_OBJETO INT,
	ACAO VARCHAR(100),
	RESULTADO_POSITIVO TEXT,
	RESULTADO_NEGATIVO TEXT,
	foreign key (ID_CENA) references CENA(ID),
	foreign key (ID_OBJETO) references OBJETO(ID)
);

/*insert into CENA VALUES (
	'1','ENTRANDO NA MONTANHA','DWALIN ENTRA NA MONTANHA CINZENTA'
); */

update CENA_OBJETO 
set 1 = '1','MONTANHA CINZENTA','DWALIN SE AVENTURA NA MONTANHA, DESFILADEIROS ÚMIDOS, PINHEIROS SOLITÁRIOS COBERTOS DE NEVE ADORNAM O AMBIENTE'
WHERE ID_CENA = 1 AND ID_OBJETO = 2;

update CENA set NOME = "Prólogo", descricao = "Dwalin’s Adventure - Desbrave a cidade perdida de Khaz Badûr como Dwalin o anão explorador, situada nas profundezas das Montanhas Cinzentas em sua perigosa jornada para recuperar sua herança de família, a picareta diamantada de Durin. Sozinho na escuridão prossiga com cautela pois não se sabe o que pode habitar a cidade perdida dos anões, utilize a astúcia e ambição de Dwalin e todas as ferramentas que puder encontrar para enfrentar os desafios à frente.
" where id = 1;



select * from CENA