package hospital.service.impl;

import hospital.model.Patient;
import hospital.model.Department;
import hospital.repository.PatientRepository;
import hospital.repository.DepartmentRepository;
import hospital.service.PatientService;

import java.util.*;
import java.util.stream.Collectors;

public class PatientServiceImpl implements PatientService {

    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 150;
    private static final String MALE = "М";
    private static final String FEMALE = "Ж";

    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    public PatientServiceImpl(PatientRepository patientRepository,
                              DepartmentRepository departmentRepository) {
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Patient createPatient(String fullName, int age, String gender, Long departmentId) {
        validatePatientData(fullName, age, gender, departmentId);

        Patient patient = new Patient();
        patient.setFullName(fullName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setDepartmentId(departmentId);

        Patient saved = patientRepository.save(patient);
        departmentRepository.updatePatientCount(departmentId, 1);
        return saved;
    }

    @Override
    public Patient updatePatient(Long id, String fullName, int age, String gender, Long departmentId) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациент не найден"));

        Long oldDepartmentId = patient.getDepartmentId();

        if (fullName != null && !fullName.trim().isEmpty()) {
            patient.setFullName(fullName);
        }
        if (age > 0) {
            if (age < MIN_AGE || age > MAX_AGE) {
                throw new IllegalArgumentException("Некорректный возраст");
            }
            patient.setAge(age);
        }
        if (gender != null && !gender.trim().isEmpty()) {
            if (!gender.equals(MALE) && !gender.equals(FEMALE)) {
                throw new IllegalArgumentException("Пол должен быть М или Ж");
            }
            patient.setGender(gender);
        }
        if (departmentId != null && !departmentId.equals(oldDepartmentId)) {
            checkDepartmentExists(departmentId);
            patient.setDepartmentId(departmentId);

            departmentRepository.updatePatientCount(oldDepartmentId, -1);
            departmentRepository.updatePatientCount(departmentId, 1);
        }

        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациент не найден"));

        patientRepository.deleteById(id);
        departmentRepository.updatePatientCount(patient.getDepartmentId(), -1);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациент не найден"));
    }

    @Override
    public List<Patient> getPatientsByDepartment(Long departmentId) {
        checkDepartmentExists(departmentId);
        return patientRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Map<Patient, String> getAllPatientsWithDepartment() {
        return patientRepository.findAll().stream()
                .collect(Collectors.toMap(
                        patient -> patient,
                        patient -> departmentRepository.findById(patient.getDepartmentId())
                                .map(Department::getName)
                                .orElse("Неизвестно")
                ));
    }

    private void validatePatientData(String fullName, int age, String gender, Long departmentId) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("ФИО не может быть пустым");
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalArgumentException("Некорректный возраст");
        }
        if (gender == null || (!gender.equals(MALE) && !gender.equals(FEMALE))) {
            throw new IllegalArgumentException("Пол должен быть М или Ж");
        }
        checkDepartmentExists(departmentId);
    }

    private void checkDepartmentExists(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new IllegalArgumentException("Отделение не существует");
        }
    }
}