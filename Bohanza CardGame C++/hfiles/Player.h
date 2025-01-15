#ifndef PLAYER_H
#define PLAYER_H

#include <cstdlib>
#include <vector>
#include "Hand.h"
#include "Chain.h"

class Player {
    std::string playerName; 
    Hand* playerHand;       
    std::vector<Chain_Base*> playerChains; 
    int playerCoins;       
    const int MAX_NUM_CHAINS;  
    const int INITIAL_NUM_CHAINS; 

public:
    // Constructeur par défaut de la classe Player
    Player(std::string& name) : MAX_NUM_CHAINS(3), INITIAL_NUM_CHAINS(2) {
        playerName = name;
        playerCoins = 0;
        playerHand = new Hand();
    };

    // Constructeur de la classe Player à partir d'un flux d'entrée
    Player(std::istream& input, const CardFactory* cf) : MAX_NUM_CHAINS(3), INITIAL_NUM_CHAINS(2) {
        std::string line;
        std::string chainType;
        Card* card = nullptr;
        playerHand = new Hand();
        int chainIndex = -1; 
        int cardCount = 0;   

        // Flags pour suivre l'état de l'initialisation
        bool isNameInitialized = false;
        bool isCoinsInitialized = false;
        bool isHandInitialized = false;
        bool areChainsInitialized = false;
        bool isChainTypeInitialized = false;

        // Lecture ligne par ligne depuis le flux d'entrée
        while (std::getline(input, line)) {
            std::istringstream iss(line);
            std::string data;
            if (!(iss >> data)) {
                continue;
            }

            // Initialisation du nom du joueur
            if (!isNameInitialized) {
                playerName = data;
                isNameInitialized = true;
                continue;
            }

            // Initialisation des pièces
            if (!isCoinsInitialized) {
                sscanf(data.c_str(), "%d", &playerCoins);
                isCoinsInitialized = true;
                continue;
            }

            // Initialisation de la main du joueur
            if (!isHandInitialized) {
                if (data == "-chains") {
                    isHandInitialized = true; // Fin de la main
                    continue;
                } else {
                    cardCount++;
                    // Création des cartes en fonction du type
                    if (data == "B") card = new Blue;
                    else if (data == "C") card = new Chili;
                    else if (data == "S") card = new Stink;
                    else if (data == "G") card = new Green;
                    else if (data == "s") card = new soy;
                    else if (data == "b") card = new black;
                    else if (data == "R") card = new Red;
                    else if (data == "g") card = new garden;
                    else {
                        std::cerr << "(Player Constructor) Carte inconnue : " << data << std::endl;
                        exit(1);
                    }
                    if (card != nullptr) *playerHand += card;
                }
            }

            // Initialisation des chaînes
            if (!areChainsInitialized && isHandInitialized) {
                if (data == "-end-chains") {
                    areChainsInitialized = true;
                    continue;
                } else {
                    if (data == "---") {
                        isChainTypeInitialized = false;
                        continue;
                    } else {
                        if (!isChainTypeInitialized) {
                            chainType = data;
                            isChainTypeInitialized = true;

                            Chain_Base* newChain;
                            if (chainType == "Blue") newChain = new Chain<Blue>;
                            else if (chainType == "Chili") newChain = new Chain<Chili>;
                            else if (chainType == "Stink") newChain = new Chain<Stink>;
                            else if (chainType == "Green") newChain = new Chain<Green>;
                            else if (chainType == "Soy") newChain = new Chain<soy>;
                            else if (chainType == "Black") newChain = new Chain<black>;
                            else if (chainType == "Red") newChain = new Chain<Red>;
                            else if (chainType == "Garden") newChain = new Chain<garden>;
                            else {
                                std::cerr << "(Player Constructor) Type de chaîne inconnu : " << chainType << std::endl;
                                exit(1);
                            }
                            if (newChain != nullptr) {
                                playerChains.push_back(newChain);
                                chainIndex++;
                            }
                        } else {
                            card = nullptr;
                            if (data == "B") card = new Blue;
                            else if (data == "C") card = new Chili;
                            else if (data == "S") card = new Stink;
                            else if (data == "G") card = new Green;
                            else if (data == "s") card = new soy;
                            else if (data == "b") card = new black;
                            else if (data == "R") card = new Red;
                            else if (data == "g") card = new garden;
                            else {
                                std::cerr << "(Player Constructor) Carte inconnue dans la chaîne : " << data << std::endl;
                                exit(1);
                            }
                            if (chainIndex != -1 && card != nullptr) {
                                *(playerChains.at(chainIndex)) += card;
                            } else {
                                std::cerr << "(Player Constructor) Aucune chaîne ajoutée jusqu'à présent." << std::endl;
                            }
                        }
                    }
                }
            }
        }
        std::cout << "Player initialisé à partir du fichier avec succès." << std::endl;
    };

    // Destructeur de la classe Player
    ~Player() {
        delete playerHand;
    };

    // Ajout de pièces au joueur
    Player& operator+=(int coins) {
        playerCoins += coins;
        return *this;
    };

    // Accès à une chaîne par index
    Chain_Base& operator[](int i) {
        return *(playerChains.at(i));
    };

    // Méthodes publiques
    void takeCard(Card*);
    Card* playCard(Card* input = nullptr, bool specified_input = false);
    Card* removeCard(int = 0);
    std::string getName();
    int getNumCoins();
    int getMaxNumChains();
    int getNumChains();
    int getNumCards(); 
    Hand* getHand();
    std::vector<Chain_Base*>* getChains();
    void buyThirdChain();
    void printHand(std::ostream& output, bool allCards);

    friend std::ostream& operator<<(std::ostream&, const Player&);
    void savePlayer(int playerId);
    void checkEndedChains();
};

#endif
