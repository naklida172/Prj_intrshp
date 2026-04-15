import 'package:dio/dio.dart';
import '../../models/auth.dart';

class AuthApi {
  final Dio _dio;
  
  AuthApi(this._dio);
  
  Future<AuthResponse> register(RegisterRequest request) async {
    final response = await _dio.post('/auth/register', data: request.toJson());
    return AuthResponse.fromJson(response.data);
  }
  
  Future<AuthResponse> login(LoginRequest request) async {
    final response = await _dio.post('/auth/login', data: request.toJson());
    return AuthResponse.fromJson(response.data);
  }
  
  Future<void> logout() async {
    await _dio.post('/auth/logout');
  }
}