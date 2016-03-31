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

class Deck(Hand):
    def populate(self):
        for suit in Card.SUITS:
            for rank in Card.RANKS:
                self.add(Card(rank,suit))
        
    def shuffle(self):
        import random
        random.shuffle(self.cards)
        
    def deal(self,hands,per_hand = 1):
        for rounds in range(per_hand):
            for hand in hands:
                if self.cards:
                    top_card = self.cards[0]
                    self.give(top_card,hand)
                else:
                    print "Out of cards!"
    
#main
print "Testing the Card and Hand classes"
theDeck = Deck()
theDeck.populate()
theDeck.shuffle()
hand1 = Hand()
hand2 = Hand()
hands = [hand1,hand2]
theDeck.deal(hands, 7)

print "First Hand:",hand1
print "Second Hand:",hand2

# Wrap up
raw_input("\nHit enter key to exit.")
