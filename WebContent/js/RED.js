/* type de recherche actuellement demandé par l'utilisateur : par date, location, ou titre */
var searchTypeSelection = 1;

var course_id = -1;
			
/* Créé et affiche un message toast:  une notification */
function createToast(){
	var options = {
		duration: 5000,
		sticky: false,
		type: "success"
	};
	
	$.toast('<h4>Success</h4> You\'ve been add to this course session.', options);
}

// remplit la liste des locations avec les items contenu dans 'data'
function populateLocationList(select, data) {
    var items = [];
    $.each(data, function (id, option) {
        items.push('<li role="presentation"> <a role="menuitem" tabindex="-1" href="#">' + option.city + '</a></li>');
    });  
    select.html(items.join(''));
}


//ajoute un cookie
function setCookie(c_name,value,exdays)
{
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie=c_name + "=" + c_value;
}

//récupère un cookie
function getCookie(c_name)
{
	var c_value = document.cookie;
	var c_start = c_value.indexOf(" " + c_name + "=");
	if (c_start == -1)
	  {
	  c_start = c_value.indexOf(c_name + "=");
	  }
	if (c_start == -1)
	  {
	  c_value = null;
	  }
	else
	  {
	  c_start = c_value.indexOf("=", c_start) + 1;
	  var c_end = c_value.indexOf(";", c_start);
	  if (c_end == -1)
	  {
	c_end = c_value.length;
	}
	c_value = unescape(c_value.substring(c_start,c_end));
	}
	return c_value;
}

//Set les cookies que nous utilisons et lance la méthode pour demander une souscription
function setUserCookie()
{
	setCookie('lastname',document.getElementById('lastname').value,1);
	setCookie('firstname',document.getElementById('firstname').value,1);
	setCookie('address',document.getElementById('address').value,1);
	setCookie('email',document.getElementById('email').value,1);
	setCookie('phone',document.getElementById('phone').value,1);
	$('#myModal').modal({
		  show: false
		});
	
	subscribe(-1);
}


// demande à l'utilisateur ses infos pour l'enregistrer à la session sessionid 
function subscribe(sessionid) {
	if(sessionid != -1) {
			course_id = sessionid;
	}
	// check if we have user info
  	var username=getCookie("lastname");
  	
  	//si on a les infos, on ne les redemande pas là l'utilisateur 
  	//et on l'enregistre directement 
  	if (username!=null && username!="")
  	{
		$.getJSON("http://localhost:8080/RED/service/subscribe/"+ course_id + "/"
			+ getCookie('lastname') + "/"
			+ getCookie('firstname')+ "/"
			+ getCookie('address') +"/"
			+ getCookie('phone') +"/"
			+ getCookie('email'),
			function (data) {}	
		);
		// trigger toast
		createToast();
		course_id=-1;
		
  	}
  	else {
  		//Si on a pas les infos dans les cookies, on lance le modal
		$('#myModal').modal({
			  show: true
			});					  
  	}
	
}

/* met à jour les données du tableau 
 * des sessions avec les données contenue dans jsonData */
function updateTable(jsonData)
{
	// on utilise cela pour mapper les objets json sur les colonnes du tableau
	 $("#dataTable").jsonTable({
		head : ['Code','Title','Start','End', 'Location', 'Subscribe'],
		json : ['courseCode','title', 'start', 'end', 'location', 'Subscribe']
	});
	
	/*pour chacun des items json, on crée un bouton que l'on place dans le champ Subscribe de l'item
	 * cela nous permet d'avoir un bouton à droite de chacune des lignes permettant de s'enregister
	 * sur la session en question 
	 */
	jQuery.each(jsonData, function(i, val) {
		val['Subscribe']="<button type=\"button\" class=\"btn btn-default btn-lg\" onclick=\"subscribe("+val['id'] + ");\"><span class=\"glyphicon glyphicon-plus-sign\"></span></button>";
	});
	
	
	var options = {
		source : jsonData,
		rowClass : "rowClass"
	};
	/* hack pour que le tableau mette à jour sa taille automatiquement
	 * Sinon, il garde sa taille de la requête précédente, et les nouveaux
	 * éléments sont étalés dans un tableau trop grand
	 */
	document.getElementById("dataTable").style.height = "10px";
	
	//On met à jour proprement dit les données du tableau
	$("#dataTable").jsonTableUpdate(options);
	
	/* et on initialise les tris sur les colonnes */
	$('table.tableSorter').tableSort();

}

/*fonction permettant de changer de mode de recherche entre
 * par date, par location ou par titre.
 * Elle s'occupe d'afficher ou non les champs en rapport avec
 * chaque mode 
 */ 
function setSearch(id) {
	searchTypeSelection = id;
	switch(searchTypeSelection) {
	case 0://par date
		document.getElementById('titleForm').style.display = 'none';
		document.getElementById('locationSaver').style.display = 'none';
		document.getElementById('launchScript').style.display = 'none';
		document.getElementById('datepicker').style.display = 'inline';
		break;
	case 1://par localisation
		document.getElementById('titleForm').style.display = 'none';
		document.getElementById('locationSaver').style.display = 'inline';
		document.getElementById('launchScript').style.display = 'none';
		document.getElementById('datepicker').style.display = 'none';
		break;
	case 2://par titre
		document.getElementById('titleForm').style.display = 'inline';
		document.getElementById('locationSaver').style.display = 'none';
		document.getElementById('launchScript').style.display = 'inline';
		document.getElementById('datepicker').style.display = 'none';
		break;
	}
}

/*récupère les données JSON depuis le serveur et met à jour la table lors d'une
 * requête par mot clé
 */
function getJsonUpdateTableByKeyword() {
	if (searchTypeSelection == 2) {
		$.getJSON("http://localhost:8080/RED/service/byKeyword/" + document.getElementById('titleForm').value, function(data) {
			updateTable(data);
			});
	}
}

// initialise les paramètres des notifications
function initiateToastsParameters() {
	$.toast.config.align = 'right';
	$.toast.config.width = 400;
}

/* initialise les évenements sur le champs de recherche par titre
 * afin que l'on puisse appuyer sur ENTER pour valider
 */
function initiateTitleFormEvents() {
	$('#titleForm').keydown(function(event){
	    if(event.keyCode == 13) {
	    	getJsonUpdateTableByKeyword();
	      return false;
	    }
	});
}



/* autoclose datepicker quand la date est cliquée et effectue
 * alors la requête par date sur le serveur et met à jour les 
 * données du tableau avec les données récupérées */
$(function() {
	$('#datepicker').datepicker().on('changeDate', function (ev) {
		if(ev.viewMode === 'days'){
			$(this).datepicker('hide');
			$.getJSON("http://localhost:8080/RED/service/byDate/" + $('#datepicker').val(), function(data) { 
				updateTable(data);
			});
		}
		
	});
});

/* Permet de récupérer la liste des localisations disponible sur le serveur, et 
 * d'utiliser la réponse pour remplir la liste des localisations
 */
function getAndPopulateLocationList() {
	$.getJSON("http://localhost:8080/RED/service/byLocation", function(json) {
		populateLocationList($('#locationValues'), json);
		$("#locationValues li a").click(function() {
			$("#locationSaver").val($(this).text());
			$.getJSON("http://localhost:8080/RED/service/byLocation/" + $(this).text(), function(data) { 
				updateTable(data);
				});
			});
	});
}