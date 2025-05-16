import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.OutputStream;
import java.util.List;

public class JsonWriter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeGroupsToJson(List<Group> groups, OutputStream os) throws Exception {
        mapper.writeValue(os, groups);
    }

    public static void writeStudentsToJson(List<Student> students, OutputStream os) throws Exception {
        mapper.writeValue(os, students);
    }

    public static void writeTeachersToJson(List<Teacher> teachers, OutputStream os) throws Exception {
        mapper.writeValue(os, teachers);
    }

    public static void writeSubjectsToJson(List<Subject> subjects, OutputStream os) throws Exception {
        mapper.writeValue(os, subjects);
    }

    public static void writeScoresToJson(List<Score> scores, OutputStream os) throws Exception {
        mapper.writeValue(os, scores);
    }


}