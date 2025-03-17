from django.shortcuts import get_object_or_404
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status, viewsets
from .models import User, Plr, SmoothPursuit
from .serializer import UserSerializer, PlrSerializer, SmoothPursuitSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer

    # /users/<patient_id>/details/
    @action(detail=True, methods=['get'], url_path='details')
    def retrieve_with_tests(self, request, pk=None):
        """
        Get user information along with any associated Plr or SmoothPursuit records.
        Example: GET /users/<patient_id>/details/
        """
        user = get_object_or_404(User, pk=pk)
        user_data = UserSerializer(user).data

        # Retrieve OneToOne fields directly
        try:
            plr_data = PlrSerializer(user.plr).data
        except Plr.DoesNotExist:
            plr_data = None

        try:
            smooth_pursuit_data = SmoothPursuitSerializer(user.smooth_pursuit).data
        except SmoothPursuit.DoesNotExist:
            smooth_pursuit_data = None

        response_data = {
            "user": user_data,
            "plr": plr_data,
            "smooth_pursuit": smooth_pursuit_data
        }

        return Response(response_data, status=status.HTTP_200_OK)

# /users/<user_pk>/plrs/
class PlrViewSet(viewsets.ModelViewSet):
    serializer_class = PlrSerializer
    queryset = Plr.objects.all()

    def get_queryset(self):
        user_pk = self.kwargs.get('user_pk')
        if user_pk:
            return self.queryset.filter(patient_id=user_pk)
        return self.queryset

    def create(self, request, *args, **kwargs):
        user_pk = self.kwargs.get('user_pk')
        get_object_or_404(User, pk=user_pk)
        data = request.data.copy()
        data['patient_id'] = user_pk  # Use 'patient' instead of 'patient_id'
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
            return self.queryset.filter(patient_id=user_pk)
        return self.queryset

    def create(self, request, *args, **kwargs):
        user_pk = self.kwargs.get('user_pk')
        get_object_or_404(User, pk=user_pk)
        data = request.data.copy()
        data['patient_id'] = user_pk  # Use 'patient' instead of 'patient_id'
        serializer = self.get_serializer(data=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data, status=status.HTTP_201_CREATED)
