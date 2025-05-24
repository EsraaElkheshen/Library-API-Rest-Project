package model;

public class CreateBook {

    public static String createNewBook(String title, String author , String isbn ,String releaseDate) {
        return "{\n" +
                " \"title\": \""+title+"\",\n" +
                " \"author\": \""+author+"\",\n" +
                "  \"isbn\": \""+isbn+"\",\n" +
                "  \"releaseDate\": \""+releaseDate+"\" \n" +
                "}";
    }
}
