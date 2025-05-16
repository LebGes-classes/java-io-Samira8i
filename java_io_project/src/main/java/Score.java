public class Score {
    private int id;
    private String name;
    private int studentId;
    private int subjectId;

    public Score() {}
    public Score(int id, String name, int studentId, int subjectId) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
}