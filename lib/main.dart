import 'package:flutter/material.dart';
import 'package:my_gradle_test_project01/methodChannelManager.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
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
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(
              height: 20,
            ),
            buttonsView(),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  Widget buttonsView() {
    return Container(
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              TextButton(
                style: const ButtonStyle(
                    backgroundColor:
                        MaterialStatePropertyAll(Colors.lightBlueAccent)),
                onPressed: () {
                  MethodChannelManager.instance.nativeMethodChannel
                      .invokeMethod(methodName_openMyTestActivity,
                          {"clickNumber": _counter});
                },
                child: Text("启动Activity"),
              ),
              TextButton(
                style: const ButtonStyle(
                    backgroundColor:
                        MaterialStatePropertyAll(Colors.lightBlueAccent)),
                onPressed: () {
                  MethodChannelManager.instance.nativeMethodChannel
                      .invokeMethod(methodName_share,
                          {"shareContent": "我今天点击了按钮$_counter下，我好棒"});
                },
                child: Text("点击分享文本"),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              TextButton(
                style: const ButtonStyle(
                    backgroundColor:
                        MaterialStatePropertyAll(Colors.lightBlueAccent)),
                onPressed: () {
                  MethodChannelManager.instance.nativeMethodChannel
                      .invokeMethod(
                    methodName_startService,
                  );
                },
                child: Text("启动Activity后台服务"),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              TextButton(
                style: const ButtonStyle(
                    backgroundColor:
                        MaterialStatePropertyAll(Colors.lightBlueAccent)),
                onPressed: () {
                  MethodChannelManager.instance.nativeMethodChannel
                      .invokeMethod(
                    methodName_bindService,
                  );
                },
                child: Text("启动Activity绑定服务"),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              TextButton(
                style: const ButtonStyle(
                    backgroundColor:
                        MaterialStatePropertyAll(Colors.lightBlueAccent)),
                onPressed: () {
                  MethodChannelManager.instance.nativeMethodChannel
                      .invokeMethod(
                    methodName_bindForegroundService,
                  );
                },
                child: Text("启动Activity启动前台服务"),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              TextButton(
                style: const ButtonStyle(
                    backgroundColor:
                        MaterialStatePropertyAll(Colors.lightBlueAccent)),
                onPressed: () {
                  MethodChannelManager.instance.nativeMethodChannel
                      .invokeMethod(
                    methodName_startCustomViewForegroundService,
                  );
                },
                child: Text("启动自定义View的前台服务"),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
