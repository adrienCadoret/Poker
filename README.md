# Poker
# Adrien Cadoret

## Fonctionnement du jeu 

Notons que le jeu est lancé en mode console. 
Aujourd'hui, étant donné que l'architecture client-serveur n'existe pas, tout le jeu est visible en clair. 
C'est pour cela qu'il est difficile de jouer car les joueurs voient le jeu de leurs adversaires.

###Lancement du programme
- Lancer le jeu en faisant un run de la classe Launcher

###Création de la partie
- Entrer une petite blinde (la grosse blinde sera automatiquement égale 2*Petite Blinde)
- Entrer le cash initial donné à chaque joueurs (doit être supérieur à la grosse blinde)
- Créer des joueurs (le nombre de joueurs doit être compris entre 2 et 10 joueurs)
  Taper le chaine de caractère "start" pour lancer le jeu
- Si le jeu peut être lancé, alors ce dernier est lancé

###Lancement de la partie
- Au lancement de la partie, le dealer (ou bouton) est choisi aléatoirement
- Tant qu'au moins deux joueurs ont de l'argent (des crédits), nous passons à un autre Round ce que j'appelle une succession d'étapes Préflop,
Flop, Turn et River avec un tour d'enchères à chaque phase. 

### ROUND

####Pré-flop
- Changement de dealer (seulement si nous sommes au premier Round/Tour)
- Paiement automatique des Blinds
- Tour d'enchères

####Flop, Turn et River
- Le premier à jouer est le joueur qui est le plus proche à gauche du dealer
- Tour d'enchères

#### Abattage des cartes 
- L'abbatage des cartes consiste à demander aux joueurs de montrer leurs cartes. 
  Aujourd'hui, comme les joueurs voient le jeu de tout le monde, l'abattage n'existe pas.
  
#### Désignation du/des gagnant(s) du Round et distribution du pot
- A la fin du Round, le ou les gagnants sont désignés
- La distribution du pot est effectuée

## Architecture du projet

#### package **actions**

Ce package référence toutes les actions possibles qu'un joueur peut réaliser lors d'un tour d'enchères.
Soient : Bet (miser), Check(checker), Raise(relancer), AllIn(tapis), Fold(se coucher), Call(suivre), SmallBlind (small Blind),
(Big Blind)

#### package **texasholdem**

-Ce package contient les classes élémentaires (Player, Card, Deck) qui représentent respectivement un joueur, une carte et paquet
de cartes. 

-Les énumérations SuitEnum, RankEnum et HandEnum contiennent respectivement les couleurs des cartes, les rangs des cartes
et les mains/combinaisons possibles.

-HandUtil propose des services permettant de connaitre la combinaison dans la main d'un joueur.

-RoundUtil permet de déterminer le ou les gagnants parmi une liste de joueurs et une table de cartes.

- GameRunner gère le déroulement du jeu

- Game contient les éléments principaux d'une partie (liste de joueurs, montant des blinds, etc.)

- Round représente, rappelons-le, un tour dans une partie. Nous appelons un tour la succession du Pré-Flop, Flop, Turn, River, 
Abattage des cartes et représentations des cartes.

Pourquoi ne pas gérer les Rounds dans Game ?

"Tout simplement pour sécuriser un peu plus l'application et plus particulièrement les joueurs. Au début d'un Round, on clône
la liste des joueurs du Game. Cette liste clônée ne sera utilisée qu'au sein même du Round. A la fin d'un Round (et lors d'un Flop),
la liste des joueurs du Game est synchronisée avec la liste qui a été clôné précédemment. 
De plus, de cette façon, l'ajout d'un joueur ou sa suppression sera bien plus facilement gérée."

#### package 'test'

Les tests unitaires des classes élémentaires Card, Player, Deck et Hand.
Dans la classe WinnerTest, c'est la méthode getWinner de la classe Round qui a été testée. 

## Avancées à réaliser

- Les tapis sont parfois mal gérés
- Mise en place de l'architecture Client/Serveur
- Développement de l'IHM
- Implémentation d'un module d'IA
