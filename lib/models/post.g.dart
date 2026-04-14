// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'post.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Post _$PostFromJson(Map<String, dynamic> json) => Post(
  id: (json['id'] as num).toInt(),
  productIds: (json['productIds'] as List<dynamic>?)
      ?.map((e) => (e as num).toInt())
      .toList(),
  description: json['description'] as String?,
  imageURL: (json['imageURL'] as List<dynamic>?)
      ?.map((e) => e as String)
      .toList(),
);

Map<String, dynamic> _$PostToJson(Post instance) => <String, dynamic>{
  'id': instance.id,
  'productIds': instance.productIds,
  'description': instance.description,
  'imageURL': instance.imageURL,
};
