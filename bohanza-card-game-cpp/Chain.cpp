#include "hfiles/Chain.h"
#include <stdexcept>
#include <iostream>
#include <iomanip>

int Chain_Base::getSize(){
    return chain.size();
}


std::string Chain_Base::getChainType(){
    return chainType;
}

void Chain_Base::setChainType(std::string chainType){
    chainType = chainType;
}

void Chain_Base::saveChain(std::ofstream& filename){
    // std::cout << "Chain Type (Chain)" <<  chainType << std::endl; // Debug purpose
    filename << std::endl << chainType << std::endl;
    for(int i = 0; i < chain.size() ; i++){
        chain.at(i)->saveCard(filename);
        filename << std::endl;
    }

    std::cout << "Chain saved." << std::endl;
}

std::ostream& operator<<( std::ostream &output, const Chain_Base & d ){
    output << d.chainType  << " " << std::setw(4);
    for(int i = 0; i < d.chain.size(); i++){
        d.chain.at(i)->print(output);
        output << " ";
    }
    
    return output;
}


std::ostream& operator<<( std::ostream &output, const Chain<Card*> & d ){
    output << d.chainType << " ";
    for(int i = 0; i < d.chain.size(); i++){
        d.chain.at(i)->print(output);
        output << " ";
    }
    
    return output;
};