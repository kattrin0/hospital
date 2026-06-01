<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Все пациенты</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1 { color: #333; border-bottom: 2px solid #ddd; padding-bottom: 10px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #666; color: white; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        tr:hover { background-color: #f5f5f5; }
        .btn { display: inline-block; margin: 5px 5px 5px 0; padding: 8px 15px; text-decoration: none; background-color: #666; color: white; border-radius: 3px; font-size: 14px; border: none; cursor: pointer; }
        .btn:hover { background-color: #555; }
        .btn-delete { background-color: #dc3545; }
        .btn-delete:hover { background-color: #c82333; }
        .btn-primary { background-color: #666; }
        .actions { white-space: nowrap; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Все пациенты</h1>

        <div>
            <a href="${pageContext.request.contextPath}/patients/add" class="btn btn-primary">Добавить пациента</a>
            <a href="${pageContext.request.contextPath}/patients/by-department" class="btn btn-primary">Показать пациентов отделения</a>
            <a href="${pageContext.request.contextPath}/" class="btn">На главную</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ФИО</th>
                    <th>Возраст</th>
                    <th>Пол</th>
                    <th>Отделение</th>
                    <th>Действия</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${patientsWithDept}" var="entry">
                    <tr>
                        <td>${entry.key.id}</td>
                        <td>${entry.key.fullName}</td>
                        <td>${entry.key.age}</td>
                        <td>${entry.key.gender}</td>
                        <td>${entry.value}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/patients/edit/${entry.key.id}" class="btn btn-primary">Редактировать</a>
                            <a href="${pageContext.request.contextPath}/patients/delete/${entry.key.id}" class="btn btn-delete" onclick="return confirm('Удалить пациента ${entry.key.fullName}?')">Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty patientsWithDept}">
                    <tr>
                        <td colspan="6" style="text-align: center;">Нет пациентов</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>