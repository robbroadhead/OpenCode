#Tic -Tac-Toe program

# Constants
NUM_SQUARES = 9
X = "X"
O = "O"
TIE = "TIE"
EMPTY = " "

# Functions for the program
def displayInstructions():
    """Display instructions for the game. """

    print """
    Welcome to the greatest intellectual challenge of all time: Tic-Tac-Toe.
    This will be a human and computer showdown.

    Make a move by entering a number from 0 - 8. The number will place your
    marker on the board as illustrated:

    0 | 1 | 2
    ---------
    3 | 4 | 5
    ---------
    6 | 7 | 8

    Prepare yourself, human. The battle is about to begin...\n
    """

def ynQuestion(question):
    """Ask a question with a Y/N answer """
    answer = None
    while answer not in ("y","n"):
        answer = raw_input(question).lower()
    return answer

def askNumber(question, low, high):
    """Ask a question with a number answer in a range """
    answer = None
    while answer not in range(low,high):
        answer = int(raw_input(question))
    return answer

def newBoard():
    """Create a blank board"""
    board = []
    for square in range(NUM_SQUARES):
        board.append(EMPTY)

    return board

def displayBoard(board):
    """Display the board on the screen"""
    print "\t ", board[0], " | ", board[1], " | ", board[2]
    print "\t  ", "------------"
    print "\t ", board[3], " | ", board[4], " | ", board[5]
    print "\t  ", "------------"
    print "\t ", board[6], " | ", board[7], " | ", board[8]

def legalMoves(board):
    """Return list of legal moves"""
    moves = []
    for square in range(NUM_SQUARES):
        if board[square] == EMPTY:
            moves.append(square)

    return moves

def pieces():
    """Determine who goes first"""
    goFirst = ynQuestion("Do you want to move first (y/n)?:")
    if goFirst == "y":
        print "Take the first move then, chicken."
        human = X
        computer = O
    else:
        print "You will regret that decision..."
        human = O
        computer = X
        
    return computer, human

def winner(board):
    """See if we have a winner"""
    WAYS_TO_WIN = ((0,1,2),
                   (3,4,5),
                   (6,7,8),
                   (0,3,6),
                   (1,4,7),
                   (2,5,8),
                   (0,4,8),
                   (2,4,6))

    for row in WAYS_TO_WIN:
        if board[row[0]] == board[row[1]] == board[row[2]] != EMPTY:
            winner = board[row[0]]
            return winner
        
    if EMPTY not in board:
        return TIE
        
    return None

def humanMove(board, human):
    """Get the human move."""
    legal = legalMoves(board)
    move = None
    while move not in legal:
        move = askNumber("Enter your move(0 - 8):",0,NUM_SQUARES)
        if move not in legal:
            print "Not a legal move, try again.\n"

    print "Okay..."
    return move

def computerMove(board, computer, human):
    """Place the computer marker."""
    board = board[:]

    # Best moves if possible:
    BEST = (4,0,2,6,8,1,3,5,7)
    actualMove = None

    # Win, if possible
    for move in legalMoves(board):
        board[move] = computer
        if winner(board) == computer:
            actualMove = move
        # undo test
        board[move] = EMPTY

    # Block, if possible
    if actualMove == None:
        for move in legalMoves(board):
            board[move] = human
            if winner(board) == human:
                actualMove = move
             # undo test
            board[move] = EMPTY
   
    if actualMove == None:
        for move in BEST:
            if move in legalMoves(board):
                actualMove = move

    # Let user know our move
    print "I will take square number", actualMove
    return actualMove
            
def nextTurn(turn):
    """Switch turns."""
    if turn == X:
        return O
    else:
        return X

def congratWinner(the_winner, computer, human):
    """Congratulate the winner"""
    if the_winner != TIE and the_winner != None:
        print the_winner, " won!\n"
    else:
        print "It is a tie.\n"

    if the_winner == computer:
        print "Bow to my superior intellect!"
    elif the_winner == human:
        print "You just got lucky."
    elif the_winner == TIE:
        print "A noble effort, but try again if you dare."

def main():
    displayInstructions()
    computer, human = pieces()
    turn = X
    board = newBoard()
    displayBoard(board)
    while not winner(board):
        if turn == human:
            move = humanMove(board, human)
            board[move] = human
        else:
            move = computerMove(board, computer, human)
            board[move] = computer
        displayBoard(board)
        turn = nextTurn(turn)
        
        the_winner = winner(board)
        congratWinner(the_winner, computer, human)

# Start it up
main()
raw_input("Press the enter key to exit program.")

    
