import java.io.*;
import java.util.*;

public class MainApp {
    private static final String JSON_DIR = "json_output/";
    private static final String EXCEL_FILE = "journal_data.xlsx";
    private static List<Student> students;
    private static List<Score> scores;
    private static List<Subject> subjects;
    private static List<Teacher> teachers;
    private static List<Group> groups;
    private static List<Journal> journals;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // загружаю все из эксельки
            students = ExcelReader.readStudents(new FileInputStream(EXCEL_FILE));
            scores = ExcelReader.readScores(new FileInputStream(EXCEL_FILE));
            subjects = ExcelReader.readSubjects(new FileInputStream(EXCEL_FILE));
            teachers = ExcelReader.readTeachers(new FileInputStream(EXCEL_FILE));
            groups = ExcelReader.readGroups(new FileInputStream(EXCEL_FILE));
            journals = new ArrayList<>();

            try {
                journals.add(ExcelReader.readJournal(new FileInputStream(EXCEL_FILE), teachers, groups, subjects));
            } catch (Exception e) {
                System.out.println("Ошибка при загрузке журнала: " + e.getMessage());
            }

            // оценки студентов
            for (Student student : students) {
                List<Score> studentScores = new ArrayList<>();
                for (Score score : scores) {
                    if (score.getStudentId() == student.getId()) {
                        studentScores.add(score);
                    }
                }
                student.setScores(studentScores);
            }

            // группы студентов
            for (Group group : groups) {
                List<Student> groupStudents = new ArrayList<>();
                for (Student student : students) {
                    if (group.getId() == student.getGroupId()) {
                        groupStudents.add(student);
                    }
                }
                group.setStudents(groupStudents);
            }

            runMenu();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void runMenu() throws Exception {
        while (true) {
            System.out.println("\n1. Найти студента\n2. Добавить оценку\n3. Просмотреть группу студента\n4. Просмотреть информацию о преподавателе\n5. Выход");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                showGrades();
            } else if (choice.equals("2")) {
                addScore();
            } else if (choice.equals("3")) {
                showStudentGroup();
            } else if (choice.equals("4")) {
                showTeacherInfo();
            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("Неверный ввод!");
            }
        }
    }

    private static void showGrades() {
        System.out.print("Введите фамилию: ");
        String name = scanner.nextLine();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("\nСтудент: " + student.getName());

                if (student.getScores().isEmpty()) {
                    System.out.println("Нет оценок");
                    continue;
                }

                for (Score score : student.getScores()) {
                    String subjectName = "Неизвестный предмет";
                    for (Subject subject : subjects) {
                        if (subject.getId() == score.getSubjectId()) {
                            subjectName = subject.getName();
                            break;
                        }
                    }
                    System.out.println(subjectName + ": " + score.getName());
                }
            }
        }
    }

    private static void addScore() throws Exception {
        System.out.println("\nСтуденты:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i+1) + ". " + students.get(i).getName());
        }

        System.out.print("Выберите студента (номер): ");
        int studentNum = Integer.parseInt(scanner.nextLine()) - 1;

        System.out.println("Предметы:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i+1) + ". " + subjects.get(i).getName());
        }

        System.out.print("Выберите предмет (номер): ");
        int subjectNum = Integer.parseInt(scanner.nextLine()) - 1;

        System.out.print("Введите оценку: ");
        String grade = scanner.nextLine();

        Score newScore = new Score(
                scores.size() + 1,
                grade,
                students.get(studentNum).getId(),
                subjects.get(subjectNum).getId()
        );

        scores.add(newScore);
        students.get(studentNum).getScores().add(newScore);

        saveData();
        System.out.println("Оценка добавлена!");
    }

    private static void showStudentGroup() {
        System.out.print("Введите фамилию студента: ");
        String name = scanner.nextLine();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                Group studentGroup = null;
                for (Group group : groups) {
                    if (group.getId() == student.getGroupId()) {
                        studentGroup = group;
                        break;
                    }
                }

                if (studentGroup != null) {
                    System.out.println("\nСтудент " + student.getName() + " находится в группе: " + studentGroup.getName());
                    System.out.println("Одногруппники:");
                    for (Student groupMate : studentGroup.getStudents()) {
                        if (groupMate.getId() != student.getId()) {
                            System.out.println("- " + groupMate.getName());
                        }
                    }
                } else {
                    System.out.println("Группа не найдена для студента " + student.getName());
                }
                return;
            }
        }
        System.out.println("Студент не найден");
    }

    private static void showTeacherInfo() {
        System.out.print("Введите фамилию преподавателя: ");
        String name = scanner.nextLine();


        Teacher foundTeacher = null;
        for (Teacher teacher : teachers) {
            if (teacher.getName().toLowerCase().contains(name.toLowerCase())) {
                foundTeacher = teacher;
                break;
            }
        }

        if (foundTeacher == null) {
            System.out.println("Преподаватель не найден");
            return;
        }

        System.out.println("\nПреподаватель: " + foundTeacher.getName());

        // Ищем журналы для этого преподавателя
        boolean hasInfo = false;
        for (Journal journal : journals) {
            if (journal.getTeacher().getId() == foundTeacher.getId()) {
                System.out.println("Предмет: " + journal.getSubject().getName());
                System.out.println("Группа: " + journal.getGroup().getName());
                hasInfo = true;
            }
        }

        if (!hasInfo) {
            System.out.println("Нет информации о преподаваемых предметах и группах");
        }
    }

    private static void saveData() throws Exception {
        JsonWriter.writeScoresToJson(scores, new FileOutputStream(JSON_DIR + "scores.json"));
    }
}