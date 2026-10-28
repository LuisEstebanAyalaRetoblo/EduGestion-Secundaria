<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Horario académico"/>
<%@include file="includes/header.jsp"%>
<%@include file="includes/menu.jsp"%>
<div class="page-heading"><div><span class="eyebrow">Programación semanal</span><h1>Horario académico</h1><p>Distribución de clases autorizada para el perfil actual.</p></div></div>
<section class="panel-card">
    <div class="panel-title table-title"><div><h2>Clases programadas</h2><p>Total: ${horario.size()} bloques académicos.</p></div><div class="search-box"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg><input type="search" class="form-control" placeholder="Buscar curso o sección" data-table-search="scheduleTable"></div></div>
    <div class="table-responsive"><table class="table data-table" id="scheduleTable"><thead><tr><th>Día</th><th>Hora de inicio</th><th>Hora de fin</th><th>Curso</th><th>Sección</th><c:if test="${sessionScope.rol == 'DIRECTOR'}"><th>Docente</th></c:if><th>Aula</th></tr></thead><tbody>
        <c:forEach items="${horario}" var="h"><tr><td><span class="day-badge"><c:out value="${h.dia_semana}"/></span></td><td><c:out value="${h.hora_inicio}"/></td><td><c:out value="${h.hora_fin}"/></td><td><strong><c:out value="${h.curso}"/></strong></td><td><c:out value="${h.seccion}"/></td><c:if test="${sessionScope.rol == 'DIRECTOR'}"><td><c:out value="${h.docente}"/></td></c:if><td><c:out value="${h.aula}"/></td></tr></c:forEach>
        <c:if test="${empty horario}"><tr><td colspan="${sessionScope.rol == 'DIRECTOR' ? 7 : 6}" class="empty-cell">No existe horario registrado.</td></tr></c:if>
    </tbody></table></div>
</section>
<%@include file="includes/footer.jsp"%>
