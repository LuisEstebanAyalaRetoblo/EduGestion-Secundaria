<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${param.resultado == 'guardado'}"><div class="alert alert-success alert-dismissible fade show">La información se guardó correctamente.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
<c:if test="${param.resultado == 'eliminado'}"><div class="alert alert-success alert-dismissible fade show">El registro se eliminó correctamente.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
<c:if test="${param.resultado == 'incompleto'}"><div class="alert alert-warning alert-dismissible fade show">Complete los campos obligatorios.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
<c:if test="${param.resultado == 'error'}"><div class="alert alert-danger alert-dismissible fade show">No fue posible completar la operación. Verifique los datos relacionados.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
<c:if test="${param.resultado == 'denegado'}"><div class="alert alert-danger alert-dismissible fade show">No cuenta con permiso para modificar este registro.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
<c:if test="${param.acceso == 'denegado'}"><div class="alert alert-danger alert-dismissible fade show">El acceso solicitado no corresponde a su perfil.<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
