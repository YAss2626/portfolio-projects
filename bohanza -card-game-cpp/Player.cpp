#include "hfiles/Player.h"
#include <string>
#include <iomanip>
#include <stdexcept>


std::string Player::getName(){ // Récupère le nom du joueur
    return this->playerName;
}


int Player::getNumCards(){ // Récupère le nombre de cartes dans la main du joueur
    return playerHand->numCards();
}

// Joue une carte depuis la main du joueur et gère la chaîne correspondante
Card* Player::playCard(Card* input, bool specified_input){
    Card* card = nullptr;
    if (playerHand->numCards() < 0)
        std::cout << "(PlayCard) Not enough card in hand for player : " << playerName << std::endl;
    else{

        if(!specified_input)
            card = playerHand->top();
        else
            card = input;
        
        Chain_Base* new_chain;
        bool ExistingChain = false;
        char user_input[2];

        // Vérifie si une chaîne correspondant à la carte existe déjà dans les chaînes du joueur
        for(Chain_Base* chain: playerChains){ 
            if(chain->getChainType() == card -> getName()){
                ExistingChain = true;
                new_chain = chain;
                if(!specified_input)
                   card = playerHand -> play();
                
                if(card == nullptr)  
                   card = playerHand -> play();

                *new_chain += card;
                break;
            }
        }

      
        // Si aucune chaîne existante ne correspond, en crée une nouvelle
        if(!ExistingChain){

            if(card->getName() == "Blue") new_chain = new Chain<Blue>;
            else if(card->getName() == "Chili") new_chain = new Chain<Chili>;
            else if(card->getName() == "Stink") new_chain = new Chain<Stink>;
            else if(card->getName() == "Green") new_chain = new Chain<Green>;
            else if(card->getName() == "soy")   new_chain = new Chain<soy>;
            else if(card->getName() == "black") new_chain = new Chain<black>;
            else if(card->getName() == "Red")   new_chain = new Chain<Red>;
            else if(card->getName() == "garden")new_chain = new Chain<garden>;
            else {
                std::cout << "(playCard) Check the card name. Value received : " << card->getName() << std::endl;
                new_chain = nullptr; 
                exit(1);
            }
            
            // Vérifie si le joueur a atteint le nombre maximal de chaînes

            if(playerChains.size() == MAX_NUM_CHAINS){
              std::cout << "Player " <<playerName << " has reached the maximum  value of chain ("<<MAX_NUM_CHAINS<<")." << std::endl;
              std::cout << "> Selling Chain of type : " << playerChains.front()->getChainType() << std::endl;

            // Conversion dynamique pour vendre la chaîne et ajouter des pièces

              if(playerChains.front()->getChainType() == "Blue"){
                  Chain<Blue>* chain = dynamic_cast<Chain<Blue>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              }
              else if(playerChains.front()->getChainType() == "Chili"){
                  Chain<Chili>* chain = dynamic_cast<Chain<Chili>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              } 

            // Répéter pour chaque type de chaîne

              else if(playerChains.front()->getChainType() == "Stink"){
                  Chain<Stink>* chain = dynamic_cast<Chain<Stink>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              } 
              else if(playerChains.front()->getChainType() == "Green"){
                  Chain<Green>* chain = dynamic_cast<Chain<Green>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              } 
              else if(playerChains.front()->getChainType() == "soy"){
                  Chain<soy>* chain  = dynamic_cast<Chain<soy>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              }   
              else if(playerChains.front()->getChainType() == "black"){
                  Chain<black>* chain = dynamic_cast<Chain<black>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              } 
              else if(playerChains.front()->getChainType() == "Red"){
                  Chain<Red>* chain  = dynamic_cast<Chain<Red>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              }  
              else if(playerChains.front()->getChainType() == "garden"){
                  Chain<garden>* chain = dynamic_cast<Chain<garden>*> (playerChains.front());
                  std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                  playerCoins += chain->sell();
              }
              else {
                std::cout << "(playCard) Check the chain type. Value received : " << playerChains.front()->getChainType() << std::endl;
                exit(1);
              }
              // Supprime la chaîne vendue

              playerChains.erase(playerChains.begin()); 

            }
            else if(playerChains.size() < INITIAL_NUM_CHAINS){
                playerChains.push_back(new_chain);
                if( (*new_chain).getSize() == 0 ){ 
                     (*new_chain).setChainType(card->getName());
                }
                card = playerHand->play();
                *new_chain += card;
            }else{
                std::cout << ">>> Player " <<playerName << " has reached the maximum allowed value of chain ("<<INITIAL_NUM_CHAINS<<")." << std::endl;
                std::cout << std::endl << "> Do you want to buy a third chain ? (y/n)" << std::endl;
                std::cin >> user_input;
                if(user_input[0] == 'y'){
                    buyThirdChain();
                    for(Chain_Base* chain : playerChains){
                        if(chain->getChainType() == card -> getName()){
                            new_chain = chain;
                            card = playerHand -> play();
                            *new_chain += card;
                            break;
                        }
                    }
                }
                else{
                 // Si le joueur ne veut pas acheter une troisième chaîne, vendre une chaîne existante


                  std::cout << "> Selling Chain of type : " << playerChains.front()->getChainType() << std::endl;

                   // Répéter le processus de vente pour chaque type
                  if(playerChains.front()->getChainType() == "Blue"){
                     Chain<Blue>* chain = dynamic_cast<Chain<Blue>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                  }
                  else if(playerChains.front()->getChainType() == "Chili"){
                     Chain<Chili>* chain =  dynamic_cast<Chain<Chili>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                  } 
                  else if(playerChains.front()->getChainType() == "Stink"){
                     Chain<Stink>* chain =  dynamic_cast<Chain<Stink>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                  } 
                  else if(playerChains.front()->getChainType() == "Green"){
                     Chain<Green>* chain =  dynamic_cast<Chain<Green>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                 } 
                 else if(playerChains.front()->getChainType() == "soy"){
                     Chain<soy>* chain  =  dynamic_cast<Chain<soy>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                 }   
                 else if(playerChains.front()->getChainType() == "black"){
                     Chain<black>* chain =  dynamic_cast<Chain<black>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                 } 

                 // Gérer une chaîne en fonction de son type, vendre ses cartes et les remplacer par une nouvelle chaîne

                 else if(playerChains.front()->getChainType() == "Red"){
                    // Conversion dynamique de la chaîne vers le type spécifique 'Red'

                     Chain<Red>* chain  =  dynamic_cast<Chain<Red>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                     playerCoins += chain->sell();
                 }  
                 else if(playerChains.front()->getChainType() == "garden"){
                     Chain<garden>* chain =  dynamic_cast<Chain<garden>*>(playerChains.front());
                     std::cout << "> Acquiring " << chain->sell() << " coins" << std::endl;
                    playerCoins += chain->sell();
                 }
                 else {
                     std::cout << "(playCard) Check the chain type. Value received : " << playerChains.front()->getChainType() << std::endl;
                     exit(1);
                 }

                  playerChains.erase(playerChains.begin()); 

                 
                  if(card->getName() == "Blue") new_chain = new Chain<Blue>;
                  else if(card->getName() == "Chili") new_chain = new Chain<Chili>;
                  else if(card->getName() == "Stink") new_chain = new Chain<Stink>;
                  else if(card->getName() == "Green") new_chain = new Chain<Green>;
                  else if(card->getName() == "soy")   new_chain = new Chain<soy>;
                  else if(card->getName() == "black") new_chain = new Chain<black>;
                  else if(card->getName() == "Red")   new_chain = new Chain<Red>;
                  else if(card->getName() == "garden") new_chain = new Chain<garden>;
                  else {

                        // Gestion d'erreurs : type de chaîne non reconnu

                    std::cout << "(playCard) Check the card name. Value received : " << card->getName() << std::endl;
                    new_chain = nullptr; 
                    exit(1);
                  }

                  playerChains.push_back(new_chain);

                  if( (*new_chain).getSize() == 0 ){ 
                     (*new_chain).setChainType(card->getName());
                  }

                  card = playerHand->play();
                  *new_chain += card;

               } 
            } 
        } 
    }
    return card; 
}

// retourne le nombre maximal de chaine autorisé dans le jeu pour le joueur correspondant
int Player::getMaxNumChains(){
    return MAX_NUM_CHAINS;
}


// retourne le nombre de chaine non nul que le joueur possède 
int Player::getNumChains(){
    int counter; 
    for(int i = 0; i < playerChains.size(); i++){
        if( (playerChains.at(i))->getSize() > 0) counter++;
    }
    return counter;
}


/* Ajoute une troisième chaîne vide au joueur pour 3 pièces.
         Réduit le nombre de pièces du joueur de 3.
        Si le joueur n'a pas assez de pièces, lève une exception. */

void Player::buyThirdChain(){
    Card* card = nullptr;
    if( (playerCoins % 3 == 0) && (playerCoins > 0)  ){
        if(playerChains.size() < MAX_NUM_CHAINS){
            playerCoins -= 3;
            card = playerHand->top();
            Chain_Base* new_chain;
            if(card->getName() == "Blue") new_chain = new Chain<Blue>;
            else if(card->getName() == "Chili") new_chain = new Chain<Chili>;
            else if(card->getName() == "Stink") new_chain = new Chain<Stink>;
            else if(card->getName() == "Green") new_chain = new Chain<Green>;
            else if(card->getName() == "soy")   new_chain = new Chain<soy>;
            else if(card->getName() == "black") new_chain = new Chain<black>;
            else if(card->getName() == "Red")   new_chain = new Chain<Red>;
            else if(card->getName() == "garden")new_chain = new Chain<garden>;
            else {
                std::cout << "(playCard) Check the card name. Value received : " << card->getName() << std::endl;
                new_chain = nullptr; 
                exit(1);
                
            }
            *new_chain += card;
            playerChains.push_back(new_chain);
        }else{
            std::cout << "A new chain can not be bought. The maximum number ["<< playerChains.size() << "] of chains have been reached. " << std::endl;
        }
        
    }else{
        throw std::runtime_error("NotEnoughCoins");
    }
}


//  permet d'ajouter une carte à la main du joueur

void Player::takeCard(Card* card){
    *playerHand += card;  
}


// Retire une carte de la main du joueur à une position donnée et la retourne.
Card* Player::removeCard(int pos){
    Card* card = nullptr;
    if(pos >  playerHand->numCards()-1){
        std::cout << "Please specify the proper index to remove from the pHand. " << std::endl;
        std::cout << "Entered idx : " << pos << std::endl;
        std::cout << "Current size of hands : " << playerHand->numCards() << std::endl;
        std::cout << "Card not removed." << std::endl;
    }else{
        card = (*playerHand)[pos];

    }
    return card;
}

// Retourne le nombre de pièces que le joueur possède.
int Player::getNumCoins(){
    return playerCoins;
}


// Opérateur d'insertion pour afficher les informations du joueur.
std::ostream& operator<<(std::ostream& output, const Player& player){
    output << player.playerName << std::setw(5) << player.playerCoins << " coins "<< std::endl;
    for(auto chain : player.playerChains){
        output << *chain;
        output << std::endl;
    }

    return output;
}


// Enregistre les informations du joueur dans un fichier texte.
void Player::savePlayer(int p_id){
    
    std::ofstream file;
    char id[2];
    
    sprintf(id, "%d", p_id);
    std::string filename ="Saved-P"+std::string(id)+".txt";

    file.open(filename, std::ios::trunc);

    file << playerName << std::endl;

    file << playerCoins << std::endl;

  
    playerHand->saveHand(file);


    file << std::endl << "-chains" << std::endl;

    for(int i = 0 ; i < playerChains.size() ; i++){
        file << std::endl << "---" << std::endl;
        playerChains.at(i)->saveChain(file);
    }
    
    file << std::endl << "-end-chains" << std::endl;

    file.close();

    std::cout << "Player-"+std::string(id) << " saved. " << std::endl;
}


// Retourne la main du joueur.
Hand* Player::getHand(){
    return playerHand;
}


// Retourne les chaînes du joueur.
std::vector<Chain_Base*>* Player::getChains(){
    return &playerChains;
}