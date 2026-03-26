#include <iostream>
#include <string>
#include <ostream>
#include <iostream>
#include <fstream>
//#pragma once

#ifndef CARD_H
#define CARD_H

//Les String name...
using namespace std;

class Card
{
public:
    virtual int getCardsPerCoin(int coins) = 0;
    virtual string getName() = 0;
    virtual void print(ostream &out) const = 0;
    ostream& operator<<(ostream& out){
        out << getName()[0] << std::endl;
        return out;};
    
    virtual ~Card();
    void saveCard(ofstream& filename);//?????????????
      
};

class Blue : public virtual Card
{
    //string name;??????????
    public:
    //Blue(string name = "Blue")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out) const;
};

class Chili : public virtual Card
{
    //string name;??????????
    public:
    //Chili(string name = "Chili")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out) const;
};


class Stink : public virtual Card
{
    //string name;??????????
    public:
    //Stink(string name = "Stink")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out) const;

};

class Green : public virtual Card
{
    //string name;??????????
    public:
    //Green(string name = "Green")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out) const;
};

class soy : public virtual Card
{
    //string name;??????????
    public:
    //soy(string name = "soy")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out)const;

};

class black : public virtual Card
{
    //string name;??????????
    public:
    //black(string name = "black")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out)const;

};

class Red : public virtual Card
{
    //string name;??????????
    public:
    //Red(string name = "Red")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out)const;

};

class garden : public virtual Card
{
    //string name;??????????
    public:
    //garden(string name = "garden")
    int getCardsPerCoin(int coins);
    string getName();
    void print(ostream &out)const;

};

#endif