package hospital.servlet;

import hospital.model.Patient;
import hospital.service.PatientService;
import hospital.service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/patients/*")
public class PatientServlet extends HttpServlet {

    private PatientService patientService;
    private DepartmentService departmentService;

    @Override
    public void init() {
        patientService = (PatientService) getServletContext().getAttribute("patientService");
        departmentService = (DepartmentService) getServletContext().getAttribute("departmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo) || "/list".equals(pathInfo)) {
            Map<Patient, String> patientsWithDept = patientService.getAllPatientsWithDepartment();
            req.setAttribute("patientsWithDept", patientsWithDept);
            req.getRequestDispatcher("/WEB-INF/jsp/patients/list.jsp").forward(req, resp);

        } else if ("/add".equals(pathInfo)) {
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.getRequestDispatcher("/WEB-INF/jsp/patients/form.jsp").forward(req, resp);

        } else if (pathInfo.startsWith("/edit/")) {
            Long id = Long.parseLong(pathInfo.substring(6));
            Patient patient = patientService.getPatientById(id);
            req.setAttribute("patient", patient);
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.getRequestDispatcher("/WEB-INF/jsp/patients/form.jsp").forward(req, resp);

        } else if ("/by-department".equals(pathInfo)) {
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.getRequestDispatcher("/WEB-INF/jsp/patients/by-department.jsp").forward(req, resp);

        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if ("/add".equals(pathInfo)) {
            String fullName = req.getParameter("fullName");
            int age = Integer.parseInt(req.getParameter("age"));
            String gender = req.getParameter("gender");
            Long departmentId = Long.parseLong(req.getParameter("departmentId"));

            patientService.createPatient(fullName, age, gender, departmentId);
            resp.sendRedirect(req.getContextPath() + "/patients/list");

        } else if (pathInfo.startsWith("/edit/")) {
            Long id = Long.parseLong(pathInfo.substring(6));
            String fullName = req.getParameter("fullName");
            int age = Integer.parseInt(req.getParameter("age"));
            String gender = req.getParameter("gender");
            String departmentIdParam = req.getParameter("departmentId");
            Long departmentId = departmentIdParam != null && !departmentIdParam.isEmpty()
                    ? Long.parseLong(departmentIdParam) : null;

            patientService.updatePatient(id, fullName, age, gender, departmentId);
            resp.sendRedirect(req.getContextPath() + "/patients/list");

        } else if (pathInfo.startsWith("/delete/")) {
            Long id = Long.parseLong(pathInfo.substring(8));
            patientService.deletePatient(id);
            resp.sendRedirect(req.getContextPath() + "/patients/list");

        } else if ("/by-department".equals(pathInfo)) {
            Long departmentId = Long.parseLong(req.getParameter("departmentId"));
            List<Patient> patients = patientService.getPatientsByDepartment(departmentId);
            req.setAttribute("patients", patients);
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.setAttribute("selectedDeptId", departmentId);
            req.getRequestDispatcher("/WEB-INF/jsp/patients/by-department.jsp").forward(req, resp);

        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}