import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
public class ExcelReader {

    public static List<Student> readStudents(InputStream is) throws Exception {
        List<Student> students = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet("Students");

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            students.add(new Student(
                    (int)row.getCell(0).getNumericCellValue(),
                    row.getCell(1).getStringCellValue(),
                    (int)row.getCell(2).getNumericCellValue()
            ));
        }

        workbook.close();
        return students;
    }

    public static List<Teacher> readTeachers(InputStream is) throws Exception {
        List<Teacher> teachers = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet("Teachers");

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            teachers.add(new Teacher(
                    (int)row.getCell(0).getNumericCellValue(),
                    row.getCell(1).getStringCellValue()
            ));
        }

        workbook.close();
        return teachers;
    }

    public static List<Group> readGroups(InputStream is) throws Exception {
        List<Group> groups = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet("Groups");

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            groups.add(new Group(
                    (int)row.getCell(0).getNumericCellValue(),
                    row.getCell(1).getStringCellValue()
            ));
        }

        workbook.close();
        return groups;
    }

    public static List<Subject> readSubjects(InputStream is) throws Exception {
        List<Subject> subjects = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet("Subjects");

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            subjects.add(new Subject(
                    (int)row.getCell(0).getNumericCellValue(),
                    row.getCell(1).getStringCellValue()
            ));
        }

        workbook.close();
        return subjects;
    }

    public static List<Score> readScores(InputStream is) throws Exception {
        List<Score> scores = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet("Scores");

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            scores.add(new Score(
                    (int)row.getCell(0).getNumericCellValue(),
                    row.getCell(1).getStringCellValue(),
                    (int)row.getCell(2).getNumericCellValue(),
                    (int)row.getCell(3).getNumericCellValue()
            ));
        }

        workbook.close();
        return scores;
    }

    public static Journal readJournal(InputStream is,
                                      List<Teacher> teachers,
                                      List<Group> groups,
                                      List<Subject> subjects) throws Exception {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheet("Journal");

        if (sheet == null || sheet.getPhysicalNumberOfRows() <= 1) {
            workbook.close();
            throw new RuntimeException("Лист Journal не найден или пуст");
        }

        Row row = sheet.getRow(1);
        if (row == null) {
            workbook.close();
            throw new RuntimeException("Нет данных в листе Journal");
        }

        int teacherId = (int)row.getCell(0).getNumericCellValue();
        int groupId = (int)row.getCell(1).getNumericCellValue();
        int subjectId = (int)row.getCell(2).getNumericCellValue();

        Teacher teacher = findById(teachers, teacherId);
        Group group = findById(groups, groupId);
        Subject subject = findById(subjects, subjectId);

        workbook.close();
        return new Journal(teacher, group, subject);
    }
    private static <T> T findById(List<T> list, int id) {
        for (T item : list) {
            try {
                if ((int)item.getClass().getMethod("getId").invoke(item) == id) {
                    return item;
                }
            } catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException("Object with id " + id + " not found");
    }
}