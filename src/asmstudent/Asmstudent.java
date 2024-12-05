/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asmstudent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author PC
 */

class Student {
    private final String studentId;
    private final String studentName;
    private double studentMark;

    public Student(String id, String name, double mark) {
        this.studentId = id;
        this.studentName = name;
        this.studentMark = mark;
    }

    public String getId() {
        return studentId;
    }

    public String getName() {
        return studentName;
    }

    public double getMark() {
        return studentMark;
    }

    public void setMark(double mark) {
        this.studentMark = mark;
    }

    public String getRank() {
        if (studentMark < 5.0) return "Fail";
        if (studentMark < 6.5) return "Medium";
        if (studentMark < 7.5) return "Good";
        if (studentMark < 9.0) return "Very Good";
        return "Excellent";
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Mark: %.2f, Rank: %s", studentId, studentName, studentMark, getRank());
    }
}

class StudentQueue {
    private final Queue<Student> studentQueue;

    public StudentQueue() {
        studentQueue = new LinkedList<>();
    }

    public void enqueue(Student student) {
        studentQueue.add(student);
    }

    public Student dequeue() {
        return studentQueue.poll();
    }

    public boolean isEmpty() {
        return studentQueue.isEmpty();
    }

    public void listAllStudents() {
        if (isEmpty()) {
            System.out.println("No students found in the queue.");
        } else {
            System.out.println("Students in queue:");
            for (Student student : studentQueue) {
                System.out.println(student);
            }
        }
    }

    public Student findStudentById(String id) {
        for (Student student : studentQueue) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public void sortStudentsByMark() {
        List<Student> sortedList = new LinkedList<>(studentQueue);
        sortedList.sort((s1, s2) -> Double.compare(s1.getMark(), s2.getMark()));

        // Update the queue with sorted students
        studentQueue.clear();
        studentQueue.addAll(sortedList);
    }

    void enqueueAll(Queue<Student> tempQueue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

public class Asmstudent {
    private static final StudentQueue studentQueue = new StudentQueue();
    private static final Scanner inputScanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            displayMenu();

            int userChoice = getUserChoice();

            switch (userChoice) {
                case 1 -> addNewStudent();
                case 2 -> updateExistingStudentMark();
                case 3 -> removeStudent();
                case 4 -> {
                    studentQueue.sortStudentsByMark();
                    System.out.println("Students sorted by mark.");
                }
                case 5 -> searchStudentById();
                case 6 -> displayAllStudents();
                case 7 -> {
                    isRunning = false;
                    System.out.println("Exiting program. Goodbye!");
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        inputScanner.close(); // Close Scanner resource
    }

    private static void displayMenu() {
        System.out.println("\n--- Student Management Menu ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student Mark");
        System.out.println("3. Delete Student");
        System.out.println("4. Sort Students by Mark");
        System.out.println("5. Find Student by ID");
        System.out.println("6. List All Students");
        System.out.println("7. Exit");
        System.out.print("Please select an option: ");
    }

    private static int getUserChoice() {
        if (inputScanner.hasNextInt()) {
            int choice = inputScanner.nextInt();
            inputScanner.nextLine(); // Consume newline
            return choice;
        } else {
            inputScanner.nextLine(); // Clear invalid input
            return -1; // Invalid choice
        }
    }

    private static void addNewStudent() {
        System.out.print("Enter student ID: ");
        String id = inputScanner.nextLine();

        System.out.print("Enter student name: ");
        String name = inputScanner.nextLine();

        System.out.print("Enter student mark: ");
        if (inputScanner.hasNextDouble()) {
            double mark = inputScanner.nextDouble();
            inputScanner.nextLine(); // Consume newline
            studentQueue.enqueue(new Student(id, name, mark));
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Invalid input for mark. Please enter a valid number.");
            inputScanner.nextLine(); // Clear invalid input
        }
    }

    private static void updateExistingStudentMark() {
        System.out.print("Enter student ID: ");
        String id = inputScanner.nextLine();

        Student student = studentQueue.findStudentById(id);
        if (student != null) {
            System.out.print("Enter new mark: ");
            if (inputScanner.hasNextDouble()) {
                double mark = inputScanner.nextDouble();
                inputScanner.nextLine();
                student.setMark(mark);
                System.out.println("Mark updated successfully.");
            } else {
                System.out.println("Invalid input for mark. Please enter a valid number.");
                inputScanner.nextLine(); // Clear invalid input
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void removeStudent() {
        System.out.print("Enter student ID to remove: ");
        String id = inputScanner.nextLine();

        Queue<Student> tempQueue = new LinkedList<>();
        boolean isRemoved = false;

        while (!studentQueue.isEmpty()) {
            Student student = studentQueue.dequeue();
            if (!student.getId().equals(id)) {
                tempQueue.add(student);
            } else {
                isRemoved = true;
            }
        }

        studentQueue.enqueueAll(tempQueue);

        if (isRemoved) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void searchStudentById() {
        System.out.print("Enter student ID: ");
        String id = inputScanner.nextLine();

        Student student = studentQueue.findStudentById(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayAllStudents() {
        studentQueue.listAllStudents();
    }
}
