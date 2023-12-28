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

$('#aidata-imported').DataTable({
  sScrollX: "100%",


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
  sScrollX: "100%",
  lengthMenu: [15, 25, 50, 100, 200, 500],
  "pageLength": 15
});




// hamburger menu
$(".hamburger,.backdrop").click(function () {
  $("body").toggleClass("nav-collpsed");
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
/*
//return exect parent node of the element
const findParent = (elem, parentClass) => {

  let currentNode = elem;

  while (!currentNode.classList.contains(parentClass)) {
    currentNode = currentNode.parentNode;
  }

  return currentNode;

};

//get active button step number
const getActiveStep = elem => {
  return Array.from(DOMstrings.stepsBtns).indexOf(elem);
};

//set all steps before clicked (and clicked too) to active
const setActiveStep = activeStepNum => {

  //remove active state from all the state
  removeClasses(DOMstrings.stepsBtns, 'js-active');

  //set picked items to active
  DOMstrings.stepsBtns.forEach((elem, index) => {

    if (index <= activeStepNum) {
      elem.classList.add('js-active');
    }

  });
};

//get active panel
const getActivePanel = () => {

  let activePanel;

  DOMstrings.stepFormPanels.forEach(elem => {

    if (elem.classList.contains('js-active')) {

      activePanel = elem;

    }

  });

  return activePanel;

};

//open active panel (and close unactive panels)
const setActivePanel = activePanelNum => {

  //remove active class from all the panels
  removeClasses(DOMstrings.stepFormPanels, 'js-active');

  //show active panel
  DOMstrings.stepFormPanels.forEach((elem, index) => {
    if (index === activePanelNum) {

      elem.classList.add('js-active');

      setFormHeight(elem);

    }
  });

};

//set form height equal to current panel height
const formHeight = activePanel => {

  const activePanelHeight = activePanel.offsetHeight;

  DOMstrings.stepsForm.style.height = `${activePanelHeight}px`;

};

const setFormHeight = () => {
  const activePanel = getActivePanel();

  formHeight(activePanel);
};

/*STEPS BAR CLICK FUNCTION
DOMstrings.stepsBar.addEventListener('click', e => {

  //check if click target is a step button
  const eventTarget = e.target;

  if (!eventTarget.classList.contains(`${DOMstrings.stepsBtnClass}`)) {
    return;
  }

  //get active button step number
  const activeStep = getActiveStep(eventTarget);

  //set all steps before clicked (and clicked too) to active
  setActiveStep(activeStep);

  //open active panel
  setActivePanel(activeStep);
});*/


/*//PREV/NEXT BTNS CLICK
DOMstrings.stepsForm.addEventListener('click', e => {

  const eventTarget = e.target;

  //check if we clicked on `PREV` or NEXT` buttons
  if (!(eventTarget.classList.contains(`${DOMstrings.stepPrevBtnClass}`) || eventTarget.classList.contains(`${DOMstrings.stepNextBtnClass}`))) {
    return;
  }

  //find active panel
  const activePanel = findParent(eventTarget, `${DOMstrings.stepFormPanelClass}`);

  let activePanelNum = Array.from(DOMstrings.stepFormPanels).indexOf(activePanel);

  //set active step and active panel onclick
  if (eventTarget.classList.contains(`${DOMstrings.stepPrevBtnClass}`)) {
    activePanelNum--;

  } else {

    activePanelNum++;

  }

  setActiveStep(activePanelNum);
  setActivePanel(activePanelNum);

});*/

/*//SETTING PROPER FORM HEIGHT ONLOAD
window.addEventListener('load', setFormHeight, false);

//SETTING PROPER FORM HEIGHT ONRESIZE
window.addEventListener('resize', setFormHeight, false);*/

//changing animation via animation select !!!YOU DON'T NEED THIS CODE (if you want to change animation type, just change form panels data-attr)

/*const setAnimationType = newType => {
  DOMstrings.stepFormPanels.forEach(elem => {
    elem.dataset.animation = newType;
  });
};*/

//selector onchange - changing animation
/*const animationSelect = document.querySelector('.pick-animation__select');*/

/*animationSelect.addEventListener('change', () => {
  const newAnimationType = animationSelect.value;

  setAnimationType(newAnimationType);
});*/





