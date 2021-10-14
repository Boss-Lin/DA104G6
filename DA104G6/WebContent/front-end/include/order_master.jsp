<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.mem.model.MemVO" %>
<%@ page import="com.order_detail.model.*" %>
<%@ page import="com.order_master.model.*" %>
<%@ page import="java.util.List" %>

<%
    MemVO memVO = (MemVO) session.getAttribute("memVO");
    String mem_no = memVO.getMem_no();

    Order_masterService order_masterSvc = new Order_masterService();
    List<Order_masterVO> list = order_masterSvc.findBymem_no(mem_no);
    pageContext.setAttribute("order_Masterlist", list);
%>

<html>
<head>
    <title>所有訂單資料</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/order_master.css">

</head>
<body>

<div class="container-fluid">
    <div class="row" id="Order_MasterScroll">
        <div class="col-12">
            <table class="table text-center">
                <thead class="thead-light">
                <tr>
                    <th>訂單編號</th>
                    <th>訂單日期</th>
                    <th>訂單狀態</th>
                    <th>訂單金額</th>
                    <th>訂單地址</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="order_masterVO" items="${order_Masterlist}">
                        <tr>
                            <td>${order_masterVO.order_id}</td>
                            <td>${order_masterVO.order_date}</td>
                            <td>${applicationScope.orderMasterStatus[order_masterVO.status]}</td>
                            <td>$${order_masterVO.total_price}</td>
                            <td>${memVO.mem_address}</td>
                            <td>
                                <form name="order_detail" method="post" action="<%=request.getContextPath() %>/order_detail/order_detail.do" class="m-0">
                                    <input type="hidden" name="action" value="getOrder_id_For_Display">
                                    <input type="hidden" name="order_id" value="${order_masterVO.order_id}">
                                    <button type="submit" class="orderDetailBtn btn btn-info">
                                        訂單詳情
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>