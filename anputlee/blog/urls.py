from django.urls import path
from . import views

app_name = 'blog'

urlpatterns = [
    path('', views.PostListView.as_view(), name='index'),
    path('post/<slug:slug>/', views.PostDetailView.as_view(), name='detail'),
    path('archive/', views.archive, name='archive'),
    path('search/', views.search, name='search'),
]