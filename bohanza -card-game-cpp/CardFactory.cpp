#include "hfiles/CardFactory.h"

CardFactory* CardFactory::instance = nullptr; //0? 

/*CardFactory::CardFactory() {
    // Créer les cartes (exemple simplifié)
    for (int i = 0; i < 104; ++i) {
        allCards.push_back(new Card(/* paramètres *///);
    //}

CardFactory::CardFactory() {

    deck = new Deck();
   
    for (int i = 0; i < 20; ++i) deck->push_back(new Blue());
    for (int i = 0; i < 18; ++i) deck->push_back(new Chili());
    for (int i = 0; i < 16; ++i) deck->push_back(new Stink());
    for (int i = 0; i < 14; ++i) deck->push_back(new Green());
    for (int i = 0; i < 12; ++i) deck->push_back(new soy());
    for (int i = 0; i < 10; ++i) deck->push_back(new black());
    for (int i = 0; i < 8; ++i) deck->push_back(new Red());
    for (int i = 0; i < 6; ++i) deck->push_back(new garden());  
}

CardFactory* CardFactory::getFactory() {
    if (!instance) {
        instance = new CardFactory();
    }
    return instance;
}

/*Deck CardFactory::getDeck() {
    Deck deck(allCards);
    std::shuffle(deck.begin(), deck.end(), std::default_random_engine());
    return deck;
}*/

Deck* CardFactory::getDeck() {
    std::default_random_engine engine(0);
    std::shuffle(deck->begin(), deck->end(), engine);
    return deck;
}
