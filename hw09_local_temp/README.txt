=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

Game instructions - every time game is run, instruction slide shows up
way to create new tiles
check if tiles are equal
move method based on direction parameter
    slide
    merge
check losing conditions

Tests - testing states and shit
File I/O - record high score and game state if unfinished game
Collection - record previous states from beginning for undo
2D arrays - game state

Order to do:
create game board /
create graphics component /
move methods /
losing conditions /
game state /

what needs to be done: write FILE I/O for keeping track of high score + last played game state
write JUnit tests
instruction slide
fill out read me

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays - maintained the internal state of the array of numbers in the game.
  This was the simplest and most efficient way to maintain this data.

  2. Collections - maintained previous game states. For the undo button, I needed a way to
  access the previous states of the games and had to keep them in a List. The undo button goes
  all the way back to the beginning.

  3. File I/O - maintains the high score and last state of the game. When you leave a game, it'll
  return you back to that state unless you choose to reset.

  4. JUnit Tests - these were used to ensure that the merge functions were operating properly and
  that undo was working properly.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  The Run2048 class is the same class from RunTicTacToe with some modifications - this class
  simply runs the game.

  The Board class maintains the state of the overall board and instantiates a new instance
  of the game.

  The Twenty class maintains all the game logic and how the users moves are interpreted by the
  objects.

  The GameStates class is a class that just maintains a 2D array and was created to easily
  store the previous states of the game in a List.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
I struggled mainly with writing to the file and reading from the file. All the states are private
and when arrays are saved, they're copied so there's no encapsulation issues.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
