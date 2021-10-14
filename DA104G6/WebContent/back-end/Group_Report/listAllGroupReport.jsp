<%@page import="java.util.LinkedList" %>
<%@page import="com.gro_report.model.Gro_ReportService" %>
<%@page import="com.gro_report.model.Gro_ReportVO" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_circleBordered_Fill.png">

    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/listAllGroupReport.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>


    <!-------------------------------------- Bootstrap CSS、jquery---------------------------------->


    <title>Bike Seeker Manager</title>
</head>
<body>

<!---------------------------------------------------- 取出全部檢舉(狀態為審核中之檢舉) --------------------------------------->
<%
    Gro_ReportService svc = new Gro_ReportService();
    List<Gro_ReportVO> list = svc.getGroReportByStatus(1);
    LinkedList<String> errorMsgs = (LinkedList<String>) request.getAttribute("errorMsgs");
%>
<!---------------------------------------------------- 取出全部檢舉(狀態為審核中之檢舉) --------------------------------------->
<jsp:useBean id="grosvc" scope="page" class="com.group.model.GroupService"/>
<jsp:useBean id="memsvc" scope="page" class="com.mem.model.MemService"/>




<div class="container-fluid">

    <!--首頁包全部Col-->
    <div class="row align-items-center justify-content-around">

        <jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

            <div class="row align-items-center justify-content-center">

                <div class="col-12 text-center">
                    <div class="display-3" id="Title">揪團檢舉案件列表</div>
                </div>

                <div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

                <div class="col-10 align-middle" id="ManagerListTable">

                    <table class="table table-striped text-center">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col" class="text-nowrap">案件編號</th>
                            <th scope="col" class="text-nowrap">檢舉人</th>
                            <th scope="col" class="text-nowrap">被檢舉行程</th>
                            <th scope="col" class="text-nowrap">檢舉日期</th>
                            <th scope="col" class="text-nowrap">狀態</th>
                            <th scope="col" class="text-nowrap"></th>
                        </tr>
                        </thead>

                        <tbody>
                        <%@ include file="page1.file" %>
                        <c:forEach var="reportVo" items="<%= list %>" begin="<%= index %>" end="<%= index + rowsNum -1 %>">
                            <tr>
                                <td>${reportVo.rep_no}</td>
                                <td>${memsvc.getOneMem(reportVo.mem_no).mem_name}</td>
                                <td>${grosvc.getOneGroup(reportVo.gro_no).gro_name}</td>
                                <td>${reportVo.rep_date}</td>
                                <td class="text-nowrap">${applicationScope.groReport[reportVo.status]}</td>
                                <td>
                                    <form method="post" action="gro_report.do" class="m-0">
                                        <button type="submit" class="btn btn-info" name="repno" value="${reportVo.rep_no}">審核</button>
                                        <input type="hidden" name="action" value="getOne_For_Display">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%@ include file="page2.file" %>

                </div>

            </div>

        </main>
    </div>
</div>

<script>
	//搜尋結果跳頁下拉選單
	$("#SelectPage").change(function () {
		let pageIndex = $(this).children("option:selected").val();
		location.href = location.pathname +"?whichPage=" + pageIndex;
	});
	
	//審核寄信(通知)
	var sendMail = '${mail}';
	if(sendMail != ''){
		<%request.removeAttribute("mail");%>
		$.ajax({
			url: '<%=request.getContextPath()%>/front-end/Sign_up/sign_up.do',
			type: 'post',
			data: {
				action: 'mail',
				gro_no: sendMail,
				message: 'cancel'
			},
			success: function(){console.log('發送成功')},
			error: function(){console.log('發送失敗')}
		});
	}
	
	
    <c:if test="${not empty message}">
    swal.fire({
        icon:'success',
        title:'已審核',
        text:"審核完成"
    });
    <% request.removeAttribute("message"); %>
    </c:if>

    //失敗的訊息
    <c:if test="${not empty requestScope.errorMsgs}">
    swal.fire({
        icon: 'error',
        title: '唉唷',
        text: "<c:forEach var="msg" items="${requestScope.errorMsgs}">${msg}</c:forEach>"
    });
    <c:remove var="errorMsgs" scope="request" />
    </c:if>

</script>

<!------------------------------------------------ bootstrap.js --------------------------------------------->
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>
