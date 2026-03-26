#include "hfiles/TradeArea.h"
#include "hfiles/Card.h"
using namespace std;
TradeArea::TradeArea(istream& inputStream, const CardFactory* factory):carte_Max(3) {
    int cardCount = 0;
    string currentLine;
    Card* currentCard = nullptr;
    
    while (getline(inputStream, currentLine)) {
        string cardCode;
        istringstream lineStream(currentLine);

        if (!(lineStream >> cardCode)) {
            continue; // la ligne est vide, on continue
        }

        // Associer le code à la carte correspondante 
        if (cardCode == "B")       currentCard = new Blue;
        else if (cardCode == "C")  currentCard = new Chili;
        else if (cardCode == "S")  currentCard = new Stink;
        else if (cardCode == "G")  currentCard = new Green;
        else if (cardCode == "s")  currentCard = new soy;
        else if (cardCode == "b")  currentCard = new black;
        else if (cardCode == "R")  currentCard = new Red;
        else if (cardCode == "g")  currentCard = new garden;
        else {
            cout << "carte inconnu " <<endl;
            exit(1);
        }

        // Ajouter la carte à la zone d'échange
        if (currentCard != nullptr) {
            trade_Area_Cards.push_back(currentCard);}
        cardCount++;
    }

    cout << "TradeZone initialisée avec succès avec " << cardCount << endl;
}




TradeArea& TradeArea::operator+=(Card* card) {
    if (this->legal(card)) {
    trade_Area_Cards.push_back(card); 
    } else {
        if (trade_Area_Cards.size() < carte_Max) {
            trade_Area_Cards.push_back(card);
        } else {
            cout << card->getName() << "ne peut pas être ajoutée à la zone d'échange." << endl;
        }
    }
    return *this;
}

bool TradeArea::legal(Card* card){
    if(!card) return false;
    for (Card* c : trade_Area_Cards) {
        if (c->getName() == card->getName()) {
            return true;
        }
    }
    return false;
}

Card* TradeArea::trade(const string& cardName) {
    for (auto it = trade_Area_Cards.begin(); it != trade_Area_Cards.end(); ++it) {
        if ((*it)->getName() == cardName) {
            Card* card = *it;
            trade_Area_Cards.erase(it);
            return card;
        }
    }
    return nullptr; // If no matching card is found
}

int TradeArea::numCards() const {
    return static_cast<int>(trade_Area_Cards.size());
}

// void TradeArea::print(ostream& out) const {
//     for (const Card* card : trade_Area_Cards) {
//         out << *card << " "<<endl; // Use Card's operator<<
//     }
// }

void TradeArea::saveTradeArea(std::ofstream& filename){
    for(auto card: trade_Area_Cards){
        card->saveCard(filename);
        filename<<std::endl;
    }

    std::cout << "TradeArea saved." << std::endl;
}

list<Card *> TradeArea::getListOfCards()
{
    return trade_Area_Cards;
}

// ostream& operator<<(ostream& out, const TradeArea& tradeArea) {
//     tradeArea.print(out);
//     return out;
// }


ostream& operator<<(std::ostream& output, const TradeArea& tr_arr){

    for(auto card : tr_arr.trade_Area_Cards){
        output << card->getName()[0] << " ";
    }

    return output;
}
