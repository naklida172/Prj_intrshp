import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import '../../../config/theme.dart';
import '../../../services/api/api_client.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> with SingleTickerProviderStateMixin {
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isLoading = false;
  bool _isCheckingConnection = false;
  bool _obscurePassword = true;
  String _connectionStatus = '';
  late AnimationController _animationController;
  late Animation<double> _fadeAnimation;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 800),
    );
    _fadeAnimation = CurvedAnimation(
      parent: _animationController,
      curve: Curves.easeOut,
    );
    _animationController.forward();
    
    // Автоматическая проверка связи при загрузке экрана
    _checkConnection();
  }

  @override
  void dispose() {
    _animationController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  // Проверка связи с бэкендом
  Future<void> _checkConnection() async {
    setState(() {
      _isCheckingConnection = true;
      _connectionStatus = '🔄 Проверка связи с сервером...';
    });

    try {
      final apiClient = ApiClient();
      final response = await apiClient.dio.get('/health');
      
      setState(() {
        _connectionStatus = '✅ Сервер доступен!';
        _isCheckingConnection = false;
      });
    } catch (e) {
      setState(() {
        _connectionStatus = '❌ Сервер недоступен. Запустите бэкенд: cd backend && mvn spring-boot:run';
        _isCheckingConnection = false;
      });
    }
  }

  Future<void> _login() async {
    if (_emailController.text.isEmpty || _passwordController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Заполните все поля')),
      );
      return;
    }

    setState(() => _isLoading = true);

    try {
      final apiClient = ApiClient();
      final response = await apiClient.dio.post('/auth/login', data: {
        'email': _emailController.text.trim(),
        'password': _passwordController.text,
      });

      if (mounted) {
        setState(() => _isLoading = false);
        
        if (response.statusCode == 200 && response.data['accessToken'] != null) {
          // Сохраняем токен (если есть secure storage)
          // await SecureStorage.saveToken(response.data['accessToken']);
          
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Вход выполнен успешно!')),
          );
          context.go('/home');
        } else {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Неверный email или пароль')),
          );
        }
      }
    } catch (e) {
      if (mounted) {
        setState(() => _isLoading = false);
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Ошибка: $e')),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: FadeTransition(
            opacity: _fadeAnimation,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const SizedBox(height: 40),
                
                // Статус подключения
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                  decoration: BoxDecoration(
                    color: _connectionStatus.contains('✅') 
                        ? Colors.green.withOpacity(0.1) 
                        : _connectionStatus.contains('❌')
                            ? Colors.red.withOpacity(0.1)
                            : Colors.grey.withOpacity(0.1),
                    borderRadius: BorderRadius.circular(12),
                    border: Border.all(
                      color: _connectionStatus.contains('✅') 
                          ? Colors.green 
                          : _connectionStatus.contains('❌')
                              ? Colors.red
                              : Colors.grey,
                      width: 1,
                    ),
                  ),
                  child: Row(
                    children: [
                      if (_isCheckingConnection)
                        const SizedBox(
                          width: 20,
                          height: 20,
                          child: CircularProgressIndicator(strokeWidth: 2),
                        )
                      else
                        Icon(
                          _connectionStatus.contains('✅') ? Icons.check_circle : Icons.wifi_off,
                          color: _connectionStatus.contains('✅') ? Colors.green : Colors.red,
                          size: 20,
                        ),
                      const SizedBox(width: 12),
                      Expanded(
                        child: Text(
                          _connectionStatus.isEmpty ? 'Проверка подключения...' : _connectionStatus,
                          style: TextStyle(
                            fontSize: 12,
                            color: _connectionStatus.contains('✅') 
                                ? Colors.green 
                                : _connectionStatus.contains('❌')
                                    ? Colors.red
                                    : Colors.grey,
                          ),
                        ),
                      ),
                      if (_connectionStatus.contains('❌'))
                        TextButton(
                          onPressed: _checkConnection,
                          style: TextButton.styleFrom(
                            minimumSize: const Size(60, 30),
                            padding: EdgeInsets.zero,
                          ),
                          child: const Text('Повторить', style: TextStyle(fontSize: 12)),
                        ),
                    ],
                  ),
                ),
                
                const SizedBox(height: 20),
                
                // Logo
                Center(
                  child: Column(
                    children: [
                      Container(
                        width: 80,
                        height: 80,
                        decoration: BoxDecoration(
                          gradient: const LinearGradient(
                            colors: [AppTheme.primaryColor, Color(0xFFFF4D6D)],
                          ),
                          borderRadius: BorderRadius.circular(24),
                        ),
                        child: const Icon(
                          Icons.push_pin,
                          size: 40,
                          color: Colors.white,
                        ),
                      ),
                      const SizedBox(height: 24),
                      const Text(
                        'Loop App',
                        style: TextStyle(
                          fontSize: 32,
                          fontWeight: FontWeight.bold,
                          color: AppTheme.textPrimary,
                        ),
                      ),
                      const SizedBox(height: 8),
                      const Text(
                        'Войдите чтобы продолжить',
                        style: TextStyle(
                          color: AppTheme.textSecondary,
                          fontSize: 16,
                        ),
                      ),
                    ],
                  ),
                ),
                const SizedBox(height: 48),
                
                // Form
                TextField(
                  controller: _emailController,
                  keyboardType: TextInputType.emailAddress,
                  decoration: const InputDecoration(
                    labelText: 'Email',
                    prefixIcon: Icon(Icons.email_outlined),
                    hintText: 'test@test.com',
                  ),
                ),
                const SizedBox(height: 16),
                TextField(
                  controller: _passwordController,
                  obscureText: _obscurePassword,
                  decoration: InputDecoration(
                    labelText: 'Пароль',
                    prefixIcon: const Icon(Icons.lock_outline),
                    suffixIcon: IconButton(
                      icon: Icon(
                        _obscurePassword ? Icons.visibility_off : Icons.visibility,
                      ),
                      onPressed: () {
                        setState(() => _obscurePassword = !_obscurePassword);
                      },
                    ),
                    hintText: '123456',
                  ),
                ),
                const SizedBox(height: 32),
                
                // Login Button
                AnimatedContainer(
                  duration: const Duration(milliseconds: 200),
                  height: 52,
                  child: ElevatedButton(
                    onPressed: (_isLoading || _isCheckingConnection || _connectionStatus.contains('❌')) ? null : _login,
                    child: _isLoading
                        ? const SizedBox(
                            height: 24,
                            width: 24,
                            child: CircularProgressIndicator(
                              strokeWidth: 2,
                              color: Colors.white,
                            ),
                          )
                        : const Text(
                            'Войти',
                            style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600),
                          ),
                  ),
                ),
                const SizedBox(height: 16),
                
                Center(
                  child: TextButton(
                    onPressed: () {
                      context.go('/register');
                    },
                    child: const Text('Нет аккаунта? Зарегистрироваться'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}