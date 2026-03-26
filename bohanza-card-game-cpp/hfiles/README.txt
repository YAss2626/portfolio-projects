Etudiant 1 : Yacine Dasso 300277181
Etudiant 2 : Salma Sabbar 300322273
Etudiant 3 : Girum Worku 300305611

Organisation du projet 

le projet est une implémentation d'un jeu de cartes en C++. Le programme gère des joueurs, des chaînes de cartes, une pile de défausse, une zone d'échange, et un deck de cartes, avec la possibilité de sauvegarder et de charger une partie en cours.

Pour run le projet :
Executer tout les fichier cpp avec une commande ' g++ *.cpp -std=c++11 -o project ' et puis jouer sur le '.exe'



Structure du projet :
1. Main
   Fichier : Main.cpp/Main.h
   Description : Gère la logique principale du jeu, l'initialisation, les tours des joueurs et l'affichage.

2. Table :
   Fichier : Table.cpp/Table.h
   Description : Regroupe les joueurs, le deck, la pile de défausse et la zone d'échange, tout en permettant la sauvegarde et le rechargement.

3. Player :
   Fichier : Player.cpp/Player.h
   Description : Représente un joueur avec sa main et ses chaînes, en gérant les actions sur les cartes.

4. Hand : 
   Fichier : Hand.cpp/Hand.h
   Description : Gère les cartes en main d’un joueur, avec des opérations pour ajouter, retirer ou sauvegarder.

5. Card :
   Fichier : Card.cpp/Card.h
   Description : Définit les caractéristiques de base des cartes et leur affichage.

6. Deck :
   Fichier : Deck.cpp/Deck.h
   Description :  Représente le paquet de cartes et gère les tirages.

7. DiscardPile : 
   Fichier : DiscardPile.cpp/DiscardPile.h
   Description : Contient les cartes défaussées et offre des opérations pour les gérer.

8. TradeArea :
   Fichier : TradeArea.cpp/TradeArea.h
   Description : Gère les cartes en cours d’échange entre les joueurs ou la pile de défausse.

9. CardFactory : 
   Fichier : CardFactory.cpp/CardFactory.h
   Description : Génère les cartes et initialise un deck unique pour le jeu.

Tout les fichier .h sont dans '/hfiles'

