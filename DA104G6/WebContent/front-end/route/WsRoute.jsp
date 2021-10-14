<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Removing Markers</title>
<style>
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
	height: 100%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body onload="connect();" onunload="disconnect();">

	<div id="map"></div>
	<p>Click on the map to add markers.</p>
	<script>
		var map;
		var markers = [];
		var markerArr = [];

		function initMap() {
			var haightAshbury = {
				lat : 24.967942,
				lng : 121.191687
			};

			map = new google.maps.Map(document.getElementById('map'), {
				zoom : 18,
				center : haightAshbury,
				mapTypeId : 'terrain'
			});

		}

		// Adds a marker to the map and push to the array.
		function addMarker(location) {
			var flightPath = new google.maps.Polyline({
				path : markerArr,
				geodesic : true,
				strokeColor : '#FF0000',
				strokeOpacity : 1.0,
				strokeWeight : 2
			});
			var image = "<%=request.getContextPath()%>/images/icons/marker.png"
			var marker = new google.maps.Marker({
				position : location,
				map : map,
				icon : image
			});
			markers.push(marker);
			markerArr.push(location);
			console.log(markerArr);
			flightPath.setMap(map);
		}

		// Sets the map on all markers in the array.
		function setMapOnAll(map) {
			for (var i = 0; i < markers.length; i++) {
				markers[i].setMap(map);
			}
		}

		// Removes the markers from the map, but keeps them in the array.
		function clearMarkers() {
			setMapOnAll(null);
		}

		// Shows any markers currently in the array.
		function showMarkers() {
			setMapOnAll(map);
		}

		// Deletes all markers in the array by removing references to them.
		function deleteMarkers() {
			clearMarkers();
			markers = [];
		}

		var MyPoint = "/MyEchoServer/M0001";
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
		//  var endPointURL = "ws://localhost:8081/IBM_WebSocket1_ChatA/MyEchoServer";

		var statusOutput = document.getElementById("statusOutput");
		var webSocket;

		function connect() {
			// 建立 websocket 物件
			webSocket = new WebSocket(endPointURL);

			webSocket.onopen = function(event) {
				console.log("成功連線");
			};

			webSocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				var latFsocket = jsonObj.lat;
				var lngFsocket = jsonObj.lng;
				console.log("lat:" + latFsocket + "lng" + lngFsocket)
				if (latFsocket != null && lngFsocket != null) {
					var loaction = {
						lat : parseFloat(latFsocket),
						lng : parseFloat(lngFsocket)
					};
					deleteMarkers();
					addMarker(loaction);
				}
			};

			webSocket.onclose = function(event) {
				updateStatus("WebSocket 已離線");
			};
		}

		function sendMessage() {
			var userName = inputUserName.value.trim();
			if (userName === "") {
				alert("使用者名稱請勿空白!");
				inputUserName.focus();
				return;
			}

			var inputMessage = document.getElementById("message");
			var message = inputMessage.value.trim();

			if (message === "") {
				alert("訊息請勿空白!");
				inputMessage.focus();
			} else {
				var jsonObj = {
					"userName" : userName,
					"message" : message
				};
				webSocket.send(JSON.stringify(jsonObj));
				inputMessage.value = "";
				inputMessage.focus();
			}
		}

		function disconnect() {
			webSocket.close();
		}

		function updateStatus(newStatus) {
			statusOutput.innerHTML = newStatus;
		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap">
		
	</script>



</body>
</html>

<!-- <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAk-AdeXtoAlDZBCh-TydXYix-HV1oJWes&callback=initMap">
    </script> -->