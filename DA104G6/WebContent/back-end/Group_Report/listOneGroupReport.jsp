<%@page import="com.gro_report.model.Gro_ReportVO" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">

    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic_Manager.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/listOneGroupReport.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->

    <title>Bike Seeker Manager</title>
</head>

<!------------------------------------------------- 取得單筆檢舉的詳細資訊 ----------------------------------->
<%
    Gro_ReportVO repVo = (Gro_ReportVO) request.getAttribute("repVo");
%>
<!------------------------------------------------- 取得單筆檢舉的詳細資訊 ----------------------------------->
<jsp:useBean id="grosvc" scope="page" class="com.group.model.GroupService"/>
<jsp:useBean id="memsvc" scope="page" class="com.mem.model.MemService"/>

<body>



<div class="container-fluid">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

                <div class="row align-items-center justify-content-center">

                    <div class="col-12 text-center mb-5">
                        <div class="display-3" id="Title">審核揪團檢舉</div>
                    </div>


                    <div class="col-3 align-middle text-center" id="ManagerProfile">

                        <h1 class="mb-5">檢舉編號 : <%=repVo.getRep_no() %></h1>

                        <table class="table table-borderless" style="font-size: 24px">
                            <tbody>
                            <tr>
                                <th scope="row">被檢舉行程</th>
                                <td><c:forEach var="grovo" items="${grosvc.allGroup}">
                                    <c:if test="${repVo.gro_no eq grovo.gro_no}">
                                        ${grovo.gro_name}
                                    </c:if>
                                </c:forEach></td>
                            </tr>


                            <tr>
                                <th scope="row">檢舉人</th>
                                <td><c:forEach var="memvo" items="${memsvc.allMem}">
                                    <c:if test="${repVo.mem_no eq memvo.mem_no}">
                                        ${memvo.mem_name}
                                    </c:if>
                                </c:forEach></td>
                            </tr>
                            <tr>
                                <th scope="row">狀態</th>
                                <td>${groReport[repVo.status]}</td>
                            </tr>
                            <tr>
                                <th scope="row">檢舉時間</th>
                                <td>${repVo.rep_date}</td>
                            </tr>
                            </tbody>
                        </table>

                        <div class="row justify-content-center">

                            <div class="col-3">

                                <form action="gro_report.do" method="post">
                                    <button type="submit" class="btn btn-outline-success" name="repno" value="<%=repVo.getRep_no() %>">通過
                                    </button>
                                    <input type="hidden" name="action" value="getOne_For_Update">
                                    <input type="hidden" name="status" value="true">
                                    <input type="hidden" name="mem_no" value="${repVo.mem_no}">
                                </form>

                            </div>

                            <div class="col-3">

                                <form action="gro_report.do" method="post">
                                    <button type="submit" class="btn btn-outline-danger" name="repno" value="<%=repVo.getRep_no() %>">駁回
                                    </button>
                                    <input type="hidden" name="action" value="getOne_For_Update">
                                    <input type="hidden" name="status" value="false">
                                    <input type="hidden" name="mem_no" value="${repVo.mem_no}">
                                </form>

                            </div>


                        </div>

                    </div>
                    <div class="col-1"></div>

                    <div class="col-4 align-middle text-center" id="ManagerPermission">
                        <h1 class="text-center mb-5">相關證明</h1>

                        <div class="row">
                            <div class="col-12 mb-5">
                                <img src="<%=request.getContextPath() %>/back-end/Group_Report/gro_report.do?getImg=<%=repVo.getRep_no() %>" id="Proof">
                            </div>

                            <div class="col-12">
                                <p style="font-size: 24px"><%=repVo.getReason() %></p>
                            </div>
                        </div>


                    </div>

                </div>
        </main>
    </div>
</div>




<!---------------------------------------------------- 新舊分界 ------------------------------------->


<%--<div class="container">--%>

<%--    <!---------------------------------------------------- 檢舉編號，狀態等 ------------------------------------->--%>
<%--    <div class="row">--%>
<%--        <div class="col-6 rep_title"><h4>檢舉編號 : <%=repVo.getRep_no() %>--%>
<%--        </h4></div>--%>
<%--        <div class="col-6">--%>
<%--            <table class="table table-sm">--%>
<%--                <tbody>--%>
<%--                <tr>--%>
<%--                    <th scope="row">被檢舉行程</th>--%>
<%--                    <td><c:forEach var="grovo" items="${grosvc.allGroup}">--%>
<%--                        <c:if test="${repVo.gro_no eq grovo.gro_no}">--%>
<%--                            ${grovo.gro_name}--%>
<%--                        </c:if>--%>
<%--                    </c:forEach></td>--%>
<%--                </tr>--%>


<%--                <tr>--%>
<%--                    <th scope="row">檢舉人</th>--%>
<%--                    <td><c:forEach var="memvo" items="${memsvc.allMem}">--%>
<%--                        <c:if test="${repVo.mem_no eq memvo.mem_no}">--%>
<%--                            ${memvo.mem_name}--%>
<%--                        </c:if>--%>
<%--                    </c:forEach></td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <th scope="row">狀態</th>--%>
<%--                    <td>${groReport[repVo.status]}</td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <th scope="row">檢舉時間</th>--%>
<%--                    <td>${repVo.rep_date}</td>--%>
<%--                </tr>--%>
<%--                </tbody>--%>
<%--            </table>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <!---------------------------------------------------- 檢舉編號，狀態等 ------------------------------------->--%>


<%--    <hr>--%>

<%--    <!------------------------------------------------------ 檢舉佐證圖片，原因--------------------------------------->--%>
<%--    <div class="row">--%>
<%--        <div class="col-6">--%>
<%--            <img src="<%=request.getContextPath() %>/back-end/Group_Report/gro_report.do?getImg=<%=repVo.getRep_no() %>">--%>
<%--        </div>--%>

<%--        <div class="col-6">--%>
<%--            <p><%=repVo.getReason() %>--%>
<%--            </p>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <!------------------------------------------------------ 檢舉佐證圖片，原因--------------------------------------->--%>


<%--    <hr>--%>

<%--    <!------------------------------------------------------------ 送出 ------------------------------------------->--%>
<%--    <div class="row">--%>
<%--        <div class="col-4"></div>--%>
<%--        <div class="col-4 d-flex justify-content-center">--%>

<%--            <!------------------------------------------------------------ 通過 ------------------------------------------->--%>
<%--            <form action="gro_report.do" method="post">--%>
<%--                <button type="submit" class="btn btn-outline-success" name="repno" value="<%=repVo.getRep_no() %>">通過--%>
<%--                </button>--%>
<%--                <input type="hidden" name="action" value="getOne_For_Update">--%>
<%--                <input type="hidden" name="status" value="true">--%>
<%--            </form>--%>

<%--            <!------------------------------------------------------------ 駁回 ------------------------------------------->--%>
<%--            <form action="gro_report.do" method="post">--%>
<%--                <button type="submit" class="btn btn-outline-danger" name="repno" value="<%=repVo.getRep_no() %>">駁回--%>
<%--                </button>--%>
<%--                <input type="hidden" name="action" value="getOne_For_Update">--%>
<%--                <input type="hidden" name="status" value="false">--%>
<%--            </form>--%>

<%--        </div>--%>
<%--        <div class="col-4"></div>--%>
<%--    </div>--%>
<%--</div>--%>


<!------------------------------------------------ bootstrap.js --------------------------------------------->
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>
