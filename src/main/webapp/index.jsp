<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Больничная система</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; }
        .container { max-width: 500px; margin: 100px auto; background: white; padding: 40px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); text-align: center; }
        h1 { color: #333; margin-bottom: 30px; }
        .menu { margin: 30px 0; }
        .menu a { display: block; margin: 15px 0; padding: 12px; font-size: 18px; text-decoration: none; background-color: #666; color: white; border-radius: 3px; transition: background-color 0.3s; }
        .menu a:hover { background-color: #555; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Система управления больницей</h1>
        <div class="menu">
            <a href="${pageContext.request.contextPath}/departments/list">Управление отделениями</a>
            <a href="${pageContext.request.contextPath}/patients/list">Управление пациентами</a>
        </div>
    </div>
</body>
</html>