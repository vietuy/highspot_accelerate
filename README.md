###### Set Up and how to run:
1. Make sure you installed Java and Maven and are able to run Java Maven project in your IDE e.g VSCode, IntelliJ
2. To compile, run `javac -cp json-simple-1.1.1.jar modify.java` in the terminal
3. To execute the class file, run `java -cp .;json-simple-1.1.1.jar modify jsonfiles\\mixtape.json jsonfiles\\changes.json jsonfiles\\output.json` in the terminal\
    Note: If on a linux system, replace ';' with ':'


The application functionalities:\
-It ingests `mixtape.json`.\
-It ingests a change file(e.g `jsonfiles/changes.json`). The changes file includes multiple types of changes.\
-it outputs `output.json` in the same structure as `mixtape.json`, with the changes applied. 

3 Type of changes:\
-Add an existing song to an existing playlist.\
-Add a new playlist for an existing user; the playlist should contain at least one existing song.\
-Remove an existing playlist.

Change I would make in order to scale this application: Reduce write requests by directly updating/modifying a copy
file of mixtape.


