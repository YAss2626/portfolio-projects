#include "hfiles/DiscardPile.h"
#include "hfiles/CardFactory.h"


DiscardPile::DiscardPile(std::istream& input, const CardFactory* cf) : std::vector<Card*>() {
    std::string line;
    Card* card = nullptr;
    int count = 0;

    while (std::getline(input, line)) {
        std::istringstream iss(line);
        std::string data;

        if (!(iss >> data)) {
            continue;
        }

        // Map the card identifiers to their respective objects
        if (data == "B")       card = new Blue();
        else if (data == "C")  card = new Chili();
        else if (data == "S")  card = new Stink();
        else if (data == "G")  card = new Green();
        else if (data == "s")  card = new soy();
        else if (data == "b")  card = new black();
        else if (data == "R")  card = new Red();
        else if (data == "g")  card = new garden();
        else {
            std::cerr << "(DiscardPile Constructor) Invalid card identifier: " << data << std::endl;
            exit(1);
        }

        // Add the created card to the pile
        if (card) push_back(card);
        count++;
    }

    std::cout << "DiscardPile with " << count << " cards initialized from file properly." << std::endl;
}




DiscardPile &DiscardPile::operator+=(Card *carte)
{   
    //this->push_back(carte);
    push_back(carte);
    return *this;
}

//CHECK
Card *DiscardPile::pickUp() 
{   if (!empty()){
    //this->back ??
    Card *tmp= back();
    pop_back();
    return tmp;
   }
   return nullptr;
}
    

//CHECK
Card *DiscardPile::top() const
{   if (!empty()){
    return back();
    }
    return nullptr;
}

void DiscardPile::print(std::ostream &outie) const
{  
    for (const auto& carte : *this){
        outie << carte->getName()[0] << " ";
    }
}


//is GETNAME() necessaire ?
ostream &operator<<(ostream& out, const DiscardPile& pile)
{   if(!pile.empty()){
        (pile.back())->print(out);}
    return out;
}
    
void DiscardPile::saveDiscardPile(std::ofstream& filename){
    for(int i = 0;  i < size() ; i++){
        at(i)->saveCard(filename);
        filename << std::endl;
    }
    std::cout << "Discard Pile saved." << std::endl;
}