# Project Activity: Insert data in mySQL from the tab-separated value file

## What this project does:
1) Creates a MySQL database named Auto and a table AutoData;  
2) Uses Java (JDBC) to insert the Auto MPG dataset into MySQL;  
3) Uses a GUI with a text box (ALL or search text) and a second window to display results;  
4) Uses two sliders (MPG and Horsepower) to filter results using a WHERE clause.  

## Task 1: Create Database in MySQL
Run the SQL file:
- [Auto Database](https://github.com/Anastasiia0Ivasyshyn/Labs/blob/main/Project/sql/auto_database.sql)

## Task 2: Insert Data Using Java
Run:
- [AutoDbLoader](https://github.com/Anastasiia0Ivasyshyn/Labs/blob/main/Project/src/AutoDbLoader.java)

This reads:
- [Auto MPG data](https://github.com/Anastasiia0Ivasyshyn/Labs/blob/main/Project/data/auto-mpg.data-original)

and inserts rows into:
- Auto.AutoData

## Task 3 + Task 4: GUI Query + Sliders
Run:
- [AutoGuiSearch](https://github.com/Anastasiia0Ivasyshyn/Labs/blob/main/Project/src/AutoGuiSearch.java)

Main window:
- Text input field (type ALL to show all rows)
- Two sliders (Minimum MPG, Minimum Horsepower)

Second window:
- Displays query results from MySQL

## How to Run
1) Start MySQL
2) Run Auto Database
3) Put Auto MPG data into the data folder
4) Edit DB username/password in Java files
5) Run AutoDbLoader once
6) Run AutoGuiSearch to use the GUI

## Video
In this project, I worked with the Auto MPG dataset and automated the process of inserting data into a MySQL database using Java. The project starts by creating a MySQL database named Auto and a table called AutoData, which is designed to match the structure of the Auto MPG dataset. The table includes fields such as miles per gallon (MPG), cylinders, horsepower, weight, model year, and car name.

Next, I used Java with JDBC to insert the data into MySQL. The program reads the Auto MPG data file, which is tab/whitespace separated, and processes it line by line. Each line is split into individual values, and the data is inserted into the database using SQL statements inside Java. Prepared statements are used to safely insert the values and avoid SQL errors. Rows with missing or invalid data are handled so that the insertion process can complete successfully.

After the data is stored in the database, I created a graphical user interface using Java Swing. The main window contains a text input field where the user can type ALL to display the entire table or enter search text to filter results. When the user submits input, Java runs the corresponding SQL query behind the scenes. The query results are displayed in a separate window, as required by the project.

In addition to text input, the GUI includes two sliders that allow the user to filter results by minimum MPG and minimum horsepower. As the sliders are adjusted, Java updates the SQL WHERE clause to retrieve only the matching records from the database. This project helped me understand how Java, SQL, and GUI components can work together to manage data, run database queries, and present results in an interactive way.
