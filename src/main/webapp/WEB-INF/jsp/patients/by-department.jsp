<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Пациенты по отделению</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1, h2 { color: #333; border-bottom: 2px solid #ddd; padding-bottom: 10px; }
        .form-group { margin: 20px 0; }
        label { font-weight: bold; margin-right: 10px; color: #333; }
        select { padding: 8px; width: 300px; border: 1px solid #ddd; border-radius: 3px; }
        button { padding: 8px 20px; background-color: #666; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background-color: #555; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #666; color: white; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .btn { display: inline-block; margin: 5px 5px 5px 0; padding: 8px 15px; text-decoration: none; background-color: #666; color: white; border-radius: 3px; }
        .btn:hover { background-color: #555; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Поиск пациентов по отделению</h1>

        <form method="post" action="${pageContext.request.contextPath}/patients/by-department" accept-charset="UTF-8">
            <div class="form-group">
                <label for="departmentId">Выберите отделение:</label>
                <select id="departmentId" name="departmentId" required>
                    <option value="">-- Выберите --</option>
                    <c:forEach items="${departments}" var="dept">
                        <option value="${dept.id}" ${selectedDeptId == dept.id ? 'selected' : ''}>
                            ${dept.name} (${dept.patientCount} пациентов)
                        </option>
                    </c:forEach>
                </select>
                <button type="submit">Показать</button>
            </div>
        </form>

        <c:if test="${not empty patients}">
            <h2>Список пациентов:</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>ФИО</th>
                        <th>Возраст</th>
                        <th>Пол</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${patients}" var="patient">
                        <tr>
                            <td>${patient.id}</td>
                            <td>${patient.fullName}</td>
                            <td>${patient.age}</td>
                            <td>${patient.gender}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <br>
        <a href="${pageContext.request.contextPath}/patients/list" class="btn">Назад к пациентам</a>
        <a href="${pageContext.request.contextPath}/" class="btn">На главную</a>
    </div>
</body>
</html>