<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.blog.model.*" %>
<%@ page import="java.util.*" %>
<%
    BlogVO blogVO = (BlogVO) request.getAttribute("blogVO");
%>
<%
    //如果沒有發佈過就取得現在時間
    java.sql.Timestamp blog_time = null;
    try {
        if (blogVO.getStatus() != 2) {
            blog_time = new java.sql.Timestamp(System.currentTimeMillis());
        } else {
            blog_time = blogVO.getBlog_time();
        }
    } catch (Exception e) {
        blog_time = new java.sql.Timestamp(System.currentTimeMillis());
    }
%>


<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
    <!--自訂CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/Generic.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/update_Blog.css">
    <link href="//cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

    <script src="<%=request.getContextPath()%>/js/jquery-slim-3.3.1.js"></script>
    <script src="//cdn.quilljs.com/1.3.6/quill.min.js"></script>

    <title>Bike Seeker</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <style>

        .card-img-top {
            object-fit: cover;
            max-width: 100%;
            height: 350px;
        }

    </style>
    <title>修改日誌</title>
</head>
<body>
<jsp:include page="/front-end/include/NavBar.jsp"/>

<!-- 內容 -->
<!-- 日誌部分 -->



<div class="container-fluid" style="margin-top: 63px">
    <!-- 第一層 -->
    <div class="row justify-content-center align-items-center mb-5" style="min-height: 75%">
        <div class="col-7 p-3 text-center" id="textBox">
            <img src="<%=request.getContextPath()%>/images/icons/Pin_Icon.png" style="position: absolute; top: 10px; right:11px; width: 100px">

            <div class="row justify-content-center align-items-center">



                <div class="col-6">
                    <h2 class="mb-3" style="color: #191919;">編輯日誌:</h2>
                </div>

                <div class="w-100"></div>

                <div class="col-3">

                    <c:if test="${not empty errorMsgs }">
                        <font style="color: #b50000">請修正以下錯誤：</font>
                        <ul>
                            <c:forEach var="message" items="${errorMsgs}">
                                <li style="color: #b50000">${message}</li>
                            </c:forEach>
                        </ul>
                    </c:if>

                </div>

                <div class="w-100"></div>

                <div class="col-12">
                    <form METHOD="post" ACTION="blog.do" name="form1" enctype="multipart/form-data" style="display: inline-block; width: 900px">
                        <div class="form-group">
                            <input type="text" name="blog_name" class="form-control" value="<%=blogVO.getBlog_name()%>" placeholder="請輸入日誌標題"/>
                        </div>

                        <div class="form-group">
                            <div id='app' class="text-left">
                                <label class="btn btn-info">
                                    <input style="display:none;" type="file" name="blog_cover_pic" accept="image/*" id="InputCoverPic">
                                    <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" class="UploadButton"> 上傳封面照片
                                </label>
                                <img src="<%= request.getContextPath()%>/front-end/blog/blog.do?action=getBlogImg&blog_no=${blogVO.blog_no}" class="card-img-top" alt="封面照片">
                            </div>
                        </div>

                        <div class="form-group">
                            <div id="Editor"><%=blogVO.getBlog_cont()%></div>
                            <textarea name="blog_cont" style="display:none" id="hiddenArea"></textarea>
                        </div>


                        <div class="form-group text-left">
                            <select class="custom-select" style="width: 130px" name="status">
                                <option value="2" selected>發布</option>
                                <option value="1">儲存為草稿</option>
                            </select>
                        </div>

                        <input type="hidden" name="blog_time" value="<%=blog_time%>">
                        <input type="hidden" name="blog_no" value="<%=blogVO.getBlog_no()%>">
                        <input type="hidden" name="action" value="getOne_For_Update">
                        <input type="submit" class="btn btn-primary" id="sendBtn" value="送出">

                    </form>
                </div>
                <div class="col-10 text-right">
                    <form method="post" action="blog.do" name="form2">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="blog_no" value="<%=blogVO.getBlog_no()%>">
                        <input type="submit" class="btn btn-danger" value="刪除日誌">
                    </form>
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
    <!-- 頁腳 -->
</div>

<script>

    var editor = new Quill('#Editor', {
        modules: {
            toolbar: [
                [{ header: [1, 2, false] }],
                ['italic', 'underline'],
                ['image']
            ]
        },
        placeholder: '寫下你的所見所聞···',
        theme: 'snow'  // or 'bubble'
    });
    $("#sendBtn").click(function () {
        $("#hiddenArea").val($(".ql-editor").html());
    });

    // 上傳封面預覽
    function readURL(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function(e) {
                $('.card-img-top').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        } else {
            $('.card-img-top').attr("src", "<%= request.getContextPath()%>/front-end/blog/blog.do?action=getBlogImg&blog_no=${blogVO.blog_no}");
        }
    }

    $("#InputCoverPic").change(function() {
        readURL(this);
    });

</script>
<script src="<%=request.getContextPath()%>/js/NavBarEvent.js"></script>
<script src="<%=request.getContextPath()%>/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-4.3.1.js"></script>


</body>
</html>