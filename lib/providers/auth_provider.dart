import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../services/api/api_client.dart';
import '../services/api/auth_api.dart';
import '../services/storage/secure_storage.dart';
import '../models/auth.dart';

final authProvider = StateNotifierProvider<AuthNotifier, AuthState>((ref) {
  final apiClient = ApiClient();
  final authApi = AuthApi(apiClient.dio);
  return AuthNotifier(authApi);
});

class AuthNotifier extends StateNotifier<AuthState> {
  final AuthApi _authApi;
  
  AuthNotifier(this._authApi) : super(const AuthState.initial());
  
  Future<void> login(String email, String password) async {
    state = const AuthState.loading();
    try {
      final response = await _authApi.login(LoginRequest(email: email, password: password));
      await SecureStorage.saveToken(response.accessToken);
      await SecureStorage.saveUser(response.user.toJson().toString());
      state = AuthState.authenticated(response.user);
    } catch (e) {
      state = AuthState.error(e.toString());
    }
  }
  
  Future<void> register(String email, String password, String username) async {
    state = const AuthState.loading();
    try {
      final response = await _authApi.register(RegisterRequest(email: email, password: password, username: username));
      await SecureStorage.saveToken(response.accessToken);
      state = AuthState.authenticated(response.user);
    } catch (e) {
      state = AuthState.error(e.toString());
    }
  }
  
  Future<void> logout() async {
    await SecureStorage.clearAll();
    state = const AuthState.unauthenticated();
  }
  
  Future<void> checkAuth() async {
    final token = await SecureStorage.getToken();
    if (token != null) {
      state = const AuthState.authenticated(null);
    } else {
      state = const AuthState.unauthenticated();
    }
  }
}

class AuthState {
  final bool isLoading;
  final User? user;
  final String? error;
  final bool isAuthenticated;
  
  const AuthState({
    required this.isLoading,
    this.user,
    this.error,
    required this.isAuthenticated,
  });
  
  const AuthState.initial() : this(isLoading: false, isAuthenticated: false);
  const AuthState.loading() : this(isLoading: true, isAuthenticated: false);
  const AuthState.authenticated(this.user) : this(isLoading: false, isAuthenticated: true, error: null);
  const AuthState.unauthenticated() : this(isLoading: false, isAuthenticated: false, error: null);
  const AuthState.error(this.error) : this(isLoading: false, isAuthenticated: false, user: null);
}