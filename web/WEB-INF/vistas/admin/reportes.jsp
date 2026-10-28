<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Reportes institucionales"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<div class="page-heading"><div><span class="eyebrow">Control institucional</span><h1>Reportes consolidados</h1><p>Indicadores académicos y administrativos para la toma de decisiones.</p></div><button class="btn btn-light" type="button" onclick="window.print()"><svg class="icono icono-print" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-print"></use></svg>Imprimir</button></div>
<div class="row g-4">
    <div class="col-xl-6">
        <section class="panel-card h-100">
            <div class="panel-title"><div><h2>Matrículas por sección</h2><p>Capacidad, ocupación y vacantes.</p></div></div>
            <div class="table-responsive"><table class="table data-table"><thead><tr><th>Sección</th><th>Capacidad</th><th>Matriculados</th><th>Vacantes</th></tr></thead><tbody><c:forEach items="${matriculas}" var="m"><tr><td><strong><c:out value="${m.seccion}"/></strong></td><td><c:out value="${m.capacidad}"/></td><td><c:out value="${m.matriculados}"/></td><td><c:out value="${m.vacantes}"/></td></tr></c:forEach><c:if test="${empty matriculas}"><tr><td colspan="4" class="empty-cell">No existen secciones registradas.</td></tr></c:if></tbody></table></div>
        </section>
    </div>
    <div class="col-xl-6">
        <section class="panel-card h-100">
            <div class="panel-title"><div><h2>Promedio por curso</h2><p>Consolidado de calificaciones.</p></div></div>
            <div class="table-responsive"><table class="table data-table"><thead><tr><th>Curso</th><th>Registros</th><th>Promedio</th><th>Mínima</th><th>Máxima</th></tr></thead><tbody><c:forEach items="${promedios}" var="p"><tr><td><strong><c:out value="${p.curso}"/></strong></td><td><c:out value="${p.calificaciones}"/></td><td><span class="grade-pill ${p.promedio >= 11 ? 'approved' : 'failed'}"><c:out value="${p.promedio}"/></span></td><td><c:out value="${p.minima}"/></td><td><c:out value="${p.maxima}"/></td></tr></c:forEach><c:if test="${empty promedios}"><tr><td colspan="5" class="empty-cell">No existen cursos registrados.</td></tr></c:if></tbody></table></div>
        </section>
    </div>
    <div class="col-xl-6">
        <section class="panel-card h-100">
            <div class="panel-title"><div><h2>Resumen de asistencia</h2><p>Registros acumulados por curso.</p></div></div>
            <div class="table-responsive"><table class="table data-table"><thead><tr><th>Curso</th><th>Registros</th><th>Presentes</th><th>Tardanzas</th><th>Faltas</th><th>Justificadas</th></tr></thead><tbody><c:forEach items="${asistencias}" var="a"><tr><td><strong><c:out value="${a.curso}"/></strong></td><td><c:out value="${a.registros}"/></td><td><c:out value="${a.presentes}"/></td><td><c:out value="${a.tardanzas}"/></td><td><c:out value="${a.faltas}"/></td><td><c:out value="${a.justificadas}"/></td></tr></c:forEach><c:if test="${empty asistencias}"><tr><td colspan="6" class="empty-cell">No existen registros de asistencia.</td></tr></c:if></tbody></table></div>
        </section>
    </div>
    <div class="col-xl-6">
        <section class="panel-card h-100">
            <div class="panel-title"><div><h2>Carga académica docente</h2><p>Asignaciones y horas semanales.</p></div></div>
            <div class="table-responsive"><table class="table data-table"><thead><tr><th>DNI</th><th>Docente</th><th>Especialidad</th><th>Asignaciones</th><th>Horas</th></tr></thead><tbody><c:forEach items="${docentes}" var="d"><tr><td><c:out value="${d.codigo}"/></td><td><strong><c:out value="${d.docente}"/></strong></td><td><c:out value="${d.especialidad}"/></td><td><c:out value="${d.asignaciones}"/></td><td><c:out value="${d.horas_semanales}"/></td></tr></c:forEach><c:if test="${empty docentes}"><tr><td colspan="5" class="empty-cell">No existen docentes registrados.</td></tr></c:if></tbody></table></div>
        </section>
    </div>
</div>
<%@include file="../includes/footer.jsp"%>
