package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

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

    public void drawDot(LatLong coordinates) {
        CircleOptions cOpts = new CircleOptions()
                .center(coordinates)
                .radius(50)
                .strokeColor("orange")
                .strokeWeight(2)
                .fillColor("orange")
                .fillOpacity(1);

        Circle c = new Circle(cOpts);
        googleMap.addMapShape(c);
    }

    public void drawLine(LatLong start, LatLong end) {
        LatLong[] ary = new LatLong[]{ start, end};
        MVCArray mvc = new MVCArray(ary);

        PolylineOptions polyOpts = new PolylineOptions()
                .path(mvc)
                .strokeColor("red")
                .strokeWeight(2);

        Polyline poly = new Polyline(polyOpts);
        googleMap.addMapShape(poly);
    }
}
