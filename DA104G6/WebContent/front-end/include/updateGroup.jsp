<%@page import="com.mem.model.MemVO" %>
<%@page import="com.group.model.GroupVO" %>
<%@page import="java.util.List" %>
<%@page import="com.group.model.GroupService" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/updateGroup.css">

    <title>我的創建資訊</title>

</head>

<!--------------------------------------- 取出Service 跑創建者的集合 ---------------------------------->
<%
    HttpSession memsession = request.getSession();
    MemVO memvo = (MemVO) memsession.getAttribute("memVO");
    GroupService sev = new GroupService();
    List<GroupVO> list = sev.getCreator(memvo.getMem_no());
%>
<jsp:useBean id="rsvc" class="com.route.model.RouteService"/>
<!--------------------------------------- 取出Service 跑創建者的集合 ---------------------------------->

<body>
    <div class="row" style="margin-right: -21px">
        <div class="col-12" id="tableBody" style="overflow-x: hidden ; overflow-y: scroll; height:760px">


            <!------------------------------------------------ 表頭 ------------------------------------------->
            <table class="table updateBody text-center table-striped">
                <thead class="thead-light">
                <tr>
                    <th scope="col" class="titleItem text-nowrap">行程名稱</th>
                    <th scope="col" class="titleItem text-nowrap">集合地址</th>
                    <th scope="col" class="titleItem text-nowrap">集合時間</th>
                    <th scope="col" class="titleItem text-nowrap">活動天數</th>
                    <th scope="col" class="titleItem text-nowrap">人數上限</th>
                    <th scope="col" class="titleItem text-nowrap">路線名稱</th>
                    <th scope="col" class="titleItem text-nowrap"></th>
                </tr>
                </thead>
                <!------------------------------------------------ 表頭 ------------------------------------------->

                <tbody>

                <!----------------------------------------------- 滾出資料 ------------------------------------------>
                <c:forEach var="updateGroVO" items="<%= list %>">
                    <tr>
                        <td class="text-nowrap">${updateGroVO.gro_name}</td>
                        <td>${updateGroVO.muster}</td>
                        <td><fmt:formatDate value="${updateGroVO.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${updateGroVO.duration}</td>
                        <td>${updateGroVO.peo_limit == 100 ? '&#8734;' : updateGroVO.peo_limit}</td>
                        <td><c:forEach var="routevo" items="${rsvc.all}">
                            <c:if test="${updateGroVO.route_no eq routevo.route_no}">
                                ${routevo.route_name}
                            </c:if>
                        </c:forEach>
                        </td>
                        <td>
                            <form method="post" class="updateForm">
                                <button class="updateGroupBtn btn btn-info" type="submit" id="update_btn" name="gro_no"
                                        value="${updateGroVO.gro_no}"
                                        formaction="<%=request.getContextPath() %>/front-end/Group/group.do">修改行程
                                </button>
                                <input type="hidden" name="action" value="getOne_For_Update">
                            </form>

                            <form method="post" class="updateForm">
                                <button class="updateGroupBtn btn btn-info mt-2" type="submit" name="gro_no" value="${updateGroVO.gro_no}"
                                        formaction="<%=request.getContextPath() %>/front-end/Group/group.do">查看詳情
                                </button>
                                <input type="hidden" name="action" value="getOne_For_Display">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <!------------------------------------------------ 滾出資料 ------------------------------------------>

                </tbody>

            </table>

        </div>
    </div>

<!------------------------------------------------ bootstrap.js --------------------------------------------->
</body>
</html>