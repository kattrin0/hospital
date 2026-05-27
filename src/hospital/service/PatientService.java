package hospital.service;

import hospital.model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService {
    Patient createPatient(String fullName, int age, String gender, Long departmentId);

    Patient updatePatient(Long id, String fullName, int age, String gender, Long departmentId);

    void deletePatient(Long id);

    List<Patient> getAllPatients();

    Patient getPatientById(Long id);

    List<Patient> getPatientsByDepartment(Long departmentId);

    Map<Patient, String> getAllPatientsWithDepartment();
}