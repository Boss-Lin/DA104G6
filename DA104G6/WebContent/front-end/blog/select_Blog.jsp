<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*" %>
<%@ page import="com.blog.model.*" %>

<%
    BlogService blogSvc = new BlogService();
    List<BlogVO> list = blogSvc.getAll();
    pageContext.setAttribute("list", list);

    BlogService blogSvcNew = new BlogService();
    List<BlogVO> listNew = blogSvc.getNew();
    pageContext.setAttribute("listNew", listNew);

%>
<jsp:useBean id="memSvc" class="com.mem.model.MemService"/>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">


    <!-- CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/select_Blog.css">

    <!--JQ-->
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>


    <title>Bike Seeker</title>

</head>

<body>

<jsp:include page="/front-end/include/NavBar.jsp" />

<!-- 內容 -->
<!-- 日誌部分 -->
<div class="container-fluid" style="margin-top: 100px;">
    <!-- 第一層 -->
    <div class="row">
        <div class="col-2">
        <c:if test="${not empty sessionScope.memVO.mem_no}">
        	<a href="addBlog.jsp"><button type="button" class="btn control" style="position: sticky; top: 85vh;"><img src="<%=request.getContextPath() %>/images/icons/addBlog_Icon.png" style="width: 128px"></button></a>
        </c:if>
        </div>
        <div class="col-6">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="select_Blog.jsp">Home</a></li>
                    <c:if test="${not empty sessionScope.memVO.mem_no}">
                        <li class="breadcrumb-item"><a href="addBlog.jsp">新增日誌</a></li>
                        <li class="breadcrumb-item"><a href="<%= request.getContextPath()%>/front-end/mem/member.jsp">我的日誌</a></li>
                    </c:if>
                    <c:if test="${empty sessionScope.memVO.mem_no}">
                        <li class="breadcrumb-item">請登入以取得更多功能</li>
                    </c:if>
                </ol>
            </nav>
            <h5 class="text-right">
                <%-- 關鍵字查詢 --%>
                <FORM METHOD="post" ACTION="blog.do">
                        <input type="text" class="form-control ml-auto" name="blog_key_name" id="KeywordBox" placeholder="輸入關鍵字">
                    <input type="hidden" name="action" value="getSome_For_Display">
                    <input type="submit" class="btn btn-primary" id="KeywordSearch" value="搜尋">
                </FORM>
                <%-- 錯誤表列 --%>
                <c:if test="${not empty errorMsgs}">
                    <ul>
                        <c:forEach var="message" items="${errorMsgs}">
                            <a style="color: #1c7430;">${message}</a>
                        </c:forEach>
                    </ul>
                </c:if>
            </h5>
            <h1 style="color:#1c7430;">熱門日誌</h1>

            <!-- 日誌列表 -->
            <div style="margin-top: 30px; background-color: rgba(255, 255, 255, 0.8)" id="textBox" class="rounded">
                <c:forEach var="BlogVO" items="${list}" begin="0" end="9">
                    <div class="card mb-3" style="max-width: 100%;border-radius: 35px;overflow: hidden">
                        <div class="row no-gutters">
                            <div class="col-md-4">
                                <a href="<%=request.getContextPath()%>/front-end/blog/blog.do?action=getOne_For_Display&blog_no=${BlogVO.blog_no}"><img src="<%= request.getContextPath()%>/front-end/blog/blog.do?action=getBlogImg&blog_no=${BlogVO.blog_no}" class="card-img-top" alt="日誌預覽圖"></a>
                            </div>
                            <div class="col-md-8" style="height: 223px">
                                <div class="card-body h-100">
                                    <div class="row h-100">

                                        <div class="col-12 title">
                                            <a href="<%=request.getContextPath()%>/front-end/blog/blog.do?action=getOne_For_Display&blog_no=${BlogVO.blog_no}"><h2 class="card-title">${BlogVO.blog_name}</h2></a>
                                        </div>
                                        <div class="col-12 card-text JQellipsis">
                                            <p class="card-text" id="Blog-text" style="color: #3c3c3c">${BlogVO.blog_cont}</p>
                                        </div>
                                        <div class="col-12 text-right my-auto" style="font-size: 14px">
                                            <span class="text-muted ml-2">日誌作者: <a href="<%=request.getContextPath()%><c:if test="${BlogVO.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${BlogVO.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${BlogVO.mem_no}</c:if>">${memSvc.getOneMem(BlogVO.mem_no).mem_name}</a></span>
                                            <span class="text-muted ml-2">發表於<fmt:formatDate type="date" value="${BlogVO.blog_time}"/></span>
                                            <span class="text-muted ml-2">觀看次數:${BlogVO.watch_count}</span>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="col-2">
            <ul class="list-group" style="margin-top: 21.7vh;position: sticky;top: 100px;">
                <li class="list-group-item active text-center" style=" background-color: #8fd19e;border-color: #8fd19e; font-size: 24px;border-top-left-radius: 30px;border-top-right-radius: 30px">最新日誌</li>
                <c:forEach var="BlogVO" items="${listNew}" begin="0" end="4">
                    <li class="list-group-item">
                        <a href="<%=request.getContextPath()%>/front-end/blog/blog.do?action=getOne_For_Display&blog_no=${BlogVO.blog_no}"><h6 class="newTitle">${BlogVO.blog_name}</h6></a>
                        <small class="text-muted">日誌作者: <a href="<%=request.getContextPath()%><c:if test="${BlogVO.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${BlogVO.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${BlogVO.mem_no}</c:if>">${memSvc.getOneMem(BlogVO.mem_no).mem_name}</a></small>
                        <FORM METHOD="get" class="text-right m-0" ACTION="<%=request.getContextPath()%>/front-end/blog/blog.do">
                            <input type="hidden" name="action" value="getOne_For_Display"/>
                            <input type="hidden" name="blog_no" value="${BlogVO.blog_no}"/>
                            <input type="submit" value="前往" class="btn btn-primary goBtn">
                        </FORM>

                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-2"></div>
    </div>
    <!-- 第二層 -->
    <!-- 第三層 -->

    <!-- 內容 -->
    <!-- 頁腳 -->
    <div class="row">
        <div class="col-12 h-25" id="FooterCol">
            <div class="row align-items-center h-25" id="FooterBackground">
                <div class="col-12 col-md-2"></div>
                <div class="col-12 col-md-2" id="FooterContact">
                    <h1>Contact.</h1>
                    <br> <br>
                    <p>人加仕旅森股份有限公司</p>
                    <p>320 桃園市中壢區中大路300號</p>
                    <p>wieduappclass@gmail.com</p>
                    <p>03-425-7387</p>
                </div>

                <div class="col-12 col-md-4 text-center">
                    <a href="#"><img src="<%=request.getContextPath()%>/images/bikeSeekerICRolling.gif"></a>
                </div>
                <div class="col-12 col-md-4 text-center">
                    <img src="<%=request.getContextPath()%>/images/bikeRolling2.gif" width="350" height="250">
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function(){
        var len = 150; // 超過50個字以"..."取代
        $(".JQellipsis").each(function(i){
            if($(this).text().length>len){
                $(this).attr("title",$(this).text());
                var text=$(this).text().substring(0,len-1)+"...";
                $(this).text(text);
            }
        });
    });
</script>

<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>