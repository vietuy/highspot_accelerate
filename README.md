###### Set Up and how to run:
1. Make sure you installed Java and Maven and are able to run Java Maven project in your IDE e.g VSCode, IntelliJ
2. Run `mvn install` in the terminal to install dependencies
3. Run the java file `modify.java` in `src/main/java`


The application functionalities:\
-It ingests `mixtape.json`.\
-It ingests a changes file(e.g `jsonfiles/changes.json`). The changes file includes multiple types of changes.\
-it outputs `output.json` in the same structure as `mixtape.json`, with the changes applied. 

3 Type of changes:\
-Add an existing song to an existing playlist.\
-Add a new playlist for an existing user; the playlist should contain at least one existing song.\
-Remove an existing playlist.

Change I would make in order to scale this application: Reduce write requests by directly updating/modifying a copy
file of mixtape.


