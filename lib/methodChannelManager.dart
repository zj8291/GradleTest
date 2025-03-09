import 'package:flutter/services.dart';

const String methodName_openMyTestActivity = "openMyTestActivity";
const String methodName_share = "share";
const String methodName_openUrlBySystemDefault = "openUrlBySystemDefault";
const String methodName_callTo = "callTo";
const String methodName_startService = "startService";
const String methodName_bindService = "bindService";
const String methodName_bindForegroundService = "bindForegroundService";

class MethodChannelManager {
  MethodChannelManager._();

  static MethodChannelManager? _instance;

  static MethodChannelManager get instance =>
      _instance ??= MethodChannelManager._();

  MethodChannel nativeMethodChannel = MethodChannel("nativeMethodChannel");
}
