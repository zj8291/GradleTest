import 'package:flutter/services.dart';

const String methodName_openMyTestActivity = "openMyTestActivity";

class MethodChannelManager {
  MethodChannelManager._();

  static MethodChannelManager? _instance;

  static MethodChannelManager get instance =>
      _instance ??= MethodChannelManager._();

  MethodChannel nativeMethodChannel = MethodChannel("nativeMethodChannel");
}
