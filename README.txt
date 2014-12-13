//-----------------------------------------------------
//NAME = Matthew Goff, Zachary Matthews, Mason Lietzke
//OS = Windows 7
//SDK = 19
//DEVICE = Motorola RAZR/Samsung Galaxy Note 3
//Link to Demo Video:
//   https://www.youtube.com/watch?v=Uivz13E-4Fo&feature=youtu.be
//-----------------------------------------------------

                                ////////|\\\\\\\\
                               <--At Word's End-->
                                \\\\\\\\|////////

                                  |-----------|
                                  |--Preface--|
                                  |-----------|
A word game meant to be played between two players where a player submits
   a word in the dictionary and their opponent has to come up with another
   valid word that starts with the same letter as the last letter in the last
   word that was played.

When the app is started for the first time, it may take 15-30 seconds because
   it is writing about 100,000 words from a text file into a database to be
   accessed by the game.  It only has to do this once so long as the data for
   the app hasn't been deleted.

A lot of functionality for having a game between two people (such as notifying
   turn changes or having a persistent account) requires the use of a central
   database, which as of this time has not been implemented.  While most of the
   the functionality such as gameplay and local data storage is operational, the
   functions that require database access are not working but there are some
   placeholder actions in place so that the app can still somewhat function.

We had a plan for our central database to use a virtual machine on campus 
   called "Church" that houses a database structure.  However, the remote 
   capabilities of the server were not successfully set up by the department
   so we were unable to follow through with it.

                                   |----------|
                                   |--Log In--|
                                   |----------|
We still have a log in activity on our application that would have synced with
   the server to log a user in or allowed a new user to create an account.
   We decided to keep the log in activity intact so that what we do have complete
   would be a better representation of how our app would behave if we were to
   be successful with having a central database.  Since there is no database,
   we have still allowed you to log in using any arbitrary username and password.

                               |------------------|
                               |--Adding Friends--|
                               |------------------|
From the main menu, "View Friends" opens up the list of your current friends.
   Click "Add Friend" to open up the add friend activity.  From here you can
   click on the edit text field to type in a friend's username.  In a world
   where this game has a server, the game would check to see if the user exists
   before sending the request.

Currently the app "sends" your request to yourself as the person you sent the
   request to.  The "Sent Requests" list would only hold pending requests and
   would sync with the server to remove friends who have acknowledged your request.

                                  |------------|
                                  |--Gameplay--|
                                  |------------|
A game is started when viewing a friend in the friend list with whom you do not
   have an active game with.  When starting a new game, any word can be played and
   there is no timer.  In a normal situation, the player's turn would end and would
   have to wait for their opponent to play, but in the world of no central database
   the game allows the player to have a second turn so that they can experience the timer.

The player loses when either the timer runs out or when they hit the back button.  In
   either case, the player will update their losses (viewed in the friend activity for
   that friend) and a database would allow the opponent to be notified of their victory.

                                |----------------|
                                |--Active Games--|
                                |----------------|
Active games are viewed by clicking "View Games" in the main menu activity.  If there
   are any active games, a view containing the opponent's name on the left and
   an indicator of whose turn it is on the right will populate the list view.
   If a game is selected when it is the player's turn, obviously they will have to play
   their turn.  If a game is selected during the opponent's turn, the player is
   able to view the list of played words for that game.
  
                               |-------------------|
                               |--Deleting Friend--|
                               |-------------------|
A friend is deleted by viewing them from the friends list and pushing "Delete".  This
   will remove them from your friends list and will also end any currently active
   game the player has with them.
   