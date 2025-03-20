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
    patient_id = models.OneToOneField(User, on_delete=models.CASCADE, related_name="plr")
    max_pupil_diam_l = models.FloatField()
    min_pupil_diam_l = models.FloatField()
    percent_contstriction_l = models.FloatField()
    peak_constriction_velocity_l = models.FloatField()
    average_constriction_velocity_l = models.FloatField()
    peak_dilation_velocity_l = models.FloatField()
    average_dilation_velocity_l = models.FloatField()
    time_to_redilation_l = models.FloatField()
    max_pupil_diam_r = models.FloatField()
    min_pupil_diam_r = models.FloatField()
    percent_contstriction_r = models.FloatField()
    peak_constriction_velocity_r = models.FloatField()
    average_constriction_velocity_r = models.FloatField()
    peak_dilation_velocity_r = models.FloatField()
    average_dilation_velocity_r = models.FloatField()
    time_to_redilation_r = models.FloatField()
    tested_at = models.DateTimeField(default=now)

class SmoothPursuit(models.Model):
    patient_id = models.OneToOneField(User, on_delete=models.CASCADE, related_name="smooth_pursuit")
    phase_lag_l_180 = models.FloatField()
    mean_squared_error_l_180 = models.FloatField()
    pearson_coefficient_l_180 = models.FloatField()
    phase_lag_r_180 = models.FloatField()
    mean_squared_error_r_180 = models.FloatField()
    pearson_coefficient_r_180 = models.FloatField()
    phase_lag_l_360 = models.FloatField()
    mean_squared_error_l_360 = models.FloatField()
    pearson_coefficient_l_360 = models.FloatField()
    phase_lag_r_360 = models.FloatField()
    mean_squared_error_r_360 = models.FloatField()
    pearson_coefficient_r_360 = models.FloatField()
    tested_at = models.DateTimeField(default=now)