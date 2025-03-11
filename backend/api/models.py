from django.db import models
from django.utils.timezone import now
import uuid

class User(models.Model):
    patient_id = models.UUIDField(default=uuid.uuid4, editable=False, unique=True)
    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)
    team_name = models.CharField(max_length=100)
    coach_name = models.CharField(max_length=100)
    date_of_hit = models.DateField()
    time_of_hit = models.TimeField()
    created_at = models.DateTimeField(default=now)

    def __str__(self):
        return self.first_name