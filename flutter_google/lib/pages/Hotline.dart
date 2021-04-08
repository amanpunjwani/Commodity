import 'package:flutter/material.dart';

class Hotline extends StatefulWidget {
  @override
  _HotlineState createState() => _HotlineState();
}

class _HotlineState extends State<Hotline> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        children: List.generate(100,(int i){
          return ListTile(
          title: Text("List Index is $i"),
            subtitle: Text("Hotline InformAtion"),
          );
        }),
    )
    );
  }
}
