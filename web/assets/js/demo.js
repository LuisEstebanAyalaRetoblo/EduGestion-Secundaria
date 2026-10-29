/* Interacciones locales: filtros y prototipos sin peticiones de escritura. */
(function () {
    'use strict';
    var toast = document.getElementById('demoToast');
    var timer;
    function notify(message) {
        toast.textContent = message;
        toast.hidden = false;
        window.clearTimeout(timer);
        timer = window.setTimeout(function () { toast.hidden = true; }, 6500);
    }
    document.querySelectorAll('[data-demo]').forEach(function (button) {
        button.addEventListener('click', function () { notify('Función de prototipo. No se ha guardado, autorizado ni modificado ningún dato.'); });
    });
    function normalize(value) { return value.toLocaleLowerCase('es').normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim(); }
    function filterTable(id) {
        var table = document.getElementById(id);
        var search = document.querySelector('[data-search="' + id + '"]');
        var filters = document.querySelectorAll('[data-filter="' + id + '"]');
        var visible = 0;
        table.querySelectorAll('tbody tr').forEach(function (row) {
            var match = !search || normalize(row.textContent).indexOf(normalize(search.value)) !== -1;
            filters.forEach(function (filter) {
                if (filter.value && normalize(row.cells[Number(filter.dataset.column)].textContent) !== normalize(filter.value)) match = false;
            });
            row.hidden = !match;
            if (match) visible++;
        });
        var empty = table.closest('.card').querySelector('.empty-message');
        if (empty) empty.hidden = visible > 0;
    }
    document.querySelectorAll('[data-search], [data-filter]').forEach(function (input) {
        input.addEventListener(input.tagName === 'SELECT' ? 'change' : 'input', function () { filterTable(input.dataset.search || input.dataset.filter); });
    });
    var menu = document.getElementById('menuButton');
    menu.addEventListener('click', function () {
        var expanded = document.getElementById('navigation').classList.toggle('open');
        menu.setAttribute('aria-expanded', String(expanded));
    });
    var dialog = document.getElementById('studentForm');
    document.querySelectorAll('[data-open]').forEach(function (button) { button.addEventListener('click', function () { dialog.showModal(); }); });
    document.querySelectorAll('[data-close]').forEach(function (button) { button.addEventListener('click', function () { dialog.close(); }); });
    document.getElementById('sampleForm').addEventListener('submit', function (event) {
        event.preventDefault();
        dialog.close();
        notify('Vista previa completada. El catálogo no ha cambiado y no se ha guardado información.');
        event.target.reset();
    });
}());
