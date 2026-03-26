#ifndef CARDFACTORY_H
#define CARDFACTORY_H


#include <vector>
#include <algorithm>
#include <random>
#include "Card.h"
#include "Deck.h"
//#pragma once

//Truc de yacine en //

class CardFactory {
    Deck* deck;
    // static CardFactory* instance;
    // std::vector<Card*> allCards;
    CardFactory();
    public:
        static CardFactory* getFactory();
        Deck* getDeck();
        static CardFactory* instance; // LALALALA

        // // Interdire la copie
        // CardFactory(const CardFactory&) = delete;
        // CardFactory& operator=(const CardFactory&) = delete;
};

#endif // CARDFACTORY_H
