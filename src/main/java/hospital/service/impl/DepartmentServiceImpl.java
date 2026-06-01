package hospital.service.impl;

import hospital.model.Department;
import hospital.model.Patient;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import hospital.service.DepartmentService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 PatientRepository patientRepository) {
        this.departmentRepository = departmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Department createDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название отделения не может быть пустым");
        }
        Department department = new Department();
        department.setName(name);
        department.setPatientCount(0);
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, String name) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Отделение не найдено"));

        if (name != null && !name.trim().isEmpty()) {
            department.setName(name);
        }
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Отделение не найдено");
        }

        List<Patient> patients = patientRepository.findByDepartmentId(id);
        if (!patients.isEmpty()) {
            throw new IllegalStateException("Нельзя удалить отделение с пациентами");
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Отделение не найдено"));
    }

    @Override
    public Map<Department, List<Patient>> getDepartmentsWithPatients() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .collect(Collectors.toMap(
                        dept -> dept,
                        dept -> patientRepository.findByDepartmentId(dept.getId())
                ));
    }
}