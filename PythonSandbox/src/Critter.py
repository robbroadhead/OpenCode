# Classy Critter
import random

class Critter(object):
    total=0

    def status():
        print "\nTotal number of critters is: ", Critter.total

    status = staticmethod(status)

    def __init__(self,name):
        print "A critter has been born!"
        self.name = name
        Critter.total += 1

#main
print "Accessing the class attribute Critter.total:",
print Critter.total

print "\nCreating Critters."
crit1 = Critter("critter 1")
crit2 = Critter("critter 2")
crit3 = Critter("critter 3")

Critter.status()

print "\nAccessing the class:",
print crit1.total

# Wrap up
raw_input("\nHit enter key to exit.")
