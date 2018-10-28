# cs414-f18-001-ByteMe
 Banqi Game Project for CS 414: Object Oriented Design at Colorado State University
 
# Byteme : Team Members
* Brian Martin
* Stephen Porsche
* Evan Salzman
# Playing Banqi
## Banqi Setup
* 4x8 game board
* 32 total pieces
    * 16 per side (red/black)
* Pieces randomly placed face down
* Pieces have different ranks
   * The General has the highest rank
   * The Chariot has the second highest rank
   * The Solider has the lowest rank
* Types and numbers of pieces
    * Name of piece, Number per color
    * General, 1
    * Advisor, 2
    * Elephant, 2
    * Chariot, 2
    * Horse, 2
    * Cannon , 2
    * Soldier, 5

## Banqi Rules
* Each player takes turns making a move
* A valid move can be either:
    * Flipping a piece over
    * Capturing an enemy piece
* Moving a piece
* Moves can only be up/down/left/right (no diagonal movements)
* Host starts first by flipping over any piece
    * The color of the piece the host turns over is the color of his/her pieces for the game
* Capturing only happens to a piece of equal or lesser rank
    * Exception: General cannot capture soldiers, but soldiers can capture the General
* A player loses when they lose all their pieces and can no longer make any moves

