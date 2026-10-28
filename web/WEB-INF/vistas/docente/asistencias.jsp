<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="pageTitle" value="Control de asistencia"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<%@include file="../includes/messages.jsp"%>
<div class="page-heading"><div><span class="eyebrow">Gestión académica</span><h1>Control de asistencia</h1><p>Registre la asistencia diaria de los estudiantes por asignación.</p></div></div>
<section class="panel-card mb-4">
    <form action="${pageContext.request.contextPath}/app/docente/asistencias" method="get" class="row g-3 align-items-end">
        <div class="col-lg-7"><label class="form-label">Asignación</label><select class="form-select" name="idAsignacion" required><option value="">Seleccione</option><c:forEach items="${asignaciones}" var="a"><option value="${a.id_asignacion}" ${idAsignacion == a.id_asignacion ? 'selected' : ''}><c:out value="${a.curso}"/> · <c:out value="${a.seccion}"/><c:if test="${not empty a.docente}"> · <c:out value="${a.docente}"/></c:if></option></c:forEach></select></div>
        <div class="col-lg-3"><label class="form-label">Fecha</label><input class="form-control" type="date" name="fecha" value="${fecha}" required></div>
        <div class="col-lg-2"><button class="btn btn-primary w-100" type="submit"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg>Consultar</button></div>
    </form>
</section>
<c:if test="${idAsignacion > 0}">
<section class="panel-card">
    <div class="panel-title"><div><h2>Lista de estudiantes</h2><p>Fecha seleccionada: <c:out value="${fecha}"/>.</p></div><span class="count-pill">${estudiantes.size()}</span></div>
    <div class="table-responsive"><table class="table data-table attendance-table"><thead><tr><th>Código</th><th>Estudiante</th><th>Estado</th><th>Observación</th><th></th></tr></thead><tbody>
        <c:forEach items="${estudiantes}" var="e"><tr><td><form id="attendance${e.id_matricula}" action="${pageContext.request.contextPath}/app/docente/asistencias" method="post"><input type="hidden" name="idAsignacion" value="${idAsignacion}"><input type="hidden" name="idMatricula" value="${e.id_matricula}"><input type="hidden" name="fecha" value="${fecha}"></form><c:out value="${e.codigo}"/></td><td><strong><c:out value="${e.estudiante}"/></strong></td><td><select class="form-select form-select-sm" name="estado" form="attendance${e.id_matricula}"><option value="PRESENTE" ${e.estado == 'PRESENTE' ? 'selected' : ''}>Presente</option><option value="TARDANZA" ${e.estado == 'TARDANZA' ? 'selected' : ''}>Tardanza</option><option value="FALTA" ${e.estado == 'FALTA' ? 'selected' : ''}>Falta</option><option value="JUSTIFICADA" ${e.estado == 'JUSTIFICADA' ? 'selected' : ''}>Justificada</option></select></td><td><input class="form-control form-control-sm" type="text" name="observacion" value="${fn:escapeXml(e.observacion)}" maxlength="250" form="attendance${e.id_matricula}"></td><td class="text-end"><button class="btn btn-sm btn-primary" type="submit" form="attendance${e.id_matricula}">Guardar</button></td></tr></c:forEach>
        <c:if test="${empty estudiantes}"><tr><td colspan="5" class="empty-cell">No existen estudiantes matriculados en esta asignación.</td></tr></c:if>
    </tbody></table></div>
</section>
</c:if>
<%@include file="../includes/footer.jsp"%>
