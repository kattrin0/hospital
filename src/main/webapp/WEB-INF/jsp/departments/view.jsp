<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отделение: ${department.name}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1, h2 { color: #333; border-bottom: 2px solid #ddd; padding-bottom: 10px; }
        .dept-info { background-color: #e3f2fd; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #666; }
        .dept-info p { margin: 8px 0; }
        .dept-info strong { color: #333; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #666; color: white; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f5f5f5; }
        .btn { display: inline-block; margin: 5px 5px 5px 0; padding: 8px 15px; text-decoration: none; background-color: #666; color: white; border-radius: 3px; }
        .btn:hover { background-color: #555; }
        .empty-message { text-align: center; padding: 20px; color: #666; font-style: italic; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Отделение: ${department.name}</h1>

        <div class="dept-info">
            <p><strong>ID отделения:</strong> ${department.id}</p>
            <p><strong>Количество пациентов:</strong> ${department.patientCount}</p>
        </div>

        <h2>Список пациентов</h2>

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
                <c:if test="${empty patients}">
                    <tr>
                        <td colspan="4" class="empty-message">В этом отделении нет пациентов</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <br>
        <a href="${pageContext.request.contextPath}/departments/list" class="btn">Назад к списку отделений</a>
        <a href="${pageContext.request.contextPath}/" class="btn">На главную</a>
    </div>
</body>
</html>