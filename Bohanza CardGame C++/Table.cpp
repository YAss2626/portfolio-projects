#include "hfiles/Table.h"

 
bool Table::win(std::string& pName){ //Vérifie si un joueur a gagné la partie. Renvoie true si un joueur a gagné, et le nom du gagnant est retourné par référence.

    bool win = false;
    if(deck->size() == 0){ 
       if(player1->getNumCoins() > player2->getNumCoins()){ 
           pName = player1->getName(); 
       }
       else if(player1->getNumCoins() < player2->getNumCoins()){ // Compare les pièces des joueurs.
           pName = player2->getName(); // Le joueur 2 gagne.
       }
       else{ // En cas d'égalité.
           pName = "Equality"; 
       }
       win = true; // La partie est terminée.
    }
    return win;
}

// Affiche la main du joueur actif : soit la carte du dessus (argument false), soit toute la main (argument true).
void Table::printHand(bool in){
     Player* current = activePlayerId == 0 ? player1 : player2; 
     current->printHand(std::cout, in); }

//Retourne le joueur correspondant à l'ID fourni.
Player* Table::getPlayer(int id){
    activePlayerId = id; 
    if (id == 0) return player1; 
    else return player2;
}

//Surcharge de l'opérateur d'insertion pour afficher les informations de la table.
std::ostream& operator<<(std::ostream& output, const Table& tb){
    output << "> Player 1 : " << std::endl << std::endl << *(tb.player1) << std::endl;
    output << "> Player 2 : " << std::endl << std::endl << *(tb.player2) << std::endl;
    output << "> Discard Pile (Top) : "  << *tb.discardPile << std::endl << std::endl;
    output << "> Trade Area : "  << *tb.tradeArea << std::endl;
    output << "_______________________" << std::endl;
    return output;
}

//Sauvegarde l'état actuel de la table dans des fichiers correspondants.
void Table::saveTable(){
    std::ofstream file;

    // Sauvegarde du deck
    file.open("Saved-Deck.txt", std::ios::trunc);
    deck->saveDeck(file);
    file.close();

    // Sauvegarde de la pile de défausse
    file.open("Saved-DiscardPile.txt", std::ios::trunc);
    discardPile->saveDiscardPile(file);
    file.close();

    // Sauvegarde de la zone d'échange
    file.open("Saved-TradeArea.txt", std::ios::trunc);
    tradeArea->saveTradeArea(file);
    file.close();

    // Sauvegarde des joueurs
    player1->savePlayer(1);
    player2->savePlayer(2);
}

//Recharge les informations d'un joueur depuis un fichier de sauvegarde.
void Table::reloadPlayer(int p_id){
    std::ifstream file;
    char id[2];
    sprintf(id, "%d", p_id); 
    std::string filename = "Saved-P" + std::string(id) + ".txt"; 
    file.open(filename);
    if(file.is_open()){ 
        if(p_id == 1){
            player1 = new Player(file, cardFactory); 
        }else{
            player2 = new Player(file, cardFactory); 
        }
    }
    file.close();
}

// Recharge le deck depuis un fichier de sauvegarde, ou le génère si le fichier n'existe pas.
void Table::reloadDeck(){
    std::ifstream deckFile("Saved-Deck.txt");
    if(deckFile.is_open()){ 
        deck = new Deck(deckFile, cardFactory);
    }else{ 
        deck = cardFactory->getDeck();
        std::cout << "Saved-Deck.txt not found. The deck has been generated from the CardFactory." << std::endl;
    }
}

//Recharge la pile de défausse depuis un fichier de sauvegarde.
void Table::reloadDiscardPile(){
    std::ifstream dpFile("Saved-DiscardPile.txt");
    if(dpFile.is_open()){
        discardPile = new DiscardPile(dpFile, cardFactory); 
    }else{
        discardPile = new DiscardPile; 
        std::cout << "Saved-DiscardPile.txt not found. Using empty discard pile." << std::endl;
    }
}

//Recharge la zone d'échange depuis un fichier de sauvegarde.
void Table::reloadTradeArea(){
    std::ifstream trArFile("Saved-TradeArea.txt");
    if(trArFile.is_open()){
        tradeArea = new TradeArea(trArFile, cardFactory); 
    }else{
        tradeArea = new TradeArea; 
        std::cout << "Saved-TradeArea.txt not found. Using empty trade area." << std::endl;
    }
}

// Retourne le deck de la table, en le générant si nécessaire.
Deck* Table::getDeck(){
    if(deck == nullptr){
        deck = cardFactory->getDeck(); 
    }
    return deck;
}

// Retourne la pile de défausse de la table.
DiscardPile* Table::getDiscardPile(){
    return discardPile;
}

// Retourne la zone d'échange de la table.
TradeArea* Table::getTradeArea(){
    return tradeArea;
}
