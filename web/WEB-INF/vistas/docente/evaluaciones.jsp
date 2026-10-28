<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="pageTitle" value="Evaluaciones"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<%@include file="../includes/messages.jsp"%>
<div class="page-heading">
    <div><span class="eyebrow">Gestión académica</span><h1>Evaluaciones y calificaciones</h1><p>Administre las evaluaciones correspondientes a las asignaciones autorizadas.</p></div>
    <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#evaluationForm"><svg class="icono icono-add" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-add"></use></svg>Nueva evaluación</button>
</div>
<div class="collapse ${not empty evaluacion ? 'show' : ''}" id="evaluationForm">
    <section class="panel-card mb-4">
        <div class="panel-title"><div><h2>${empty evaluacion ? 'Registrar evaluación' : 'Actualizar evaluación'}</h2><p>La ponderación debe encontrarse entre 0 y 100.</p></div></div>
        <form action="${pageContext.request.contextPath}/app/docente/evaluaciones" method="post" class="row g-3">
            <input type="hidden" name="id" value="${empty evaluacion ? 0 : evaluacion.id_evaluacion}">
            <div class="col-md-6"><label class="form-label">Asignación *</label><select class="form-select" name="idAsignacion" required><option value="">Seleccione</option><c:forEach items="${asignaciones}" var="a"><option value="${a.id_asignacion}" ${evaluacion.id_asignacion == a.id_asignacion ? 'selected' : ''}><c:out value="${a.curso}"/> · <c:out value="${a.seccion}"/><c:if test="${not empty a.docente}"> · <c:out value="${a.docente}"/></c:if></option></c:forEach></select></div>
            <div class="col-md-6"><label class="form-label">Título *</label><input class="form-control" type="text" name="titulo" value="${fn:escapeXml(evaluacion.titulo)}" maxlength="120" required></div>
            <div class="col-md-4"><label class="form-label">Tipo *</label><select class="form-select" name="tipo" required><option value="TAREA" ${evaluacion.tipo == 'TAREA' ? 'selected' : ''}>Tarea</option><option value="PRACTICA" ${evaluacion.tipo == 'PRACTICA' ? 'selected' : ''}>Práctica</option><option value="EXAMEN" ${evaluacion.tipo == 'EXAMEN' ? 'selected' : ''}>Examen</option><option value="PROYECTO" ${evaluacion.tipo == 'PROYECTO' ? 'selected' : ''}>Proyecto</option><option value="PARTICIPACION" ${evaluacion.tipo == 'PARTICIPACION' ? 'selected' : ''}>Participación</option></select></div>
            <div class="col-md-4"><label class="form-label">Fecha *</label><input class="form-control" type="date" name="fecha" value="${evaluacion.fecha}" required></div>
            <div class="col-md-4"><label class="form-label">Ponderación *</label><input class="form-control" type="number" name="ponderacion" value="${evaluacion.ponderacion}" min="0.01" max="100" step="0.01" required></div>
            <div class="col-12"><div class="form-check form-switch switch-field"><input class="form-check-input" type="checkbox" name="estado" id="estado" ${empty evaluacion || evaluacion.estado == true || evaluacion.estado == 1 ? 'checked' : ''}><label class="form-check-label" for="estado">Evaluación activa</label></div></div>
            <div class="col-12 form-actions"><a class="btn btn-light" href="${pageContext.request.contextPath}/app/docente/evaluaciones">Cancelar</a><button class="btn btn-primary" type="submit"><svg class="icono icono-save" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-save"></use></svg>Guardar evaluación</button></div>
        </form>
    </section>
</div>
<section class="panel-card">
    <div class="panel-title table-title"><div><h2>Evaluaciones registradas</h2><p>Total: ${evaluaciones.size()} evaluaciones.</p></div><div class="search-box"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg><input type="search" class="form-control" placeholder="Buscar evaluación" data-table-search="evaluationTable"></div></div>
    <div class="table-responsive"><table class="table data-table" id="evaluationTable"><thead><tr><th>Curso</th><th>Sección</th><th>Evaluación</th><th>Tipo</th><th>Fecha</th><th>Ponderación</th><th>Estado</th><th class="text-end">Acciones</th></tr></thead><tbody>
        <c:forEach items="${evaluaciones}" var="e"><tr><td><strong><c:out value="${e.curso}"/></strong></td><td><c:out value="${e.seccion}"/></td><td><c:out value="${e.titulo}"/></td><td><span class="type-badge"><c:out value="${e.tipo}"/></span></td><td><c:out value="${e.fecha}"/></td><td><c:out value="${e.ponderacion}"/>%</td><td><c:out value="${e.estado}"/></td><td class="text-end actions-cell"><a class="icon-btn grade" title="Calificaciones" href="${pageContext.request.contextPath}/app/docente/calificaciones?idEvaluacion=${e.id_evaluacion}"><svg class="icono icono-grading" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-grading"></use></svg></a><a class="icon-btn edit" title="Editar" href="${pageContext.request.contextPath}/app/docente/evaluaciones?accion=editar&id=${e.id_evaluacion}"><svg class="icono icono-edit" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-edit"></use></svg></a><a class="icon-btn delete" title="Eliminar" data-confirm="¿Desea eliminar esta evaluación y sus calificaciones?" href="${pageContext.request.contextPath}/app/docente/evaluaciones?accion=eliminar&id=${e.id_evaluacion}"><svg class="icono icono-delete" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-delete"></use></svg></a></td></tr></c:forEach>
        <c:if test="${empty evaluaciones}"><tr><td colspan="8" class="empty-cell">No existen evaluaciones registradas.</td></tr></c:if>
    </tbody></table></div>
</section>
<%@include file="../includes/footer.jsp"%>
