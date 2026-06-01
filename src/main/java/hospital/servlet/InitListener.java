package hospital.servlet;

import hospital.repository.DepartmentRepository;
import hospital.repository.PatientRepository;
import hospital.repository.jdbc.JdbcConnectionFactory;
import hospital.repository.jdbc.JdbcDepartmentRepository;
import hospital.repository.jdbc.JdbcPatientRepository;
import hospital.service.DepartmentService;
import hospital.service.PatientService;
import hospital.service.impl.DepartmentServiceImpl;
import hospital.service.impl.PatientServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        String url = context.getInitParameter("jdbc.url");
        if (url == null) {
            url = "jdbc:postgresql://localhost:5432/hospital";
        }

        String user = context.getInitParameter("jdbc.user");
        if (user == null) {
            user = "postgres";
        }

        String password = context.getInitParameter("jdbc.password");
        if (password == null) {
            password = "qwerty";
        }

        JdbcConnectionFactory connectionFactory = new JdbcConnectionFactory(url, user, password);

        DepartmentRepository departmentRepository = new JdbcDepartmentRepository(connectionFactory);
        PatientRepository patientRepository = new JdbcPatientRepository(connectionFactory);

        DepartmentService departmentService = new DepartmentServiceImpl(departmentRepository, patientRepository);
        PatientService patientService = new PatientServiceImpl(patientRepository, departmentRepository);

        context.setAttribute("departmentService", departmentService);
        context.setAttribute("patientService", patientService);

        System.out.println("Hospital Application Started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Hospital Application Stopped!");
    }
}