# Word Jumble
import random

WORDS = ("personal","firewall","software","may","warn","about","connection",
         "idle","makes","subprocess","using","this","computer","internal","loopback"
    )

# pick a word
word = random.choice(WORDS)
correct = word

# jumble the word
jumble = "";
while word:
    pos = random.randrange(len(word))
    jumble += word[pos]
    word = word[:pos] + word[(pos + 1):]

# Game Header
print """

        Welcome to the Word Jumble

    Unscramble the letters to make a word.

    (Press the enter key to quit.)

"""

print "The jumbled word is: ", jumble
guess = raw_input("\nEnter your guess: ")
guess = guess.lower()
while (guess !="") and (guess != correct):
    guess = raw_input("\nThat is not correct. Try Again: ")
    guess = guess.lower()

if guess == correct:
    print "\nYou got it. Congratulations!"
else:
    print "\nSorry. The word was", correct

# Wrap up
raw_input("\n\nThank You for playing. [Hit enter key to exit]")
