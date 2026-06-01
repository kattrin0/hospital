package hospital.model;

import java.util.Objects;

public class Department {
    private Long id;
    private String name;
    private int patientCount;

    public Department() {
    }

    public Department(Long id, String name, int patientCount) {
        this.id = id;
        this.name = name;
        this.patientCount = patientCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Название: %s | Пациентов: %d",
                id, name, patientCount);
    }
}