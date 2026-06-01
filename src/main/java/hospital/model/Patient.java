package hospital.model;

import java.util.Objects;

public class Patient {
    private Long id;
    private String fullName;
    private int age;
    private String gender;
    private Long departmentId;

    public Patient() {
    }

    public Patient(Long id, String fullName, int age, String gender, Long departmentId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.departmentId = departmentId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | ФИО: %s | Возраст: %d | Пол: %s | Отделение ID: %d",
                id, fullName, age, gender, departmentId);
    }
}