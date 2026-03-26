#ifndef HAND_H
#define HAND_H

#include <iostream>
#include <list>
#include <queue>
#include "Card.h"
#pragma once

using namespace std;

class CardFactory; // À SUPPRIMER À LA FIN

class Hand {
   queue<Card*, list<Card*>> hand;

   public:
        Hand() {};
        Hand(istream& in, const CardFactory* factory); // Constructeur avec chargement depuis un fichier

        Hand& operator+=(Card* card); // Ajouter une carte à la main

        Card* play(); // Jouer la carte en haut de la pile

        Card* top() const; // Obtenir la carte en haut de la pile
        Card* getCard(int pos); // Obtenir une carte à un index donné

        Card* operator[](int index); // Accéder à une carte par son index

        queue<Card*, list<Card*>>* getCards(); // Obtenir la file des cartes
        friend ostream& operator<<(ostream& out, const Hand& hand); // Surcharge pour afficher la main
        void saveHand(ofstream& filename); // Sauvegarder la main dans un fichier
        ~Hand(); // Destructeur

        void clearHand() {
            // Vider la main
            while (!hand.empty()) {
                hand.pop();
            }
        }

        int getsize(); // Retourner la taille de la main
};

#endif


// class CardFactory; //A REMOVE A LA FIN


// class Hand{
//     std::queue <Card*, std::list<Card*>> hand;
    
//     public:
//         Hand(){};
//         Hand(std::istream& in, const CardFactory* factory);

//         Hand& operator+=(Card* card);

//         Card* play();

//         Card* top() const;
//         Card* getCard(int pos);

//         Card* operator[](int index);

//         // void print(std::ostream& out) const;
//         std::queue <Card*, std::list<Card*>>* getCards();
//         friend std::ostream& operator<<(std::ostream& out, const Hand& hand);
//         void saveHand(std::ofstream& filename);
//         ~Hand();

//         void clearHand(){
//             while(!hand.empty()){
//                 hand.pop();
//             }
//         }

//         int getsize();
// };


// #endif 


