/**
 * @author J
 */

public class PredefinedQuery {
    
    private final String label;
    private final String predefinedQuery;
    
    //constructor
    public PredefinedQuery(){
        label = "All Authors";
        predefinedQuery = "SELECT AuthorID, LastName, FirstName FROM Authors";
    }
    
    //constructor if title of book
    public PredefinedQuery(String title){
        label = "Title: " + title;
        predefinedQuery = "SELECT LastName, FirstName FROM Titles INNER JOIN AuthorISBN ON Titles.isbn = AuthorISBN.isbn INNER JOIN Authors ON AuthorISBN.authorID = Authors.authorID WHERE Title = '"+ title +"' ORDER BY LastName, FirstName";
    }
    
    //constrcutor if two strings = two names = author 
    public PredefinedQuery(String LastName, String FirstName){
        label = "Author: " + LastName + ", " + FirstName;
        predefinedQuery = "SELECT LastName, FirstName, Title, Copyright, Titles.isbn FROM Titles INNER JOIN AuthorISBN ON Titles.isbn = AuthorISBN.isbn INNER JOIN Authors ON AuthorISBN.authorid = Authors.authorid WHERE LastName = '" + LastName + "' AND FirstName = '" + FirstName + "' ORDER BY LastName, FirstName";
    }
    
    
    public String getLabel(){
        return label;
    }
    
    public String getQuery(){
        return predefinedQuery;
    }
    
    @Override
    public String toString(){
        return label;
    
    }

}


//SELECT LastName, FirstName, Title, Copyright, Titles.isbn FROM Titles INNER JOIN AuthorISBN ON Titles.isbn = AuthorISBN.isbn INNER JOIN Authors ON AuthorISBN.authorid = Authors.authorid WHERE LastName = 'Deitel' AND FirstName = 'Harvey' ORDER BY LastName, FirstName;
//SELECT LastName, FirstName FROM Titles INNER JOIN AuthorISBN ON Titles.isbn = AuthorISBN.isbn INNER JOIN Authors ON AuthorISBN.authorID = Authors.authorID WHERE Title = 'Java How to Program' ORDER BY LastName, FirstName;