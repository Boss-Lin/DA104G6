<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<!--------------------------------------------------------導覽列開頭------------------------------------------------------->
<nav class="navbar fixed-top navbar-expand-xl navbar-light">

	<a class="navbar-brand" href="<%=request.getContextPath() %>/front-end/index/Index.jsp"><img src="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png" alt="Bike Seeker" title="Bike Seeker"></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
		<span class="navbar-toggler-icon"></span>
	</button>


	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav ml-auto">
			<li class="nav-item">
				<a class="nav-link" href="<%= request.getContextPath()%>/front-end/Group/listAllGroup.jsp">揪團去</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath()%>/front-end/route/listAllRoute.jsp">路線</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath()%>/front-end/blog/select_Blog.jsp">日誌</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath()%>/front-end/bike_rental/bike_List.jsp">租車點查詢</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">客服中心</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath() %>/front-end/product/shop.jsp">商城</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="<%=request.getContextPath() %>/front-end/product/cart.jsp"><img src="<%= request.getContextPath()%>/images/icons/Cart_Icon.png" width="30" height="30" alt="購物車"></a>
			</li>


			<!--------------------------------------------------登入前的會員登錄-------------------------------------------------->
			<c:if test="${empty sessionScope.account}">
				<button type="button" class="btn btn-outline-light" id="Login" data-toggle="modal" data-target="#LoginBox">會員登入</button>
			</c:if>
			<!---------------------------------------------------登入前的會員登錄-------------------------------------------------->
			
			

			<!-----------------------------------------------------登入後會員圖示------------------------------------------------>
			<c:if test="${not empty sessionScope.account}">

				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="navbarMemDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<img src="<%= request.getContextPath()%>/images/icons/member_Icon.png">
							${sessionScope.memVO.mem_name}
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%= request.getContextPath()%>/front-end/mem/member.jsp">個人資料</a>
						<a class="dropdown-item" href="<%= request.getContextPath()%>/front-end/record/recordListAll.jsp">里程統計</a>
						<a class="dropdown-item" href="<%= request.getContextPath()%>/mem/mem.do?action=Logout">登出</a>
					</div>
				</li>

			</c:if>
			<!----------------------------------------------------登入後會員圖示-------------------------------------------------->

		</ul>

	</div>
</nav>
<!-------------------------------------------------------------導覽列結束------------------------------------------------------->



<!------------------------------------------------- 登箱 ---------------------------------------------->
	<div class="modal fade" id="LoginBox" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">登入會員</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body text-center">
					<form action="<%= request.getContextPath()%>/mem/memLogin.do" method="post">
						<label>帳號 :
						<input type="email" class="textBox" name="mem_email">
					</label><br><br>
						<label>密碼 :
						<input type="password" class="textBox" name="mem_psw">
					</label><br>
						<p><a href="<%= request.getContextPath()%>/front-end//mem/memResendPsw.jsp">忘記密碼</a></p><br>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">登入</button>
							<button type="button" onclick="location.href = '<%= request.getContextPath()%>/front-end/mem/addMem.jsp';" class="btn btn-secondary" data-dismiss="modal">註冊</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<!------------------------------------------------- 登箱 ---------------------------------------------->