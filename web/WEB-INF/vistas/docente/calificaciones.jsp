<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Calificaciones"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<%@include file="../includes/messages.jsp"%>
<div class="page-heading">
    <div><span class="eyebrow">Registro de notas</span><h1><c:out value="${evaluacion.titulo}"/></h1><p><c:out value="${evaluacion.curso}"/> · <c:out value="${evaluacion.seccion}"/> · <c:out value="${evaluacion.fecha}"/></p></div>
    <a class="btn btn-light" href="${pageContext.request.contextPath}/app/docente/evaluaciones"><svg class="icono icono-arrow_back" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-arrow_back"></use></svg>Evaluaciones</a>
</div>
<section class="panel-card mb-4">
    <div class="panel-title"><div><h2>Registrar calificación</h2><p>Escala vigesimal de 0 a 20.</p></div><span class="type-badge"><c:out value="${evaluacion.tipo}"/></span></div>
    <form action="${pageContext.request.contextPath}/app/docente/calificaciones" method="post" class="row g-3 align-items-end">
        <input type="hidden" name="idEvaluacion" value="${evaluacion.id_evaluacion}">
        <div class="col-lg-5"><label class="form-label">Estudiante *</label><select class="form-select" name="idMatricula" required><option value="">Seleccione</option><c:forEach items="${matriculas}" var="m"><option value="${m.id_matricula}"><c:out value="${m.codigo}"/> · <c:out value="${m.estudiante}"/></option></c:forEach></select></div>
        <div class="col-lg-2"><label class="form-label">Nota *</label><input class="form-control" type="number" name="nota" min="0" max="20" step="0.01" required></div>
        <div class="col-lg-3"><label class="form-label">Observación</label><input class="form-control" type="text" name="observacion" maxlength="250"></div>
        <div class="col-lg-2"><button class="btn btn-primary w-100" type="submit"><svg class="icono icono-save" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-save"></use></svg>Guardar</button></div>
    </form>
</section>
<section class="panel-card">
    <div class="panel-title table-title"><div><h2>Calificaciones registradas</h2><p>Total: ${calificaciones.size()} estudiantes calificados.</p></div><div class="search-box"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg><input type="search" class="form-control" placeholder="Buscar estudiante" data-table-search="gradeTable"></div></div>
    <div class="table-responsive"><table class="table data-table" id="gradeTable"><thead><tr><th>Código</th><th>Estudiante</th><th>Nota</th><th>Observación</th><th>Fecha de registro</th></tr></thead><tbody>
        <c:forEach items="${calificaciones}" var="c"><tr><td><c:out value="${c.codigo}"/></td><td><strong><c:out value="${c.estudiante}"/></strong></td><td><span class="grade-pill ${c.nota >= 11 ? 'approved' : 'failed'}"><c:out value="${c.nota}"/></span></td><td><c:out value="${c.observacion}"/></td><td><c:out value="${c.fecha_registro}"/></td></tr></c:forEach>
        <c:if test="${empty calificaciones}"><tr><td colspan="5" class="empty-cell">Aún no se registraron calificaciones.</td></tr></c:if>
    </tbody></table></div>
</section>
<%@include file="../includes/footer.jsp"%>
