from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework import status
from rest_framework import viewsets
from .models import User, Plr, SmoothPursuit
from .serializer import UserSerializer, PlrSerializer, SmoothPursuitSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer

    # /users/<pk>/plrs/
    @action(detail=True, methods=['get', 'post'], url_path='plrs')
    def plrs(self, request, pk=None):
        """
        GET: Return all PLR records for the specified patient.
        POST: Create a new PLR record for the specified patient.
        """
        user = self.get_object()  # Retrieve the patient using the pk from the URL

        if request.method == 'POST':
            # Copy incoming data and inject the patient ID
            data = request.data.copy()
            data['patient_id'] = user.pk
            serializer = PlrSerializer(data=data)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

        elif request.method == 'GET':
            # Fetch and return all PLR records for this patient
            plr_records = Plr.objects.filter(patient_id=user)
            serializer = PlrSerializer(plr_records, many=True)
            return Response(serializer.data)

    # /users/<pk>/smooth_pursuits/
    @action(detail=True, methods=['get', 'post'], url_path='smoothpursuits')
    def smoothpursuits(self, request, pk=None):
        """
        GET: Return all SmoothPursuit records for the specified patient.
        POST: Create a new SmoothPursuit record for the specified patient.
        """
        user = self.get_object()  # Retrieve the patient based on the pk

        if request.method == 'POST':
            data = request.data.copy()
            data['patient_id'] = user.pk  # Attach the patient to the data
            serializer = SmoothPursuitSerializer(data=data)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

        elif request.method == 'GET':
            # Fetch all SmoothPursuit records for this patient
            records = SmoothPursuit.objects.filter(patient_id=user)
            serializer = SmoothPursuitSerializer(records, many=True)
            return Response(serializer.data)
