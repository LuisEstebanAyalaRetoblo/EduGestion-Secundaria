<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Cambiar contraseña"/>
<%@include file="includes/header.jsp"%>
<%@include file="includes/menu.jsp"%>
<div class="page-heading"><div><span class="eyebrow">Seguridad de la cuenta</span><h1>Cambiar contraseña</h1><p>Actualice la contraseña personal de acceso al sistema.</p></div></div>
<div class="row justify-content-center">
    <div class="col-xl-6 col-lg-8">
        <section class="panel-card">
            <c:if test="${param.resultado == 'guardado'}"><div class="alert alert-success">La contraseña se actualizó correctamente.</div></c:if>
            <c:if test="${param.resultado == 'actual'}"><div class="alert alert-danger">La contraseña actual no es correcta.</div></c:if>
            <c:if test="${param.resultado == 'invalido'}"><div class="alert alert-warning">La nueva contraseña debe tener al menos 8 caracteres y coincidir con la confirmación.</div></c:if>
            <form action="${pageContext.request.contextPath}/app/cuenta/clave" method="post" autocomplete="off" class="row g-3">
                <div class="col-12"><label class="form-label">Contraseña actual *</label><input class="form-control" type="password" name="actual" maxlength="100" required></div>
                <div class="col-12"><label class="form-label">Nueva contraseña *</label><input class="form-control" type="password" name="nueva" minlength="8" maxlength="100" required></div>
                <div class="col-12"><label class="form-label">Confirmar nueva contraseña *</label><input class="form-control" type="password" name="confirmar" minlength="8" maxlength="100" required></div>
                <div class="col-12 form-actions"><a class="btn btn-light" href="${pageContext.request.contextPath}/app/dashboard">Cancelar</a><button class="btn btn-primary" type="submit"><svg class="icono icono-password" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-password"></use></svg>Actualizar contraseña</button></div>
            </form>
        </section>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
