# Development Manual

## Software Required to Build/Run/Test:
* The current version of the Banqi Game does not require additional software to build or run the system.  Everything can be done through a terminal by command line.

* To run the tests for the Banqi Game system, Eclipse, or an equivalent IDE, is necessary.
  * Visit this site (https://www.eclipse.org/downloads/) to download the Eclipse IDE and follow the instructions to complete installation.
  * To run the tests, see the last section in this manual.
## To Download the Repository:

1. Go to the Github repository at the url: https://github.com/FoxXix/cs414-f18-001-ByteMe

2. Click on button: Clone or download
3. Copy the link that pops up
4. Make a new folder on your computer where you want to place the repository
5. Go into that new directory
6. Clone the repository by entering the command ‘git clone <Paste link from github>’

## To Begin Working on Project:

1. Create a new branch by typing ‘git branch <branchname>’ in the the terminal
2. Switch to the new branch by typing ‘git checkout <branchname>’
3. Type ‘git status’ to check that you are now in the new branch
4. Open Eclipse and click File, New -> Java Project
5. If you name the new Java Project the same as the folder you created, the project should show up in Eclipse.
    
    a. If not - click File, Open File… Navigate to where the repository is stored on your machine, and click OK
6. Ensure JUnit5 is included in your project. Right-click on your new project, Build Path, Add Libraries…

    a. Select JUnit, and click Next
    b. Ensure JUnit Library Version is on JUnit 5, and click finish

## To Run the Main Program:

1. In the terminal, navigate to the directory (bottom) that holds the .java classes for the project

2. Type `‘javac *.java’` to compile the classes

3. Now type `‘java -cp <Path to directory containing src files> <classpath> <Path to argument (.txt file)> ‘`
e.g.: `java -cp ~/cs414/Banqi/cs414-f18-001-ByteMe/src main.edu.colostate.cs.cs414.ByteMe.banqi.client.BanqiController ~/cs414/Banqi/cs414-f18-001-ByteMe/src/main/edu/colostate/cs/cs414/ByteMe/banqi/client/UserProfiles.txt`

## Running the Banqi Game on Two Devices:

* As a two-player game, the Banqi Game can be run on multiple devices, so that Users can play from different locations.

## To Run the Tests:

1. In Eclipse, ensure that the Run Configurations are set to the current project

    a. Run -> Run Configuration. Click Test, select project, select run all test in the selected project, and ensure JUnit 5 is selected as the Test runner and click run.
