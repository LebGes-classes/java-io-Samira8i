import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        MainApp.setStudents(List.of());
        MainApp.setGroups(List.of());
        MainApp.setTeachers(List.of());
        MainApp.setJournals(List.of());
        MainApp.setScores(List.of());
        MainApp.setSubjects(List.of());
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testShowGradesWithStudentFound() {
        Student student = new Student(1, "Иванов", 101);
        Score score = new Score(1, "A", 1, 1);
        Subject subject = new Subject(1, "Математика");

        student.setScores(List.of(score));
        MainApp.setStudents(List.of(student));
        MainApp.setSubjects(List.of(subject));
        String input = "1\nИванов\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainApp.main(new String[]{});

        assertTrue(outContent.toString().contains("Студент: Иванов"));
        assertTrue(outContent.toString().contains("Математика: A"));
    }


    @Test
    void testAddScore() {
        Student student = new Student(1, "Иванов", 101);
        Subject subject = new Subject(1, "Математика");

        MainApp.setStudents(List.of(student));
        MainApp.setSubjects(List.of(subject));
        MainApp.setScores(List.of());

        String input = "2\n1\n1\nA\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainApp.main(new String[]{});

        assertEquals(1, MainApp.getScores().size());
        assertEquals("A", MainApp.getScores().get(0).getName());
        assertTrue(outContent.toString().contains("Оценка добавлена!"));
    }

    @Test
    void testShowStudentGroup() {
        Student student1 = new Student(1, "Иванов", 101);
        Student student2 = new Student(2, "Петров", 101);
        Group group = new Group(101, "Группа 101");

        group.setStudents(List.of(student1, student2));
        MainApp.setStudents(List.of(student1, student2));
        MainApp.setGroups(List.of(group));

        String input = "3\nИванов\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainApp.main(new String[]{});

        assertTrue(outContent.toString().contains("Студент Иванов находится в группе: Группа 101"));
        assertTrue(outContent.toString().contains("Петров"));
    }

    @Test
    void testShowTeacherInfo() {
        Teacher teacher = new Teacher(1, "Смирнова");
        Group group = new Group(101, "Группа 101");
        Subject subject = new Subject(1, "Математика");
        Journal journal = new Journal(teacher, group, subject);

        MainApp.setTeachers(List.of(teacher));
        MainApp.setJournals(List.of(journal));

        String input = "4\nСмирнова\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainApp.main(new String[]{});

        assertTrue(outContent.toString().contains("Преподаватель: Смирнова"));
        assertTrue(outContent.toString().contains("Предмет: Математика"));
        assertTrue(outContent.toString().contains("Группа: Группа 101"));
    }

    @Test
    void testShowTeacherInfoNotFound() {
        MainApp.setTeachers(List.of(new Teacher(1, "Иванова")));

        String input = "4\nСмирнова\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainApp.main(new String[]{});

        assertTrue(outContent.toString().contains("Преподаватель не найден"));
    }
}