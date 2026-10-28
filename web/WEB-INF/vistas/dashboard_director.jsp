<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Panel de Dirección"/>
<%@include file="includes/header.jsp"%>
<%@include file="includes/menu.jsp"%>
<%@include file="includes/messages.jsp"%>
<div class="page-heading">
    <div><span class="eyebrow">Administración general</span><h1>Panel de Dirección</h1><p>Resumen operativo del sistema de gestión educativa.</p></div>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/admin/crud?m=matriculas"><svg class="icono icono-add" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-add"></use></svg>Nueva matrícula</a>
</div>
<div class="stats-grid">
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=usuarios"><span class="stat-icon"><svg class="icono icono-manage_accounts" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-manage_accounts"></use></svg></span><div><strong>${dashboard.usuarios}</strong><span>Usuarios</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=docentes"><span class="stat-icon"><svg class="icono icono-co_present" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-co_present"></use></svg></span><div><strong>${dashboard.docentes}</strong><span>Docentes</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=estudiantes"><span class="stat-icon"><svg class="icono icono-groups" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-groups"></use></svg></span><div><strong>${dashboard.estudiantes}</strong><span>Estudiantes</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=cursos"><span class="stat-icon"><svg class="icono icono-menu_book" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-menu_book"></use></svg></span><div><strong>${dashboard.cursos}</strong><span>Cursos</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=secciones"><span class="stat-icon"><svg class="icono icono-meeting_room" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-meeting_room"></use></svg></span><div><strong>${dashboard.secciones}</strong><span>Secciones</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=matriculas"><span class="stat-icon"><svg class="icono icono-how_to_reg" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-how_to_reg"></use></svg></span><div><strong>${dashboard.matriculas}</strong><span>Matrículas</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/admin/crud?m=asignaciones"><span class="stat-icon"><svg class="icono icono-assignment_ind" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-assignment_ind"></use></svg></span><div><strong>${dashboard.asignaciones}</strong><span>Asignaciones</span></div></a>
    <a class="stat-card" href="${pageContext.request.contextPath}/app/anuncios"><span class="stat-icon"><svg class="icono icono-campaign" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-campaign"></use></svg></span><div><strong>${dashboard.anuncios}</strong><span>Anuncios</span></div></a>
</div>
<div class="row g-4 mt-1">
    <div class="col-xl-8">
        <section class="panel-card">
            <div class="panel-title"><div><h2>Accesos de gestión</h2><p>Operaciones principales de la institución.</p></div></div>
            <div class="quick-grid">
                <a href="${pageContext.request.contextPath}/app/admin/crud?m=estudiantes"><svg class="icono icono-person_add" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-person_add"></use></svg><strong>Registrar estudiante</strong><small>Datos personales y cuenta</small></a>
                <a href="${pageContext.request.contextPath}/app/admin/crud?m=asignaciones"><svg class="icono icono-assignment_ind" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-assignment_ind"></use></svg><strong>Asignar docente</strong><small>Curso, sección y periodo</small></a>
                <a href="${pageContext.request.contextPath}/app/docente/evaluaciones"><svg class="icono icono-quiz" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-quiz"></use></svg><strong>Supervisar evaluaciones</strong><small>Evaluaciones y calificaciones</small></a>
                <a href="${pageContext.request.contextPath}/app/horario"><svg class="icono icono-calendar_month" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-calendar_month"></use></svg><strong>Consultar horarios</strong><small>Distribución académica</small></a>
            </div>
        </section>
    </div>
    <div class="col-xl-4">
        <section class="panel-card h-100">
            <div class="panel-title"><div><h2>Anuncios recientes</h2><p>Comunicaciones registradas.</p></div><a href="${pageContext.request.contextPath}/app/anuncios">Ver todos</a></div>
            <div class="announcement-list compact">
                <c:forEach items="${anuncios}" var="a" begin="0" end="3">
                    <article><svg class="icono icono-notifications" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-notifications"></use></svg><div><strong><c:out value="${a.titulo}"/></strong><small><c:out value="${a.fecha_publicacion}"/> · <c:out value="${a.destino}"/></small></div></article>
                </c:forEach>
                <c:if test="${empty anuncios}"><div class="empty-state">No existen anuncios registrados.</div></c:if>
            </div>
        </section>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
