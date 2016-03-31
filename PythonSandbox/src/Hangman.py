# Hangman Game
import random

# constants
HANGMAN = (
"""
    ----
    |   |
    |
    |
    |
    |
    |
    |
    |
----------
""",
"""
    ----
    |   |
    |   0
    |
    |
    |
    |
    |
    |
----------
""",
"""
    ----
    |   |
    |   0
    |  -+-
    |
    |
    |
    |
    |
----------
""",
"""
    ----
    |   |
    |   0
    | /-+-
    |
    |
    |
    |
    |
----------
""",
"""
    ----
    |   |
    |   0
    | /-+-/
    |
    |
    |
    |
    |
----------
""",
"""
    ----
    |   |
    |   0
    | /-+-/
    |   |
    |
    |
    |
    |
----------
""",
"""
    ----
    |   |
    |   0
    | /-+-/
    |   |
    |   |
    |   |
    |   |
    |
----------
""",
"""
    ----
    |   |
    |   0
    | /-+-/
    |   |
    |   |
    |  | |
    |  | |
    |
----------
"""
)

MAX_WRONG = len(HANGMAN) - 1
WORDS = ("overused","clam","guam","puck","taffeta","bender","computer","apple","backup","hockey")

# Initialize variables
word = random.choice(WORDS)
so_far = "-" * len(word)
wrong = 0
used = []


# Game Header
print """

        Welcome to Hangman 1.0

    Guess the word before you complete the picture of the hanged man

    (Press the enter key to quit.)

"""

while wrong < MAX_WRONG and so_far != word:
    # Update user
    print HANGMAN[wrong]
    print "\nYou have used the following letters: ", used
    print "\nSo far, the word is:\n", so_far

    # get the guess
    guess = raw_input("\n\nEnter your guess:")
    guess = guess[0:1].lower()
    while guess in used:
        print "You have already guessed ", guess
        guess = raw_input("\n\nEnter a new guess:")
        guess = guess.lower()
    used.append(guess)
        
    # 
    if guess in word:
        print "\nYes! that is in the word"
        new = ""
        for i in range(len(word)):
            if guess == word[i]:
                new += guess
            else:
                new += so_far[i]
        so_far = new

    else:
        wrong += 1
        print "\nThat is not a letter in the word"

# Game Ending
if so_far == word:
    print "\nCongratulations, you won!\n"
else:
    print "\nYou Lose!\n"
    print HANGMAN[wrong]

print "\nThe word was ",word
# Wrap up
raw_input("\nHit enter key to exit.")
