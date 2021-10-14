<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.blog.model.*" %>
<%@ page import="com.blog_comment.model.*" %>
<%@ page import="com.blog_report.model.*" %>
<%@ page import="com.mem.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.stream.*" %>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
    BlogVO blogVO = (BlogVO) request.getAttribute("blogVO");
    Blog_CommentService blogComSvc = new Blog_CommentService();
    List<Blog_CommentVO> commentList = blogComSvc.getBlog(blogVO.getBlog_no());
    pageContext.setAttribute("commentList", commentList);


    //給檢舉用

    Blog_ReportService blogReportSvc = new Blog_ReportService();
    List<Blog_ReportVO> reportList = blogReportSvc.getAll();
    List<Integer> reportStatusList = new ArrayList<Integer>();
    MemVO memVO = (MemVO) session.getAttribute("memVO");
    //Lambda寫法
    if (memVO != null) {
        List<Blog_ReportVO> blogReportList = reportList.stream()
                .filter(blog_reportVO -> blog_reportVO.getBlog_no().equals(blogVO.getBlog_no()))
                .filter(blog_reportVO -> blog_reportVO.getMem_no().equals(memVO.getMem_no()))
                .collect(Collectors.toList());
        pageContext.setAttribute("blogReportList", blogReportList);
    }
%>

<jsp:useBean id="memSvc" class="com.mem.model.MemService"/>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">


    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/listOneBlog.css">
    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="<%= request.getContextPath()%>/js/sweetAlert2%209.5.2.js"></script>


    <!--上線時改位址-->

    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title>日誌</title>
</head>
<body>

<jsp:include page="/front-end/include/NavBar.jsp"/>

<!-- 內容 -->
<!-- 日誌部分 -->
<div class="container-fluid" style="margin-top: 100px;">
    <!-- 第一層 -->
    <div class="row mb-5">
        <div class="col-2"></div>
        <div class="col-6">
            <nav aria-label="breadcrumb" style="width: 951px;margin-left: -15px;">
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
            <div class="row justify-content-center align-items-center p-4" style="margin-top: 30px;background-color: #e9ecef;">

                <div class="col-12">
                    <h1 style="color: #1c7430; font-weight:900;margin-bottom: 1rem"><%=blogVO.getBlog_name()%></h1>
                </div>

                <div class="col-6">
                    <span class="text-muted mr-2"><a href="<%=request.getContextPath()%><c:if test="${blogVO.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${blogVO.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${blogVO.mem_no}</c:if>">${memSvc.getOneMem(blogVO.mem_no).mem_name}&nbsp;&nbsp;&nbsp;|</a></span>
                    <span class="text-muted mr-2"><fmt:formatDate type="date" value="${blogVO.blog_time}"/> 發表&nbsp;&nbsp;&nbsp;|</span>
                    <span class="text-muted">${blogVO.watch_count}次瀏覽</span>
                </div>

                <div class="col-6 text-right">
                    <c:if test="${not empty sessionScope.memVO.mem_no && empty blogReportList}">
                        <span><button type="button" class="btn control" data-toggle="modal" data-target="#exampleModalCenter"><img src="<%= request.getContextPath()%>/images/icons/Report_Blog_Icon.png" style="width: 20px; height: 20px;" alt="檢舉日誌">檢舉日誌</button></span>
                    </c:if>

                    <c:if test="${not empty sessionScope.memVO.mem_no && not empty blogReportList}">
                        <span>已收到檢舉</span>
                    </c:if>

                    <c:if test="${blogVO.mem_no == sessionScope.memVO.mem_no}">
                        <form method="post" action="blog.do" style="display: inline">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="blog_no" value="<%=blogVO.getBlog_no()%>">
                            <button type="submit" class="btn control"><img src="<%= request.getContextPath()%>/images/icons/Edit_Blog_Icon.png" style="width: 20px; height: 20px;" alt="編輯日誌">編輯日誌</button>
                        </form>
                    </c:if>
                </div>

                <div class="col-12">
                    <hr style="border: 0.5px solid #AAAAAA">
                </div>

                <div class="col-12 mb-5">
                    <img src="<%= request.getContextPath()%>/front-end/blog/blog.do?action=getBlogImg&blog_no=${blogVO.blog_no}" class="card-img-top" alt="封面圖">
                </div>

                <div class="col-11" id="Blog_Cont">
                    <%=blogVO.getBlog_cont()%>
                </div>

                <div class="col-11">
                    <hr style="border: 0.5px solid #AAAAAA">
                </div>

                <div class="col-11">
                    <c:if test="${commentList!=null}">
                        <table id = "commentTable" class="table table-sm table-striped">
                            <tbody>
                                <c:forEach var="Blog_CommentVO" items="${commentList}">
                                    <tr>
                                        <td style="vertical-align: middle; width: 110px">
                                            <div class="row justify-content-center align-items-center">
                                                <div class="col-12 text-center mt-1 mb-1">
                                                    <img src="<%= request.getContextPath()%>/mem/mem.do?action=showOthersImg&mem_no=${Blog_CommentVO.mem_no}" style="object-fit: cover; width: 60px;height: 60px;border-radius: 100%">
                                                </div>
                                                <div class="col-12 text-center">
                                                    <a href="<%=request.getContextPath()%><c:if test="${Blog_CommentVO.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${Blog_CommentVO.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${Blog_CommentVO.mem_no}</c:if>">${memSvc.getOneMem(Blog_CommentVO.mem_no).mem_name}</a>
                                                </div>

                                            </div>
                                        </td>
                                        <td style="vertical-align: middle">
                                            <div class="row align-items-center justify-content-center">
                                                <div class="col-12">
                                                    ${Blog_CommentVO.com_cont}
                                                </div>
                                                <c:if test="${not empty Blog_CommentVO.com_pic}">
                                                    <div class="col-12">
                                                        <hr style="border: 0.5px solid #AAAAAA">
                                                        <img src="<%= request.getContextPath()%>/front-end/blog/blog.do?action=getBlogCommentImg&com_pic=${Blog_CommentVO.com_no}" class="commentImg">
                                                    </div>
                                                </c:if>
                                                <div class="col-12 text-right">
                                                    <small><fmt:formatDate type="both" value="${Blog_CommentVO.com_time}"/></small>
                                                </div>
                                            </div>
                                        </td>
                                        <td style="vertical-align: middle" class="text-center">
                                            <c:if test="${Blog_CommentVO.mem_no == sessionScope.memVO.mem_no}">
                                                <form method="post" action="blog_comment.do" name="form4" class="m-0">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="blog_no" value="<%=blogVO.getBlog_no()%>">
                                                    <input type="hidden" name="blogCom_no" value="${Blog_CommentVO.com_no}">
                                                    <button type="submit" class="btn control" data-toggle="tooltip" data-placement="right" title="刪除留言"><img src="<%= request.getContextPath()%>/images/icons/Blog_Comment_Delete.png" style="width: 30px; height: 30px;" alt="刪除留言"></button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>

                <c:if test="${not empty sessionScope.memVO.mem_no}">
                        <form method="post" action="blog_comment.do" name="form3" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="insert">
                            <input type="hidden" name="getOneBlog" value="${blogVO.blog_no}">
                            <input type="hidden" name="mem_no" value="${sessionScope.memVO.mem_no}">
                            <textarea cols="78" rows="3" id="com_cont" class="mt-5" placeholder="新增留言" name="com_cont"></textarea>
                            <div id='app' class="mt-2">
                                <label class="btn btn-info">
                                    <input style="display:none;" type="file" name="com_pic" accept="image/*" @change="fileSelected" id="uploadForm">
                                    <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" class="UploadButton"> 留言附圖
                                </label>
                                <img :src="image.src" :height="image.height" v-for="image in images"/>
                                <br>
                            </div>

                            <button id="insertComment" type="button" class="btn btn-info">留言</button>

                        </form>
                </c:if>

            </div>
        </div>
        <div class="col-4">
            <div class="card" style="width: 18rem;position:sticky;top: 180px">

                <div class="card-body" style="background-color: #e9ecef;">
                    <div class="row align-items-center justify-content-center">
                        <div class="col-5">
                            <img src="<%= request.getContextPath()%>/mem/mem.do?action=showOthersImg&mem_no=${requestScope.blogVO.mem_no}" id="UserPic" style="object-fit: cover; width: 100px;height: 100px;border-radius: 100%">
                        </div>
                        <div class="col-7">
                            <h6>作者</h6>
                            <h5 class="card-title"><a href="<%=request.getContextPath()%><c:if test="${blogVO.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${blogVO.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${blogVO.mem_no}</c:if>">${memSvc.getOneMem(blogVO.mem_no).mem_name}</a></h5>
                        </div>
                        <div class="col-12 mt-4">
                            <p class="card-text mb-1">會員階級：<img src="<%= request.getContextPath()%>/bicyclist_rank/bicyclist_rank.do?action=showImg&rank_no=${memSvc.getOneMem(blogVO.mem_no).rank_no}" style="width: 25px;">${applicationScope.memRank.get(memSvc.getOneMem(blogVO.mem_no).rank_no)}</p>
                            <p class="card-text">總里程數：${memSvc.getOneMem(blogVO.mem_no).total_record}Km</p>
                        </div>

                        <div class="col-12 text-center mt-4">
                            <a href="<%=request.getContextPath()%><c:if test="${blogVO.mem_no eq sessionScope.memVO.mem_no}">/front-end/mem/member.jsp</c:if><c:if test="${blogVO.mem_no ne sessionScope.memVO.mem_no}">/mem/mem.do?action=getOne_For_Display&mem_no=${blogVO.mem_no}</c:if>" class="btn btn-info">查看會員日誌</a>
                        </div>



                    </div>
                </div>
            </div>
        </div>
    </div>
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
    <div class="modal fade" id="exampleModalCenter" tabindex="-1"
         role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">我對這篇日誌有疑慮</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/blog/blog_report.do">
                        <input type="hidden" name="action" value="insertReport"></input>
                        <input type="hidden" name="blog_no" value="<%=blogVO.getBlog_no()%>"/>
                        <input type="hidden" name="mem_no" value="${sessionScope.memVO.mem_no}"/>
                        <textarea name="reason" placeholder="請輸入原因" cols="43" rows="10"></textarea>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消	</button>
                            <input	type="submit" value="送出檢舉"  class="btn btn-primary">
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
    <!-- 頁腳 -->
</div>

<script>
    $("#insertComment").click(function(){

        var formData = new FormData();

        var com_pic = $('#uploadForm')[0].files[0];

        var action = "insert";
        var getOneBlog ="<%=blogVO.getBlog_no()%>";
        var mem_no = "${sessionScope.memVO.mem_no}";
        var com_cont = $("#com_cont");
        var scrollBottom = $(window).scrollTop() + $(window).height();

        if($.trim(com_cont.val())==="" && !com_pic){
            swal.fire({
               icon: 'warning',
               text: '留言請勿空白'
            });
            return;
        }else if(com_pic){
            formData.append('action',action);
            formData.append('mem_no',mem_no);
            formData.append('getOneBlog',getOneBlog);
            formData.append('com_cont',com_cont.val());
            formData.append('com_pic',com_pic);
        }else{
            formData.append('action',action);
            formData.append('mem_no',mem_no);
            formData.append('getOneBlog',getOneBlog);
            formData.append('com_cont',com_cont.val());
        }

        $.ajax({
            url:"<%=request.getContextPath()%>/front-end/blog/blog_comment.do",
            type:"POST",
            contentType : false,
            processData: false,
            data:formData,

            success:function(data){

                window.history.go(0)

            }
        })
    });

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
</script>
<script>
    //照片預覽js
    new Vue({
        el: "#app",
        data: {
            images: []
        },
        methods: {
            fileSelected(event) {
                this.images = [];
                const files = event.target.files; //取得File物件
                [].forEach.call(files, this.fileReader);
            },
            fileReader(file) {
                const reader = new FileReader(); //建立FileReader 監聽 Load 事件
                reader.addEventListener("load", this.createImage);
                reader.readAsDataURL(file);
            },
            createImage(event) {
                const file = event.target;
                const image = {
                    height: 100,
                    title: file.name,
                    src: file.result
                };
                this.images.push(image);
            }
        }
    });
</script>
<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>
</body>
</html>