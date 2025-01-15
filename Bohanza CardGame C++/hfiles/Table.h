#ifndef TABLE_H
#define TABLE_H

#include "Player.h"
#include "Deck.h"
#include "DiscardPile.h"
#include "TradeArea.h"
#include "CardFactory.h"
 
class Table {
    /*Représente une table de jeu, contenant les joueurs, le deck, la pile de défausse, la zone de commerce, 
    et d'autres éléments nécessaires pour gérer une partie.*/

    Player* player1;          
    Player* player2;          
    int activePlayerId;       
    DiscardPile* discardPile; 
    TradeArea* tradeArea;     
    Deck* deck;           
    CardFactory* cardFactory;

public:
    
 
    Table(Player& p_one, Player& p_two, DiscardPile& d_pile, TradeArea& tr_area, Deck& dck, CardFactory& c_factory) {
        /*
      Initialise la table avec les joueurs, le deck, la pile de défausse, la zone de commerce,
      et l'usine de cartes.
      */
        player1 = &p_one;
        player2 = &p_two;
        discardPile = &d_pile;
        tradeArea = &tr_area;
        deck = &dck;
        cardFactory = &c_factory;
    }

    
     
    ~Table() {
        // Libère la mémoire associée aux différents composants de la table.

        delete player1;
        delete player2;
        delete discardPile;
        delete tradeArea;
        delete deck;
        delete cardFactory;
    }

    
    bool win(std::string& winner); //  Vérifie si un joueur a gagné la partie.



    
    void printHand(bool isPlayerOne); //Affiche la main d'un joueur.

    friend std::ostream& operator<<(std::ostream& os, const Table& table); //Surcharge de l'opérateur d'affichage pour afficher l'état de la table.

    
    void saveTable(); //Sauvegarde l'état actuel de la table par exemple dans un fichier.

   
    void reloadPlayer(int playerId); //Recharge l'état d'un joueur à partir d'une sauvegarde.

   
    void reloadDeck(); //Recharge le deck à partir d'une sauvegarde.

   
    void reloadDiscardPile(); // Recharge la pile de défausse à partir d'une sauvegarde.


    void reloadTradeArea(); //Recharge la zone de commerce à partir d'une sauvegarde.

   
    Deck* getDeck(); // Accède au deck principal.

    
    Player* getPlayer(int playerId); // Accède à un joueur spécifique.

 
    DiscardPile* getDiscardPile(); // Accède à la pile de défausse.

  
    TradeArea* getTradeArea(); //Accède à la zone de commerce.
};

#endif 
