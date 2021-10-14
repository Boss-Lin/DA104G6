<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>

<style>
	#map{
		width: 100%;
		height: 660px;
	}
</style>
 <script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>

<div id='map'></div>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap" async defer></script>

<script>
var myInitCenter = {lat: 24.9680014, lng: 121.1900142};
window.onload = initMap;
function initMap() {

	var map;//地圖物件
	var location;//緯經度
	var geocoder = new google.maps.Geocoder();//查詢地址
	var points = new Array();
	var markers = [];
	var infowindows = [];
	map = new google.maps.Map(document.getElementById('map'), {
        center: myInitCenter,
        zoom: 12
    });	
	
	//起始標記
	var markerSelect = new google.maps.Marker({
        position: myInitCenter,
        map: map,
        icon: '<%=request.getContextPath()%>/images/icons/helmet.png',
        draggable: true
        });
	
	
	google.maps.event.addListener(markerSelect, 'dragstart', (e) => {
		cleanMarkers();
	});
	

	//拖曳事件
	google.maps.event.addListener(markerSelect, 'dragend', (e) => {
		location = e.latLng;
		$.ajax({
			url: '<%=request.getContextPath()%>/bike_rental/bike_rental.do',
			type: 'post',
			data: {
				action: 'rentalSelect',
				lat: location.lat(),
				lng: location.lng()
			},
			success: (data) => {
				var dataArray = JSON.parse(data);
				if(data.length != 0){
					createMarker(dataArray);
				}
			},
			error: () => {
				console.log('error');
			}
		});
	});
	
	
	function createMarker(data){
		for(var i = 0; i < data.length; i++){	
			var address = showAddress(data[i].lng, data[i].lat);
			console.log(address);
			
			//marker
			markers[i] = new google.maps.Marker({
				position: {
					lat: parseFloat(data[i].lng),
					lng: parseFloat(data[i].lat),
				},
				title: '單車店',
				animation: google.maps.Animation.DROP,
				icon: '<%= request.getContextPath()%>/images/icons/Spot_Icon.png',
			});
			//infowindow
			
			infowindows[i] = new google.maps.InfoWindow({
				maxWidth: 350,
				content: '<p>' + data[i].店名 + '</p><p>' + data[i].address + '</p>'
			});
			//eventListener
			google.maps.event.addListener(markers[i], 'click', callback(infowindows[i], markers[i]));

			markers[i].setMap(map);
		}
	}
	
	function callback(infoWindow, marker){
		return function(e){
			openInfo(infoWindow, marker);
		}
	}
	
	function openInfo(infowindow, marker){
		infowindow.open(map, marker)
	}
	
	
	function cleanMarkers(){
		for(var i = 0; i < markers.length; i++){	
			markers[i].setMap(null);
		}
		markers = [];
	}
	
	function showAddress(lat, lng){
		var address;
		var geocoder = new google.maps.Geocoder();
		var coder = new google.maps.LatLng(lat, lng);
		geocoder.geocode({'latLng' : coder}, (results, status) => {
			if(status === google.maps.GeocoderStatus.OK){
				address = results[0].formatted_address;
			}else
				console.log(status);
		});
		return address;
	}
}
</script>
</body>
</html>