/*
Template Name: Golden-Xchange - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.7
Version: 2.1.0
Author: Sean Ngu
Website: http://www.seantheme.com/color-admin-v2.1/admin/html/
*/

var handleDataTableButtons = function() {
	"use strict";
    
    if ($('#data-table').length !== 0) {
        $('#data-table').DataTable({
            dom: 'Bfrtip',
            buttons: [
                { extend: 'copy', className: 'btn-sm' },
                { extend: 'csv', className: 'btn-sm' },
                { extend: 'excel', className: 'btn-sm' },
                { extend: 'pdf', className: 'btn-sm' },
                { extend: 'print', className: 'btn-sm' }
            ],
            responsive: true
        });
    }
};

var TableManageButtons = function () {
	"use strict";
    return {
        //main function
        init: function () {
            handleDataTableButtons();
        }
    };
}();