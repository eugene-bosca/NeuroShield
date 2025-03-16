from django.db import models
from django.utils.timezone import now
import uuid

class User(models.Model):
    patient_id = models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True)
    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)
    team_name = models.CharField(max_length=100)
    coach_name = models.CharField(max_length=100)
    date_of_hit = models.DateField()
    time_of_hit = models.TimeField()
    created_at = models.DateTimeField(default=now)
    
    def __str__(self):
        return str(self.patient_id)

class Plr(models.Model):
    patient_id = models.ForeignKey(User, on_delete=models.CASCADE)
    max_pupil_diam = models.FloatField()
    min_pupil_diam = models.FloatField()
    percent_contstriction = models.FloatField()
    peak_constriction_velocity = models.FloatField()
    average_constriction_velocity = models.FloatField()
    peak_dilation_velocity = models.FloatField()
    average_dilation_velocity = models.FloatField()
    time_to_redilation = models.FloatField()
    tested_at = models.DateTimeField(default=now)

class SmoothPursuit(models.Model):
    patient_id = models.ForeignKey(User, on_delete=models.CASCADE)
    phase_lag = models.FloatField()
    mean_squared_error = models.FloatField()
    pearson_coefficient = models.FloatField()
    tested_at = models.DateTimeField(default=now)