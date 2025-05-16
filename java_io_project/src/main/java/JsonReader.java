import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class JsonReader { //не знаю зачем вообще тут он, но вроде по заданию его тоже надо было уметь реализовывать...
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Teacher> readTeachers(FileInputStream fis) throws IOException {
        return objectMapper.readValue(fis,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Teacher.class));
    }

    public static List<Group> readGroups(FileInputStream fis) throws IOException {
        return objectMapper.readValue(fis,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Group.class));
    }

    public static List<Student> readStudents(FileInputStream fis) throws IOException {
        return objectMapper.readValue(fis,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
    }

    public static List<Subject> readSubjects(FileInputStream fis) throws IOException {
        return objectMapper.readValue(fis,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Subject.class));
    }

    public static List<Score> readScores(FileInputStream fis) throws IOException {
        return objectMapper.readValue(fis,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Score.class));
    }

    public static Journal readJournal(FileInputStream fis) throws IOException {
        return objectMapper.readValue(fis, Journal.class);
    }
}