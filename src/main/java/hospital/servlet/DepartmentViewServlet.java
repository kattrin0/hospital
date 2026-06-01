package hospital.servlet;

import hospital.model.Department;
import hospital.model.Patient;
import hospital.service.DepartmentService;
import hospital.service.PatientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/department-view/*")
public class DepartmentViewServlet extends HttpServlet {

    private DepartmentService departmentService;
    private PatientService patientService;

    @Override
    public void init() {
        departmentService = (DepartmentService) getServletContext().getAttribute("departmentService");
        patientService = (PatientService) getServletContext().getAttribute("patientService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                Department department = departmentService.getDepartmentById(id);
                List<Patient> patients = patientService.getPatientsByDepartment(id);

                req.setAttribute("department", department);
                req.setAttribute("patients", patients);
                req.getRequestDispatcher("/WEB-INF/jsp/departments/view.jsp").forward(req, resp);

            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid department ID");
            } catch (IllegalArgumentException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            }
        } else {
            List<Department> departments = departmentService.getAllDepartments();
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("/WEB-INF/jsp/departments/list.jsp").forward(req, resp);
        }
    }
}