class AppConstants {
  static const String appName = 'Loop App';
  static const String baseUrl = 'http://10.0.2.2:8080/api';
  
  // Storage keys
  static const String accessTokenKey = 'access_token';
  static const String refreshTokenKey = 'refresh_token';
  static const String userKey = 'user';
  
  // Error messages
  static const String networkError = 'Network error. Please check your connection.';
  static const String serverError = 'Server error. Please try again later.';
  static const String unauthorized = 'Session expired. Please login again.';
}