#include "hfiles/Deck.h"

 
// DEFINIR GETCARD dans cardfactory ??
Deck::Deck(std::istream& in, const CardFactory* factory): std::vector<Card*>() {
        std::string line;
        Card* card = nullptr;
        int count = 0;
        while (std::getline(in, line)){

            std::istringstream iss(line);
                std::string data;
                if (!(iss >> data)) { 
                    continue;
                } 
                // std::cout << data << std::endl; //debug purpose
                count++;
                if(data == "B")       card = new Blue;
                else if(data == "C")  card = new Chili;
                else if(data == "S")  card = new Stink;
                else if(data == "G")  card = new Green;
                else if(data == "s")  card = new soy;
                else if(data == "b")  card = new black;
                else if(data == "R")  card = new Red;
                else if(data == "g")  card = new garden;
                else {
                    std::cout << "(Deck Constructor) Check the card name in the file. Value received : " << data << std::endl;
                    exit(1);
                }
                //
                if(card != nullptr) this->push_back(card);

            }
            std::cout << "Deck with " << count << " cards initialized from file properly." <<std::endl;
        
    //     string cardName;
    //     while (in >> cardName) {
    //     // Créer des cartes à partir du flux
    //     Card* card = nullptr;
    //     if (cardName == "Blue") card = new Blue();
    //     if (cardName == "Chili") card = new Chili();
    //     if (cardName == "Stink") card = new Stink();
    //     if (cardName == "Green") card = new Green();
    //     if (cardName == "soy") card = new soy();
    //     if (cardName == "black") card = new black();
    //     if (cardName == "Red") card = new Red();
    //     if (cardName == "garden") card = new garden();
    //     if (card) push_back(card);
    // }
};  

Card *Deck::draw()
{   
    //debut ou fin back ou front ??
    if (!empty()){
        Card* topCard= this->back();
        this->pop_back();
        //this->erase(this->begin());
        return topCard;}
    
    return nullptr;

    /*if (empty()) return nullptr; // Si le deck est vide
    Card* topCard = back();     // Récupère la carte au sommet
    pop_back();                 // Supprime la carte du deck
    return topCard;*/

    
}

Deck& Deck::operator=(const Deck& d){
    for(int i = 0 ; i < d.size() && i < 104; i++){
        this->push_back(d[i]);
    }
    return *this;
}



ostream &operator<<(ostream& out, const Deck& deck)
{ for(int i = 0; i < deck.size(); i++){
        out << deck.at(i)->getName()[0] << endl;
    }

    return out;
    
}


void Deck::saveDeck(std::ofstream& filename){
    for(int i = 0;  i < this->size() ; i++){
        this->at(i)->saveCard(filename);
        filename << std::endl;
    }
    std::cout << "Deck saved." << std::endl;
}
