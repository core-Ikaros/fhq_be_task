from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.views.generic.edit import CreateView, UpdateView
from django.views import View
from django.views.generic.list import ListView
from django.urls import reverse_lazy
from blog.models import Post
from .forms import PostForm
from django.contrib.auth.views import LoginView
from django.contrib.auth import logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.contrib.auth.forms import UserCreationForm

# Create your views here.
@login_required
def home(request):
    if not request.user.is_authenticated:
        return HttpResponseRedirect(reverse_lazy('panel:login'))
    return render(request, 'panel/home.html', {user: request.user})

@login_required
def user(request):
    posts = Post.objects.filter(author=request.user)
    return render(request, 'panel/user.html', {'posts': posts})


class NewPostView(CreateView):
    model = Post
    form_class = PostForm
    template_name = 'panel/newpost.html'
    success_url = reverse_lazy('panel:posts')
    
    def form_valid(self, form):
        form.instance.author = self.request.user
        return super().form_valid(form)

class ManageListView(ListView):
    model = Post
    template_name = 'panel/posts.html'
    context_object_name = 'post_list'
    
    def get_queryset(self):
        return Post.objects.filter(author=self.request.user)

class ManageDetailView(UpdateView):
    model = Post
    form_class = PostForm
    template_name = 'panel/post_update_form.html'
    success_url = reverse_lazy('panel:posts')

def success(request):
    return HttpResponse("Success page")

class MyLoginView(LoginView):
    template_name = 'registration/login.html'
    redirect_authenticated_user = True

@login_required
def logout_view(request):
    logout(request)
    return HttpResponseRedirect(reverse_lazy('panel:login'))

class RegisterView(View):
    def get(self, request):
        form = UserCreationForm()
        return render(request,'registration/register.html', {'form': form})
    
    def post(self, request):
        form = UserCreationForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect(reverse_lazy('panel:login'))
        return render(request,'registration/register.html', {'form': form})