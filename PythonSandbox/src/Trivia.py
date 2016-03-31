# Trivia Challenge
import random

# Some global functions
def openFile(file_name, mode):
    """Open a file"""
    try:
        theFile = open(file_name,mode)
    except(IOError), e:
        print "Unable to open the file " ,file_name, " Ending program.\n", e
        raw_input("Press the enter key to exit program")
        sys.exit()
    else:
        return theFile

def nextLine(theFile):
    """ Return next line from the file, formatted"""
    line = theFile.readline()
    theFile.readline()
    line = line.replace("/","\n")
    return line

def nextBlock(theFile):
    """ Return the next block of data."""
    category = nextLine(theFile)
    question = nextLine(theFile)

    answers = []
    for i in range(4):
        answers.append(nextLine(theFile))

    correct = nextLine(theFile)
    if correct:
        correct = correct[0]

    explanation = nextLine(theFile)

    return category, question, answers, correct, explanation

def welcome(title):
    """Welcome the player and get name"""
    print "\t\tWelcome to Trivia Challenge!\n"
    print"\t\t",title,"\n"

def main():
    triviaFile = openFile("trivia.txt", "r")
    title = nextLine(triviaFile)
    welcome(title)
    score = 0

    category, question, answers, correct, explanation = nextBlock(triviaFile)
    while category:
        print category
        print question
        for i in range(4):
            print "\t",i + 1,"-", answers[i]

        userAnswer = raw_input("Which answer?: ")

        if userAnswer == correct:
            print "\nCorrect!"
            score += 1
        else:
            print "\nIncorrect!"

        print explanation
        print "Score:",score,"\n\n"
        category, question, answers, correct, explanation = nextBlock(triviaFile)

    triviaFile.close()

    print "You have completed the quiz with a score of ", score

# Run the application
main()
raw_input("Hit the enter key to exit.")
