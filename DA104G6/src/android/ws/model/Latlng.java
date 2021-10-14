package android.ws.model;

public class Latlng {
	private String type;
    private String receiver;
    private Double lat;
    private Double lng;

    public Latlng() {
    }

    public Latlng(String type, String receiver, Double lat, Double lng) {
        this.type = type;
        this.receiver = receiver;
        this.lat = lat;
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}