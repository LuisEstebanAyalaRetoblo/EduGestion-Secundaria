<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Panel del Estudiante"/>
<%@include file="includes/header.jsp"%>
<%@include file="includes/menu.jsp"%>
<%@include file="includes/messages.jsp"%>
<div class="page-heading">
    <div><span class="eyebrow">Portal del estudiante</span><h1>Bienvenido, <c:out value="${perfil.nombre}"/></h1><p>Consulta personal de calificaciones, asistencia, horarios y comunicados.</p></div>
</div>
<div class="profile-strip">
    <div><svg class="icono icono-badge" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-badge"></use></svg><small>Código</small><strong><c:out value="${perfil.codigo}"/></strong></div>
    <div><svg class="icono icono-fingerprint" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-fingerprint"></use></svg><small>DNI</small><strong><c:out value="${perfil.dni}"/></strong></div>
    <div><svg class="icono icono-mail" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-mail"></use></svg><small>Correo</small><strong><c:out value="${perfil.correo}"/></strong></div>
    <div><svg class="icono icono-call" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-call"></use></svg><small>Teléfono</small><strong><c:out value="${perfil.telefono}"/></strong></div>
</div>
<div class="row g-4 mt-1">
    <div class="col-xl-7">
        <section class="panel-card">
            <div class="panel-title"><div><h2>Calificaciones recientes</h2><p>Resultados académicos registrados.</p></div><a href="${pageContext.request.contextPath}/app/estudiante/notas">Ver todas</a></div>
            <div class="table-responsive"><table class="table data-table"><thead><tr><th>Curso</th><th>Evaluación</th><th>Fecha</th><th>Nota</th></tr></thead><tbody>
                <c:forEach items="${notas}" var="n" begin="0" end="5"><tr><td><strong><c:out value="${n.curso}"/></strong></td><td><c:out value="${n.evaluacion}"/></td><td><c:out value="${n.fecha}"/></td><td><span class="grade-pill ${n.nota >= 11 ? 'approved' : 'failed'}"><c:out value="${n.nota}"/></span></td></tr></c:forEach>
                <c:if test="${empty notas}"><tr><td colspan="4" class="empty-cell">Aún no existen calificaciones registradas.</td></tr></c:if>
            </tbody></table></div>
        </section>
    </div>
    <div class="col-xl-5">
        <section class="panel-card h-100">
            <div class="panel-title"><div><h2>Comunicados</h2><p>Información institucional vigente.</p></div><a href="${pageContext.request.contextPath}/app/anuncios">Ver todos</a></div>
            <div class="announcement-list">
                <c:forEach items="${anuncios}" var="a" begin="0" end="3"><article><svg class="icono icono-campaign" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-campaign"></use></svg><div><strong><c:out value="${a.titulo}"/></strong><p><c:out value="${a.contenido}"/></p><small><c:out value="${a.fecha_publicacion}"/></small></div></article></c:forEach>
                <c:if test="${empty anuncios}"><div class="empty-state">No existen comunicados vigentes.</div></c:if>
            </div>
        </section>
    </div>
</div>
<section class="panel-card mt-4">
    <div class="panel-title"><div><h2>Horario de clases</h2><p>Programación académica semanal.</p></div><a href="${pageContext.request.contextPath}/app/horario">Horario completo</a></div>
    <div class="table-responsive"><table class="table data-table"><thead><tr><th>Día</th><th>Inicio</th><th>Fin</th><th>Curso</th><th>Sección</th><th>Aula</th></tr></thead><tbody>
        <c:forEach items="${horario}" var="h" begin="0" end="5"><tr><td><span class="day-badge"><c:out value="${h.dia_semana}"/></span></td><td><c:out value="${h.hora_inicio}"/></td><td><c:out value="${h.hora_fin}"/></td><td><strong><c:out value="${h.curso}"/></strong></td><td><c:out value="${h.seccion}"/></td><td><c:out value="${h.aula}"/></td></tr></c:forEach>
        <c:if test="${empty horario}"><tr><td colspan="6" class="empty-cell">No existe horario registrado.</td></tr></c:if>
    </tbody></table></div>
</section>
<%@include file="includes/footer.jsp"%>
