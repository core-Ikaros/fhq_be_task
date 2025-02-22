from django.db import models

# Create your models here.

class Site(models.Model):
    site_title = models.CharField(max_length=200)
    site_description = models.TextField()