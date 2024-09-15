/* create database dwalinadventure */

create table scene(
	id int auto_increment primary key,
	name varchar(100) not null,
	description text not null
);

create table itens(
	id int auto_increment primary key,
	name varchar(100) not null,
	description text not null,
	scene_id int,
	positiveResult_id int,
	negativeResult_id int,
	is_in_inventory boolean default false,
	foreign key (scene_id) references scene(id),
	foreign key (positiveResult_id) references scene(id),
	foreign key (negativeResult_id) references scene(id)
);

alter table itens
add nextScene_id int;


create table actions(
	id int auto_increment primary key,
	item_id int,
	scene_id int,
	is_sucess boolean,
	message text not null,
	nextScene_id int,
	foreign key(item_id) references itens(id),
	foreign key(scene_id) references scene(id),
	foreign key(nextScene_id) references scene(id)
); 

INSERT INTO scene (name, description) VALUES
('Prólogo', 'Dwalin’s Adventure - Desbrave a cidade perdida de Khaz Badûr como Dwalin o anão explorador, situada nas profundezas das Montanhas Cinzentas em sua perigosa jornada para recuperar sua herança de família, a picareta diamantada de Durin. Sozinho na escuridão prossiga com cautela pois não se sabe o que pode habitar a cidade perdida dos anões. Utilize a astúcia e ambição de Dwalin e todas as ferramentas que puder encontrar para enfrentar os desafios à frente.'),
('Entrada de Khaz Badûr', 'Dwalin está na boca de uma caverna colossal. As montanhas ao redor são inóspitas, com ventos cortantes que ecoam pelos corredores naturais, a entrada da cidade perdida está coberta de musgo e detritos. Ele acende uma tocha e nota uma inscrição nas paredes: "Aquele que entrar, que não volte de MÃOS vazias". À sua esquerda há uma curiosa estátua de um anão empunhando um machado em uma de suas mãos e a outra parece estar oferecendo algo, você chega mais perto para examinar e encontra uma CHAVE rúnica de ferro sob a mão do gentil anão de pedra.'),
('O Salão dos Ecos', 'Dwalin avança pelo salão principal de Khaz Badûr, um vasto espaço cujo teto se perde nas sombras. O som de seus passos ecoa, parecendo haver outros caminhando ao seu lado. Uma estátua quebrada de Durin segura o que resta de um machado. Entre as ruínas você pode observar uma espécie de biblioteca contendo inúmeros livros, mapas e pergaminhos, a maioria deles parece estar em péssimo estado e alguns foram queimados. Ao vasculhar uma estante no canto, Dwalin percebe um LIVRO grande que parece estar em bom estado.'),
('A Caverna dos Orcs', 'Dwalin desce mais fundo nas entranhas de Khaz Badûr e encontra uma câmara mal iluminada por tochas gastas. O ar está carregado com o odor de carne em decomposição. Ele vê três orcs grandes patrulhando. Dwalin sabe que não pode enfrentá-los diretamente já que não está bem equipado, e então decide se esgueirar pela câmara e percebe alguns corpos em uma cela e uma pilha de pertences espalhados e decide investigar. Em meio a pertences pessoais como roupas e acessórios de uso comum, Dwalin encontra um corpo segurando um MACHADO de duas mãos. Ao se aproximar do que parece ter sido um anão em vida, ele percebe uma mochila nas costas do desafortunado aventureiro e, ao abrir, encontra um saco de MOEDAS de ouro.'),
('O Suborno dos Goblins', 'Após derrotar os orcs, Dwalin prossegue e adentra um túnel apertado. O som de passos pequenos e rápidos ecoa ao seu redor. De repente, ele é cercado por um grupo de goblins, que surgem das sombras com suas lâminas enferrujadas e olhos brilhando de malícia. São muitos, e embora fracos individualmente, em grupo eles são letais. Dwalin se lembra do peso que está carregando em sua mochila e que os olhos dos goblins não brilham somente por malícia... Os goblins são gananciosos, e ele decide tentar um suborno para evitar uma luta desnecessária.'),
('A Câmara de Durin', 'Finalmente, Dwalin encontra a câmara real, o coração de Khaz Badûr. As portas de pedra gigantescas estão gravadas com a história de Durin e sua linhagem. Ao entrar, a sala se ilumina magicamente, revelando um trono de ouro e um cofre no centro. Dwalin sabe que sua herança está próxima, mas algo vigia nas sombras. Um rugido profundo reverbera pelas paredes, alertando-o para a presença de uma criatura ancestral que protege o cofre.');


