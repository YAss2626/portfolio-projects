#include "hfiles/Hand.h"

#include <queue>
#include <list>
#include <sstream>
#include <cstdlib>

using namespace std;

Hand::Hand(istream& in, const CardFactory* factory){
    string line;
    Card* card = nullptr;
    int count = 0;
    while (getline(in, line)) {
        istringstream iss(line);
        string data;
        if (!(iss >> data)) { 
            // Ligne vide
            continue;
        }
        // Vérifier le type de carte
        if(data == "B")       card = new Blue;
        else if(data == "C")  card = new Chili;
        else if(data == "S")  card = new Stink;
        else if(data == "G")  card = new Green;
        else if(data == "s")  card = new soy;
        else if(data == "b")  card = new black;
        else if(data == "R")  card = new Red;
        else if(data == "g")  card = new garden;
        else {
            cout << "(Constructeur Hand) Nom de carte invalide : " << data << endl;
            exit(1);
        }

        // Ajouter la carte à la main
        if(card != nullptr) hand.push(card);
    }

    cout << "Main avec " << count << " cartes initialisée depuis le fichier." << endl;
}

Hand::~Hand() {
    // Supprimer toutes les cartes
    while (!hand.empty()) {
        Card* card = hand.front();
        delete card;                
        hand.pop();                 
    }
    cout << "Main détruite." << endl;
}

Hand& Hand::operator+=(Card* card) {
    // Ajouter une carte
    hand.push(card);
    return *this;
}

Card* Hand::play() {
    // Jouer la carte en haut de la pile
    if (hand.empty()) {
        throw runtime_error("Impossible de jouer depuis une main vide");
    }
    Card* card = hand.front();
    hand.pop();
    return card;
}

Card* Hand::top() const {
    // Obtenir la carte en haut
    if (hand.empty()) {
        throw runtime_error("La main est vide");
    }
    return hand.front();
}

int Hand::getsize(){
    // Retourner la taille de la main
    return hand.size();
}

Card* Hand::operator[](int pos){
    Card* card = nullptr; // Carte supprimée
    queue<Card*, list<Card*>> temp; // Main temporaire
    Card* temp_card = nullptr;
    int find_idx = 0;

    // Parcourir la main pour trouver la carte
    while(!hand.empty()){
        if(find_idx++ == pos){
            card = hand.front();
            hand.pop();
        } else {
            temp_card = hand.front();
            hand.pop();
            temp.push(temp_card);
        }
    }

    // Restaurer la main initiale
    while(!temp.empty()){
        temp_card = temp.front();
        temp.pop();
        hand.push(temp_card);
    }
    return card;  
}

ostream& operator<<(ostream& out, Hand& hand_) {
    // Afficher les cartes de la main
    for(int pos = 0; pos < hand_.getsize(); pos++) {
        out << hand_.getCard(pos) << endl;
    }
    return out;
}

Card* Hand::getCard(int pos){
    Card* card = nullptr;
    if(pos > hand.size() - 1){
        cout << "(getCard) Index " << pos << " invalide. Taille de la main = " << hand.size() << endl;
    } else {
        queue<Card*, list<Card*>> temp; // Main temporaire
        Card* temp_card = nullptr;
        int find_idx = 0;

        // Parcourir pour trouver la carte
        while(!hand.empty()){
            if(find_idx++ == pos){
                card = hand.front();
            }
            temp_card = hand.front();
            hand.pop();
            temp.push(temp_card);
        }

        // Restaurer la main initiale
        while(!temp.empty()){
            temp_card = temp.front();
            temp.pop();
            hand.push(temp_card);
        }
    }
    return card;  
}

void Hand::saveHand(ofstream& filename){
    Card* temp_card = nullptr;
    queue<Card*, list<Card*>> temp;

    // Sauvegarder les cartes
    while(!hand.empty()){
        temp_card = hand.front();
        temp_card->saveCard(filename);
        filename << endl;
        hand.pop();
        temp.push(temp_card);
    }

    // Restaurer la main initiale
    while(!temp.empty()){
        temp_card = temp.front();
        temp.pop();
        hand.push(temp_card);
    }

    cout << "Main sauvegardée." << endl;
}
