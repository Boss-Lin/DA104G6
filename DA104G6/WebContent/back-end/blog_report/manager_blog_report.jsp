<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.blog_report.model.*" %>
<%@ page import="com.blog.model.*" %>
<%@ page import="com.manager.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.*"%>

<jsp:useBean id="blogReportSvc" class="com.blog_report.model.Blog_ReportService" />
<jsp:useBean id="blogSvc" class="com.blog.model.BlogService" />
<%
	List<Blog_ReportVO> blogReportList = blogReportSvc.getAll();
	List<Blog_ReportVO> blogReportListStatus1 = blogReportList.stream()
		.filter(blog_reportVO ->blog_reportVO.getStatus()==1)
		.collect(Collectors.toList());
	pageContext.setAttribute("blogReportListStatus1", blogReportListStatus1);
	
	
%>

<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!--網址縮圖-->
	<link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css">
	<!--自訂CSS-->
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/Generic_Manager.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/css/managerList.css">
	<script src="<%= request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
	<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	<script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>
	<title>Bike Seeker Manager</title>
</head>
<body>


<div class="container-fluid">

	<!--首頁包全部Col-->
	<div class="row align-items-center justify-content-around">

		<jsp:include page="/back-end/include/NavBar.jsp" flush="true" />

		<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

			<div class="row align-items-center justify-content-center">

				<div class="col-12 text-center">
					<div class="display-3" id="Title">日誌檢舉管理</div>
				</div>
				<div>
				<div class="col-12 text-center">
					<div class="display-5" >
					<a href="<%= request.getContextPath()%>/back-end/blog_report/manager_blog_report.jsp" class="btn btn-secondary btn-sm" tabindex="-1" role="button" aria-disabled="true">未審核</a>
					<a href="<%= request.getContextPath()%>/back-end/blog_report/manager_blog_report_Audited.jsp" class="btn btn-secondary btn-sm" tabindex="-1" role="button" aria-disabled="true">已審核</a>
					</div>
				</div>
				</div>
				<div class="w-100"><hr style="border-color: #EAB965; border-width: 5px; width: 80em"></div>

				<div class="col-10 align-middle" id="ManagerListTable" style="width: 80em">
							
				   <table class="table table-hover" style= "word-break:break-all" >
					  <thead class="thead-dark">
					    <tr>
					      <th scope="col">日誌名稱</th>
					      <th scope="col">檢舉原因</th>
					      <th scope="col">審核狀態</th>
					      <th scope="col">前往查看</th>
					      <th scope="col">處理</th>
					    </tr>
					  </thead>
					  <tbody>
					  <%@ include file="page1.file"%>
					    <c:forEach var="Blog_ReportVO" items="${blogReportListStatus1}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
						
							<tr>
								<th scope="row">${blogSvc.getOneBlog(Blog_ReportVO.blog_no).blog_name}</th>
								<td style="width:500px;">${Blog_ReportVO.reason}</td>
								<td>${applicationScope.blogReportStatus[Blog_ReportVO.status]}</td>
								<td><a href="<%= request.getContextPath()%>/front-end/blog/blog.do?action=getOne_For_Display&blog_no=${Blog_ReportVO.blog_no}" target="_blank">前往</a></td>
								<td><div class="dropdown">
									  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									 	   處理
									  </button>
									  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
									  	<form method="post" action="<%= request.getContextPath()%>/front-end/blog/blog_report.do" name="form2">
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="blog_noFromReport" value="${Blog_ReportVO.blog_no}">
											<input type="hidden" name="rep_no" value="${Blog_ReportVO.rep_no}">
											<input type="hidden" name="status" value="2">
											<input type="submit" value="檢舉通過" class="dropdown-item" type="button">
										</form>
									    <form method="post" action="<%= request.getContextPath()%>/front-end/blog/blog_report.do" name="form2">
											<input type="hidden" name="action" value="update">
											<input type="hidden" name="rep_no" value="${Blog_ReportVO.rep_no}">
											<input type="hidden" name="status" value="3">
											<input type="submit" value="檢舉未過" class="dropdown-item" type="button">
										</form>
									   </div>
									</div></td>
							</tr>
							
							</c:forEach>
					   </tbody>
					</table>
					<%@ include file="page2.file"%>
				</div>
				</div>
				</main>

	</div>
</div>





<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="<%= request.getContextPath()%>/js/popper.js"></script>
<script src="<%= request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>