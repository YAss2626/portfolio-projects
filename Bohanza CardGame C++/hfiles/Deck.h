#ifndef DECK_H
#define DECK_H

#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <cstdlib>
#include "Card.h"
//#pragma once
class CardFactory;
//vector de cartes 


class Deck : public std::vector<Card*> {
 
    public:
        Deck(std::istream&, const CardFactory*); 
        //constructeur par deafaut 
        //Deck();*/
        Deck(): std::vector<Card*>(){};

        ~Deck(){
            for(int i = 0; i < this->size(); i++){
                // std::cout << "deleting i = " << i << "; name = " << this->at(i)->getName() << std::endl; // DEBUG PURPOSE
                delete this->at(i);
            }
            std::cout << "Deck of size("<<this->size()<<") destroyed."<<std::endl;
        };

        Deck(const Deck& d){
            // clear the item from the current deck
            this->clear(); 
            // get the items from d inside this current deck
            for(int i = 0; i < 104; i++){
                this->push_back(d.at(i));
            }
            std::cout << "Deck of size("<<d.size()<<") copied."<<std::endl;
        }   
        Card* draw();
        Deck& operator=(const Deck&);
        friend std::ostream& operator<<( std::ostream &output, const Deck& d );
        void saveDeck(std::ofstream& filename);
        

      
    
};
#endif 