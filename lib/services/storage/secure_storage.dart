import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SecureStorage {
  static const FlutterSecureStorage _storage = FlutterSecureStorage();
  
  static Future<void> saveToken(String token) async {
    await _storage.write(key: 'access_token', value: token);
  }
  
  static Future<String?> getToken() async {
    return await _storage.read(key: 'access_token');
  }
  
  static Future<void> deleteToken() async {
    await _storage.delete(key: 'access_token');
  }
  
  static Future<void> saveUser(String userJson) async {
    await _storage.write(key: 'user', value: userJson);
  }
  
  static Future<String?> getUser() async {
    return await _storage.read(key: 'user');
  }
  
  static Future<void> clearAll() async {
    await _storage.deleteAll();
  }
}