# JavaDatabaseAssignment

This is an assignment I did for a Java class.
The SQL file is copyrighted, so I do not know if I can put it in the repository.

This uses Java, Javafx, and JDBC (derby by apache now?). \n
You need to have Derby setup in environment variables.
•	(Windows 10) Settings -> System -> About -> Advanced System settings -> Environment Variable
Need to have javafx’s “lib” file in there?  (javafx-sdk-17.0.0.1\lib)
Need to have Derby’s “bin” file (db-derby-10.15.2.0-bin\bin)
Commands to run it
•	set DERBY_INSTALL=….\db-derby-10.15.1.3-bin
•	cd %DERBY_INSTALL%\bin
•	setEmbeddedCP.bat
•	cd (src file path)
•	%DERBY_INSTALL%\bin\ij
•	connect 'jdbc:derby:books;create=true;user=????;password=????';
•	run 'books.sql';
•	set PATH_TO_FX="….. \javafx-sdk-15\lib"
•	javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.swing *.java
•	java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.swing  QueryAppRun
