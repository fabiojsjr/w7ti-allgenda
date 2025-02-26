  //DOC https://developers.google.com/calendar/overview
  
  var CLIENT_ID = '466094684716-3s6ol5hivcll59e1f4blq671iaonsrtv.apps.googleusercontent.com';
  var API_KEY = 'AIzaSyAP9oJg2Jct0uEw2tOUVpEpn-pnO6nKmX4';      
  var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"]; 
  var SCOPES = 'https://www.googleapis.com/auth/calendar';   
  var CALENDAR_ID = 'fabioreis1415@gmail.com';  
  
  function handleClientLoad() {
    gapi.load('client:auth2', initClient);
  }  

   function listGoogleCalendarEvents() {
        gapi.client.init({
            apiKey: API_KEY,
            clientId: CLIENT_ID,
            discoveryDocs: DISCOVERY_DOCS,
            scope: SCOPES
        }).then(function () {
        
        var request = gapi.client.calendar.events.list({
            'calendarId': CALENDAR_ID                
        });     

        request.execute(function(events) {
             
            if (typeof(Storage) !== "undefined")        	  
            	localStorage.setItem("listGoogleCalendar", JSON.stringify(events.items));            	
        	else
        	  alert("Erro ao buscar os dados do Google Calendar");        	
        });      

    }, function(error) {
      console.log(JSON.stringify(error, null, 2));
    });
  } 

   function getEvent(idEvent){

    gapi.client.init({
      apiKey: API_KEY,
      clientId: CLIENT_ID,
      discoveryDocs: DISCOVERY_DOCS,
      scope: SCOPES
    }).then(function () {
        
        var requestGet = gapi.client.calendar.events.get({
            'calendarId': CALENDAR_ID, 
            'eventId': 'bo7vvdq29igeivr9hmodsbqib0'               
        });    

        requestGet.execute(function(resp) { 

            if(typeof resp === 'undefined')
                throw 'Erro ao buscar o evento';                                                         
            else
                console.log(resp);                      
        }); 

    }, function(error) {
      console.log(JSON.stringify(error, null, 2));
    });

   }

   function updateEvent(idEvent) {

      getEvent('bo7vvdq29igeivr9hmodsbqib0');

      gapi.client.init({
          apiKey: API_KEY,
          clientId: CLIENT_ID,
          discoveryDocs: DISCOVERY_DOCS,
          scope: SCOPES
      }).then(function () {             

      var request = gapi.client.calendar.events.patch({
          'calendarId': CALENDAR_ID, 
          'eventId': 'bo7vvdq29igeivr9hmodsbqib0',
          'resource' :  {

              'summary': 'Titulo do event noite2',
              'location': 'Sala 2',
              'description': 'Allgenda',
              'end': {
                  'dateTime': '2019-04-23T14:00:00',
                  'timeZone': 'America/Sao_Paulo'
              },
              'start': {
                  'dateTime': '2019-04-23T12:00:00',
                  'timeZone': 'America/Sao_Paulo'
              }                   
          }            
      });     

      request.execute(function(resp) {
          if(typeof resp === 'undefined')
              throw 'Erro ao atualizar o evento';
          else
              console.log('Evento Atualizado com sucesso!');                    
                      
      });           

      }, function(error) {
          console.log(JSON.stringify(error, null, 2));
      }); 
  }

  function deleteEvent(idEvent){

    getEvent('bo7vvdq29igeivr9hmodsbqib0');

    gapi.client.init({
        apiKey: API_KEY,
        clientId: CLIENT_ID,
        discoveryDocs: DISCOVERY_DOCS,
        scope: SCOPES
    }).then(function () {
        
        var request = gapi.client.calendar.events.delete({
            'calendarId': CALENDAR_ID,
            'eventId': 'heqqvkjdips5iu24blj27dc3ag'                
        });     

        request.execute(function(resp){
            if(typeof resp === 'undefined')
                throw 'Erro ao deletar o evento';
            else
                console.log('Evento deletado com sucesso!');
        });                  

    }, function(error) {
        console.log(JSON.stringify(error, null, 2));
    });
   
  }     

   function addEventGoogleCalendar(event) {

      gapi.client.init({
        apiKey: API_KEY,
        clientId: CLIENT_ID,
        discoveryDocs: DISCOVERY_DOCS,
        scope: SCOPES
      }).then(function () {     

        var request = gapi.client.calendar.events.insert({
            'calendarId': CALENDAR_ID,
            'resource': event
        });

        request.execute(function(resp) {
            
            if(typeof resp === 'undefined')
              throw 'Erro ao criar evento';
            else 
              console.log("evento criado: " + resp.id);        
        });
        
    }, function(error) {
      console.log(JSON.stringify(error, null, 2));
    });

  }

  function initClient() {
    gapi.client.init({
      apiKey: API_KEY,
      clientId: CLIENT_ID,
      discoveryDocs: DISCOVERY_DOCS,
      scope: SCOPES
    }).then(function (response) {          
      
    	//verifica se esta autorizado pela api    
      if(!gapi.auth2.getAuthInstance().isSignedIn.Ab)
    	  handleAuthClick();
     
    }, function(error) {
      console.log(JSON.stringify(error, null, 2));
    });
  } 
  
  //chama a tela de autorização
  function handleAuthClick(event) {
    gapi.auth2.getAuthInstance().signIn();
  }
  
  //desloga as credenciais
  function handleSignoutClick(event) {
    gapi.auth2.getAuthInstance().signOut();
  } 

  