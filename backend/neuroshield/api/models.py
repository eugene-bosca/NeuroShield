from django.db import models

class User(models.Model):
    patient_id = models.UUIDField(max_length=100)
    first_name = models.CharField(max_length=100)
    last_name = models.CharField(max_length=100)
    team_name = models.CharField(max_length=100)
    coach_name = models.CharField(max_length=100)
    date_of_hit = models.DateTimeField()
    time_of_hit = models.TimeField()

    def __str__(self):
        return self.first_name