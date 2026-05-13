package hospital;

import hospital.controller.HospitalConsoleController;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import hospital.repository.impl.InMemoryDepartmentRepository;
import hospital.repository.impl.InMemoryPatientRepository;
import hospital.service.DepartmentService;
import hospital.service.PatientService;
import hospital.service.impl.DepartmentServiceImpl;
import hospital.service.impl.PatientServiceImpl;

public class Main {
    public static void main(String[] args) {
        DepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
        PatientRepository patientRepository = new InMemoryPatientRepository();

        DepartmentService departmentService = new DepartmentServiceImpl(
                departmentRepository, patientRepository);
        PatientService patientService = new PatientServiceImpl(
                patientRepository, departmentRepository);

        HospitalConsoleController controller = new HospitalConsoleController(
                departmentService, patientService);

        controller.start();
    }
}