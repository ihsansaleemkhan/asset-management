<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>


<%@ page import="com.beans.Printerink"%>

<jsp:useBean id="PrinterinkDAO" class="com.dao.PrinterinkDAO"></jsp:useBean>

<%
  ArrayList<Printerink> printerink = PrinterinkDAO.getPritnerInkStockDetails();
%>

<c:set var="printerink" value="<%=printerink%>" />

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
	$('#printer_ink_stock').DataTable();
</script>
