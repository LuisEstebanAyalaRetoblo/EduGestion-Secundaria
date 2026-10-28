document.addEventListener('DOMContentLoaded',function(){
    var toggle=document.getElementById('menuToggle');
    var sidebar=document.getElementById('sidebar');
    if(toggle&&sidebar){
        toggle.addEventListener('click',function(){sidebar.classList.toggle('open');toggle.setAttribute('aria-expanded',sidebar.classList.contains('open')?'true':'false');});
        document.addEventListener('click',function(e){if(window.innerWidth<992&&sidebar.classList.contains('open')&&!sidebar.contains(e.target)&&!toggle.contains(e.target))sidebar.classList.remove('open');});
    }
    document.querySelectorAll('[data-confirm]').forEach(function(link){link.addEventListener('click',function(e){if(!window.confirm(link.getAttribute('data-confirm')))e.preventDefault();});});
    document.querySelectorAll('[data-table-search]').forEach(function(input){input.addEventListener('input',function(){var table=document.getElementById(input.getAttribute('data-table-search'));if(!table)return;var text=input.value.toLowerCase().trim();table.querySelectorAll('tbody tr').forEach(function(row){row.style.display=row.textContent.toLowerCase().indexOf(text)>=0?'':'none';});});});
    document.querySelectorAll('[data-password]').forEach(function(button){button.addEventListener('click',function(){var input=document.getElementById(button.getAttribute('data-password'));if(!input)return;var visible=input.type==='text';input.type=visible?'password':'text';var use=button.querySelector('use');if(use){var href=use.getAttribute('href')||'';use.setAttribute('href',href.replace(visible?'#icon-visibility_off':'#icon-visibility',visible?'#icon-visibility':'#icon-visibility_off'));}button.setAttribute('aria-label',visible?'Mostrar contraseña':'Ocultar contraseña');});});
    var current=window.location.pathname+window.location.search;
    document.querySelectorAll('.sidebar-nav a').forEach(function(link){var href=link.getAttribute('href');if(href&&current.indexOf(href.replace(window.location.origin,''))>=0)link.classList.add('active');});
});
