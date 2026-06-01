<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${empty patient}">Добавление пациента</c:when>
            <c:otherwise>Редактирование пациента</c:otherwise>
        </c:choose>
    </title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 500px; margin: 0 auto; background: white; padding: 30px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1 { color: #333; border-bottom: 2px solid #ddd; padding-bottom: 10px; margin-bottom: 20px; }
        .form-group { margin: 20px 0; }
        label { display: inline-block; width: 120px; font-weight: bold; color: #333; }
        input, select { padding: 8px; width: 250px; border: 1px solid #ddd; border-radius: 3px; }
        button { padding: 10px 25px; background-color: #666; color: white; border: none; border-radius: 3px; cursor: pointer; font-size: 14px; }
        button:hover { background-color: #555; }
        .btn { display: inline-block; margin-left: 10px; padding: 10px 25px; text-decoration: none; background-color: #999; color: white; border-radius: 3px; }
        .btn:hover { background-color: #888; }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${empty patient}">Добавление пациента</c:when>
                <c:otherwise>Редактирование пациента</c:otherwise>
            </c:choose>
        </h1>

        <form action="${pageContext.request.contextPath}/patients/${empty patient ? 'add' : 'edit/'.concat(patient.id)}"
              method="post" accept-charset="UTF-8">
            <div class="form-group">
                <label for="fullName">ФИО:</label>
                <input type="text" id="fullName" name="fullName" value="${patient.fullName}" required>
            </div>
            <div class="form-group">
                <label for="age">Возраст:</label>
                <input type="number" id="age" name="age" value="${patient.age}" min="1" max="150" required>
            </div>
            <div class="form-group">
                <label for="gender">Пол:</label>
                <select id="gender" name="gender" required>
                    <option value="">Выберите</option>
                    <option value="MALE" ${patient.gender == 'MALE' ? 'selected' : ''}>Мужской</option>
                    <option value="FEMALE" ${patient.gender == 'FEMALE' ? 'selected' : ''}>Женский</option>
                </select>
            </div>
            <div class="form-group">
                <label for="departmentId">Отделение:</label>
                <select id="departmentId" name="departmentId" required>
                    <option value="">Выберите отделение</option>
                    <c:forEach items="${departments}" var="dept">
                        <option value="${dept.id}" ${patient.departmentId == dept.id ? 'selected' : ''}>
                            ${dept.name} (${dept.patientCount} пациентов)
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <button type="submit">Сохранить</button>
                <a href="${pageContext.request.contextPath}/patients/list" class="btn">Отмена</a>
            </div>
        </form>
    </div>
</body>
</html>