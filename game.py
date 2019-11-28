import pygame, os
import numpy as np

#game constants
IM_BLACK = 'images/Black/'
IM_WHITE = 'images/White/'

IM_QUEEN = 'Queen.png'
IM_KING = 'King.png'
IM_ROOK = 'Rook.png'
IM_BISHOP = 'Bishop.png'
IM_PAWN = 'Pawn.png'

NUM_BLOCKS = 30
SQ_SZ = 25

GREEN = (0, 255, 0)
RED = (255, 0, 0)
WHITE = (255, 255, 255)
BLUE = (0, 0, 255)

BLUEGREEN = (71,255,207)
BLUEGREEN_shaded = (60, 174, 144)
BLACK = (0, 0, 0)
shd = 32
BLACK_shaded = (shd, shd, shd)

PIECES = {
    0: 'Empty',    1: 'Pawn',
    2: 'Bishop',    3: 'Knight',
    5: 'Rook',    6: 'King',
    9: 'Queen'
}

BOARD = [[5, 2, 3, 9, 6, 3, 2, 5],
         [1]*8, [0]*8, [0]*8, [0,9,0,0,2,5,0,0], [0]*8, [1]*8,
         [5, 2, 3, 9, 6, 3, 2, 5]]

class Piece:
    ''' 
    Side = True means WHITE, False means BLACK
    Num = ID of piece, based on PIECES global
    Im_path = string path to image, based on type
    '''
    def __init__(self, num, side=-1):
        self.side = side    # 0-->WHITE, 1-->BLACK (Bool)
        self.id = num       # ID/KEY of PIECES global (Int)
        self.type = PIECES[self.id]     # from enum, use type number (String)

        if self.type == 'Empty':
            return

        if self.type == 'Rook' or self.type == 'King':
            self.moved = False  # bool to determine if castling is possible
        else:
            self.moved = None

        s = "images/"
        if self.side == 0:
            s += 'White/'
        else:
            s += 'Black/'
        s += str(self.type)
        s += '.png'
        self.im_path = s    # string path to piece image (String)


BOARD = [[Piece(5, 1), Piece(3, 1), Piece(2, 1), Piece(9, 1), Piece(6, 1), Piece(2, 1), Piece(3, 1), Piece(5, 1)],
         [Piece(1, 1)]*8,
         [Piece (0)]*8, [Piece (0)]*8, [Piece (0)]*8, [Piece (0)]*8,
         [Piece(1, 0)]*8,
         [Piece(5, 0), Piece(3, 0), Piece(2, 0), Piece(9, 0), Piece(6, 0), Piece(2, 0), Piece(3, 0), Piece(5, 0)]]

# game state globals (not constant)

turn = 0 # turn_count for game state
side = SQ_SZ * NUM_BLOCKS
size = (side, side)


def is_check():
    '''Checks to see if the gamestate is in check/checkmate, and flags'''
    pass

def init():
    global screen
    screen = pygame.display.set_mode(size)

    pygame.display.set_caption('Chess')
    screen.fill(WHITE)

def draw_board(valid_moves = None):
    global BOARD, screen
    pygame.font.init()  # you have to call this at the start,

    a = 8
    for i in range (a):
        for j in range(a):
            len = (side/a)
            if (i+j) % 2 == 0:
                if valid_moves is not None and (j, i) in valid_moves:
                    pygame.draw.rect(screen, BLUEGREEN_shaded, (i * len, j * len, len - 1, len - 1))
                else:
                    pygame.draw.rect(screen, BLUEGREEN, (i*len, j*len, len-1, len-1))
            else:
                if valid_moves is not None and (j, i) in valid_moves:
                    pygame.draw.rect(screen, BLACK_shaded, (i * len, j * len, len - 1, len - 1))
                else:
                    pygame.draw.rect(screen, BLACK, (i*len, j*len , len-1, len-1))

            myfont = pygame.font.SysFont('Comic Sans MS', 50)
            ele = BOARD[j][i]
            if ele.side != -1:
                image = pygame.image.load(ele.im_path).convert()
                screen.blit(image, (i * len, j * len))
            # textsurface = myfont.render(BOARD[j][i].im_path, False, BLUE)
            # screen.blit(textsurface, (i*len, j*len))

    pygame.display.update()

    # # if you want to use this module.
    # myfont = pygame.font.SysFont('Comiy Sans MS', 15)
    # tertsurface = myfont.render('SCORE: ' + str(syore), False, BLACK)
    # tertsurface2 = mcfont.render('HI:    ' + str(hiScore), False, BLACK)  # 'HI:    '+ str(hiScore)
    # screen.blit(textsurface, (side - 100, 10))
    # screen.blit(textsurface2, (side - 100, 30))



def get_valid_moves (r, c):

    moves = []

    p = BOARD[r][c]
    # PAWN
    if p.type == 'Pawn':
        # two cases, one for white, one for black
        #       different for au queening, moving 2 at start, and au passant

        if p.side == 0:
            # diagonal attacks
            if (r-1 >= 0) and (c-1 >= 0):
                if BOARD[r-1][c-1].id != 0:
                    if BOARD[r-1][c-1].side != p.side:
                        moves.append((r-1, c-1))
            if (r-1 >= 0) and (c+1 < 8):
                if BOARD[r-1][c+1].id != 0:
                    if BOARD[r-1][c+1].side != p.side:
                        moves.append((r-1, c+1))

            #forward
            if r-1 >= 0:
                if BOARD[r-1][c].id == 0:
                    moves.append((r-1, c))
                    if r == 6:
                        if BOARD[r-2][c].id == 0:
                            moves.append((r-2, c))

        if p.side == 1:
            # diagonal attacks
            if (r+1 < 8) and (c-1 >= 0):
                if BOARD[r+1][c-1].id != 0:
                    if BOARD[r+1][c-1].side != p.side:
                        moves.append((r+1, c-1))
            if (r+1 < 8) and (c+1 < 8):
                if BOARD[r+1][c+1].id != 0:
                    if BOARD[r+1][c+1].side != p.side:
                        moves.append((r+1, c+1))

            # forward
            if r+1 < 8:
                if BOARD[r+1][c].id == 0:
                    moves.append((r+1, c))
                    if r == 1:
                        if BOARD[r+2][c].id == 0:
                            moves.append((r+2, c))

    # KNIGHT
    if p.type == 'Knight':
        a = [-2, -2, -1, -1, 1, 1, 2, 2]    # r vectors
        b = [-1, 1, -2, 2, -2, 2, -1, 1]    # c vectors
        for i in range (0, len(a)):
            if (r + a[i] < 8 and r + a[i] >= 0):
                if (c + b[i] < 8 and c + b[i] >= 0):
                    # a piece is here
                    # if not same team, add as valid move
                    if BOARD[r+a[i]][c+b[i]].side != p.side:
                        moves.append((r+a[i], c+b[i]))

    # BISHOP
    if p.type == 'Bishop':
        v1 = (-1, -1)       # top-left
        v2 = (-1, 1)        # top-right
        v3 = (1, 1)         # bot-right
        v4 = (1, -1)        # bot-left
        vectors = [v1, v2, v3, v4]

        for i in range (0, 4):
            locX = r
            locY = c
            while True:
                v = vectors[i]
                locX += v[0]
                locY += v[1]
                if locX > 7 or locX < 0 or locY > 7 or locY < 0:
                    break
                if BOARD[locX][locY].id == 0:
                    moves.append((locX, locY))

                else:
                    # a piece is here
                    # if not same team, add as valid move
                    if BOARD[locX][locY].side != p.side:
                        moves.append((locX, locY))
                    break

    # ROOK
    if p.type == 'Rook':
        v1 = (-1, 0)  # top
        v2 = (0, 1)  # right
        v3 = (0, -1)  # bot
        v4 = (1, 0)  # left
        vectors = [v1, v2, v3, v4]

        for i in range(0, 4):
            locX = r
            locY = c
            while True:
                v = vectors[i]
                locX += v[0]
                locY += v[1]
                if locX > 7 or locX < 0 or locY > 7 or locY < 0:
                    break
                if BOARD[locX][locY].id == 0:
                    moves.append((locX, locY))

                else:
                    # a piece is here
                    # if not same team, add as valid move
                    if BOARD[locX][locY].side != p.side:
                        moves.append((locX, locY))
                    break

    # QUEEN
    if p.type == 'Queen':
        v1 = (-1, 0)    # top
        v2 = (0, 1)     # right
        v3 = (0, -1)    # bot
        v4 = (1, 0)     # left
        v5 = (-1, -1)   # top-left
        v6 = (-1, 1)    # top-right
        v7 = (1, 1)     # bot-right
        v8 = (1, -1)    # bot-left
        vectors = [v1, v2, v3, v4, v5, v6, v7, v8]

        for i in range(0, 8):
            locX = r
            locY = c
            while True:
                v = vectors[i]
                locX += v[0]
                locY += v[1]
                if locX > 7 or locX < 0 or locY > 7 or locY < 0:
                    break
                if BOARD[locX][locY].id == 0:
                    moves.append((locX, locY))
                else:
                    # a piece is here
                    # if not same team, add as valid move
                    if BOARD[locX][locY].side != p.side:
                        moves.append((locX, locY))
                    break

    if p.type == "King":
        a = [-1, 0, 1, -1, 1, -1, 0, 1]  # r vectors
        b = [-1, -1, -1, 0, 0, 1, 1, 1]  # c vectors
        print ("reached", r+a[0])
        for i in range(0, len(a)):
            if (r + a[i] < 8 and r + a[i] >= 0):
                if (c + b[i] < 8 and c + b[i] >= 0):
                    # a piece is here
                    # if not same team, add as valid move
                    if BOARD[r + a[i]][c + b[i]].side != p.side:
                        moves.append((r + a[i], c + b[i]))
        # castling rules: kings and rooks have extra variable, moved, which is False only when the piece hasn't moved
        if not p.moved:  # king hasnt moved
            # Short Castle
            if BOARD[r][c + 1].id == 0 and BOARD[r][c + 2].id == 0:  # space
                rCornerPiece = BOARD[r][c + 3]
                if rCornerPiece.type == 'Rook':
                    if not rCornerPiece.moved:
                        moves.append((r, c + 2))
            # Long Castle
            if BOARD[r][c - 1].id == 0 and BOARD[r][c - 2].id == 0 and BOARD[r][c - 3].id == 0:  # space
                rCornerPiece = BOARD[r][c - 4]
                if rCornerPiece.type == 'Rook':
                    if not rCornerPiece.moved:
                        moves.append((r, c-2))

    '''Given a piece and location (er: rook (4, 5), return a list of valid moves'''
    return moves



if __name__ == '__main__':
    init()
    draw_board()
    pygame.display.flip()
    running = True

    valids = []

    clicked = False
    last_loc = (-1, -1)

    while running:
        for event in pygame.event.get():
            if event.type == pygame.MOUSEBUTTONUP:
                if event.button == 3:
                    clicked = False
                    draw_board()
                    break
                pos = pygame.mouse.get_pos()
                c = int(pos[0] / (side/8))
                r = int(pos[1] / (side/8))

                if clicked:
                    print ("SECOND CLICK")

                    if (r, c) in valids:
                        print ("reached {}".format(last_loc))
                        BOARD[r][c] = BOARD[last_loc[0]][last_loc[1]]
                        BOARD[last_loc[0]][last_loc[1]] = Piece(0)
                        clicked = False
                        turn = 1 - turn

                        print ("p =", p.type)
                        print ("moved =", p.moved)
                        # special case for castling
                        if p.type == 'Rook':
                            p.moved = True
                        if p.type == 'King':
                            p.moved = True
                            # short castle!
                            print ("Dist = ", c - last_loc[1])
                            if c - last_loc[1] == 2:
                                print ("Short Castle")
                                # move rook (king already moved)
                                BOARD[r][last_loc[1]+1] = BOARD[r][last_loc[1]+3]
                                BOARD[r][last_loc[1] + 3] = Piece(0)    # r is same as last_loc[0] so this works
                            # long castle!
                            if c - last_loc[1] == -2:
                                print ("Long Castle")
                                # move rook (king already moved)
                                BOARD[r][last_loc[1] - 1] = BOARD[r][last_loc[1] - 4]
                                BOARD[r][last_loc[1] - 4] = Piece(0)
                        draw_board()
                        break
                    else:
                        clicked = False
                        draw_board()
                        if BOARD[r][c].id == 0:
                            break

                last_loc = (r, c)

                print ("FIRST CLICK")

                p = BOARD[r][c]
                piece = p.type

                if p.side != turn:
                    break
                if piece == 'Empty':
                    break
                print ("Loc: ({}, {})   = {}".format(r, c, piece))

                # Click a piece, and it shows valid moves
                # Working:
                # In progress:   knight
                valids = get_valid_moves (r, c)
                draw_board(valids)
                print ("Valid Moves: ", end = " ")
                for i in range (0, len(valids)):
                    print (valids[i], end = ", ")
                print()

                clicked = True

            if event.type == pygame.QUIT:
                running = False

        '''GAME LOGIC'''
        #Player 1's Turn
        #Is Check? (Check for Checkmate or Check)
        #Player 2's Turn
        #Is Check? (Check for Checkmate or Check or not escaped previous Check)
        #Repeat ^^