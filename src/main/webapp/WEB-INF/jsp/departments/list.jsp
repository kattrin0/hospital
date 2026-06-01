<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отделения больницы</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1 { color: #333; border-bottom: 2px solid #ddd; padding-bottom: 10px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #666; color: white; font-weight: bold; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .btn { display: inline-block; margin: 5px 5px 5px 0; padding: 8px 15px; text-decoration: none; background-color: #666; color: white; border-radius: 3px; }
        .btn:hover { background-color: #555; }
        .btn-delete { background-color: #dc3545; }
        .btn-delete:hover { background-color: #c82333; }
        .error { color: #dc3545; padding: 10px; background-color: #ffe6e6; margin: 10px 0; border-radius: 3px; }
        .actions { white-space: nowrap; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Управление отделениями</h1>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div>
            <a href="${pageContext.request.contextPath}/departments/add" class="btn">Добавить отделение</a>
            <a href="${pageContext.request.contextPath}/" class="btn">На главную</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Название отделения</th>
                    <th>Кол-во пациентов</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${departments}" var="dept">
                    <tr>
                        <td>${dept.id}</td>
                        <td>${dept.name}</td>
                        <td>${dept.patientCount}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/departments/edit/${dept.id}" class="btn">Редактировать</a>
                            <a href="${pageContext.request.contextPath}/department-view/${dept.id}" class="btn">Просмотр</a>
                            <a href="${pageContext.request.contextPath}/departments/delete/${dept.id}" class="btn btn-delete" onclick="return confirm('Удалить отделение ${dept.name}? Пациентов в нем быть не должно!')">Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty departments}">
                    <tr>
                        <td colspan="4" style="text-align: center;">Нет отделений</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>