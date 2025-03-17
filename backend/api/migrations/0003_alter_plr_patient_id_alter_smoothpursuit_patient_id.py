# Generated by Django 5.1.7 on 2025-03-17 16:02

import django.db.models.deletion
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('api', '0002_alter_plr_patient_id_alter_smoothpursuit_patient_id'),
    ]

    operations = [
        migrations.AlterField(
            model_name='plr',
            name='patient_id',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, related_name='plr', to='api.user'),
        ),
        migrations.AlterField(
            model_name='smoothpursuit',
            name='patient_id',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, related_name='smooth_pursuit', to='api.user'),
        ),
    ]
