<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<aside class="sidebar" id="sidebar">
    <a class="sidebar-brand" href="${pageContext.request.contextPath}/app/dashboard">
        <span class="brand-mark small"><svg class="icono icono-school" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-school"></use></svg></span>
        <span><strong>EduGestión</strong><small>Secundaria</small></span>
    </a>
    <div class="user-panel">
        <div class="user-avatar">${fn:toUpperCase(fn:substring(sessionScope.usuario,0,1))}</div>
        <div><strong><c:out value="${sessionScope.usuario}"/></strong><small><c:choose><c:when test="${sessionScope.rol == 'DIRECTOR'}">Director</c:when><c:when test="${sessionScope.rol == 'DOCENTE'}">Docente</c:when><c:otherwise>Estudiante</c:otherwise></c:choose></small></div>
    </div>
    <nav class="sidebar-nav">
        <a href="${pageContext.request.contextPath}/app/dashboard"><svg class="icono icono-dashboard" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-dashboard"></use></svg>Inicio</a>
        <c:if test="${sessionScope.rol == 'DIRECTOR'}">
            <span class="nav-label">Administración</span>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=usuarios"><svg class="icono icono-manage_accounts" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-manage_accounts"></use></svg>Usuarios</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=docentes"><svg class="icono icono-co_present" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-co_present"></use></svg>Docentes</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=estudiantes"><svg class="icono icono-groups" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-groups"></use></svg>Estudiantes</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=apoderados"><svg class="icono icono-family_restroom" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-family_restroom"></use></svg>Apoderados</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=vinculos"><svg class="icono icono-diversity_1" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-diversity_1"></use></svg>Vínculos familiares</a>
            <span class="nav-label">Organización académica</span>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=grados"><svg class="icono icono-stairs" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-stairs"></use></svg>Grados</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=periodos"><svg class="icono icono-date_range" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-date_range"></use></svg>Periodos</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=secciones"><svg class="icono icono-meeting_room" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-meeting_room"></use></svg>Secciones</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=cursos"><svg class="icono icono-menu_book" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-menu_book"></use></svg>Cursos</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=matriculas"><svg class="icono icono-how_to_reg" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-how_to_reg"></use></svg>Matrículas</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=asignaciones"><svg class="icono icono-assignment_ind" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-assignment_ind"></use></svg>Asignaciones</a>
            <a href="${pageContext.request.contextPath}/app/admin/crud?m=horarios"><svg class="icono icono-calendar_month" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-calendar_month"></use></svg>Configurar horarios</a>
            <a href="${pageContext.request.contextPath}/app/admin/reportes"><svg class="icono icono-monitoring" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-monitoring"></use></svg>Reportes</a>
        </c:if>
        <c:if test="${sessionScope.rol == 'DIRECTOR' || sessionScope.rol == 'DOCENTE'}">
            <span class="nav-label">Gestión docente</span>
            <a href="${pageContext.request.contextPath}/app/docente/evaluaciones"><svg class="icono icono-quiz" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-quiz"></use></svg>Evaluaciones y notas</a>
            <a href="${pageContext.request.contextPath}/app/docente/asistencias"><svg class="icono icono-fact_check" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-fact_check"></use></svg>Control de asistencia</a>
        </c:if>
        <c:if test="${sessionScope.rol == 'ESTUDIANTE'}">
            <span class="nav-label">Información académica</span>
            <a href="${pageContext.request.contextPath}/app/estudiante/notas"><svg class="icono icono-grading" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-grading"></use></svg>Mis calificaciones</a>
            <a href="${pageContext.request.contextPath}/app/estudiante/asistencias"><svg class="icono icono-event_available" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-event_available"></use></svg>Mi asistencia</a>
        </c:if>
        <a href="${pageContext.request.contextPath}/app/horario"><svg class="icono icono-schedule" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-schedule"></use></svg>Horario</a>
        <a href="${pageContext.request.contextPath}/app/anuncios"><svg class="icono icono-campaign" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-campaign"></use></svg>Anuncios</a>
        <a href="${pageContext.request.contextPath}/app/cuenta/clave"><svg class="icono icono-password" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-password"></use></svg>Cambiar contraseña</a>
    </nav>
    <a class="logout-link" href="${pageContext.request.contextPath}/logout"><svg class="icono icono-logout" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-logout"></use></svg>Cerrar sesión</a>
</aside>
<div class="main-area">
    <header class="topbar">
        <button class="menu-toggle" id="menuToggle" type="button" aria-label="Abrir menú" aria-expanded="false"><svg class="icono icono-menu" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-menu"></use></svg></button>
        <div><span class="topbar-school">Institución Educativa Secundaria</span></div>
        <div class="role-badge"><svg class="icono icono-verified_user" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-verified_user"></use></svg><c:choose><c:when test="${sessionScope.rol == 'DIRECTOR'}">Director</c:when><c:when test="${sessionScope.rol == 'DOCENTE'}">Docente</c:when><c:otherwise>Estudiante</c:otherwise></c:choose></div>
    </header>
    <main class="content-area">
