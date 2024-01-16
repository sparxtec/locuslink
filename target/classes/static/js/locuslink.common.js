

function showSpinner() {
    $('.modal-backdrop').css('opacity', '0.2');
    document.querySelector("#spinnerModal").style.display = "none";
    document.querySelector("#spinnerModal").style.display = "block";				
}
function hideSpinner() {	
	document.querySelector("#spinnerModal").style.display = "none";	
}

function submitFormHTML(action, formName) {
	//alert ("action: " + action + "  formName: " + formName);
	form = document.getElementById(formName);
	form.action = action;
	form.submit();
}

function submitFormHTML_Get(action, formName) {
	//alert ("action: " + action + "  formName: " + formName);
	form = document.getElementById(formName);
	form.action = action;
	form.method = 'GET';
	form.submit();
}

function setupAjaxSend(){
	$(document).ajaxSend(function(e, xhr, options) {
 	    // Retrieve token
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
		
	    //console.log("Get token from veiw sanctions 1: ", token, header);
 	    xhr.setRequestHeader(header, token);
 	});
}


//AJAX Submit Page -JSON Post
function submitFormAjaxJSON(page, myData, responseMethodName) {

    $.ajax({
           type: "POST",
           url: page,

           crossDomain: true,

        headers: {
              "accept": "application/json",
              "Access-Control-Allow-Origin":"*",

           "Access-Control-Allow-Credentials":true,

            "Access-Control-Allow-Headers": "Accept, Origin, Content-Type, Depth, User-Agent, If-Modified-Since, x-auth-token,x-requested-by, Cache-Control, Authorization, X-Req, X-File-Size, X-Requested-With, X-File-Name",

              "Access-Control-Allow-Methods": "POST, GET, OPTIONS, PUT, DELETE",
              "Access-Control-Max-Age": "36001"
          },

           timeout: 3600000, // 1 hour
           data: JSON.stringify(myData),
           cache: false,
           contentType: 'application/json',

     	  dataType: "json",



           success: function (data) {
               // Generic Form name
			   var processAjaxResponse = window[responseMethodName];
			   processAjaxResponse(data);
		   },
		   error: ajaxResponseError
     });
}

//AJAX Submit Page -HTML Post
function submitFormAjaxHTML(url, datastring, responseMethodName) {
     $.ajax({
        type: "POST",
        url: url,

        crossDomain: true,

        headers: {
              "accept": "application/json",
               "Access-Control-Allow-Origin":"*",

               "Access-Control-Allow-Credentials":true,

            "Access-Control-Allow-Headers": "Accept, Origin, Content-Type, Depth, User-Agent, If-Modified-Since, x-auth-token,x-requested-by, Cache-Control, Authorization, X-Req, X-File-Size, X-Requested-With, X-File-Name",


              "Access-Control-Allow-Methods": "POST, GET, OPTIONS, PUT, DELETE",
              "Access-Control-Max-Age": "36002"
          },

        data: datastring,
        success: function (data) {
			 var processAjaxResponse = window[responseMethodName];
			 processAjaxResponse(data);
		},
		error: ajaxResponseError
	 });
}


function ajaxResponseError(data) {
	alert("!!!! Ajax ERROR Returned ->: " + data.status);
	$("#spinnerModal").modal('hide');//added to hide spinner on error
}


// Format Number with comma's for UI
function formatNumber (num) {
	if(num != undefined){
			return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
	}
};

