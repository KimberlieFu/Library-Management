## How to run the program
Preparation:
* MySQL database (MySQL Workbench for better management)
* Download the Database file from the project

In MySQL local instance 3306, make a new database called "a2". Within the database, import all the tables in the folder. This will serve the backend of the menu that keeps tracks of scrolls in the library

In the App.java file, rename the following variables according to your account settings:
* username (This does not need to change it is the default root)
* password

Use`gradle run`commands that will start the main method of the App class to run the program to be ready to browse through the library of agility

## How to test it
Gradle uses JUnit to run tests by default. To run all the tests, just run the`gradle test`command in the root directory of the project to check the test coverages. The test coverage can be found in the`build/jacocoReport/index.html`it is expected that the code coverage is 75% backend

## How to contribute/collaborate on the database
Clone the project locally ('https://github.com/KimberlieFu/Library-Management.git')
Here are the steps to perform for each modification:
1. create and switch to your own branch (`git checkout-b myNewFeature`)
2. Update the latest version of your main branch locally (`git pull origin main`)
3. Changing the content you want
4. Commit your changes (`git commit -am 'Add some feature'`)
5. Switch to the main branch (`git checkout main`)
6. Merge your main branch (`git merge myNewFeature`)
7. Push to a remote repository (`git push`)

### Project members
- **Kimberlie Fu (Scrum Master)** - *Oversee the development team to properly execute the Scrum process in Jira and provide support as necessary. Lead of the GUI implementation. Part of function tests*
- **Keyue An (Project Owner)** - *Further refine and adjust user stories based on user feedback and needs, and prioritize stories. Part of the GUI implementation and function tests*
- **Wancheng Liu (Development Team)** - *Participate in the implementation of removing, editing and uploading scrolls. Part of the GUI implementation and function tests*
- **Yinbo Zhao (Development Team)** - *Implement more features according to the product backlog work list. Leaders implement the ability to remove, edit and upload scrolls. Part of function tests. Responsible for completing database methodology testing*
