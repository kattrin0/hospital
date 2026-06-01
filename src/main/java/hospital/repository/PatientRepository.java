package hospital.repository;

import hospital.model.Patient;

import java.util.List;


public interface PatientRepository extends Repository<Patient, Long> {
    List<Patient> findByDepartmentId(Long departmentId);

    List<Patient> findAllWithDepartment();
}