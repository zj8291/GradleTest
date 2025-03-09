import 'package:flutter/services.dart';

const String methodName_openMyTestActivity = "openMyTestActivity";
const String methodName_share = "share";
const String methodName_openUrlBySystemDefault = "openUrlBySystemDefault";
const String methodName_callTo = "callTo";

class MethodChannelManager {
  MethodChannelManager._();

  static MethodChannelManager? _instance;

  static MethodChannelManager get instance =>
      _instance ??= MethodChannelManager._();

  MethodChannel nativeMethodChannel = MethodChannel("nativeMethodChannel");
}
