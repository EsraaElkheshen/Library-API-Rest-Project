package model;

public class UpdateBook {
    public static String updateBook(String title, String author , String isbn ,String releaseDate) {
        return "{\n" +
                " \"title\": \""+title+"\",\n" +
                " \"author\": \""+author+"\",\n" +
                "  \"isbn\": \""+isbn+"\",\n" +
                "  \"releaseDate\": \""+releaseDate+"\" \n" +
                "}";
    }
    }
