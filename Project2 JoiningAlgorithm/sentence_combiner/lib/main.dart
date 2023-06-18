import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:http/http.dart' as http;
import 'package:oktoast/oktoast.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'COMBINER',
      darkTheme: ThemeData.dark(useMaterial3: true ),
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});


  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int formCounter = 2;
  List<Map<String, dynamic>> dataArray = [];
  Map<String , dynamic> dataArrayMap = {};
  String datas = "";
  String result = "";
  double time = 0.0;

  Widget inputForm (index) => TextFormField(
      decoration: InputDecoration(hintText: "${index+1} Input"),
      onChanged: (context) => updateInputs(index,context)

  );

  Widget IconRow () => Row(
    mainAxisAlignment: MainAxisAlignment.spaceBetween,
    children: [
      IconButton(onPressed: ()=>setState(() {
        formCounter++;
      }), icon: Icon(Icons.add_circle),color: Colors.lightBlue),
      Visibility(
        visible: formCounter>0,
        child: IconButton(onPressed: ()=>setState(() {
          formCounter--;
        }), icon: Icon(Icons.remove_circle),color: Colors.red,visualDensity: VisualDensity.comfortable),
      )],

  );

  void updateInputs(int index,String context) {

    void addData() {
      Map<String, dynamic> json = {'id': index, 'value': context};
      dataArray.add(json);
      setState(() {
        datas = dataArray.toString();
      });
    }
    if (dataArray.isEmpty) {
      addData();
    }
    else {
      for (var map in dataArray) {
        if(map["id"] == index)
        {
          dataArray[index]['value'] = context;
          setState(() {
            datas = dataArray.toString();
          });
          break;
        }
      }
    }
    for (var m in dataArray) {
      if (m["id"] == index) {
        return;
      }
    }
    addData();




  }


  void saveData(){
    setState(() {
      dataArray.clear();
      dataArrayMap.clear();
      formCounter = 0;
      result = "";
      time = 0.0;

    });


  }

//Future<Map<String, dynamic>>?
  void submitData()async{
    //print(dataArrayMap.toString());
    Map<String , Map> resultMap = {};
    resultMap["texts"] = dataArrayMap;
    print(resultMap.toString());

    var response = await http.post(Uri.parse("http://localhost:8080/api"),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },body: jsonEncode(resultMap));



    var data = json.decode(utf8.decode(response.bodyBytes));
    setState(() {
      result = data["result"];
      time = data["time"];
    });


    print(data.toString());

  }

  void updateArray(){
    for (var map in dataArray)
    {
      int index = map["id"];
      dataArrayMap[index.toString()] = map["value"];

    }



  }

  void message(){
      showToast(
        " Successfully saved ",
        position: ToastPosition.bottom,
        backgroundColor: Color(0xFFEE507A),
        duration: Duration(seconds: 2),
        radius: 13.0,
        textStyle: TextStyle(fontSize: 18.0,color:Colors.white),);
  }






  @override
  Widget build(BuildContext context) {

    return OKToast(
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,

          title: Center(child: Text("COMBINER", style: TextStyle(fontStyle: FontStyle.italic,color: Colors.black,fontSize: 34), )),
        ),
        body:Row(
          children: [
            Expanded(
              flex: 1,
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(12.0),
                    child: Text("INPUT FIELD",style: TextStyle(color: Colors.black,fontSize: 25)),
                  ),
                  const SizedBox(height: 15),
                  //ListView.builder(padding: EdgeInsets.all(8),itemCount: formCounter,itemBuilder:(BuildContext context,int index){
                  //return Container(height: 60,child: inputForm(index));
                  //}),
                  ...List.generate(formCounter, (index) => inputForm(index)),
                  IconRow(),
                  SizedBox(height: 15,),
                  Tooltip(textStyle: TextStyle(color: Colors.yellow,fontStyle: FontStyle.italic),message: "Combine all inputs and show the result".toUpperCase(),child: OutlinedButton(onPressed: (){updateArray();submitData();}, child:Text("COMBINE",style: TextStyle(color: Colors.black,),selectionColor: Colors.blue,) ))
                ],

              ),
            ),
            Expanded(flex: 2,child: Column(
              mainAxisAlignment: MainAxisAlignment.start,

              children: [
                Padding(
                  padding: const EdgeInsets.all(12.0),
                  child: Text("RESULT : ${result}",style: TextStyle(color: Colors.black,fontSize: 25)),
                ),
                Padding(
                  padding: const EdgeInsets.all(12.0),
                  child: Text("TIME : ${time} Second",style: TextStyle(color: Colors.black,fontSize: 25)),
                ),
                Tooltip(textStyle: TextStyle(color: Colors.yellow,fontStyle: FontStyle.italic),message: "SAVE ALL THE INFORMATION",child: OutlinedButton(onPressed: (){saveData();message();}, child:Text("SAVE",style: TextStyle(color: Colors.black,),selectionColor: Colors.blue,) ))

              ],
            )),

          ],
        ),
      ),
    );
  }
}