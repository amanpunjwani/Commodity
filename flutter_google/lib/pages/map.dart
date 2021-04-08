import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:flutter_google/pages/Announcements.dart';
import 'package:flutter_google/pages/Profile.dart';


class Map extends StatefulWidget {

  _MapState createState() => _MapState();
}

class _MapState extends State<Map> {

  Set<Marker> _markers = {};

  void _onMapCreated(GoogleMapController controller){
    setState(() {
      _markers.add(
        Marker(markerId: MarkerId('id-1'),
            position: LatLng(32.8025,-96.8351),
            infoWindow:  InfoWindow(
              title: 'American Red Cross' ,
              snippet: "Address: 2055 Kendall Drive Dallas ; Number: (214) 678-4800",
            )
        ),
      );
      _markers.add(
        Marker(markerId: MarkerId('Brother Bills Helping Hand'),
          position: LatLng(32.8089,-96.8314),
          infoWindow:  InfoWindow(
            title: 'Brother Bills Helping Hand' ,
            snippet: "Address: 3906 N Westmoreland Rd ; Number: (214) 638-2196",
          )
        ),
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: GoogleMap(
        onMapCreated: _onMapCreated,
        markers: _markers,
        initialCameraPosition: CameraPosition(
        target: LatLng(32.8025,-96.8351),
        zoom:13,
      ),
      ),
    );
  }

  }


