#ifndef DISCARDPILE_H
#define DISCARDPILE_H
#include <iostream>
#include <vector>
#include <string>

#include "Card.h"
//#pragma once

//vector de cartes 

class CardFactory;
class DiscardPile : public vector<Card*> {
   

    public:
        DiscardPile(istream&, const CardFactory* );
        DiscardPile(): std::vector<Card*>(){};
        //jette la carte dans la pile 
        DiscardPile& operator+=( Card* );

        //renvoie et supprime la carte superieure de la pile au rebut 
        Card* pickUp();

        //renvoie mais ne supprime pas la carte supérieure de la pile au rebut 
        Card* top() const;  

        //insere cartes de la pile dans ostream
        void print( std::ostream& ) const;


        
        friend ostream &operator<<(ostream& out, const DiscardPile &pile);
        void saveDiscardPile(std::ofstream& filename);
      
    
};
#endif 