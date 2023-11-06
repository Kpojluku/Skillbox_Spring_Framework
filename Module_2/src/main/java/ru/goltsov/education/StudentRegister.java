package ru.goltsov.education;

import lombok.Getter;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class StudentRegister {

    private List<Student> list;

    public StudentRegister(String filePath) {
        this.list = readStudentsFromFile(filePath);
    }

    public StudentRegister() {
        this.list = new ArrayList<>();
    }

    public Student add(String firstName,
                       String lastName,
                       String age) {
        Student student = new Student(generateNewId(list), new String(firstName.getBytes(StandardCharsets.UTF_8)),
                lastName, Integer.parseInt(age));
        list.add(student);
        return student;
    }

    public boolean remove(String id) {

        Map<Integer, List<Student>> collect = list.stream().collect(Collectors.groupingBy(Student::getId));
        int key = Integer.parseInt(id);
        List<Student> studentByIdList = collect.get(key);
        if (!studentByIdList.isEmpty()) {
            return list.remove(studentByIdList.get(0));
        } else {
            return false;
        }

    }

    private List<Student> readStudentsFromFile(String fileName) {
        List<Student> lines = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            int i = 0;
            while (reader.ready()) {
                String[] split = reader.readLine().split(" ");
                lines.add(new Student(i, split[0], split[1], Integer.parseInt(split[2])));
                i++;
            }
        } catch (IOException e) {
            System.err.println("Проверьте файл со студентами на корректность");
            e.printStackTrace();
        }
        return lines;
    }

    private int generateNewId(List<Student> studentList){
        return studentList.stream()
                .max(Comparator.comparingInt(Student::getId))
                .map(o -> o.getId() + 1).orElse(0);
    }

    public void clear() {
        list.clear();
    }
}
