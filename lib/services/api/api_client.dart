import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class ApiClient {
  static const String baseUrl = 'http://localhost:9090/api';
  
  final Dio _dio = Dio(BaseOptions(
    baseUrl: baseUrl,
    connectTimeout: const Duration(seconds: 30),
    receiveTimeout: const Duration(seconds: 30),
    headers: {'Content-Type': 'application/json'},
  ));
  
  final FlutterSecureStorage _storage = const FlutterSecureStorage();
  
  Dio get dio => _dio;
  
  Future<void> setToken(String token) async {
    await _storage.write(key: 'access_token', value: token);
    _dio.options.headers['Authorization'] = 'Bearer $token';
  }
  
  Future<String?> getToken() async {
    return await _storage.read(key: 'access_token');
  }
  
  Future<void> clearToken() async {
    await _storage.delete(key: 'access_token');
    _dio.options.headers.remove('Authorization');
  }
}