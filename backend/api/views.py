from django.shortcuts import get_object_or_404
from rest_framework.response import Response
from rest_framework import status, viewsets
from .models import User, Plr, SmoothPursuit
from .serializer import UserSerializer, PlrSerializer, SmoothPursuitSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer

# /users/<user_pk>/plrs/
class PlrViewSet(viewsets.ModelViewSet):
    serializer_class = PlrSerializer
    queryset = Plr.objects.all()

    def get_queryset(self):
        user_pk = self.kwargs.get('user_pk')
        if user_pk:
            return self.queryset.filter(patient=user_pk)
        return self.queryset

    def create(self, request, *args, **kwargs):
        user_pk = self.kwargs.get('user_pk')
        get_object_or_404(User, pk=user_pk)
        data = request.data.copy()
        data['patient'] = user_pk  # Use 'patient' instead of 'patient_id'
        serializer = self.get_serializer(data=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data, status=status.HTTP_201_CREATED)
    
# /users/<user_pk>/smoothpursuits/
class SmoothPursuitViewSet(viewsets.ModelViewSet):
    serializer_class = SmoothPursuitSerializer
    queryset = SmoothPursuit.objects.all()

    def get_queryset(self):
        user_pk = self.kwargs.get('user_pk')
        if user_pk:
            return self.queryset.filter(patient=user_pk)
        return self.queryset

    def create(self, request, *args, **kwargs):
        user_pk = self.kwargs.get('user_pk')
        get_object_or_404(User, pk=user_pk)
        data = request.data.copy()
        data['patient'] = user_pk  # Use 'patient' instead of 'patient_id'
        serializer = self.get_serializer(data=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data, status=status.HTTP_201_CREATED)
