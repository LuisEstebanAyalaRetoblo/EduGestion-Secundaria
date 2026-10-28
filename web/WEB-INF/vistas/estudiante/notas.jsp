<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Mis calificaciones"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<div class="page-heading"><div><span class="eyebrow">Información académica</span><h1>Mis calificaciones</h1><p>Consulta personal de resultados académicos registrados.</p></div></div>
<section class="panel-card">
    <div class="panel-title table-title"><div><h2>Historial de calificaciones</h2><p>Total: ${notas.size()} registros.</p></div><div class="search-box"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg><input type="search" class="form-control" placeholder="Buscar curso" data-table-search="studentGrades"></div></div>
    <div class="table-responsive"><table class="table data-table" id="studentGrades"><thead><tr><th>Curso</th><th>Evaluación</th><th>Tipo</th><th>Fecha</th><th>Nota</th><th>Observación</th></tr></thead><tbody>
        <c:forEach items="${notas}" var="n"><tr><td><strong><c:out value="${n.curso}"/></strong></td><td><c:out value="${n.evaluacion}"/></td><td><span class="type-badge"><c:out value="${n.tipo}"/></span></td><td><c:out value="${n.fecha}"/></td><td><span class="grade-pill ${n.nota >= 11 ? 'approved' : 'failed'}"><c:out value="${n.nota}"/></span></td><td><c:out value="${n.observacion}"/></td></tr></c:forEach>
        <c:if test="${empty notas}"><tr><td colspan="6" class="empty-cell">Aún no existen calificaciones registradas.</td></tr></c:if>
    </tbody></table></div>
</section>
<%@include file="../includes/footer.jsp"%>
