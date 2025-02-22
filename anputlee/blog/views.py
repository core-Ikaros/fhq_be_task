from django.shortcuts import render
from django.views.generic import ListView, DetailView
from blog.models import Post
from django.http import HttpResponse
from markdown import markdown

# Create your views here.
class PostListView(ListView):
    model = Post
    template_name = 'blog/index.html'

class PostDetailView(DetailView):
    model = Post
    template_name = 'blog/post.html'
    slug_field = 'slug'
    context_object_name = 'post'
    
    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        context['content_html'] = markdown(self.object.content)
        print(context['content_html'])
        return context

def archive(request):
    posts = Post.objects.all()
    return render(request, 'blog/archive.html', {'post_list': posts})

def search(request):
    return HttpResponse("Search Page")