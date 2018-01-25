# Validaator Simulaator

Validaator Simulaator tries to simulate the e-ticketing system Ridango AS has applied to public transportation systems in Estonia and in some places of Sweden and Ukraine. This simulation has been done in a very crude and basic fashion.

## Backend

Technologies used:
  - REST API
  - Relational database

A webserver is running powered by [Spark Framework](http://sparkjava.com/), which listens to certain Restful routes to query the [PostgrSQL](https://www.postgresql.org/) database. Available API routes are discussed later.

#### Installation

Validaator Simulaator requires [PostgreSQL](https://www.postgresql.org/download/) to be installed and the service must be running.
The application will need a database on URL ```localhost:5432/validaatordb```, a user named ```testuser``` with a password ```test```.
Running these commands from Linux terminal creates a database and a user (requires sudo):
```
$ sudo -u postgres psql -c "CREATE DATABASE validaatordb;"
$ sudo -u postgres psql -c "CREATE USER testuser WITH PASSWORD 'test';GRANT ALL PRIVILEGES ON DATABASE validaatordb TO testuser;"
```
Table creation is handled by a gradle task: ```$ gradle firstTimeSetup```. This task creates the tables and populates them with dummy data.
###### Configuring database credentials
If you wish to change the credentials used by the project, read the following.
* Configuring from a config file has not been implemented and must be done manually

Credentials must be changed in two places.
In the constructor of DatabaseWrapper class and in gradle.build.
```
/src/main/java/server.database.DatabaseWrapper.java
/gradle.build
```
#### Running the project
The project is using gradle to pull in third-party libraries. It is also using Gradle to set up the database with needed tables and populate it with dummy data.
First the database tables must be created with ```$ gradle firstTimeSetup```.
Then the project is ready to be executed with ```$ gradle run```.
Once the application is running, the RESTful routes are open for HTTP requests on ```localhost:5432/```.

#### Routes
```GET /api/users```

    Returns all users in a JSONArray format.
        [ "$id": {
            "name":          "$name".
            "personal_id":   "$personal_id",
            "date_of_birth": "$dateOfBirth",
            "date_added":    "$dateAdded"
             },
            ...
        ]
```POST /api/users```

    Adds a user on the database.
        Accepts a json in format:
            {
                "name":          "$name",
                "personal_id":   "$personalId",
                "date_of_birth": "$dateOfBirth"
            }

        Upon success, returns the $id (database id) of the new user.
        Upon failure, returns -1.
            Fails when:
                - A user with a same personal_id already exists on the DB
                - TODO: validate personal_id with a regex pattern
```GET /api/stops```

    Returns all stops in a JSONArray format.
        [ "$id": {
            "name":          "$name".
            "date_added":    "$dateAdded"
            },
        ...
        ]
```POST /api/stops```

    Adds a stop on the database.
        Accepts a json in format:
        {
            "name":          "$name",
            "personal_id":   "$personalId",
            "date_of_birth": "$dateOfBirth",
        }

        Upon success, returns the $id of the new stop.
        Upon failure, returns -1.
          Fails when:
            - No constraints! There may be multiple stops with the same name.

```POST /api/tickets```

    Registers a ticket validation on the database
        Accepts a json in format:
            {
                "user_id":  "$user_id",
                "stop_id":  "$stop_id",
            }

        Upon success, returns the $ticketNr of the new ticket.
        Upon failure, returns -1
            Fails when:
                -
```GET /api/users/:id/tickethistory```

    Returns user's ticket validation history in detail, in json array.
        [ "$ticketNr": {
            "stop_name":     "$stopName".
            "date_added":    "$dateAdded"
            },
            ...
        ]

        Upon failure, returns ""
        Fails when:
            - user with :id does not exist

##### Database managment with gradle
```$ gradle firstTimeSetup```
Creates the users, stops and transaction tables . Then inserts dummy data.
```$ gradle reset```
Drops all tables and runs firstTimeSetup

```$ gradle createTables```
Creates the users, stops and transaction tables.
```$ gradle populateTables```
Inserts dummy data into users and stops tables.
```$ gradle clearTables```
Deletes all insertions from tables. Does not clear sequence tables.
```$ gradle dropTables```
Drops all tables and sequences in the database.


#### What could be improved

 - API Authentication is a must in production
 - Tests
 - More routes
 - Higher abstraction
 - Handling of big queries
 - More automation with Gradle

##### Dependencies
[org.json:json:20171018](https://mvnrepository.com/artifact/org.json/json/20171018)
[com.sparkjava:spark-core:2.7.1](https://mvnrepository.com/artifact/com.sparkjava/spark-core/2.3)
[org.slf4j:slf4j-simple:1.7.25](https://mvnrepository.com/artifact/org.slf4j/slf4j-simple)
[org.postgresql:postgresql:42.2.0](https://mvnrepository.com/artifact/org.postgresql/postgresql/42.2.0)
[junit:junit:4.12](https://mvnrepository.com/artifact/junit/junit/4.12)

----------------------------------------
----------------------------------------
----------------------------------------
