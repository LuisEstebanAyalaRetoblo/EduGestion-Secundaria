<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="pageTitle" value="Mi asistencia"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<div class="page-heading"><div><span class="eyebrow">Información académica</span><h1>Mi asistencia</h1><p>Consulta personal del registro de asistencia por curso.</p></div></div>
<section class="panel-card">
    <div class="panel-title table-title"><div><h2>Historial de asistencia</h2><p>Total: ${asistencias.size()} registros.</p></div><div class="search-box"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg><input type="search" class="form-control" placeholder="Buscar curso" data-table-search="studentAttendance"></div></div>
    <div class="table-responsive"><table class="table data-table" id="studentAttendance"><thead><tr><th>Fecha</th><th>Curso</th><th>Estado</th><th>Observación</th></tr></thead><tbody>
        <c:forEach items="${asistencias}" var="a"><tr><td><c:out value="${a.fecha}"/></td><td><strong><c:out value="${a.curso}"/></strong></td><td><span class="attendance-badge ${fn:toLowerCase(a.estado)}"><c:out value="${a.estado}"/></span></td><td><c:out value="${a.observacion}"/></td></tr></c:forEach>
        <c:if test="${empty asistencias}"><tr><td colspan="4" class="empty-cell">Aún no existen asistencias registradas.</td></tr></c:if>
    </tbody></table></div>
</section>
<%@include file="../includes/footer.jsp"%>
