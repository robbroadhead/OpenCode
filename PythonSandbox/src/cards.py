'''
Created on Dec 8, 2010

@author: robbroadhead
'''

class Card(object):
    """ A playing card """
    RANKS = ["A","2","3","4","5","6","7","8","9","10","J","Q","K"]
    SUITS = ["c","d","h","s"]
    
    def __init__(self, rank, suit):
        self.rank = rank
        self.suit = suit
    
    def __str__(self):
        rep = self.rank + self.suit
        return rep
    
class Hand(object):
    """ A hand of cards """
    def __init__(self):
        self.cards = [];
        
    def __str__(self):
        if self.cards:
            rep = ""
            for card in self.cards:
                rep += str(card) + " "
        else:
            rep = "<Empty Hand>"
            
        return rep    

    def clear(self):
        self.cards = [];
        
    def add(self, card):
        self.cards.append(card)
        
    def give(self, card, other_hand):
        self.cards.remove(card)
        other_hand.add(card)
        
if __name__ == "__main__":
    print "Error: Tried to run a module directly"
    raw_input("\n\nPress the enter key to exit.")