package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utilities {

    private static final String[] FIRST_NAMES = {
            "Ahmed", "Sara", "John", "Emily", "Ali", "Laila", "Michael", "Zainab", "David", "Noor"
    };

    private static final String[] LAST_NAMES = {
            "Mohamed", "Smith", "Johnson", "Brown", "Khan", "Ali", "Yousef", "Hassan", "Williams", "Ibrahim"
    };

    private static final String[] ADJECTIVES = {
            "Amazing", "Incredible", "Mysterious", "Dark", "Hidden", "Lost", "Brilliant", "Silent"
    };

    private static final String[] NOUNS = {
            "Journey", "Empire", "Truth", "Legacy", "Forest", "Dream", "Shadow", "Secret"
    };

    public static String generateRandomTitle() {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        return adjective + " " + noun;
    }

    public static String getRandomIsbn() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    public static String getRandomAuthor() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        return firstName;
    }

    public static Date getRandomDate(Date startInclusive, Date endExclusive) {

        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
        return new Date(randomMillis);
    }

    public static String getSingleJsonData(String jsonFilePath, String jsonKey) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(jsonFilePath);
        Object obj = jsonParser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject.get(jsonKey).toString();
    }

    public static String getExcelData(int RowNum, int ColNum, String SheetName) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String projectPath = System.getProperty("user.dir");
        String cellData = null;
        try {
            workBook = new XSSFWorkbook(projectPath + "\\src\\test\\resources\\testData\\data.xlsx");
            sheet = workBook.getSheet(SheetName);
            cellData = sheet.getRow(RowNum).getCell(ColNum).getStringCellValue();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return cellData;
    }

    public static String getExcelDataWithDate(int RowNum, int ColNum, String SheetName) {
        String projectPath = System.getProperty("user.dir");
        String cellData = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (FileInputStream fis = new FileInputStream(projectPath + "\\src\\test\\resources\\testData\\data.xlsx");
             XSSFWorkbook workBook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workBook.getSheet(SheetName);
            Cell cell = sheet.getRow(RowNum).getCell(ColNum);

            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    // Format the date
                    cellData = sdf.format(cell.getDateCellValue());
                } else {
                    cell.setCellType(CellType.STRING);
                    cellData = cell.getStringCellValue();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellData;
    }


    public static Map<String, String> readExcelAsMap(String filePath) {
        Map<String, String> data = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // format to return
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell keyCell = row.getCell(0);
                Cell valueCell = row.getCell(1);
                if (keyCell != null && valueCell != null) {
                    keyCell.setCellType(CellType.STRING);
                    String key = keyCell.getStringCellValue().trim();
                    String value;
                    if (valueCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(valueCell)) {
                        // it's a date - format it
                        value = dateFormat.format(valueCell.getDateCellValue());
                    } else {
                        // fallback to string
                        valueCell.setCellType(CellType.STRING);
                        value = valueCell.getStringCellValue().trim();
                    }

                    data.put(key, value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }



    public static Object[][] getBookData() throws Exception {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\dataprovider.xlsx";
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Row headerRow = sheet.getRow(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = headerRow.getPhysicalNumberOfCells();

        List<Object[]> data = new ArrayList<>();

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Map<String, String> requestMap = new HashMap<>();

            for (int j = 0; j < colCount; j++) {
                String key = headerRow.getCell(j).getStringCellValue();
                Cell cell = row.getCell(j);
                String value;

                if (cell == null) {
                    value = "";
                } else if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                    value = sdf.format(cell.getDateCellValue()); // format date
                } else {
                    cell.setCellType(CellType.STRING);
                    value = cell.getStringCellValue();
                }

                requestMap.put(key, value);
            }

            data.add(new Object[]{requestMap});
        }

        workbook.close();
        fis.close();
        return data.toArray(new Object[0][0]);
    }

    public static Object[][] readJsonData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/testData/data-Provider.json");

        List<Map<String, String>> dataList = mapper.readValue(file, new TypeReference<List<Map<String, String>>>() {});
        Object[][] result = new Object[dataList.size()][1];

        for (int i = 0; i < dataList.size(); i++) {
            result[i][0] = dataList.get(i);
        }

        return result;
    }

    public static String generateRandomEmail() {
        String[] domains = {"example.com", "test.com", "mail.com", "mydomain.org"};
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder localPart = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            localPart.append(characters.charAt(random.nextInt(characters.length())));
        }

        String domain = domains[random.nextInt(domains.length)];
        return localPart + "@" + domain;
    }

    public static String generateRandomFirstName() {
        String[] firstNames = {"Ali", "Sara", "Omar", "Laila", "Khalid", "Noor", "Tariq", "Amina", "Fadi", "Mona"};
        int index = (int) (Math.random() * firstNames.length);
        return firstNames[index];
    }
    public static String generateRandomLastName() {
        String[] lastNames = {"Ali", "Sara", "Omar", "Laila", "Khalid", "Noor", "Tariq", "Amina", "Fadi", "Mona"};
        int index = (int) (Math.random() * lastNames.length);
        return lastNames[index];
    }


}



