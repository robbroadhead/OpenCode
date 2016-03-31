from django.db import models

# Keyword Search model
from django.contrib.flatpages.models import FlatPage

class SearchKeyword(models.Model):
    keyword = models.CharField(max_length=50)
    page = models.ForeignKey(FlatPage)

    def __unicode__(self):
        return self.keyword