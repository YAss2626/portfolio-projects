#include "hfiles/Main.h"
#include <fstream>
#include <sstream>

using namespace std;

int main(){
    std::cout << "-------------------------------------------"<< std::endl;
    std::cout << "Yacine Dasso 300277181" << std::endl; 
    std::cout << "Salma Sabbar 300322273" << std::endl; 
    std::cout << "Girum Worku 300305611" << std::endl; 
    std::cout << "-------------------------------------------" << std::endl ;
    std::cout << std::endl;

    std::string p1_name;
    std::string p2_name;
    std::string winner_name;

    // Initialiser les composants
    const int MAX_NUM_PLAYER = 2;
    Player* p1 = nullptr;
    Player* p2 = nullptr;




    // Tableau de joueurs
    Player** pArr =  new Player*[MAX_NUM_PLAYER];
    DiscardPile* dp = new DiscardPile;
    CardFactory* cf = CardFactory::getFactory();
    Deck* deck =  nullptr;
    TradeArea* trAr = new TradeArea;
    Table* tb = new Table(*p1,*p2,*dp, *trAr, *deck, *cf);

    char user_input[2];
    bool savedGame;

    // Charger une partie sauvegardée
    std::cout << "Voulez-vous recharger une partie ? (y/n)" << std::endl;
    std::cin >> user_input;

    // Chercher un fichier sauvegardé
    if(user_input[0] == 'y'){
        // Recharger le deck
        tb-> reloadDeck();
        deck = tb ->getDeck();

        tb -> reloadPlayer(1); // Recharger joueur 1
        tb -> reloadPlayer(2); // Recharger joueur 2

        p1 = tb -> getPlayer(1);
        p2 = tb -> getPlayer(2);

        tb -> reloadDiscardPile();   // Recharger la défausse
        dp = tb -> getDiscardPile(); // Obtenir la défausse

        tb -> reloadTradeArea();     // Recharger la zone d'échange
        trAr = tb -> getTradeArea();
        
    } else {
        // Saisie des noms des joueurs
        cout << std::endl;
        cout << "Entrez le nom du premier joueur : ";
        cin  >> p1_name;
        cout << std::endl;
        cout << "Entrez le nom du deuxième joueur : ";
        cin  >> p2_name;
        cout << std::endl;

        p1 = new Player(p1_name);
        p2 = new Player(p2_name);

        pArr[0] = p1;
        pArr[1] = p2;

        tb = new Table(*p1,*p2,*dp, *trAr, *deck, *cf);

        deck = tb -> getDeck();        // Obtenir le deck
        dp   = tb -> getDiscardPile(); // Obtenir la défausse
        trAr = tb -> getTradeArea();   // Obtenir la zone d'échange

        // Distribuer 5 cartes à chaque joueur
        std::cout << "Initialiser chaque joueur avec 5 cartes. " << std::endl;
        std::cout << "Taille actuelle du deck : " << deck -> size() << std::endl;

        for(int player = 0 ; player < MAX_NUM_PLAYER ; player++){
            for(int card = 0; card < 5; card++){
                pArr[player]->takeCard(deck->draw());
            }
        }
    }
  
    // Boucle du jeu
    while(deck->size() != 0){
       std::cout << "Cartes restantes dans le deck : " << deck->size() << std::endl;
       std::cout << std::endl << "Voulez-vous sauvegarder la partie ? (y/n)" << std::endl;
       std::cin >> user_input;
       if(user_input[0] == 'y'){
           // Sauvegarder le jeu
           tb -> saveTable();
           std::cout << "Partie sauvegardée. Au revoir." << std::endl;
           break;
       }
       else{ 
           for(int i = 0; i < MAX_NUM_PLAYER; i++){
               // Afficher les infos de la table
               std::cout << std::endl <<  ">>>> Infos de la table : <<<<" << std::endl << std::endl << *tb << std::endl;

               // Logique du tour
               std::cout << ">>>> Tour du joueur " << i+1 << " <<<<" << std::endl;

               Player* p = tb -> getPlayer(i);  

               // Tirer une carte du deck
               std::cout <<  "> Tirer une carte du deck..." << std::endl;
               p->takeCard(deck->draw());

               std::cout << std::endl << "> Main du joueur " << i+1 << " : " << std::endl;
               p->printHand(std::cout, true);
               std::cout << std::endl;

               // Ajouter des cartes de la zone d'échange aux chaînes ou les défausser
               if(trAr->numCards() > 0) {
                   bool cardAdded = false;
                   for( Card* card : trAr->getListOfCards() ){
                        for( Chain_Base* chain : *(p->getChains()) ){
                            if(card->getName() == chain->getChainType()){
                               *chain += trAr->trade(card->getName());
                               cardAdded = true;
                            }
                        }

                        // Défausser la carte si non ajoutée
                        if(!cardAdded){
                           std::cout << "> Carte : " ;
                           card->print(std::cout);
                           std::cout << " ajoutée à la défausse " << std::endl;
                           *dp+=trAr->trade(card->getName());
                        }
                   }
               }

               // Jouer la carte du dessus de la main
               std::cout << "> Jouer la carte du dessus (" << p->getHand()->top()->getName()[0] << ")" << std::endl;
               p->playCard();

               std::cout << std::endl <<  *p << std::endl;

               std::cout << "> Main du joueur " << i+1 << " : " << std::endl;
               p->printHand(std::cout, true);
               std::cout << std::endl;

               // Choisir de jouer ou défausser une carte
               std::cout << "> Jouer la carte du dessus ? (y) ou défausser ? (n)" << std::endl;
               std::cin >> user_input; 

               if(user_input[0] == 'y'){
                   // Jouer la carte
                   std::cout << "> Jouer la carte (" << p->getHand()->top()->getName()[0] << ")" << std::endl;
                   p -> playCard();
               } else {
                   // Défausser une carte arbitraire
                   int idx; 
                   Card* card = nullptr;
                   std::cout << "> Main complète du joueur " << i+1 << " : " << std::endl;
                   p->printHand(std::cout, true);
                   std::cout << "Index de la carte à retirer : ";
                   std::cin  >> idx;

                   card = p->removeCard(idx);
                   while(card == nullptr){
                      std::cout << "Index invalide. Retentez : ";
                      std::cin  >> idx;
                      card = p->removeCard(idx);
                   }
                   
                   std::cout << "> Carte : " ;
                   card->print(std::cout);
                   std::cout << " ajoutée à la défausse " << std::endl;
                   *dp+= card;
               }

               // Tirer 3 cartes pour la zone d'échange
               std::cout << "> Tirer 3 cartes pour la zone d'échange. " << std::endl;
               for(int drw = 0 ;  drw < 3; drw++){
                   *trAr += (deck -> draw());
               }

               // Si des cartes de la défausse correspondent, les ajouter à la zone d'échange
               if(dp->size() > 0){
                   while( trAr->legal(dp->top())){
                        *trAr += (dp->pickUp());
                   }
               }

               // Demander si une carte doit être chaînée
               for(Card* card : trAr->getListOfCards()){
                   std::cout << "> Chainer cette carte ? (" ;
                   card -> print(std::cout);
                   std::cout << ") ? (y/n)" << std::endl;
                   std::cin >> user_input; 

                   if(user_input[0] == 'y'){
                      p -> playCard(trAr -> trade(card->getName()),  true);
                   }
               }
               
               // Tirer 2 cartes pour la main
               for(int i = 0; i < 2; i++){
                   if(deck->size() > 0 )
                      p -> takeCard(deck->draw());
               }
           }
       }
    }

    // Fin du jeu
    if(deck->size() == 0){
       std::cout << "> Plus de cartes dans le deck." << std::endl;
       tb->win(winner_name);
       std::cout << "> Le gagnant est : " << winner_name << std::endl;
    }
    
    return 0;
};
