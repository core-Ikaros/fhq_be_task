from django.urls import path
from . import views
from django.urls import include

app_name = 'panel'

urlpatterns = [
    path('', views.home, name='home'),
    path('user/', views.user, name='user'),
    path('newpost/', views.NewPostView.as_view(), name='newpost'),
    path('posts/', views.ManageListView.as_view(), name='posts'),
    path('posts/<slug:slug>/', views.ManageDetailView.as_view(), name='posts_detail'),
    path("accounts/login/", views.MyLoginView.as_view(), name="login"),
    path("accounts/logout/", views.logout_view, name="logout"),
    path("accounts/register/", views.RegisterView.as_view(), name="register"),
]
