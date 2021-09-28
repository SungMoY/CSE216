public class Student implements Comparable<Student>{
    private String  name;
    private int     id;



    public Student(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Student o) {
        if (getId() > o.getId()){
            return 1;
        } else if (getId()< o.getId()){
            return -1;
        }
        return 0;
    }
}
