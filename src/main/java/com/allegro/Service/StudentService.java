package com.allegro.Service;

import com.allegro.Entity.Student;
import com.allegro.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void addStudent(){
        Student student = new Student("e", "f");
        studentRepository.insert(student);
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
}
