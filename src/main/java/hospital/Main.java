package hospital;

import hospital.controller.HospitalConsoleController;
import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import hospital.repository.jdbc.JdbcConnectionFactory;
import hospital.repository.jdbc.JdbcDepartmentRepository;
import hospital.repository.jdbc.JdbcPatientRepository;
import hospital.service.DepartmentService;
import hospital.service.PatientService;
import hospital.service.impl.DepartmentServiceImpl;
import hospital.service.impl.PatientServiceImpl;

public class Main {
    public static void main(String[] args) {
        JdbcConnectionFactory jdbc = new JdbcConnectionFactory(
                System.getProperty("hospital.jdbc.url", "jdbc:postgresql://localhost:5432/hospital"),
                System.getProperty("hospital.jdbc.user", "postgres"),
                System.getProperty("hospital.jdbc.password", "qwerty"));

        DepartmentRepository departmentRepository = new JdbcDepartmentRepository(jdbc);
        PatientRepository patientRepository = new JdbcPatientRepository(jdbc);

        DepartmentService departmentService = new DepartmentServiceImpl(
                departmentRepository, patientRepository);
        PatientService patientService = new PatientServiceImpl(
                patientRepository, departmentRepository);

        HospitalConsoleController controller = new HospitalConsoleController(
                departmentService, patientService);

        controller.start();
    }
}