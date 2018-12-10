# Development Manual

## I.  Software Required to Build/Run/Test the System:
* The current version of the Banqi Game does not require additional software to build or run the system.  Everything can be done through a terminal by command line.

* You will need a text editor/notepad on each device used to create, save and access a UserProfiles.txt file.

* To run the tests, you will need to ensure you have JUnit 5 installed/included within your eclipse workspace.  Refer to this resource to install and learn about using JUnit 5 (https://junit.org/junit5/docs/current/user-guide/).

* To run the tests for the Banqi Game system, Eclipse, or an equivalent IDE, is necessary.
  * Visit this site (https://www.eclipse.org/downloads/) to download the Eclipse IDE and follow the instructions to complete installation.
  * To run the tests, see the last section in this manual.
## II.  To Download the Repository:

1. Go to the Github repository at the url: https://github.com/FoxXix/cs414-f18-001-ByteMe

2. Click on button: Clone or download
3. Copy the link that pops up
4. Make a new folder on your computer where you want to place the repository
5. Go into that new directory
6. Clone the repository by entering the command ‘git clone https://github.com/FoxXix/cs414-f18-001-ByteMe’

## III.  To Download and Begin Working on the Project:

1. Create a new branch by typing ‘git branch <branchname>’ in the the terminal
2. Switch to the new branch by typing ‘git checkout <branchname>’
3. Type ‘git status’ to check that you are now in the new branch
4. Open Eclipse and click File, New -> Java Project
5. If you name the new Java Project the same as the folder you created, the project should show up in Eclipse.
    
    a. If not - click File, Open File… Navigate to where the repository is stored on your machine, and click OK
6. Ensure JUnit5 is included in your project. Right-click on your new project, Build Path, Add Libraries…

    a. Select JUnit, and click Next
    b. Ensure JUnit Library Version is on JUnit 5, and click finish

## IV.  To Run the Main Banqi Game Program:

* As a two-player game, the Banqi Game can be run on two devices, so that Users can play from different locations.
* Steps 4 - 7 are completed by one device.  Steps 5 and 6 must be done by both players.
1. Before starting, make sure there is a local version of UserProfiles.txt saved on both devices and record the path to it.  Here is what that file should contain.  Copy this text into a new UserProfiles.txt (text file) on each device (remove any blank lines between entries):

`scoobs scoobs@email.com pass 2018/10/27 17:45:45 5 4 3 2`

`NoScope69 noscope 123 2018/10/29 10:45:37 3 3 3 100`

`Cornedbeef beef 123 2018/12/03 14:13:10 1 0 0 0`

`Evan evan 123 2018/12/03 14:20:48 7 65 3 0`

`Bill Joe@bill.com 123 2018/12/03 14:22:59 0 0 0 0`

`Brian bobby 123 2018/4/23 14:34:52 35 3 6 2`

`test test 123 2018/12/03 15:42:32 0 0 0 0`

`lmoreno dr 123 2018/12/06 13:38:34 0 0 0 0`

2. Next download the source code onto both devices playing Banqi.  This can be done via command line using the following the steps in II to download the code onto each of the devices.

3. Find and copy the path to where that file is saved on each device, open up the src folder for the project (`cs414-f18-001-ByteMe/src/main/edu/colostate/cs/cs414/ByteMe/banqi/client/`) and change the filepath in both BanqiController.java and Server.java to that path.  Line 21 in BanqiController.java and line 86 in Server.java.  This must be done in the source code on both devices and the path that is put here on each device must match where the file is saved on that specific device.

4. Now start a server 

    a. A Server, which holds information about Users, Invites and Games, must be set up before anyone can play Banqi.  The Server is provided in the source code for the Banqi Game.
5. Navigate into the folder (assuming you are in the directory holding the cloned repository) which contains the code for the Banqi Game (the src folder): `cd cs414-f18-001-ByteMe/src`
6. Now in that same directory in the terminal window, type `make` through command line.
   
   a. If running on the latest version of Mac (OSX Mojave) you must install xcode commande before `make` will work.  To do this, follow the instructions provided on the stack overflow post in the first solution: https://stackoverflow.com/questions/52522565/git-is-not-working-after-macos-mojave-update-xcrun-error-invalid-active-devel
7. Next, type `java main.edu.colostate.cs.cs414.ByteMe.banqi.server.Server [enter a port number]` (the port number can be any number that is 4 to 5 digits long, such as 3130).
8. Now Users are ready to connect to the server.  One both devices, connect to the server by entering the folowing command:
    
    a. Each User enters in command line: `java main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode [IP address of computer server is on] [enter the same port number as above in step 4]` such as `java main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode denver 3130` (CS lab machines) or `java main.edu.colostate.cs.cs414.ByteMe.banqi.server.UserNode 10.0.0.105 3130` (general device).
  
9. Now Users are connected and ready to play the Banqi!


## V.  To Run the Tests:

1. In Eclipse, ensure that the Run Configurations are set to the current project

    a. Run -> Run Configuration. Click Test, select project, select run all test in the selected project, and ensure JUnit 5 is selected as the Test runner and click run.
