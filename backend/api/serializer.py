from rest_framework import serializers
from .models import User, Plr, SmoothPursuit

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

class PlrSerializer(serializers.ModelSerializer):
    class Meta:
        model = Plr
        fields = '__all__'

class SmoothPursuitSerializer(serializers.ModelSerializer):
    class Meta:
        model = SmoothPursuit
        fields = '__all__'