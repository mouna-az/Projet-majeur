var vehicleList=[]
var vehiclShow=[]

function fetch_fire() {
    const GET_FIRE_URL="http://127.0.0.1:8081/fire"; 
    let context = {
    method: 'GET'
    };
    
    fetch(GET_FIRE_URL,context)
    .then(reponse => reponse.json().then(body => fireList_callback(body)))
    .catch(error => err_callback(error));
   }
   
   function err_callback(error){
    console.log(error);
   }
   
   function fireList_callback(reponse) {
    fireList=[];
    
    for(var i = 0; i < reponse.length; i++) {
    fireList[i] = reponse[i]; 
    }
    create_fire();
   }
   
   function create_fire() {
    for(const fire of fireListShow){
 
    print_fire(fire);
    }
   }
   
   function print_fire(fire) {
    var content="intensitÃ© :"+fire.intensity+ " type :" +fire.type +" range :"+ fire.range;
    var circle = L.circle([fire.lat, fire.lon],
    {
    color: 'red',
    fillColor: '#f03',
    fillOpacity: fire.intesity/10,
    radius: fire.range
    } 
    ).addTo(mymap).bindPopup(content);
    firePrinted.push(circle);
 



   }
   
   // --- CODE ---
   
   let fireList = [];
   let firePrinted = [];
   let fireListShow = [];

   
   var intervalId = window.setInterval(function(){
       filtre();
       
    for (i of firePrinted) {
    i.remove();
    }
    firePrinted = [];
    /// call your function here
    fetch_fire();
   }, 10000);
   
   fetch_fire();


var popup = L.popup();

function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString())
        .openOn(mymap);
    console.log(e.latlng)
}

mymap.on('click', onMapClick);


function removeallfire(){
    for (i of firePrinted){
        i.remove()
    } 
    firePrinted= [];
}

function filtre(){
    removeallfire();
    
    var fireTypes=["ALLf","E_Electric","B_Gasoline","D_Metals","A","B_Plastics"];
    var l=[];
    fireListShow=[];
    for (i of fireTypes){
        if (document.getElementById(i).checked){
            console.log(i)
            l.push(i)
            
        }
    }
    if (l.includes("ALLf")){
        fireListShow=fireList

    }
    else{
  
        for (i of fireList){
           if(l.includes(i.type)){
              
            fireListShow.push(i)
           }

    }
    }
    create_fire()
}

























//vehicule

var popup = L.popup();
function onMapClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString())
        .openOn(mymap);
    console.log(e.latlng.lat)
    deplacement(e.latlng.lat,e.latlng.lng,21425)
    fVeh()
   
}

mymap.on('click', onMapClick);
function fetch_vehicle() {
    const GET_VEHICLE_URL="http://127.0.0.1:8081/vehicle"; 
    let context = {
    method: 'GET'
    };
    
    fetch(GET_VEHICLE_URL,context)
    .then(reponse => reponse.json().then(body => vehicleList_callback(body)))
    .catch(error => err_callback(error));
    
    }
    function err_callback(error){
    console.log(error);
    }
    
   


    function vehicleList_callback(reponse) {
    vehicleList=[];
    for(var i = 0; i < reponse.length; i++) {
    vehicleList[i] = reponse[i]; 
    }
    create_vehicle();
    }
    
    function create_vehicle() {
    for(const vehicle of vehiclShow){
    
    print_vehicle(vehicle);
    }
    }
    // "efficiency": 10.0,
   // "liquidType": "WATER",
   // "liquidQuantity": 100.0,
    //"liquidConsumption": 1.0,
   // "fuel": 100.0,
   // "fuelConsumption": 10.0,
    //"crewMember": 8,
   // "crewMemberCapacity": 8,
   // "facilityRefID": 0

    function print_vehicle(vehicle) {
    var content ="id :"+vehicle.id+"<br>type: "+vehicle.type+"<br>liquidType: "+vehicle.liquidType+"<br>liquidQuantity: "+vehicle.liquidQuantity+"<br>fuel: "+vehicle.fuel+"<br>crewMembers: "+vehicle.crewMember+"<br>crewMemberCapacity: "+vehicle.crewMemberCapacity+"<br> vehicule Position: ("+vehicle.lat+","+vehicle.lon+")"

    var myicon = L.icon({
        iconUrl: 'vehicule.png'});
    var vehic =L.marker([vehicle.lat, vehicle.lon],{icon: myicon}).addTo(mymap).bindPopup(content);
    

    L.imageOverlay(imageUrl, imageBounds).addTo(mymap).bindPopup(content) ;
    
        vehiclePrinted.push(vehic);
    
   
    }
    
    //vehicule
  
    let vehiclePrinted = [];

    
    var intervalId2 = window.setInterval(function(){
    fVeh();
    
    
    
    /// call your function here
    fetch_vehicle();
    }, 10000);
   
    fetch_vehicle();

function removeall(){
    for (i of vehiclePrinted){
        i.remove()
    } 
    vehiclePrinted= [];
}
function deleteVehicle(){
   
    var id= document.getElementById("id").value;
    var deleteUrl= "http://127.0.0.1:8081/vehicle/"+id; 
    let context = {
    method: 'DELETE'}
    fetch(deleteUrl,context);
    
    
}

function add(){
    
    var id= 325;
    var lon=document.getElementById("long").value
    var lat=document.getElementById("lat").value
    var ty=document.getElementById("type").value
    var addvehcle= "http://127.0.0.1:8081/vehicle"; 
    var param = {
        "id": 20328,
        "lon": lon,
        "lat": lat,
        "type": ty,
        "efficiency": 10.0,
        "liquidType": "WATER",
        "liquidQuantity": 100.0,
        "liquidConsumption": 1.0,
        "fuel": 100.0,
        "fuelConsumption": 10.0,
        "crewMember": 8,
        "crewMemberCapacity": 8,
        "facilityRefID": 0
    }

    let context = {
        headers: {
            
            'Content-Type': 'application/json'
          },
    method: 'POST',
    body: JSON.stringify(param)
    

    }
    fetch(addvehcle,context);
    
    
}

function fVeh(){
    removeall()
    
    var vehTypes=["TRUCK","CAR","FIRE_ENGINE","PUMPER_TRUCK","WATER_TENDER","TURNTABLE_LADDER_TRUCK","TRUCK","ALL"]
    var l=[]
    vehiclShow=[]
    for (i of vehTypes){
        if (document.getElementById(i).checked){
            l.push(i)
            
        }
    }
    if (l.includes("ALL")){
        vehiclShow=vehicleList
        
       

    }
    else{
  
        for (i of vehicleList){
           if(l.includes(i.type)){
              
            vehiclShow.push(i)
           }

    }
    }
    create_vehicle()
}
function reset(){
    const resetfire="http://127.0.0.1:8081/fire/reset"; 
    let context = {
    method: 'GET'
    }
    fetch(resetfire,context)
}


function deplacement(lat,lon,id){
    var vehic
    for (i of vehicleList){
        if (i.id==id){
            vehic=i;
            break
        }
    }
    vehic.lat=lat;
    vehic.lon=lon;
    const confVeh="http://127.0.0.1:8081/vehicle/"+vehic.id; 
   
    var param = {
        
        "lon": lon,
        "lat": lat,}
    let context = {

        headers: {
            
            'Content-Type': 'application/json'
          },
        method: 'PUT',
        body:JSON.stringify(vehic)
    }
    fetch(confVeh,context);
    console.log(vehic)


}