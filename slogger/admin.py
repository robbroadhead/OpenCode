from django.contrib import admin
from slogger.models import Category, Entry, Link

class CategoryAdmin(admin.ModelAdmin):
    prepopulated_fields = { 'slug': ['title']}

class EntryAdmin(admin.ModelAdmin):
    prepopulated_fields = { 'slug': ['title']}

class LinkAdmin(admin.ModelAdmin):
    prepopulated_fields = { 'slug': ['title']}

# Register the admin pages
admin.site.register(Category, CategoryAdmin)
admin.site.register(Entry, EntryAdmin)
admin.site.register(Link, LinkAdmin)