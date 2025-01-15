#include "hfiles/Card.h"
using namespace std;


Card::~Card() {}


//Les Constructeur Blue:: machin

//Blue

// Blue::Blue(std::string name){
//     this->name = name;
// }


int Blue::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 4;
        case 2:
            return 6;
        case 3:
            return 8;
        case 4:
            return 10;
        default:
            return -1; 
    }
}

string Blue::getName(){
    return "Blue";
}

void Blue:: print(ostream& out) const{
    out<<"B";
}










//Chili
// Chili::Chili(std::string name){
//     this->name = name;
// }

int Chili::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 3;
        case 2:
            return 6;
        case 3:
            return 8;
        case 4:
            return 9;
        default:
            return -1; 
    }
}

string Chili::getName(){
    return "Chili";
}

void Chili:: print(ostream& out) const{
    out<<"C";
}

//Stink
// Stink::Stink(std::string name){
//     this->name = name;
// }

int Stink::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 3;
        case 2:
            return 5;
        case 3:
            return 7;
        case 4:
            return 8;
        default:
            return -1; 
    }
}


string Stink::getName(){
    return "Stink";
}

void Stink:: print(ostream& out) const{
    out<<"S";
}


//Green
// Green::Green(std::string name){
//     this->name = name;
// }
int Green::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 3;
        case 2:
            return 5;
        case 3:
            return 6;
        case 4:
            return 7;
        default:
            return -1; 
    }
}

string Green::getName(){
    return "Green";
}

void Green:: print(ostream& out) const{
    out<<"G";
}


//soy
// soy::soy(std::string name){
//     this->name = name;
// }
int soy::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 2;
        case 2:
            return 4;
        case 3:
            return 6;
        case 4:
            return 7;
        default:
            return -1; 
    }
}

string soy::getName(){
    return "soy";
}

void soy:: print(ostream& out)const{
    out<<"s";
}



//black
// black::black(std::string name){
//     this->name = name;
// }
int black::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 2;
        case 2:
            return 4;
        case 3:
            return 5;
        case 4:
            return 6;
        default:
            return -1; 
    }
}

string black::getName(){
    return "black";
}

void black:: print(ostream& out)const{
    out<<"b";
}

//Red
// Red::Red(std::string name){
//     this->name = name;
// }
int Red::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 2;
        case 2:
            return 3;
        case 3:
            return 4;
        case 4:
            return 5;
        default:
            return -1; 
    }
}

string Red::getName(){
    return "Red";
}

void Red:: print(ostream& out)const{
    out<<"R";
}

//Garden
// Garden::Garden(std::string name){
//     this->name = name;
// }
int garden::getCardsPerCoin(int coins) {
    switch(coins){
        case 1:
            return 0;
        case 2:
            return 2;
        case 3:
            return 3;
        case 4:
            return 0;
        default:
            return -1; 
    }
}

string garden::getName(){
    return "garden";
}

void garden:: print(ostream& out)const{
    out<<"g";
}



ostream& operator<<(ostream& out, Card& card) 
{ 
    card.print(out);
    return out; 
} 

void Card::saveCard(std::ofstream& filename){
    filename << *this;
}