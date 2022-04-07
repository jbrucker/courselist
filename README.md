
## Database Drivers for JDBC

- SQLite <https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.36.0.3>

## Object and Database Design

A Course has these attributes:

| Attribute  | Meaning           | Example            |
|------------|-------------------|--------------------|
| id         | The id or Primary Key | 0              |
| courseNumber | Course number, may begin with "0" | 01204111 |
| title      | The course name   | "Programming for Dummies" |
| credits    | The course credits | 3   |
| difficulty | The difficulty rating (0-10)   | 0.5 |

A `difficulty` of 1 is very easy, 10 is very hard; 0 means no difficulty rating assigned yet.


## Create database schema and import data

Using SQLite and the `sqlite3` command line utility:

```
# create "sample.db" using schema commands in a file
sqlite3 sample.db < sample.schema

# start the sqlite command line utility (or use a GUI tool)
# then import course data from a CSV file.
ubuntu$ sqlite3 sample.db
sqlite> .mode csv
sqlite> .import data/courses.csv courses
```

You can use the command line utility to explore the database and issue SQL commands:
```
sqlite> .tables
course     enrollment
sqlite> .schema courses
CREATE TABLE courses (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    course_number TEXT    NOT NULL,
    title         TEXT    NOT NULL,
    credits       INTEGER DEFAULT 0,
    difficulty    REAL    DEFAULT 0
);
sqlite> .mode column
sqlite> SELECT * FROM courses LIMIT 5;
shows the course data
sqlite> .quit
```
Browsing tables and modifying the database structure can also be done using a GUI tool
such as DBeaver or SqliteBrowser (introduced in ISP).

## Access the Database Programmatically using JDBC

Using the lowest level interface possible (JDBC) we can access the table data
directly using JDBC:

1. Create a database Connection (java.jdbc.Connection)
2. Create a database Statement or PreparedStatement. A Prepared Statement is safer and in some cases more efficient.
3. Execute the statement and get a ResultSet.
4. Iterate over the ResultSet, parse data, and print it.

This demonstrates how to use JDBC, but it's not a good design for software.

### Design and Implement a Data Access Object

Let's create a persistence component as a Data Access Object (DAO), for the Course class only.

A DAO should provide the basic CRUD+ operations:

| Method  | Meaning    |
|---------|------------|
| get(id) | Get one object using its primary key (id). |
| save(course) | Add a new course to the database.  The course object should have id 0, and the DAO will assign the id to the primary key value. |
| update(course) | Update an existing course in the database. The course id must be the primary key value in the database. |
| delete(course) | Delete an existing course from the database. The course id must be the primary key value in the database. After completion, the id field is set to 0. |

Two other operations that most persistence frameworks provide are:

| Method  | Meaning    |
|---------|------------|
| count() | Return the number of objects in the courses table. |
| findAll() | Return a list of all persisted course objects. |

We are writing a DAO for only one table with no relationships, and not concerning ourselves with guaranteeing object uniqueness.  You can imagine how much work writing a production-quality DAO can be. 
