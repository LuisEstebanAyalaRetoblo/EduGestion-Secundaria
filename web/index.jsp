<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty sessionScope.idUsuario}">
    <c:redirect url="/app/dashboard"/>
</c:if>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EduGestión Secundaria</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/main.css" rel="stylesheet">
</head>
<body class="login-body">
    <main class="login-shell">
        <section class="login-brand">
            <div class="brand-mark"><svg class="icono icono-school" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-school"></use></svg></div>
            <span class="eyebrow">Colegio Secundario</span>
            <h1>EduGestión</h1>
            <p>Plataforma institucional para la administración académica, docente y estudiantil.</p>
            <div class="login-features">
                <div><svg class="icono icono-verified_user" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-verified_user"></use></svg> Acceso según rol</div>
                <div><svg class="icono icono-analytics" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-analytics"></use></svg> Control académico</div>
                <div><svg class="icono icono-groups" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-groups"></use></svg> Gestión centralizada</div>
            </div>
        </section>
        <section class="login-card">
            <div class="mb-4">
                <span class="eyebrow">Acceso institucional</span>
                <h2>Iniciar sesión</h2>
                <p class="text-secondary mb-0">Ingrese sus credenciales asignadas.</p>
            </div>
            <c:if test="${param.error == 'credenciales'}"><div class="alert alert-danger">Usuario o contraseña incorrectos.</div></c:if>
            <c:if test="${param.error == 'campos'}"><div class="alert alert-warning">Complete todos los campos.</div></c:if>
            <c:if test="${param.error == 'perfil'}"><div class="alert alert-danger">La cuenta no tiene un perfil institucional activo.</div></c:if>
            <c:if test="${param.sesion == 'expirada'}"><div class="alert alert-warning">La sesión ha finalizado. Ingrese nuevamente.</div></c:if>
            <c:if test="${param.salida == 'correcta'}"><div class="alert alert-success">La sesión se cerró correctamente.</div></c:if>
            <form action="${pageContext.request.contextPath}/login" method="post" autocomplete="off">
                <div class="mb-3">
                    <label class="form-label">Usuario</label>
                    <div class="input-icon"><svg class="icono icono-person" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-person"></use></svg><input type="text" name="usuario" class="form-control" maxlength="40" required autofocus></div>
                </div>
                <div class="mb-4">
                    <label class="form-label">Contraseña</label>
                    <div class="input-icon"><svg class="icono icono-lock" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-lock"></use></svg><input type="password" name="clave" id="clave" class="form-control" maxlength="100" required><button type="button" class="show-password" data-password="clave" aria-label="Mostrar contraseña"><svg class="icono icono-visibility" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-visibility"></use></svg></button></div>
                </div>
                <button type="submit" class="btn btn-primary btn-lg w-100">Ingresar al sistema</button>
            </form>
        </section>
    </main>
    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>
