<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.mem.model.MemVO" %>
<%@ page import="com.record.model.RecordService" %>
<%@ page import="com.record.model.RecordVO" %>
<%@ page import="java.util.List" %>

<%
    RecordService recordSvc = new RecordService();
    MemVO memVO = (MemVO) session.getAttribute("memVO");
    String mem_no = memVO.getMem_no();
    List<RecordVO> recordList = recordSvc.getMemRecords(mem_no);
    pageContext.setAttribute("recordList", recordList);
%>

<jsp:useBean id="routeSvc" scope="request" class="com.route.model.RouteService"/>
<jsp:useBean id="rankSvc" scope="request" class="com.bicyclist_rank.model.Bicyclist_RankService"/>


<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!--CSS-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/memRecord.css">

    <!--網址縮圖-->
    <link rel="icon" href="<%= request.getContextPath()%>/images/bikeSeekerIC_horizon_bordered.png">

    <title>Bike Seeker</title>

</head>
<body>

<div class="tab-pane fade h-100 show active" id="MyRecord" role="tabpanel" aria-labelledby="MyRecord-tab">
    <div class="row align-items-end h-25" id="RecordProgress">
        <div class="col-12 pl-5 pr-5">
            <span id="RankBottom">
				<img src="<%= request.getContextPath()%>/images/icons/Gold_Tooltip.png">
				<p>${rankSvc.getOneBicyclist_Rank(sessionScope.memVO.rank_no).rank_req}公里</p>
			</span>
            <span id="RankTop">
				<img src="<%= request.getContextPath()%>/images/icons/Gold_Tooltip_Flip.png">
				<p>${rankSvc.getOneBicyclist_Rank(sessionScope.memVO.rank_no).rank_req + 200}公里</p>
			</span>
            <div class="progress">
                <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuemin="0" aria-valuemax="200" style="width: ${(rankSvc.getOneBicyclist_Rank(sessionScope.memVO.rank_no).rank_req + 200 - sessionScope.memVO.total_record) / 2}%s">
					${sessionScope.memVO.total_record}公里
                </div>
            </div>
            <br>
            <p class="text-center">
                還需 ${rankSvc.getOneBicyclist_Rank(sessionScope.memVO.rank_no).rank_req + 200 - (sessionScope.memVO.total_record)}公里 升級
				<img src="<%= request.getContextPath()%>/bicyclist_rank/bicyclist_rank.do?action=get_Next_Rank_Img&rank_req=${rankSvc.getOneBicyclist_Rank(sessionScope.memVO.rank_no).rank_req + 200}">${rankSvc.getRankWithRequirement(rankSvc.getOneBicyclist_Rank(sessionScope.memVO.rank_no).rank_req + 200).rank_name}
            </p>
        </div>
        <div class="col-12"><hr></div>
    </div>

    <div class="row justify-content-center align-items-start h-75 ml-0 mr-0" id="RecordList">

        <div class="col-12 mt-2" id="RecordLatest3">

            <h3 class="text-center">近三筆里程紀錄</h3>

            <c:if test="${not empty recordList}">
                <table class="table table-striped text-center">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">封面圖</th>
                        <th scope="col">路線名</th>
                        <th scope="col">開始時間</th>
                        <th scope="col">結束時間</th>
                        <th scope="col">花費時間<br>(時分秒)</th>
                        <th scope="col">距離</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="recordVO" items="${recordList}" begin="0" end="2" varStatus="count">
                        <fmt:parseDate value="${recordVO.start_time}" var="start_date" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <fmt:parseDate value="${recordVO.end_time}" var="end_date" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <tr>
                            <td>
								<img src="<%= request.getContextPath()%>/record/record.do?action=showImg&mem_no=${recordVO.mem_no}&start_time=${recordVO.start_time}" alt="路線封面圖" style="max-width: 100px; max-height: 90px">
							</td>
                            <td>${routeSvc.getOneRoute(recordVO.route_no).route_name}</td>
                            <td><fmt:formatDate value="${start_date}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${end_date}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                            <td>
                                <script>document.write((Math.floor(parseInt(${recordVO.duration}) / 3600)) + ":" + (Math.floor(parseInt(${recordVO.duration}) / 60 % 60)) + ":" + ${recordVO.duration} % 60);</script>
                            </td>
                            <td>${recordVO.distance}公里</td>
                            <td>
                                <button type="button" class="recordBtn btn btn-primary" data-toggle="modal" data-target="#ModalId${count.index}">修改封面</button>
                                <form action="<%= request.getContextPath()%>/record/record.do" method="get" class="m-0 mt-3">
                                    <input type="hidden" name="action" value="getOne_For_Detail">
                                    <input type="hidden" name="mem_no" value="${recordVO.mem_no}">
                                    <input type="hidden" name="start_time" value="${recordVO.start_time}">
                                    <button type="submit" class="recordBtn btn btn-primary">
                                        查看詳情
                                    </button>
                                </form>
                                <div class="routeModal modal fade " id="ModalId${count.index}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <form action="<%= request.getContextPath()%>/record/record.do" method="post" class="m-0" enctype="multipart/form-data">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="ModalTitle${count.index}" style="color:#000;">修改封面圖片</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">

                                                    <img src="<%= request.getContextPath()%>/record/record.do?action=showImg&mem_no=${recordVO.mem_no}&start_time=${recordVO.start_time}" alt="封面圖預覽" class="mx-auto" style="max-width: 150px;max-height: 150px;border: 5px solid #000;border-radius: 10px;" id="CoverPic${count.index}">
                                                    <div class="form-group mt-2 text-center">
                                                        <label class="btn btn-info">
                                                            <input id="InputCoverPic${count.index}" style="display:none;" type="file" name="record_pic" accept="image/*">
                                                            <img src="<%= request.getContextPath()%>/images/icons/Upload_Icon.png" alt="上傳圖片鈕" style="width: 30px;height: 30px;border:0" class="fileUploadBtn"> 上傳圖片
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消
                                                    </button>
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="mem_no" value="${recordVO.mem_no}">
                                                    <input type="hidden" name="start_time" value="${recordVO.start_time}">
                                                    <input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
                                                    <!--送出本網頁的路徑給Controller-->
                                                    <button type="submit" class="btn btn-primary">修改</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty recordList}">

                <h3 class="text-center">你還未有任何里程紀錄，趕快出門騎騎腳踏車吧~</h3>

            </c:if>

        </div>

        <c:if test="${not empty recordList}">
            <div class="col-3 text-center mb-4">
                <a href="<%= request.getContextPath()%>/front-end/record/recordListAll.jsp">
                    <button type="button" class="btn btn-primary">查看全部里程</button>
                </a>
            </div>
        </c:if>

        <div class="col-3 text-center mb-4">
            <a href="<%= request.getContextPath()%>/front-end/route/WsRoute.jsp">
                <button type="button" class="btn btn-primary">神奇小功能</button>
            </a>
        </div>

    </div>

</div>
<script>

    // 封面圖預覽
    <c:forEach var="recordVO" items="${recordList}" begin="0" end="2" varStatus="count">

    function readURL${count.index}(input) {
        if (input.files && input.files[0]) {
            let reader = new FileReader();

            reader.onload = function (e) {
                $('#CoverPic${count.index}').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    $('#InputCoverPic${count.index}').change(function () {
        readURL${count.index}(this);
    });

    </c:forEach>

    //修改成功的通知
    <c:if test="${not empty requestScope.updateSuccess}">
    swal.fire({
        icon: 'success',
        title: '叮鈴',
        text: "${requestScope.updateSuccess}"
    });
    <c:remove var="updateSuccess" scope="request" />
    </c:if>

    //其他錯誤的訊息
    <c:if test="${not empty requestScope.errorMsgs}">
    swal.fire({
        icon: 'error',
        title: '唉唷',
        text: "<c:out value="${requestScope.errorMsgs}" />"
    });
    <c:remove var="errorMsgs" scope="request" />
    </c:if>

</script>
</body>
</html>