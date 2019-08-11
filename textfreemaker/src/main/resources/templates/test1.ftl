<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
    <#list stu as stus>


<tr>
    <td>${stus.age}</td>
</tr>
    </#list>
<br/>
map遍历
${stuss['sutmap'].age}
</body>
</html>