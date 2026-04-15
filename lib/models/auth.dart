class LoginRequest {
  final String email;
  final String password;
  
  LoginRequest({required this.email, required this.password});
  
  Map<String, dynamic> toJson() => {'email': email, 'password': password};
}

class RegisterRequest {
  final String email;
  final String password;
  final String username;
  
  RegisterRequest({required this.email, required this.password, required this.username});
  
  Map<String, dynamic> toJson() => {
    'email': email,
    'password': password,
    'username': username,
  };
}

class AuthResponse {
  final String accessToken;
  final User user;
  
  AuthResponse({required this.accessToken, required this.user});
  
  factory AuthResponse.fromJson(Map<String, dynamic> json) {
    return AuthResponse(
      accessToken: json['accessToken'],
      user: User.fromJson(json['user']),
    );
  }
}

class User {
  final int id;
  final String email;
  final String username;
  final String? name;
  
  User({required this.id, required this.email, required this.username, this.name});
  
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      email: json['email'],
      username: json['username'],
      name: json['name'],
    );
  }
}