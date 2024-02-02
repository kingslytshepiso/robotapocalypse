# Robot Apocalypse Application
<img src="https://github.com/kingslytshepiso/robotapocalypse/assets/83579824/c428920e-7c4c-4fed-adfa-5f3c49e65c8e" width="250" />

@Author Kingsly Mokgwathi

### Project Description

The Robot Apocalypse application encompasses the APIs to cater for survivors during the crisis of a robot zombie outbreak.
The application provides survivors with the resources to manage and organise resources during the outbreak. Survivors 
are able to flag other survivors incase they suspect or confirm an infection. The inventory for a survivor allows to 
specify their resources, not limiting them with the kind of resources they can have. The source code also includes JUnit 5
tests that were used during development to verify the required functionality and pass the acceptance citerias.

The project is built using the Java Spring boot Framework with Maven

### Requirements

- JDK 21
- IDE

### Instructions
  1. Pull or clone the project to your IDE
  1. Allow your respective IDE to finish importing the required dependencies
  1. No extra steps are required to setup the application database
  1. ** The application uses an in-memory database called H2 to store information
     
  1. ** The database does not persist the data, and the information used
     
     and stored in the database is for demonstration purposes and thus will be

     removed when the application stops running.
  1. After all, build and run the project

### Endpoints
  1. GET /survivors : The endpoint to get all the available survivors from the database.
       
       - RETURNS a http status code of 200 with the list of survivors
     
  3. POST /survivors : The endpoint to post a survivor into the database.
       
       - EXPECTS a survivor object with the required values
     
       - RETURNS a http status code of 201 to indicate a successfull insertion and a location to the newly created item
       
       - WOULD RETURN a http status code of 409(Conflict) if a surivivor with the same ID already exists in the database
  4. GET /survivors/{survivor id} : The endpoint to get a specific survivor using their ID stored in the database

     - EXPECTS a survivor id used to search through the database

     - RETURNS a status code of 200 with survivor object if it is found, and a status code of 404(Not found) for when the survivor does not exist

  5. PUT /survivors/{survivor id}/lati-{latitude}:long-{longitude} : The endpoint to update the last location of a survivor.

     - EXPECTS longitude and latitude values of which will be used to update the specified user according to the survivor id supplied.

     - RETURNS a status code of 204(No content) to indicate that the request processed successfully and no further action is rquired

     - WOULD RETURN a http status code of 404 if the the specified survivor id does not exist

  6. PUT /survivors/flag/{survivor id} : The endpoint to flag a survivor as infected

     - EXPECTS a survivor id to specify the survivor to flag

     - RETURNS a http status code of 201 to indicate a successful flag process

  7. GET /survivors/percentage/infected : The endpoint to get the percentage of infected survivors
  7. GET /survivors/percentage/non-infected : The endpoint to get the percentage of non-infected survivors
  7. GET /survivors/infected : The endpoint to get a list infected survivors
  7. GET /survivors/non-infected : The endpoint to get a list non-infected survivors
       
