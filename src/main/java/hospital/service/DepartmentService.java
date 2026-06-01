package hospital.service;

import hospital.model.Department;
import hospital.model.Patient;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    Department createDepartment(String name);

    Department updateDepartment(Long id, String name);

    void deleteDepartment(Long id);

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Map<Department, List<Patient>> getDepartmentsWithPatients();
}