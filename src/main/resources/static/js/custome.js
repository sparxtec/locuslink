const xValues = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"];


/*new Chart("myChart", {
  type: "line",

  data: {
    labels: xValues,
    datasets: [{
      label: 'Loyal Customers ',
      data: [60, 140, 160, 106, 107, 110, 130, 20, 80, 478, 25, 65],
      borderColor: "#A700FF",
      backgroundColor: [
        '#A700FF'
      ],
      fill: false
    },


      //{
      //     labels: xValues,
      //     label: 'New Customers',
      //     data: [100, 100, 170, 100, 200, 70, 400, 50, 60, 200, 54, 45],
      //     borderColor: "#A700FF",
      //     fill: false
      // }, {
      //     label: 'Unique Customers',
      //     data: [30, 70, 20, 500, 60, 40, 200, 100, 20, 10, 70, 22],
      //     borderColor: "#EF4444",
      //     fill: false
      // },


    ]
  },
  options: {
    legend: {
      labels: {
        boxWidth: 12,
        boxradius: 10,
        borderRadius: 20,
      },

      display: true,
      position: "bottom",
      align: "center",
      itemWidth: 150,



    }
  }
});*/



// dashboard datatable
$('#recent-uploded-data').DataTable({
  sScrollX: "100%",


});




// dashboard datatable
$('#mangeuser-dtatable').DataTable({
  dom: '<"top"i>rt<"bottom"flp><"clear">',
  "searching": false,
  info: false,
});


// asset details datatable
$('#Assetdata-dtatable').DataTable({
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
  // sScrollXInner: "100%",
  // bScrollCollapse: true

});



// hamburger menu
$(".hamburger,.backdrop").click(function () {
  $("body").toggleClass("nav-collpsed");
});

// add input fileds on click

/* Variables */
var row = $(".attr");

function addRow() {
  row.clone(true, true).appendTo("#attributes");
}

function removeRow(button) {
  button.closest("div.attr").remove();
}

$('#attributes .attr:first-child').find('.remove').hide();

/* Doc ready */
$(".add").on('click', function () {
  addRow();
  if ($("#attributes .attr").length > 1) {
    //alert("Can't remove row.");
    $(".remove").show();
  }
});
$(".remove").on('click', function () {
  if ($("#attributes .attr").size() == 1) {
    //alert("Can't remove row.");
    $(".remove").hide();
  } else {
    removeRow($(this));

    if ($("#attributes .attr").size() == 1) {
      $(".remove").hide();
    }

  }
});

