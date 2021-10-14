<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
	<jsp:useBean id="resvc" class='com.record.model.RecordService'/>
	<jsp:useBean id="rsvc" class='com.route.model.RouteService'/>
<body>


<div class="row" style="margin-right: -21px">
    <div class="col-12" id="ReviewTableBody" style="overflow-x: hidden ; overflow-y: scroll; height:760px">
        <table class="table reviewBody text-center table-striped">
            <thead class="thead-light">
            <tr>
                <th scope="col" class="titleItem text-nowrap">路線名稱</th>
                <th scope="col" class="titleItem text-nowrap">騎乘距離</th>
                <th scope="col" class="titleItem text-nowrap">海拔高度</th>
                <th scope="col" class="titleItem text-nowrap">總花費時間(時:分:秒)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var='recordvo' items='${resvc.getMemRecords(param.mem_no)}' varStatus='i'>
                <tr>
                    <td>
                        <c:forEach var='routevo' items='${rsvc.all}'>
                            <c:if test="${recordvo.route_no eq routevo.route_no}">
                                ${routevo.route_name}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        ${recordvo.distance}公里
                    </td>
                    <td>
                        ${recordvo.elevation}公尺
                    </td>
                    <td>
                        <script>
                            document.write((Math.floor(parseInt(${recordvo.duration}) / 3600))  + ":" + (Math.floor(parseInt(${recordvo.duration}) / 60 % 60)) + ":" + ${recordvo.duration} % 60);
                        </script>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>

</body>
</html>