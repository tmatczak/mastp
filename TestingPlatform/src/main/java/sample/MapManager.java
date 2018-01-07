package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;

import java.util.Locale;

public class MapManager implements MapComponentInitializedListener{

    private GoogleMapView mapView;
    private GoogleMap googleMap;

    public MapManager() {
        mapView = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapView.addMapInializedListener(this);
    }

    public GoogleMapView getMapView() {
        return mapView;
    }

    @Override
    public void mapInitialized() {
        LatLong warsaw = new LatLong(52.237049,  21.017532);

        MapOptions options = new MapOptions();
        options.center(warsaw)
                .mapMarker(true)
                .zoom(4)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN)
                .styleString("[{'featureType':'landscape','stylers':[{'saturation':-100},{'lightness':65},{'visibility':'on'}]},{'featureType':'poi','stylers':[{'saturation':-100},{'lightness':51},{'visibility':'simplified'}]},{'featureType':'road.highway','stylers':[{'saturation':-100},{'visibility':'simplified'}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]");
        googleMap = mapView.createMap(options,false);
    }

    public void setMarker(String title, LatLong coordinates) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(coordinates)
                .visible(Boolean.TRUE)
                .title(title);

        Marker marker = new Marker( markerOptions );
        googleMap.addMarker(marker);
    }
}
