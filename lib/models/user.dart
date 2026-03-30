class User {
  final int id;
  final String email;
  final String username;
  final String? avatarUrl;
  final String? bio;
  final DateTime createdAt;
  
  User({
    required this.id,
    required this.email,
    required this.username,
    this.avatarUrl,
    this.bio,
    required this.createdAt,
  });
  
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      email: json['email'],
      username: json['username'],
      avatarUrl: json['avatarUrl'],
      bio: json['bio'],
      createdAt: DateTime.parse(json['createdAt']),
    );
  }
  
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'email': email,
      'username': username,
      'avatarUrl': avatarUrl,
      'bio': bio,
      'createdAt': createdAt.toIso8601String(),
    };
  }
}