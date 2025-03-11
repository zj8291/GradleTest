import 'package:flutter/services.dart';

const String methodName_openMyTestActivity = "openMyTestActivity";
const String methodName_share = "share";
const String methodName_startService = "startService";
const String methodName_bindService = "bindService";
const String methodName_bindForegroundService = "bindForegroundService";
const String methodName_startCustomViewForegroundService =
    "startCustomViewForegroundService";

class MethodChannelManager {
  MethodChannelManager._();

  static MethodChannelManager? _instance;

  static MethodChannelManager get instance =>
      _instance ??= MethodChannelManager._();

  MethodChannel nativeMethodChannel = MethodChannel("nativeMethodChannel");
}
