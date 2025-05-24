package model;

public class PartialUpdateBook {
    public static String partialUpdateBook(String title, String author , String isbn ,String releaseDate) {
        return "{\n" +
                " \"title\": \""+title+"\",\n" +
                " \"author\": \""+author+"\",\n" +
                "  \"isbn\": \""+isbn+"\",\n" +
                "  \"releaseDate\": \""+releaseDate+"\" \n" +
                "}";
    }
}
