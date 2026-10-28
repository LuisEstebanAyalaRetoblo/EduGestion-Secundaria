<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="pageTitle" value="${modulo.titulo}"/>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/menu.jsp"%>
<%@include file="../includes/messages.jsp"%>
<div class="page-heading">
    <div><span class="eyebrow">Mantenimiento institucional</span><h1><c:out value="${modulo.titulo}"/></h1><p>Registro, consulta, actualización y eliminación de información.</p></div>
    <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#formPanel"><svg class="icono icono-add" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-add"></use></svg>Nuevo registro</button>
</div>
<div class="collapse ${not empty registro ? 'show' : ''}" id="formPanel">
    <section class="panel-card mb-4">
        <div class="panel-title"><div><h2>${empty registro ? 'Registrar información' : 'Actualizar información'}</h2><p>Complete los datos solicitados.</p></div></div>
        <form action="${pageContext.request.contextPath}/app/admin/crud" method="post" class="row g-3">
            <input type="hidden" name="m" value="${modulo.clave}">
            <input type="hidden" name="id" value="${empty registro ? 0 : registro[modulo.pk]}">
            <c:forEach items="${modulo.campos}" var="campo">
                <c:choose>
                    <c:when test="${campo.tipo == 'checkbox'}">
                        <div class="col-md-6 d-flex align-items-end">
                            <div class="form-check form-switch switch-field">
                                <input class="form-check-input" type="checkbox" role="switch" name="${campo.nombre}" id="${campo.nombre}" ${registro[campo.nombre] == 1 || registro[campo.nombre] == true ? 'checked' : ''}>
                                <label class="form-check-label" for="${campo.nombre}"><c:out value="${campo.etiqueta}"/></label>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${campo.tipo == 'textarea'}">
                        <div class="col-12">
                            <label class="form-label"><c:out value="${campo.etiqueta}"/>${campo.requerido ? ' *' : ''}</label>
                            <textarea class="form-control" name="${campo.nombre}" rows="3" ${campo.requerido ? 'required' : ''}><c:out value="${registro[campo.nombre]}"/></textarea>
                        </div>
                    </c:when>
                    <c:when test="${campo.tipo == 'select'}">
                        <div class="col-md-6">
                            <label class="form-label"><c:out value="${campo.etiqueta}"/>${campo.requerido ? ' *' : ''}</label>
                            <select class="form-select" name="${campo.nombre}" ${campo.requerido ? 'required' : ''}>
                                <option value="">Seleccione</option>
                                <c:forEach items="${opciones[campo.nombre]}" var="opcion">
                                    <option value="${opcion.id}" ${registro[campo.nombre] == opcion.id ? 'selected' : ''}><c:out value="${opcion.texto}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-md-6">
                            <label class="form-label"><c:out value="${campo.etiqueta}"/>${campo.requerido ? ' *' : ''}</label>
                            <input class="form-control" type="${campo.tipo}" name="${campo.nombre}" value="${fn:escapeXml(campo.tipo == 'time' && not empty registro[campo.nombre] ? fn:substring(registro[campo.nombre],0,5) : registro[campo.nombre])}" ${campo.requerido ? 'required' : ''} ${campo.tipo == 'password' ? 'autocomplete="new-password"' : ''}>
                            <c:if test="${campo.tipo == 'password' && not empty registro}"><small class="form-hint">Déjela vacía para conservar la contraseña actual.</small></c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <div class="col-12 form-actions">
                <a class="btn btn-light" href="${pageContext.request.contextPath}/app/admin/crud?m=${modulo.clave}">Cancelar</a>
                <button class="btn btn-primary" type="submit"><svg class="icono icono-save" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-save"></use></svg>Guardar información</button>
            </div>
        </form>
    </section>
</div>
<section class="panel-card">
    <div class="panel-title table-title"><div><h2>Registros disponibles</h2><p>Total: ${registros.size()} registros.</p></div><div class="search-box"><svg class="icono icono-search" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-search"></use></svg><input type="search" class="form-control" placeholder="Buscar registro" data-table-search="crudTable"></div></div>
    <div class="table-responsive">
        <table class="table data-table" id="crudTable">
            <thead><tr><c:forEach items="${modulo.columnas}" var="columna"><th><c:out value="${columna.etiqueta}"/></th></c:forEach><th class="text-end">Acciones</th></tr></thead>
            <tbody>
                <c:forEach items="${registros}" var="fila">
                    <tr>
                        <c:forEach items="${modulo.columnas}" var="columna"><td><c:out value="${fila[columna.clave]}"/></td></c:forEach>
                        <td class="text-end actions-cell">
                            <a class="icon-btn edit" title="Editar" href="${pageContext.request.contextPath}/app/admin/crud?m=${modulo.clave}&accion=editar&id=${fila[modulo.pk]}"><svg class="icono icono-edit" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-edit"></use></svg></a>
                            <a class="icon-btn delete" title="Eliminar" data-confirm="¿Desea eliminar este registro?" href="${pageContext.request.contextPath}/app/admin/crud?m=${modulo.clave}&accion=eliminar&id=${fila[modulo.pk]}"><svg class="icono icono-delete" aria-hidden="true"><use href="${pageContext.request.contextPath}/assets/img/iconos.svg#icon-delete"></use></svg></a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty registros}"><tr><td colspan="${modulo.columnas.size()+1}" class="empty-cell">No existen registros disponibles.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</section>
<%@include file="../includes/footer.jsp"%>
