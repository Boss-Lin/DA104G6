<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.blog.model.*" %>
<%@ page import="com.mem.model.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    MemVO memVO = (MemVO) request.getAttribute("memVO");
    BlogService blogSvc = new BlogService();
    List<BlogVO> blogList = blogSvc.getMyBlog(memVO.getMem_no());
    pageContext.setAttribute("blogList", blogList);
%>

<jsp:useBean id="memSvc" class="com.mem.model.MemService"/>

<html>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/listMyBlog.css">

    <title>日誌</title>
</head>
<body>
<div class="row justify-content-center align-items-center p-3" style="font-family: 'Noto Bold', sans-serif">
    <div class="col-12" id="MemBlogScroll">
        <c:forEach var="BlogVO" items="${blogList}">
            <div class="card mb-3" style="max-width: 100%;border-radius: 35px;overflow: hidden">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <a href="<%=request.getContextPath()%>/front-end/blog/blog.do?action=getOne_For_Display&blog_no=${BlogVO.blog_no}"><img src="<%= request.getContextPath()%>/front-end/blog/blog.do?action=getBlogImg&blog_no=${BlogVO.blog_no}" class="card-img-top" alt="日誌預覽圖"></a>
                    </div>
                    <div class="col-md-8" style="height: 223px">
                        <div class="card-body h-100">
                            <div class="row h-100">

                                <div class="col-12 title">
                                    <a href="<%=request.getContextPath()%>/front-end/blog/blog.do?action=getOne_For_Display&blog_no=${BlogVO.blog_no}">
                                        <h2 class="card-title">${BlogVO.blog_name}</h2></a>
                                </div>
                                <div class="col-12 card-text JQellipsis" style="color: #000">
                                    <p class="card-text" id="Blog-text" style="color: #3c3c3c">${BlogVO.blog_cont}</p>
                                </div>
                                <div class="col-12 text-right my-auto" style="font-size: 14px">
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

</body>
</html>
