
/* $("#aifile-details").DataTable({
                responsive: true,
            });

            $("#edit-details").DataTable({
                responsive: true,
                searching: false,
                info: false,
                paging: false
            });
*/

$('#aidata-imported').DataTable({
  responsive: true,

});

// mangeuser datatable
$('#mangeuser-dtatable').DataTable({
  dom: '<"top"i>rt<"bottom"flp><"clear">',
  "searching": false,
  info: false,
  sScrollX: "100%",
});


// asset details datatable
$('#Assetdata-dtatable').DataTable({
  // responsive: true,
  dom: '<"top"i>rt<"bottom"flp><"clear">',
  searching: false,
  info: false, scrollX: true

});
// upload data page datatable

$('#uploaddata-dtatable').DataTable({
  // responsive: true,
  dom: '<"top"i>rt<"bottom"flp><"clear">',
  searching: false,
  info: false, scrollX: true

});


// history tab of asset details
$('#history-table').DataTable({
  // responsive: true,
  dom: '<"top"i>rt<"bottom"flp><"clear">',
  searching: false,
  info: false,
  sScrollX: "100%",
});


//integraion page -table 
$('#integraion-tble').DataTable({
  responsive: true,
  sScrollX: "100%",
  lengthMenu: [15, 25, 50, 100, 200, 500],
  "pageLength": 15
});

// conection page -table 
$('#conection-tble').DataTable({
  responsive: true,
  sScrollX: "100%",
  lengthMenu: [15, 25, 50, 100, 200, 500],
  "pageLength": 15
});


// Dictionaries page -table 
$('#dictionari-tble').DataTable({
  responsive: true,
 
  lengthMenu: [15, 25, 50, 100, 200, 500],
  "pageLength": 15
});


$(".save-conetion").click(function () {
  $(this).parents(".crete-conetion-bx").hide("");
  $(this).parents(".borderbx").find('.editconetion, .detailsbx').show();
  $(this).parents(".borderbx").next('.borderbx').find('.btn_authOuter').removeAttr('disabled');
});

$(".editconetion").click(function () {
  $(this).parents(".borderbx").find('.crete-conetion-bx').show();
  $(this).parents(".borderbx").find(".detailsbx,.editconetion").hide("");
});

$('.btn_authOuter').click(function () {
  $(this).parent().hide();
  $(this).parents('.auth_container').find('.from_wrap').show();
})

$('.delete-btn').click(function () {
  $(this).parents('tr').hide();
});

// add input fileds on click


// step form checking
//DOM elements
const DOMstrings = {
  stepsBtnClass: 'multisteps-form__progress-btn',
  stepsBtns: document.querySelectorAll(`.multisteps-form__progress-btn`),
  
/*  stepsBar: document.querySelector('.multisteps-form__progress'),*/
  
  stepsForm: document.querySelector('.multisteps-form__form'),
  stepsFormTextareas: document.querySelectorAll('.multisteps-form__textarea'),
  stepFormPanelClass: 'multisteps-form__panel',
  stepFormPanels: document.querySelectorAll('.multisteps-form__panel'),
  stepPrevBtnClass: 'js-btn-prev',
  stepNextBtnClass: 'js-btn-next'
};


//remove class from a set of items
const removeClasses = (elemSet, className) => {

  elemSet.forEach(elem => {

    elem.classList.remove(className);

  });

};




