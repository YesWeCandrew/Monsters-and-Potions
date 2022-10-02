# User Stories

Our user stories were created during the first meeting in a Google Document.
They have been imported to the git repo as issues and we have been using this
to prioritise and complete tasks since.

We have also created additional user stories when needed during development as
needs of users were identified. These were added directly to Gitlab issues.

Additional features:
* Expandable game, with different type of Monsters and Items possible
* Saves into JSON and CSV for easy creation of new games and levels
* Show all possible moves show the player their next possible moves
* Main menu introduces the game and provides options and instructions
* Maps can be any rectangular size and will be displayed correctly by the UI

----
### User stories on Maze and Movement
+ As a gamer, I want my role to move left, right, forward and backward within the maze so that I can move around to interact with NPCs, avoid blocks, and collect boxes.
+ As a gamer, I want to be informed of all the possible next moves for me so that I can choose from them.
+ As a gamer, I want to see the up-to-date maze everytime I make a move so that I can tell what changes I have made.

### General
+ As a developer, I want the code to be well commented and understandable
+ As a developer, I want the code to be separated into appropriate classes and methods, so it is understandable and maintainable.
+ As a player, I want to have options when I first launch the game, such as new games, loading a game or seeing instructions, so I can play the game they way I want to.
+ As a developer, I want the game to load the JSON and CSV into Java classes and manipulate them there, so that the code can be easily tested and changed.
+ As a developer, I want the whole game to have tests written against it to make sure that it responds as expected.
+ User stories on fighting and text
+ As a first time player, I want a tutorial so I can learn how to play.
+ As a novice gamer, I want text explaining how each item works and what it does when I pick it up.
+ As a gamer, I want to be able to view all the help items in one place.
+ As a regular gamer, I want to be able to skip the explanation text so I don’t get bored.
+ As a game designer, I want to be able to edit the item text in the game JSON so I can easily make changes.
+ As a game designer, I don’t want to have to type the same information multiple times in a JSON.
+ As a gamer, I want to be able to fight monsters.
+ As a gamer, I want to be able to see my current health and the health of my enemy so I can decide whether to attack or retreat.
+ As a gamer, I want to be able to use items that increase my hit strength so I can kill enemies faster
+ As a gamer, I want to be able to use items that increase my health so I can take more hits
+ As a gamer fighting, I want to be able to decide whether to attack or retreat
+ As a gamer, I want simple commands to control my character
+ As a game designer, I want to be able to create different items easily to keep the game interesting.
+ As a game designer, I want to be able to create items that can multiply or divide the current health/attack points of the player.
+ As a game designer, I want to be able to easily edit and add text to items.

User Stories on GUI
+ As a PC gamer, I would like to be able to see my player and other characters / enemies move in real-time so the environment feels alive and responsive
+ As a novice gamer, I would like to see the world from a top-down angle view, so I can see the maze in its entirety
+ As an aspiring game developer, I would like the characters to be represented as symbols on the terminal because I’m more interested in learning the logic of game making
+ As a gamer, I would like the GUI to display quickly with a high fps, so I can respond to events as they occur and not experience lag
+ As a gamer on an older system, I would like the game to perform well on older systems

User Stories on Tool-tips
+ As a novice gamer, I would like some in-program explanation for items and mechanics of the game, so i don't need to search online for instructions

User Stories on Loading/Saving
+ As a gamer, I want to be able to save my game, so I can return to continue my session at any time I like.
+ As a family, we want to have multiple save files, so we can have individual sessions for each family member.
+ As a family, we want to be able to name our saved sessions, so we can identify which family member’s game is which.
+ As a gamer, I want to load a saved file from the start of the program, so I can return to continue a previous session.
+ As a game designer, I want the option to either create a new game, or load a saved game when starting the program, so I can give gamers the option to choose what to play
+ As a developer, I want to be able to save game session data in a JSON file, so I can easily identify attributes and make changes easily.
+ As a developer, I would like to store maps in a csv file, so I can easily visualise and customise maps in a practical format.
+ As a developer, I would like to have all saved game data stored locally, to make sure internet connection does not result in corrupted/slow game sessions.

User Stories on Inventory System
+ As a gamer, I want to view my inventory through the terminal so that I can check my current items.
+ As a gamer, I want to be able to pick up items and place them in my inventory so that I can upgrade my HP/strength and other components within the game.
+ As a gamer, I want to be able to remove items from my inventory that I no longer need so that I can have more room for other items.
+ As a first time player, I want to be able to get help through a list of commands for the inventory so that I can have more ease when playing.
+ As a gamer, I want to be able to view the individual stats of each item in my inventory so that I can determine the best items to carry on my character.

User stories on licenses
+ As a business owner, I want to choose an open-source, permissive software licence, so that our company can benefit from safe and secure practices with the possibility of commercial distribution.

----
