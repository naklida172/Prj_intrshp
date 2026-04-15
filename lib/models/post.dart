class Post {
  final int id;
  final String description;
  final List<String>? imageURL;
  
  Post({required this.id, required this.description, this.imageURL});
  
  factory Post.fromJson(Map<String, dynamic> json) {
    return Post(
      id: json['id'],
      description: json['description'] ?? '',
      imageURL: json['imageURL'] != null ? List<String>.from(json['imageURL']) : null,
    );
  }
}