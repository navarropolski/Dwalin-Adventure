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

ALTER TABLE actions CHANGE is_sucess is_success BOOLEAN;

create table save (
	id int auto_increment primary key,
	scene_id int not null,
	foreign key (scene_id) references scene(id)
);

create table inventory (
	id int auto_increment primary key,
	item_id int not null,
	scene_id int not null,
	used_in_scene_id int,
	is_used boolean default false,
	foreign key (item_id) references itens(id),
	foreign key (scene_id) references scene(id),
	foreign key (used_in_scene_id) references scene(id)
);

alter table itens
	add column is_consumable boolean default false;

alter table actions
	add column requires_item boolean default false,
	add column required_item_id int,
	add foreign key (required_item_id) references itens(id);

INSERT INTO inventory (item_id, scene_id) 
VALUES ((SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro'), (SELECT id FROM scene WHERE name = 'Entrada de Khaz Badûr'));

INSERT INTO actions (item_id, scene_id, is_success, message, nextScene_id, requires_item, required_item_id) VALUES
((SELECT id FROM itens WHERE name = 'Machado de Duas Mãos de Karak'), (SELECT id FROM scene WHERE name = 'A Caverna dos Orcs'), TRUE, 'Dwalin usa o Machado de Karak e derruba o Orc com um golpe certeiro.', NULL, TRUE, (SELECT id FROM itens WHERE name = 'Machado de Duas Mãos de Karak')),
((SELECT id FROM itens WHERE name = 'Saco de Moedas de Ouro'), (SELECT id FROM scene WHERE name = 'O Suborno dos Goblins'), TRUE, 'Dwalin usa o Saco de Moedas de Ouro para subornar os goblins.', (SELECT id FROM scene WHERE name = 'A Câmara de Durin'), TRUE, (SELECT id FROM itens WHERE name = 'Saco de Moedas de Ouro')),
((SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro'), (SELECT id FROM scene WHERE name = 'A Câmara de Durin'), TRUE, 'Dwalin usa a Chave Rúnica de Ferro e abre o cofre.', NULL, TRUE, (SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro'));

UPDATE itens SET is_consumable = TRUE WHERE name = 'Saco de Moedas de Ouro';

UPDATE inventory SET is_used = TRUE WHERE item_id = (SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro') AND scene_id = (SELECT id FROM scene WHERE name = 'A Câmara de Durin');

INSERT INTO scene (name, description) VALUES
('Prólogo', 'Dwalin’s Adventure - Desbrave a cidade perdida de Khaz Badûr como Dwalin o anão explorador, situada nas profundezas das Montanhas Cinzentas em sua perigosa jornada para recuperar sua herança de família, a picareta diamantada de Durin. Sozinho na escuridão prossiga com cautela pois não se sabe o que pode habitar a cidade perdida dos anões. Utilize a astúcia e ambição de Dwalin e todas as ferramentas que puder encontrar para enfrentar os desafios à frente.'),
('Entrada de Khaz Badûr', 'Dwalin está na boca de uma caverna colossal. As montanhas ao redor são inóspitas, com ventos cortantes que ecoam pelos corredores naturais, a entrada da cidade perdida está coberta de musgo e detritos. Ele acende uma tocha e nota uma inscrição nas paredes: "Aquele que entrar, que não volte de MÃOS vazias". À sua esquerda há uma curiosa estátua de um anão empunhando um machado em uma de suas mãos e a outra parece estar oferecendo algo, você chega mais perto para examinar e encontra uma CHAVE rúnica de ferro sob a mão do gentil anão de pedra.'),
('O Salão dos Ecos', 'Dwalin avança pelo salão principal de Khaz Badûr, um vasto espaço cujo teto se perde nas sombras. O som de seus passos ecoa, parecendo haver outros caminhando ao seu lado. Uma estátua quebrada de Durin segura o que resta de um machado. Entre as ruínas você pode observar uma espécie de biblioteca contendo inúmeros livros, mapas e pergaminhos, a maioria deles parece estar em péssimo estado e alguns foram queimados. Ao vasculhar uma estante no canto, Dwalin percebe um LIVRO grande que parece estar em bom estado.'),
('A Caverna dos Orcs', 'Dwalin desce mais fundo nas entranhas de Khaz Badûr e encontra uma câmara mal iluminada por tochas gastas. O ar está carregado com o odor de carne em decomposição. Ele vê três orcs grandes patrulhando. Dwalin sabe que não pode enfrentá-los diretamente já que não está bem equipado, e então decide se esgueirar pela câmara e percebe alguns corpos em uma cela e uma pilha de pertences espalhados e decide investigar. Em meio a pertences pessoais como roupas e acessórios de uso comum, Dwalin encontra um corpo segurando um MACHADO de duas mãos. Ao se aproximar do que parece ter sido um anão em vida, ele percebe uma mochila nas costas do desafortunado aventureiro e, ao abrir, encontra um saco de MOEDAS de ouro.'),
('O Suborno dos Goblins', 'Após derrotar os orcs, Dwalin prossegue e adentra um túnel apertado. O som de passos pequenos e rápidos ecoa ao seu redor. De repente, ele é cercado por um grupo de goblins, que surgem das sombras com suas lâminas enferrujadas e olhos brilhando de malícia. São muitos, e embora fracos individualmente, em grupo eles são letais. Dwalin se lembra do peso que está carregando em sua mochila e que os olhos dos goblins não brilham somente por malícia... Os goblins são gananciosos, e ele decide tentar um suborno para evitar uma luta desnecessária.'),
('A Câmara de Durin', 'Finalmente, Dwalin encontra a câmara real, o coração de Khaz Badûr. As portas de pedra gigantescas estão gravadas com a história de Durin e sua linhagem. Ao entrar, a sala se ilumina magicamente, revelando um trono de ouro e um cofre no centro. Dwalin sabe que sua herança está próxima, mas algo vigia nas sombras. Um rugido profundo reverbera pelas paredes, alertando-o para a presença de uma criatura ancestral que protege o cofre.');

INSERT INTO itens (name, description, scene_id) VALUES
('Chave Rúnica de Ferro', 'Uma chave antiga com inscrições rúnicas que brilham ao contato com a luz.', 2),
('Livro Grande', 'Um livro grande em bom estado, encontrado em uma estante em meio aos livros queimados.', 3),
('Machado de Duas Mãos de Karak', 'Um machado antigo e poderoso, encontrado num corpo de um desafortunado anão.', 4),
('Saco de Moedas de Ouro', 'Moedas de ouro que atraem olhos gananciosos.', 4);

INSERT INTO actions (item_id, scene_id, is_success, message, nextScene_id, requires_item, required_item_id) VALUES
((SELECT id FROM itens WHERE name = 'Orc'), (SELECT id FROM scene WHERE name = 'A Caverna dos Orcs'), TRUE, 'Dwalin usa o Machado de Karak e derruba o Orc com um golpe certeiro.', NULL, TRUE, (SELECT id FROM itens WHERE name = 'Machado de Duas Mãos de Karak')),
((SELECT id FROM itens WHERE name = 'Goblin'), (SELECT id FROM scene WHERE name = 'O Suborno dos Goblins'), TRUE, 'Dwalin usa o Saco de Moedas para subornar os goblins.', (SELECT id FROM scene WHERE name = 'A Câmara de Durin'), TRUE, (SELECT id FROM itens WHERE name = 'Saco de Moedas de Ouro')),
((SELECT id FROM itens WHERE name = 'Cofre'), (SELECT id FROM scene WHERE name = 'A Câmara de Durin'), TRUE, 'Dwalin usa a Chave Rúnica de Ferro e abre o cofre, reivindicando sua herança.', NULL, TRUE, (SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro'));


INSERT INTO actions (item_id, scene_id, is_success, message, nextScene_id) VALUES
(NULL, 1, TRUE, 'Você decide continuar sua jornada e avança para a próxima cena.', (SELECT id FROM scene WHERE name = 'Entrada de Khaz Badûr')),

((SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro'), (SELECT id FROM scene WHERE name = 'Entrada de Khaz Badûr'), TRUE, 'Dwalin encontra a Chave Rúnica de Ferro na estátua e prossegue para dentro da cidade, uma longa descida pelas escadarias o aguarda.', (SELECT id FROM scene WHERE name = 'O Salão dos Ecos')),

((SELECT id FROM itens WHERE name = 'Livro Grande'), (SELECT id FROM scene WHERE name = 'O Salão dos Ecos'), TRUE, 'Dwalin interage com o Livro Grande e descobre uma nova passagem rumo ao centro da cidade.', (SELECT id FROM scene WHERE name = 'A Caverna dos Orcs')),

((SELECT id FROM itens WHERE name = 'Machado de Duas Mãos de Karak'), (SELECT id FROM scene WHERE name = 'A Caverna dos Orcs'), TRUE, 'Dwalin encontra o Machado de Duas Mãos e um Saco de Moedas de Ouro no corpo do desafortunado anão. Dwalin empunha o machado de duas mãos e avança em direção aos orcs, num avanço de fúria, sua pequena estatura é compensada pela sua força enânica e os longos dias minerando mithril também vieram a calhar. Com golpes certeiros e ferozes Dwalin derrota os orcs. Enquanto recupera seu fôlego ele diz a si mesmo: "Essa foi por você aventureiro, seja lá quem você tenha sido". Após recuperar as forças Dwalin segue para o centro da cidade que parece muito próximo.', NULL),

((SELECT id FROM itens WHERE name = 'Saco de Moedas de Ouro'), (SELECT id FROM scene WHERE name = 'A Caverna dos Orcs'), TRUE, 'Dwalin coleta as Moedas de Ouro.', NULL),

((SELECT id FROM itens WHERE name = 'Saco de Moedas de Ouro'), (SELECT id FROM scene WHERE name = 'O Suborno dos Goblins'), TRUE, 'Dwalin usa o Saco de Moedas de Ouro e tenta subornar os goblins, as criaturas verdes brilham os olhos ao ver as moedas e rapidamente se esquecem da presença de Dwalin e começam a discutir sobre quem fica com quanto e uma briga generalizada começa, vendo uma oportunidade você se esgueira e sai de fininho meio a confusão.', (SELECT id FROM scene WHERE name = 'A Câmara de Durin')),

((SELECT id FROM itens WHERE name = 'Chave Rúnica de Ferro'), (SELECT id FROM scene WHERE name = 'A Câmara de Durin'), TRUE, 'Dwalin rapidamente usa a Chave Rúnica de Ferro para abrir o cofre e recuperar sua herança. Os rugidos e estrondos do passo da criatura se aproximam, Dwalin pega a picareta diamantada de Durin, coloca em sua mochila e corre o máximo que suas pernas curtas conseguem em direção a entrada da cidade. Dwalin avança pelo corredor estreito, as sombras das estátuas antigas parecem ganhar vida, e o som dos seus próprios passos é abafado pelo rugido crescente. Ele passa por passagens secretas que ele havia descoberto anteriormente, utilizando a sua astúcia para navegar pelos caminhos tortuosos da cidade perdida. Após enfrentar os desafios, Dwalin emerge da cidade perdida, a entrada da caverna está à vista. Ele corre para fora, sentindo o ar fresco das Montanhas Cinzentas. A cidade atrás dele se torna um borrão de sombras e pedras, enquanto a criatura ancestral, ainda enfurecida, permanece na escuridão, impotente para seguir Dwalin para fora.
Ele finalmente chega ao sopé das montanhas, exausto mas triunfante. O sol começa a nascer, iluminando o caminho de volta para sua terra natal. Com a picareta diamantada de Durin segura em sua mochila e a cidade perdida atrás dele, Dwalin sorri, sabendo que sua jornada épica chegou ao fim.!', NULL);