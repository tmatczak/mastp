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
                .mapType(MapTypeIdEnum.TERRAIN);
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
