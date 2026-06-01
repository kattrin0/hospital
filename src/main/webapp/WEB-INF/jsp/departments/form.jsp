<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${empty department}">Добавление отделения</c:when>
            <c:otherwise>Редактирование отделения</c:otherwise>
        </c:choose>
    </title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .container { max-width: 500px; margin: 0 auto; background: white; padding: 30px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        h1 { color: #333; border-bottom: 2px solid #ddd; padding-bottom: 10px; }
        .form-group { margin: 20px 0; }
        label { display: inline-block; width: 150px; font-weight: bold; color: #333; }
        input[type="text"] { padding: 8px; width: 250px; border: 1px solid #ddd; border-radius: 3px; }
        button { padding: 10px 25px; background-color: #666; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background-color: #555; }
        .btn { display: inline-block; margin-left: 10px; padding: 10px 25px; text-decoration: none; background-color: #999; color: white; border-radius: 3px; }
        .btn:hover { background-color: #888; }
    </style>
</head>
<body>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${empty department}">Добавление отделения</c:when>
                <c:otherwise>Редактирование отделения</c:otherwise>
            </c:choose>
        </h1>

        <form action="${pageContext.request.contextPath}/departments/${empty department ? 'add' : 'edit/'.concat(department.id)}"
              method="post" accept-charset="UTF-8">
            <div class="form-group">
                <label for="name">Название отделения:</label>
                <input type="text" id="name" name="name" value="${department.name}" required>
            </div>
            <div class="form-group">
                <button type="submit">Сохранить</button>
                <a href="${pageContext.request.contextPath}/departments/list" class="btn">Отмена</a>
            </div>
        </form>
    </div>
</body>
</html>