#ifndef TRADEAREA_H
#define TRADEAREA_H


#include "Card.h"
#include <list>
#include <sstream>
#include <cstdlib>

#pragma once

using namespace std;
//#include "CardFactory.h"


class CardFactory;

class TradeArea{
    list<Card*> trade_Area_Cards; 
    const int carte_Max;

    
    public:

        int numCards() const;
        

        TradeArea():carte_Max(3){};
        TradeArea(std::istream& in, const CardFactory* factory);


        //~TradeArea();

        bool legal(Card* card);


        TradeArea& operator+=(Card* card);

        Card* trade(const string& cardName);

        
        //void print(ostream& out) const;
        void saveTradeArea(std::ofstream& filename);
        list<Card*> getListOfCards();


        friend ostream& operator<<(ostream& out, const TradeArea& tradeArea);
};

#endif