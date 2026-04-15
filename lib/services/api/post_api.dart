import 'package:dio/dio.dart';
import '../../models/post.dart';

class PostApi {
  final Dio _dio;
  
  PostApi(this._dio);
  
  Future<List<Post>> getPosts() async {
    final response = await _dio.get('/posts');
    return (response.data as List).map((e) => Post.fromJson(e)).toList();
  }
  
  Future<Post> createPost(String description, List<String>? imageUrls) async {
    final response = await _dio.post('/posts', data: {
      'description': description,
      'imageURL': imageUrls ?? [],
    });
    return Post.fromJson(response.data);
  }
  
  Future<void> deletePost(int id) async {
    await _dio.delete('/posts/$id');
  }
}