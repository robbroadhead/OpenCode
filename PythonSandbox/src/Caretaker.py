# Classy Critter
import random

class Critter(object):
    total=0
    name=""
    hunger=0
    boredom=0

    def __init__(self,name,hunger = 0,boredom = 0):
        print "A critter has been born!"
        self.name = name
        self.boredom = boredom
        self.hunger = hunger
        Critter.total += 1

    def __passTime(self):
        self.hunger += 1
        self.boredom += 1

    def __get_mood(self):
        unhappy = self.hunger + self.boredom

        if unhappy < 5:
            mood = "happy"
        elif 5 <= unhappy <= 10:
            mood = "ok"
        elif 11<= unhappy <= 15:
            mood = "frustrated"
        else:
            mood = "mad"
        return mood

    mood = property(__get_mood)

    def talk(self):
        print "I'm",self.name,"and I feel ",self.mood,"now.\n"
        self.__passTime()

    def eat(self,food = 4):
        print "Brrrup. Thank You."
        self.hunger -= food
        if self.hunger < 0:
            self.hunger = 0
        self.__passTime()

    def play(self, fun = 4):
        print "Wheeee!"
        self.boredom -= fun
        if self.boredom < 0:
            self.boredom = 0
        self.__passTime()
        
    def status():
        print "\nTotal number of critters is: ", Critter.total

    status = staticmethod(status)

#main
def main():
    critName = raw_input("Enter the name of the critter:")
    crit = Critter(critName)

    choice = None
    while choice != "0":
        print """
        Critter Caretaker

        0 - Quit
        1 - Listen to your critter.
        2 - Feed your critter.
        3 - Play with your critter.
    """

        choice = raw_input("Choice: ")
        print

        #exit
        if choice == "0":
            print "Good-bye."
        elif choice == "1":
            crit.talk()
        elif choice == "2":
            crit.eat()
        elif choice == "3":
            crit.play()
        else:
            print "\nSorry, but that is not a valid choice."

main()
("\n\nPress the enter key to exit.")

