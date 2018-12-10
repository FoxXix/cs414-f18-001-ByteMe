# Refactoring Operations and Design Pattern List
## Refactoring Operations:
**Extracting methods**:
* In several places, methods which were originally long methods (a code smell), were split up into separate methods using this refactoring operation.  The was most frequently used in the BanqiController.java class.

**Moving features between objects:**
* Throughout the process, new classes were created to separate and spread out responsibilities.  Additionally, to provide more structure to the system, the BanqiController class took on some responsibilities originally given to and spread out among other classes.

**Replace error code with exception:**
* This was used in both the source code and the unit testing.  The original code had some error code, which was found in many cases, which could be replaced with throwing a NullPointerException.

**Pull up method:** 
* Each of the classes for the individual Banqi Game pieces had it's own movePiece() method.  Looking back on this later, the team found that all pieces had the same move, except for the Cannon piece.  Therefore, the pull up method took care of this functionality for all the classes besides the one that still needed to override the superclass's movePiece() method.  

* However, after implementing this, we realized that the method was not really needed at all (movePiece()) as we ended up moving the pieces elsewhere.

**Gameplay on two computers:**
* The code was refactored so that gameplay was split between two devices, rather than two players playing on one individual device.  The overall functionality of the code did not change the way the game is coded, however, it was used to improve the nonfunctional attributes of the system.  In this case, to allow users to play by means of a server connection, so they can play from a distance.

## Current Design Patterns:
1. Builder (Creational Design Pattern): BanqiController.java in 
package main.edu.colostate.cs.cs414.ByteMe.banqi.client implements the builder pattern for it essentially sets up/starts all actions within the game from the view side.
2. Factory Method (Creational Design Pattern): EventFactory.java in package main.edu.colostate.cs.cs414.ByteMe.banqi.wireformats implements the Factory Method.
3. Model-View-Controller:  The BanqiController.java is a controller to separate the model (server side) from the view (game/user side)
