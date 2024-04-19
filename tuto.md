# Tuto Todo List for CDP (Intellij)

## TODO steps

- [x] Create project
    - [x] Create the MVC structure (stucture only)
    - [x] Implements the model (attributes, getters, setters, hashcode, equals)
    - [x] Create Tests
    - [x] Create the JSP
    - [x] Implements the Control
    - [x] Implement the form (in the jsp)
    - [x] Add the log4j
    - [x] Add the database
    - [ ] Add some js/jquery (todo)
    - [ ] Add some React (todo)


## Create project

First start by creating a new project in IntelliJ IDEA.

The project is a new project with "Jakarta EE", template "Web Application".

Do not check a thing in the column "Implementation".

## Create the MVC structure

Go to src/utilisateur/java/utilisateur and create a new package called utilisateur.model and utilisateur.test (control already exists, you just have to rename it)

Create Application.java and Todo.java in the model package.

Create the attribute of each class (check the requirements)

On every class, create getters/setters by going on the class name and right click on generate.

Do the same with hashcode and equals.

Write the code of Application.

## Test

Go to File > Project Structure > Modules > Edit Test source folders > put utilisateur/java/utilisateur/test as test folder

Edit pom.xml and change the scope of the junit dependency to compile.

(You just have to change the word in the <scope> of org.junit.jupiter groupeId)
```xml
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>compile</scope>
        </dependency>
```

Then right click on pom.xml > Maven > Reload project

## Create Tests

Create a new class in the test package called TodoTest.

Paste the code

For all the code in red, click on the word and click on "import class"


To create the Application Test :
-  Create a new class in the test package called ApplicationTest and use a template (recommended)
- Go on Application and right click on Application and generate Tests

Here is a template for the ApplicationTest class

```java

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class ApplicationTest {


  private Application app;

  @BeforeEach
  void setUp() {
    app = Application.getInstance();
  }

  @AfterEach
  void tearDown() {
    app = null;
  }

// TODO

}

```

## Create the JSP

Then go to webapp and create a todos.jsp file

Paste the code

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="utilisateur.model.*" %>
Foreach list of todo itemps :
<%
for(Todo todo : Application.getInstance().getLesTodos() ){
%>
<p>
   <%=todo.getTexte() %>
</p>
<%
}
%>
```


Modify index.jsp to redirect to todos.jsp

```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.sendRedirect("./Control?action=list");
%>
```

Then click on your browser on the top right of todos.jsp to see the result.

## Implements the Control

In the control package, rename the class WebServlet to Control.

Edit the doGet method to redirect to the todos.jsp file.

First import Servlet exception
```java
import jakarta.servlet.ServletException;
```

Then replace the doGet method by this one
```java
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {

    String vueFinale = "medical.jsp";

// TODO: a future doAction() method will be call from here to choose which JSP page to display  

    request.getRequestDispatcher(vueFinale).forward(request, response);

}
```

Do the same with doAction method

```java
private String doAction(HttpServletRequest request) {

  String vueFinale = "erreur.jsp";

   String action = request.getParameter("action");

   // Action1 addTodo
   if ("addTodo".equals(action)) {

       String texte = request.getParameter("todo_texte");
       Application.getInstance().addTodo(texte, false);

       vueFinale = "medical.jsp";
    }

     // Action2 Update list ...


    // ... 
    return vueFinale;
}
```

Edit the url at the top :

```java
@WebServlet(name = "Control", value = "/Control")
```


Also call doAction from doGet :

```java
String action = doAction(request);
```


## Implement the form

Implement this form in the todos.jsp file (add the for loop and iterate)

```html
<form method="get" action="./Control">
    <ul>
        <li>
            <input type="checkbox" name="checkbox_chocolat" />
            chocolat
        </li>
        <li>
            <input type="checkbox" name="checkbox_ananas" />
            ananas
        </li>
    </ul>
    <input type="submit" name="action" value="Update list" />
</form>

<hr />
<form method="get" action="./Control">
    New Todo: <input type="text" name="todo_texte" />
    <input type="submit" name="action" value="Add todo" />
</form>
```


## Add the log4j

### Install
- File > Project Structure > Libraries > + > Maven > apache.logging.log4j.api
### Not working?
Check if pom.xml contains this :
```xml
      <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>
```
If not, put it
### Create a log folder
Create a folder named log in the project, place it in the root of the project
### Create a log.txt file
Create a file named log.txt in the log folder created.
### Configuration
Create a file  "log4j.properties" file in the resources folder (src/utilisateur/ressources)

Replace <ABSOLUTE_PATH_OF_LOGS_TXT_IN_THE_PROJECT> by the path of the logs.txt file in the project

log4j.properties
```properties
log4j.rootLogger=DEBUG,stdout,file

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}:%L - %m%n


log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=<ABSOLUTE_PATH_OF_LOGS_TXT_IN_THE_PROJECT>
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%-7p %d [%t] %c %x - %m%n
```

### Use in Control.java
Replace <NAME_OF_THE_PROJECT> by the name of the project

At the beginning of the class
```java
 private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("<NAME_OF_THE_PROJECT>");
```

In the method
```java
logger.debug("Debug message");
logger.info("Info message");
```


## Add the DB (sqlite)

### Install

Add the dependency in the pom.xml file right below the log4j dependency

```xml
  <!-- https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.34.0</version>
</dependency>

```

Then right click on pom.xml > Maven > Reload project

### Create a db folder
Create a folder named db in the project, place it in the root of the project

### Create a db file
Create a file named db.sqlite in the db folder created.

### Check your db in Intellij
To do that in Intellij,

- Go to the database tab on the right
    - Go to the + on the top left
    - Data source > SQLite
    - Choose the db.sqlite file of your project
    - (install drivers if needed)

### Create TODO DB

Right click on utilisateur on the DB tab, new > query console, paste this and click on run :

```sql
CREATE TABLE IF NOT EXISTS TODO ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TEXTE TEXT NOT NULL, ACTIF BOOLEAN NOT NULL )
```

### Create class DaoManager

Create a new class in the model package called DaoManager and put the code below (change the <ABSOLUTE_PATH_OF_DB_SQLITE_IN_THE_PROJECT> by the path of the db.sqlite file in the project)

```java
package utilisateur.model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DaoManager {
  private static final Logger log = Logger.getLogger(DaoManager.class.getName());
  private Connection connection;

  public DaoManager() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:<ABSOLUTE_PATH_OF_DB_SQLITE_IN_THE_PROJECT>");
      createDatabase();
    } catch (Exception e) {
      log.severe(e.getClass().getName() + ": " + e.getMessage());
    }
  }


  private void createDatabase() {
    try {
      Statement stmt = connection.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS TODO ( ID INTEGER PRIMARY KEY AUTOINCREMENT, TEXTE TEXT NOT NULL, ACTIF BOOLEAN NOT NULL)";

      stmt.executeUpdate(sql);
      stmt.close();
    } catch (Exception e) {
      log.severe(e.getClass().getName() + ": " + e.getMessage());
    }
  }

  public void addTodo(Todo todo) {

    try {
      PreparedStatement preparedStatment = connection.prepareStatement("insert into TODO(TEXTE,ACTIF) values( ? , ? )");

      preparedStatment.setString(1, todo.getText());
      preparedStatment.setBoolean(2, todo.isActif());

      preparedStatment.execute();
      preparedStatment.close();

    } catch (Exception e) {
      log.severe(e.getClass().getName() + ": " + e.getMessage());
    }
  }

  public List<Todo> getAllTodo() {
    List<Todo> returnListTodo = new ArrayList<Todo>();
    try {
      Statement statement = connection.createStatement();

      if (statement.execute("Select TEXTE,ACTIF FROM TODO ")) {
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
          String texte = resultSet.getString("TEXTE");
          boolean actif = resultSet.getBoolean("ACTIF");

          returnListTodo.add(new Todo(texte, actif));
        }
      }
      statement.close();

    } catch (Exception e) {
      log.severe(e.getClass().getName() + ": " + e.getMessage());
    }
    return returnListTodo;
  }

}
```

### Connect it to the Control

Create a new instance of daoManager and use the method to add a todo and other stuffs


## Add some js/jquery

### Create a new folder called js in the webapp folder

### Create a new file called script.js in the js folder
### Add the script in the todos.jsp file

```html
<script src="js/script.js"></script>
```

### Add the code below in the script.js file

```javascript
// Form button trigger checkTodo method
function checkTodo(){
   // Variable to reference a field by the ID todo_text
   let todo_text = document.getElementById("todo_text");

   // content of the text field value
   let todo_textValue = todo_text.value;

   // Remove addition space at the beginning and the end of the value
   todo_textValue = todo_textValue.trim();
   
   // If not text is present, change the text into the field as a warning message and do not submit the form
   if ( todo_textValue === "" ){
       todo_text.value = "Mandatory!";
    }else{
       // Store a reference on the HTML form  
       let form = document.getElementById("the_form");
       // Trigger the submit event on this HTML form as usual
       form.submit();
   }
}
```
### Edit the submit button in the todos.jsp file

```html
 <form id="the_form" method="get" action="./Control">
    New Todo: <input type="text" name="todo_text" id="todo_text">

    <input type="button" name="bouton" value="Add Todo"
        onclick="checkTodo()">
    <input type="hidden" name="action" value="Add Todo">
  </form>
```

Now the button will trigger the checkTodo method and check if the field is empty or not before submitting the form.
It checks the data before sending it to the server.

### And Jquery?

Jquery is a library that makes it easier to use javascript.
But today, it is not recommended to use it because it is heavy and not necessary.
But let's see how to use it.


### Add the jquery library

Add this line in the head of the todos.jsp file

```html
<script
 src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
```

### Add the code below in the todos.jsp

```html
<script>

  $("input[type=checkbox]").click(
          function(event){
            let name = $(this).attr("name");
            let checked = $(this).prop('checked');
            $("span#checkox_"+name).addClass("barre");
          });
// 
</script>
```

## Add some React (FF 15, Surrender)

### Create react.jsp

Create a new file called react.jsp in the webapp folder

Paste the code below in the react.jsp file

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en"><head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>React Local</title>
  <script crossorigin src="https://unpkg.com/react@16/umd/react.development.js"></script>
  <script crossorigin src="https://unpkg.com/react-dom@16/umd/react-dom.development.js"></script> 
  <script type="application/javascript" src="https://unpkg.com/babel-standalone@6.26.0/babel.js"></script>
</head><body>

<div id="utilisateur"></div>
<script type="text/babel" src="js/react.js" />

</body></html>
```

### Create react.js

Create a new file called react.js in the js folder

Paste the code below in the react.js file

```javascript
const rootElement = document.getElementById('utilisateur')

function App(){
   return( <div> newApp </div> )
}

ReactDOM.render( <App />, rootElement )
```

### Add the link to the react.jsp in the todos.jsp file

```html
<a href="react.jsp">React</a>
```

Check if the link is working by clicking on it and see if the newApp is displayed on the page.

### Create a folder named jsx in the webapp folder

### Create 3 files

- App.jsx

With this code
```javascript
function App(){
   return( <div> <Liste name="liste1" /> </div> )
}

ReactDOM.render( <App />, rootElement )
```

- Liste.jsx

With this code
```javascript
class Liste extends React.Component {
        // state variable store the fact to refresh react component
        // 
	constructor(props) {
		super(props);
		this.state = {
			error: null,
			isLoaded: false,
			todos:[{"key":15,"texte":"Beurre","actif":"" },{ "key":16,"texte":"Lait","actif":"checked" },{ "key":17,"texte":"Yaourt","actif":""}]
		};
	}
    // Called when component is ready to display into the DOM
    componentDidMount() {
      this.setState({
          isLoaded: true
        });
    }
   // Called when component display is trigger
   render() {
	const { error, isLoaded, todos} = this.state;
	if (error) {
	  return <div>Error: {error.message}</div>;
	} else if (!isLoaded) {
	   return <div>Loading...</div>;
	} else {
	   return (
		<div className="list">
		<h1>List for {this.props.name}</h1>
		<ul>
		{todos.map(todo => (
		  <TodoItem key={todo.key} name={todo.texte} actif={todo.actif} />
          	))}	
		</ul>
		</div>
		);
	  }
	}
}
```

- TodoItem.jsx

With this code
```javascript
class TodoItem extends React.Component {
// add actif variable and record method checkboxHandler on the button
constructor(props){
   super(props);
   this.state = { actif: props.actif };
   this.checkboxHandler = this.checkboxHandler.bind(this);
}

// Method called from the checkbox click
checkboxHandler(){
    // Use console to display information
    console.log("Event");
    // A breakpoint for debug 
    debugger;
    if ( this.state.actif == "checked"){
       this.setState( {actif: ""} );
    }else{
       this.setState( {actif: "checked"} );
    }
}

 // Render the component when needed
 render() { 
  return (
   <li>
     <input type="checkbox" name={this.props.name} checked={this.state.actif} 
onChange={this.checkboxHandler} />
         {this.props.name}
   </li>
   );
  }

 
}
```