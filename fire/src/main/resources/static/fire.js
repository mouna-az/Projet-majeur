
//animation 



L.AnimatedMarker = L.Marker.extend({
    options: {
    // meters
    distance: 2000,
    // ms
    interval: 100,
    // animate on add?
    autoStart: true,
    // callback onend
    onEnd: function(){},
    clickable: false
    },
    
    initialize: function (latlngs, options) {
    this.setLine(latlngs);
    L.Marker.prototype.initialize.call(this, latlngs[0], options);
    },
    
    // Breaks the line up into tiny chunks (see options) ONLY if CSS3 animations
    // are not supported.
    _chunk: function(latlngs) {
    var i,
    len = latlngs.length,
    chunkedLatLngs = [];
    
    for (i=1;i<len;i++) {
    var cur = latlngs[i-1],
    next = latlngs[i],
    dist = cur.distanceTo(next),
    factor = this.options.distance / dist,
    dLat = factor * (next.lat - cur.lat),
    dLng = factor * (next.lng - cur.lng);
    
    if (dist > this.options.distance) {
    while (dist > this.options.distance) {
    cur = new L.LatLng(cur.lat + dLat, cur.lng + dLng);
    dist = cur.distanceTo(next);
    chunkedLatLngs.push(cur);
    }
    } else {
    chunkedLatLngs.push(cur);
    }
    }
    chunkedLatLngs.push(latlngs[len-1]);
    
    return chunkedLatLngs;
    },
    
    onAdd: function (mymap) {
    L.Marker.prototype.onAdd.call(this, mymap);
    
    // Start animating when added to the map
    if (this.options.autoStart) {
    this.start();
    }
    },
    
    animate: function() {
    var self = this,
    len = this._latlngs.length,
    speed = this.options.interval;
    
    // Normalize the transition speed from vertex to vertex
    if (this._i < len && this.i > 0) {
    speed = this._latlngs[this._i-1].distanceTo(this._latlngs[this._i]) / this.options.distance * this.options.interval;
    }
    
    // Only if CSS3 transitions are supported
    if (L.DomUtil.TRANSITION) {
    if (this._icon) { this._icon.style[L.DomUtil.TRANSITION] = ('all ' + speed + 'ms linear'); }
    }
    
    // Move to the next vertex
    this.setLatLng(this._latlngs[this._i]);
    this._i++;
    
    // Queue up the animation to the next next vertex
    this._tid = setTimeout(function(){
    if (self._i === len) {
    self.options.onEnd.apply(self, Array.prototype.slice.call(arguments));
    } else {
    self.animate();
    }
    }, speed);
    },
    
    // Start the animation
    start: function() {
    this.animate();
    },
    
    // Stop the animation in place
    stop: function() {
    if (this._tid) {
    clearTimeout(this._tid);
    }
    },
    
    setLine: function(latlngs){
    if (L.DomUtil.TRANSITION) {
    // No need to to check up the line if we can animate using CSS3
    this._latlngs = latlngs;
    } else {
    // Chunk up the lines into options.distance bits
    this._latlngs = this._chunk(latlngs);
    this.options.distance = 10;
    this.options.interval = 30;
    }
    this._i = 0;
    }
    
    });
    
    L.animatedMarker = function (latlngs, options) {
    return new L.AnimatedMarker(latlngs, options);
    };
   
   
   



//NOTREajax
$(document).ready(function () {

    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        vehicadd_ajax_submit() ;
    });

});
//ajouter
function vehicadd_ajax_submit() {	
    console.log("Je suis dans la fonction")
    var search = {}
    search["lon"] = $("#lon").val();
	search["lat"] = $("#lat").val();
	search["type"] = $("#type").val();
    search["efficiency"] = $("#efficiency").val();
    search["liquidType"] = $("#liquidType").val();
    search["liquidQuantity"] = $("#liquidQuantity").val();
    search["lliquidConsumption"] = $("#liquidConsumption").val();
    search["fuel"] = "100.0" ;
    search["fuelCompsumption"] = "1.0";
    search["crewMenber"] = "2";
    search ["crewMenberCapacity"] = "2"; //faire type==car * 2 + ....
    search ["facilityRefID"] = "0" ; 

    $("#sign").prop("disabled", true);
    
    console.log(JSON.stringify(search))
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/vehicle/add",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("SUCCESS : ", data);
            $("#sign").prop("disabled", false);
        },
    });

    var url= "/vehicle/add"; 
    let context = {
        headers: {
            
            'Content-Type': 'application/json'
          },
    method: 'POST',
    body: JSON.stringify(JSON.stringify(search))
    

    }
    fetch(url,context).then(fetch_vehicle);

}

var vehicleList=[] 
var vehiclShow=[] //??  afficher

function fetch_fire() {
    const GET_FIRE_URL="/allfire"; 
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
    filtre()
    create_fire();
}
   
   function create_fire() {
    for(const fire of fireListShow){
 
    print_fire(fire);
    }
   }
   
   function print_fire(fire) {
    var content="intensite :"+fire.intensity+ " type :" +fire.type +" range :"+ fire.range;
    var circle = L.circle([fire.lat, fire.lon],
    {
    color: 'red',
    fillColor: '#f03',
    fillOpacity: fire.intesity/10,
    radius: fire.range
    } 
    ).addTo(mymap).bindPopup(content)
       

    firePrinted.push(circle);
 



   }

   function remplir(){
		//?? r??fl??chir
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
   }, 5000);
   
   fetch_fire();


var popup = L.popup();

function onMapClick(e) {
    if (document.getElementById("butDep").value=="deplacer"){
    popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.latlng.toString())
        .openOn(mymap);
    console.log(e.latlng)}}


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
function onMapClickDeplacer(e) {
  
  if (document.getElementById("butDep").value=="arreter"){
    deplacement(e.latlng.lat,e.latlng.lng)
    fVeh()
  }
}
function changer(){
    if (document.getElementById("butDep").value=="deplacer"){
        mymap.on('click', onMapClickDeplacer);
    document.getElementById("butDep").value="arreter";

    }
    else{
        document.getElementById("butDep").value="deplacer";
        mymap.on('click', onMapClick);
    }
}


function fetch_vehicle() {
    const GET_VEHICLE_URL="/allvehicule"; 
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
   
    fVeh();
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
    const ids=["long","lat","efficiency","liquidType","liquidQuantity","liquidConsumption","id"]
    var myicon = L.icon({
        iconUrl: 'vehicule.png'});
    var vehic =L.marker([vehicle.lat, vehicle.lon],{icon: myicon}).addTo(mymap).bindPopup(content).on('click',function(e) {
       
        document.getElementById("id").value =vehicle.id
        document.getElementById("lon").value =vehicle.lon
        document.getElementById("lat").value =vehicle.lat
        document.getElementById("efficiency").value =vehicle.efficiency
        document.getElementById("liquidType").value =vehicle.liquidType
        document.getElementById("liquidConsumption").value =vehicle.liquidConsumption
        document.getElementById("liquidQuantity").value =vehicle.liquidQuantity
       
    } );
    

    L.imageOverlay(imageUrl, imageBounds).addTo(mymap).bindPopup(content)
    
        vehiclePrinted.push(vehic);
    
   
    }
    
    //vehicule
  
    let vehiclePrinted = [];

    
    var intervalId2 = window.setInterval(function(){
    fVeh();
    
    
    
    /// call your function here
    fetch_vehicle();
    }, 1000);
   
    fetch_vehicle();

function removeall(){
    for (i of vehiclePrinted){
        i.remove()
    } 
    vehiclePrinted= [];
}
function deleteVehicle(){
   
    var id= document.getElementById("id").value;
    var deleteUrl= "/deleteVehicule/"+id; 
    let context = {
    method: 'DELETE'}
    fetch(deleteUrl,context).then(fetch_vehicle);
        
}

function modifier(){
    id0=document.getElementById("id").value;
    for (vehicule of vehicleList){
        if (vehicule.id==id0){break}
    }
     var id =document.getElementById("id").value
	
    vehicule.type=document.getElementById("type").value
    vehicule.lon=document.getElementById("lon").value 
    vehicule.lat=document.getElementById("lat").value 
    vehicule.efficiency=document.getElementById("efficiency").value 
    vehicule.liquidType=document.getElementById("liquidType").value 
    vehicule.liquidConsumption=document.getElementById("liquidConsumption").value 
    vehicule.liquidQuantity=document.getElementById("liquidQuantity").value 
     var url="/vehicle/"+id;    
   
    let context = {

        headers: {
            
            'Content-Type': 'application/json'
          },
        method: 'PUT',
        body:JSON.stringify(vehicule)

    }
console.log(JSON.stringify(vehicule))


 	fetch(url,context);
    refresh()


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
function resetfire(){
    const resetfire="/fire/reset"; 
    let context = {
    method: 'GET'
    }
    fetch(resetfire,context).then(fetch_fire)
    
}


function deplacement(lat,lon){
    id0=document.getElementById("id").value;
    for (vehic of vehicleList){
        console.log(id0)
        if (vehic.id==id0){break}
    }
    
       
   
    vehic.lat=lat;
    vehic.lon=lon;
    const confVeh="/vehicle/"+vehic.id; 
   
    
    let context = {

        headers: {
            
            'Content-Type': 'application/json'
          },
        method: 'PUT',
        body:JSON.stringify(vehic)

    }
	console.log(JSON.stringify(vehic))
    fetch(confVeh,context).then(fetch_vehicle);
   
}