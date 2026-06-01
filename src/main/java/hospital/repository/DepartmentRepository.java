package hospital.repository;

import hospital.model.Department;

public interface DepartmentRepository extends Repository<Department, Long> {
    void updatePatientCount(Long departmentId, int delta);
}