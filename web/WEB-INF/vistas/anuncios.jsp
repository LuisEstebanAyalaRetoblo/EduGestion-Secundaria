<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="pageTitle" value="Anuncios"/>
<%@include file="includes/header.jsp"%>
<%@include file="includes/menu.jsp"%>
<%@include file="includes/messages.jsp"%>
<div class="page-heading">
    <div><span class="eyebrow">Comunicación institucional</span><h1>Anuncios</h1><p>${editor ? 'Publique y administre comunicados institucionales.' : 'Comunicados institucionales vigentes para estudiantes.'}</p></div>
    <c:if test="${editor}"><button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#announcementForm"><svg class="icono icono-add" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-add"></use></svg>Nuevo anuncio</button></c:if>
</div>
<c:if test="${editor}">
<div class="collapse ${not empty anuncio ? 'show' : ''}" id="announcementForm">
    <section class="panel-card mb-4">
        <div class="panel-title"><div><h2>${empty anuncio ? 'Publicar anuncio' : 'Actualizar anuncio'}</h2><p>Defina la vigencia y el público destinatario.</p></div></div>
        <form action="${pageContext.request.contextPath}/app/anuncios" method="post" class="row g-3">
            <input type="hidden" name="id" value="${empty anuncio ? 0 : anuncio.id_anuncio}">
            <div class="col-md-8"><label class="form-label">Título *</label><input class="form-control" type="text" name="titulo" value="${fn:escapeXml(anuncio.titulo)}" maxlength="150" required></div>
            <div class="col-md-4"><label class="form-label">Destino *</label><select class="form-select" name="destino" required><option value="TODOS" ${anuncio.destino == 'TODOS' ? 'selected' : ''}>Todos</option><option value="DOCENTES" ${anuncio.destino == 'DOCENTES' ? 'selected' : ''}>Docentes</option><option value="ESTUDIANTES" ${anuncio.destino == 'ESTUDIANTES' ? 'selected' : ''}>Estudiantes</option></select></div>
            <div class="col-12"><label class="form-label">Contenido *</label><textarea class="form-control" name="contenido" rows="4" maxlength="1500" required><c:out value="${anuncio.contenido}"/></textarea></div>
            <div class="col-md-4"><label class="form-label">Fecha de publicación *</label><input class="form-control" type="date" name="fechaPublicacion" value="${anuncio.fecha_publicacion}" required></div>
            <div class="col-md-4"><label class="form-label">Fecha de expiración</label><input class="form-control" type="date" name="fechaExpiracion" value="${anuncio.fecha_expiracion}"></div>
            <div class="col-md-4 d-flex align-items-end"><div class="form-check form-switch switch-field"><input class="form-check-input" type="checkbox" name="estado" id="announcementStatus" ${empty anuncio || anuncio.estado == true || anuncio.estado == 1 ? 'checked' : ''}><label class="form-check-label" for="announcementStatus">Anuncio activo</label></div></div>
            <div class="col-12 form-actions"><a class="btn btn-light" href="${pageContext.request.contextPath}/app/anuncios">Cancelar</a><button class="btn btn-primary" type="submit"><svg class="icono icono-send" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-send"></use></svg>Guardar anuncio</button></div>
        </form>
    </section>
</div>
</c:if>
<section class="panel-card">
    <div class="panel-title"><div><h2>${editor ? 'Anuncios administrados' : 'Comunicados vigentes'}</h2><p>Total: ${anuncios.size()} anuncios.</p></div></div>
    <div class="announcement-cards">
        <c:forEach items="${anuncios}" var="a">
            <article class="announcement-card">
                <div class="announcement-head"><span class="announcement-icon"><svg class="icono icono-campaign" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-campaign"></use></svg></span><div><span class="type-badge"><c:out value="${a.destino}"/></span><h3><c:out value="${a.titulo}"/></h3></div></div>
                <p><c:out value="${a.contenido}"/></p>
                <div class="announcement-meta"><span><svg class="icono icono-event" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-event"></use></svg><c:out value="${a.fecha_publicacion}"/></span><c:if test="${not empty a.fecha_expiracion}"><span><svg class="icono icono-event_busy" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-event_busy"></use></svg><c:out value="${a.fecha_expiracion}"/></span></c:if><span><svg class="icono icono-person" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-person"></use></svg><c:out value="${a.autor}"/></span></div>
                <c:if test="${editor}"><div class="announcement-actions"><a class="btn btn-sm btn-light" href="${pageContext.request.contextPath}/app/anuncios?accion=editar&id=${a.id_anuncio}"><svg class="icono icono-edit" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-edit"></use></svg>Editar</a><a class="btn btn-sm btn-outline-danger" data-confirm="¿Desea eliminar este anuncio?" href="${pageContext.request.contextPath}/app/anuncios?accion=eliminar&id=${a.id_anuncio}"><svg class="icono icono-delete" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-delete"></use></svg>Eliminar</a></div></c:if>
            </article>
        </c:forEach>
        <c:if test="${empty anuncios}"><div class="empty-state large"><svg class="icono icono-notifications_off" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-notifications_off"></use></svg>No existen anuncios disponibles.</div></c:if>
    </div>
</section>
<%@include file="includes/footer.jsp"%>
