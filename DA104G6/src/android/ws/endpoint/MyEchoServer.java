package android.ws.endpoint;



import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import android.ws.model.Latlng;




@ServerEndpoint("/MyEchoServer/{group_no}")
public class MyEchoServer {
	private static Map<String, Set<Session>> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("group_no") String group_no, Session userSession) throws IOException {
		/* save the new user in the map */
		if(!sessionsMap.containsKey(group_no)) {
			Set<Session> u = new HashSet<>();
			u.add(userSession);
			sessionsMap.put(group_no, u);
		}else {
			Set<Session> u = sessionsMap.get(group_no);
			u.add(userSession);
			sessionsMap.put(group_no, u);
		}
		System.out.println("userSession: "+userSession);
		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(),
				group_no, userSession);
		System.out.println(text);
		
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		System.out.println("onMessage");
		Latlng chatMessage = gson.fromJson(message, Latlng.class);
		System.out.println(chatMessage);
		double lat = chatMessage.getLat();
		System.out.println(lat);
		double lng = chatMessage.getLng();
		String receiver = chatMessage.getReceiver();
		System.out.println(receiver);
		
		
		for(Session session : sessionsMap.get(receiver)) {
			if(session.isOpen() && userSession!=session)
			session.getAsyncRemote().sendText(message);
		}
		
		System.out.println("Message received: " + message);
		
		for(Session a:sessionsMap.get(receiver)) {
			a.toString();
		}
		
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		Set<Session> session1 ;
		
		for (String userName : userNames) {
			System.out.println("userName");
			for(Session session : sessionsMap.get(userName)) {	
				if (session.equals(userSession)) {
					System.out.println("session:" +session);
					System.out.println("userSession:" +userSession);
					sessionsMap.get(userName).remove(session);
					break;
				}
			}
		}
	
	}
}