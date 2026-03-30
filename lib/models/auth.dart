class LoginRequest {
  final String email;
  final String password;
  
  LoginRequest({
    required this.email,
    required this.password,
  });
  
  Map<String, dynamic> toJson() => {
    'email': email,
    'password': password,
  };
}

class RegisterRequest {
  final String email;
  final String password;
  final String username;
  
  RegisterRequest({
    required this.email,
    required this.password,
    required this.username,
  });
  
  Map<String, dynamic> toJson() => {
    'email': email,
    'password': password,
    'username': username,
  };
}

class AuthResponse {
  final String accessToken;
  final String refreshToken;
  final User user;
  
  AuthResponse({
    required this.accessToken,
    required this.refreshToken,
    required this.user,
  });
  
  factory AuthResponse.fromJson(Map<String, dynamic> json) {
    return AuthResponse(
      accessToken: json['accessToken'],
      refreshToken: json['refreshToken'],
      user: User.fromJson(json['user']),
    );
  }
}