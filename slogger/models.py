import datetime

# Standard imports
from django.db import models
from django.contrib.auth.models import User
from django.conf import settings

# Third party imports
from markdown import markdown
from tagging.fields import TagField


# Category class definition
class Category(models.Model):
    title = models.CharField(max_length=250, help_text='Maximum 250 characters.')
    slug = models.SlugField(unique=True, help_text='Suggested value is automatically generated from title and must be unique.')
    description = models.TextField()
    
    class Meta:
        ordering = ['title']
        verbose_name_plural = "Categories"
        
    def __unicode__(self):
        return self.title
    
    def get_absolute_url(self):
        return "/categories/%s/" % self.slug
# End of Category class definition
    
# Entry class definition
class Entry(models.Model):
    LIVE_STAT = 1
    DRAFT_STAT = 2
    HIDDEN_STAT = 3
    STATUS_CHOICES = (
                      (LIVE_STAT, 'Live'),
                      (DRAFT_STAT, 'Draft'),
                      (HIDDEN_STAT, 'Hidden'),
                      )
    status = models.IntegerField(choices = STATUS_CHOICES, default=1)
    
    # Main fields - article/post
    pub_date = models.DateTimeField(default = datetime.datetime.now)
    title = models.CharField(max_length=250)
    body = models.TextField()
    excerpt = models.TextField(blank=True)
    
    # Categorization
    categories = models.ManyToManyField(Category)
    tags = TagField()
    featured = models.BooleanField(default=False)
    enable_comments = models.BooleanField(default=True)
    author = models.ForeignKey(User)    
    slug = models.SlugField(unique_for_date='pub_date', help_text='Suggested value is automatically generated from title and must be unique.')

    # Performance/Storage fields
    excerpt_html = models.TextField(editable=False, blank=True)
    body_html = models.TextField(editable=False, blank=True)

    def save(self, force_insert=False, force_update=False):
        self.body_html = markdown(self.body)
        if self.excerpt:
            self.excerpt_html = markdown(self.excerpt)
        super(Entry, self).save(force_insert,force_update)
        
    class Meta:
        ordering = ['-pub_date']
        verbose_name_plural = "Entries"

    def __unicode__(self):
        return self.title
    
    def get_absolute_url(self):
        return ('slogger_entry_detail',(), { 'year': self.pub_date.strftime("%Y"),
                                           'month': self.pub_date.strftime("%b").lower(),
                                           'day': self.pub_date.strftime("%d"),
                                           'slug': self.slug })
    get_absolute_url = models.permalink(get_absolute_url)

# End of Entry class definition
    
# Link class definition
class Link(models.Model):
    title = models.CharField(max_length=250)
    description = models.TextField(blank=True)
    description_html = models.TextField(blank=True)
    url = models.URLField(unique=True)
    posted_by = models.ForeignKey(User)
    pub_date = models.DateTimeField(default = datetime.datetime.now)
    slug = models.SlugField(unique_for_date='pub_date', help_text='Suggested value is automatically generated from title and must be unique.')
    tags = TagField()
    post_elsewhere = models.BooleanField('Post to Delicious',default=True)
    enable_comments = models.BooleanField(default=True)
    via_name = models.CharField('Via',max_length=250,blank=True,help_text='The name of the site you spotted the link on. Optional')
    via_url = models.URLField('Via URL',blank=True,help_text='The address of the site you spotted the link on. Optional')

    class Meta:
        ordering = ['-pub_date']

    def __unicode__(self):
        return self.title    
    
    def save(self):
        if self.description:
            self.description_html = markdown(self.description)
        if not self.id and self.post_elsewhere:
            import pydelicious
            from django.utils.encoding import smart_str
            pydelicious.add(settings.DELICIOUS_USER, settings.DELICIOUS_PWD,
                            smart_str(self.url), smart_str(self.title),
                            smart_str(self.tags))
        super(Link, self).save()
        
    def get_absolute_url(self):
        return ('slogger_link_detail',(), { 'year': self.pub_date.strftime("%Y"),
                                           'month': self.pub_date.strftime("%b").lower(),
                                           'day': self.pub_date.strftime("%d"),
                                           'slug': self.slug })
    get_absolute_url = models.permalink(get_absolute_url)
    
# End of Link class definition