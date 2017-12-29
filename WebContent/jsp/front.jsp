<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${books}" var="item">
    ${item.getName()}<br>
</c:forEach>