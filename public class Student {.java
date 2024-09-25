public class Student {
    private int id;
    private String name;
    private double gpa;

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public String toString() {
        return "Student [ID=" + id + ", Name=" + name + ", GPA=" + gpa + "]";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}

class StudentManager {
    private Map<Integer, Student> studentMap;

    public StudentManager() {
        studentMap = new HashMap<>();
    }

    public void addStudent(Student student) {
        studentMap.put(student.getId(), student);
    }

    public Map<Integer, Student> getStudentMap() {
        return studentMap;
    }
}

class SortByGPA implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getGpa(), s1.getGpa()); 
    }
}

class SortByName implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return s1.getName().compareTo(s2.getName());
    }
}
interface StudentFilter {
    boolean filter(Student student);
}

public class StudentProcessor {

    public List<Student> filterStudents(Map<Integer, Student> students, StudentFilter filter) {
        return students.values().stream()
                .filter(filter::filter)
                .collect(Collectors.toList());
    }

    public void displayStudents(List<Student> students) {
        students.forEach(System.out::println);
    }
}


class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        manager.addStudent(new Student(1, "Alice", 3.8));
        manager.addStudent(new Student(2, "Bob", 3.5));
        manager.addStudent(new Student(3, "Charlie", 3.9));
        manager.addStudent(new Student(4, "David", 2.8));
        Map<Integer, Student> students = manager.getStudentMap();
        System.out.println("Students sorted by GPA:");
        students.values().stream()
                .sorted(new SortByGPA())
                .forEach(System.out::println);

        System.out.println("\nStudents sorted by Name:");
        students.values().stream()
                .sorted(new SortByName())
                .forEach(System.out::println);

        System.out.println("\nStudents with GPA > 3.0:");
        StudentProcessor processor = new StudentProcessor();
        List<Student> filteredStudents = processor.filterStudents(students, s -> s.getGpa() > 3.0);
        processor.displayStudents(filteredStudents);
    }
}