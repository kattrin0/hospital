package hospital.controller;

import hospital.model.Department;
import hospital.model.Patient;
import hospital.service.DepartmentService;
import hospital.service.PatientService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HospitalConsoleController {
    private final DepartmentService departmentService;
    private final PatientService patientService;
    private final Scanner scanner;
    private final Map<String, Runnable> mainMenuCommands;
    private final Map<String, Runnable> departmentMenuCommands;
    private final Map<String, Runnable> patientMenuCommands;

    public HospitalConsoleController(DepartmentService departmentService,
                                     PatientService patientService) {
        this.departmentService = departmentService;
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
        this.mainMenuCommands = Map.of(
                "1", this::manageDepartments,
                "2", this::managePatients,
                "3", this::viewAllDepartmentsWithPatients,
                "4", this::viewAllPatientsWithDepartments
        );
        this.departmentMenuCommands = Map.of(
                "1", this::addDepartment,
                "2", this::updateDepartment,
                "3", this::deleteDepartment,
                "4", this::showAllDepartments
        );
        this.patientMenuCommands = Map.of(
                "1", this::addPatient,
                "2", this::updatePatient,
                "3", this::deletePatient,
                "4", this::showAllPatients,
                "5", this::showPatientsByDepartment
        );
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            String choice = scanner.nextLine();
            if ("0".equals(choice)) {
                exit = true;
                continue;
            }
            Runnable command = mainMenuCommands.get(choice);
            if (command != null) {
                command.run();
            } else {
                System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== СИСТЕМА УПРАВЛЕНИЯ БОЛЬНИЦЕЙ ===");
        System.out.println("1. Управление отделениями");
        System.out.println("2. Управление пациентами");
        System.out.println("3. Просмотр отделений с пациентами");
        System.out.println("4. Просмотр всех пациентов (с отделениями)");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void manageDepartments() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- УПРАВЛЕНИЕ ОТДЕЛЕНИЯМИ ---");
            System.out.println("1. Добавить отделение");
            System.out.println("2. Редактировать отделение");
            System.out.println("3. Удалить отделение");
            System.out.println("4. Показать все отделения");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) {
                back = true;
                continue;
            }
            Runnable command = departmentMenuCommands.get(choice);
            try {
                if (command != null) {
                    command.run();
                } else {
                    System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void addDepartment() {
        System.out.print("Введите название отделения: ");
        String name = scanner.nextLine();
        Department dept = departmentService.createDepartment(name);
        System.out.println("Отделение добавлено: " + dept);
    }

    private void updateDepartment() {
        System.out.print("Введите ID отделения для редактирования: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Введите новое название: ");
        String name = scanner.nextLine();
        Department dept = departmentService.updateDepartment(id, name);
        System.out.println("Отделение обновлено: " + dept);
    }

    private void deleteDepartment() {
        System.out.print("Введите ID отделения для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        departmentService.deleteDepartment(id);
        System.out.println("Отделение удалено");
    }

    private void showAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("Нет отделений");
        } else {
            departments.forEach(System.out::println);
        }
    }

    private void managePatients() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- УПРАВЛЕНИЕ ПАЦИЕНТАМИ ---");
            System.out.println("1. Добавить пациента");
            System.out.println("2. Редактировать пациента");
            System.out.println("3. Удалить пациента");
            System.out.println("4. Показать всех пациентов");
            System.out.println("5. Показать пациентов отделения");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) {
                back = true;
                continue;
            }
            Runnable command = patientMenuCommands.get(choice);
            try {
                if (command != null) {
                    command.run();
                } else {
                    System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void addPatient() {
        System.out.print("Введите ФИО пациента: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите пол (М/Ж): ");
        String gender = scanner.nextLine();
        System.out.print("Введите ID отделения: ");
        Long deptId = Long.parseLong(scanner.nextLine());

        Patient patient = patientService.createPatient(fullName, age, gender, deptId);
        System.out.println("Пациент добавлен: " + patient);
    }

    private void updatePatient() {
        System.out.print("Введите ID пациента для редактирования: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Введите ФИО (Enter - без изменений): ");
        String fullName = scanner.nextLine();
        System.out.print("Введите возраст (0 - без изменений): ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите пол (Enter - без изменений): ");
        String gender = scanner.nextLine();
        System.out.print("Введите ID отделения (Enter - без изменений): ");
        String deptInput = scanner.nextLine();
        Long deptId = deptInput.isEmpty() ? null : Long.parseLong(deptInput);

        Patient patient = patientService.updatePatient(id,
                fullName.isEmpty() ? null : fullName,
                age == 0 ? -1 : age,
                gender.isEmpty() ? null : gender,
                deptId);
        System.out.println("Пациент обновлен: " + patient);
    }

    private void deletePatient() {
        System.out.print("Введите ID пациента для удаления: ");
        Long id = Long.parseLong(scanner.nextLine());
        patientService.deletePatient(id);
        System.out.println("Пациент удален");
    }

    private void showAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("Нет пациентов");
        } else {
            patients.forEach(System.out::println);
        }
    }

    private void showPatientsByDepartment() {
        System.out.print("Введите ID отделения: ");
        Long deptId = Long.parseLong(scanner.nextLine());
        List<Patient> patients = patientService.getPatientsByDepartment(deptId);
        if (patients.isEmpty()) {
            System.out.println("В этом отделении нет пациентов");
        } else {
            patients.forEach(System.out::println);
        }
    }

    private void viewAllDepartmentsWithPatients() {
        System.out.println("\n=== ОТДЕЛЕНИЯ С ПАЦИЕНТАМИ ===");
        Map<Department, List<Patient>> deptWithPatients = departmentService.getDepartmentsWithPatients();

        if (deptWithPatients.isEmpty()) {
            System.out.println("Нет отделений");
            return;
        }

        deptWithPatients.forEach((dept, patients) -> {
            System.out.println("\n" + dept);
            if (patients.isEmpty()) {
                System.out.println("  Пациентов нет");
            } else {
                patients.forEach(p -> System.out.println("  - " + p.getFullName() +
                        " (" + p.getAge() + " лет, " + p.getGender() + ")"));
            }
        });
    }

    private void viewAllPatientsWithDepartments() {
        System.out.println("\n=== ВСЕ ПАЦИЕНТЫ (С ОТДЕЛЕНИЯМИ) ===");
        Map<Patient, String> patientsWithDept = patientService.getAllPatientsWithDepartment();

        if (patientsWithDept.isEmpty()) {
            System.out.println("Нет пациентов");
            return;
        }

        patientsWithDept.forEach((patient, deptName) -> {
            System.out.println(patient + " | Отделение: " + deptName);
        });
    }
}
