public class Journal {
    private Teacher teacher;
    private Group group;
    private Subject subject;

    public Journal() {}
    public Journal(Teacher teacher, Group group, Subject subject) {
        this.teacher = teacher;
        this.group = group;
        this.subject = subject;
    }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
}