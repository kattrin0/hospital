package hospital.servlet;

import hospital.model.Department;
import hospital.service.DepartmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/departments/*")
public class DepartmentServlet extends HttpServlet {

    private DepartmentService departmentService;

    @Override
    public void init() {
        departmentService = (DepartmentService) getServletContext().getAttribute("departmentService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo) || "/list".equals(pathInfo)) {
            List<Department> departments = departmentService.getAllDepartments();
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("/WEB-INF/jsp/departments/list.jsp").forward(req, resp);

        } else if ("/add".equals(pathInfo)) {
            req.getRequestDispatcher("/WEB-INF/jsp/departments/form.jsp").forward(req, resp);

        } else if (pathInfo.startsWith("/edit/")) {
            Long id = Long.parseLong(pathInfo.substring(6));
            Department department = departmentService.getDepartmentById(id);
            req.setAttribute("department", department);
            req.getRequestDispatcher("/WEB-INF/jsp/departments/form.jsp").forward(req, resp);

        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if ("/add".equals(pathInfo)) {
            String name = req.getParameter("name");
            departmentService.createDepartment(name);
            resp.sendRedirect(req.getContextPath() + "/departments/list");

        } else if (pathInfo.startsWith("/edit/")) {
            Long id = Long.parseLong(pathInfo.substring(6));
            String name = req.getParameter("name");
            departmentService.updateDepartment(id, name);
            resp.sendRedirect(req.getContextPath() + "/departments/list");

        } else if (pathInfo.startsWith("/delete/")) {
            Long id = Long.parseLong(pathInfo.substring(8));
            try {
                departmentService.deleteDepartment(id);
                resp.sendRedirect(req.getContextPath() + "/departments/list");
            } catch (IllegalStateException e) {
                req.setAttribute("error", e.getMessage());
                List<Department> departments = departmentService.getAllDepartments();
                req.setAttribute("departments", departments);
                req.getRequestDispatcher("/WEB-INF/jsp/departments/list.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}