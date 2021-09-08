/**
 * This Student class contains the details of the Student information and
 * constructor to initialize the data.
 * */

public class Student {
    String studentName;
    int redId;
    double gpa;
  
    Student(String studentName, int redId, double gpa) {
      this.studentName = studentName;
      this.redId = redId;
      this.gpa = gpa;
    }
  
    public String getStudentName() {
      return studentName;
    }
  
    public int getRedId() {
      return redId;
    }
  
    public double getGpa() {
      return gpa;
    }
  
  }