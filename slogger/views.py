from django.shortcuts import render_to_response, get_object_or_404
from slogger.models import Entry

def entries_index(request):
    return render_to_response('slogger/entry_index.html',
                              { 'entry_list': Entry.objects.all()})
