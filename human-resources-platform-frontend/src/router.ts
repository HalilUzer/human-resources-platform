import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import SignInView from '@/views/SignInView.vue';
import ProfileUrlView from '@/views/ProfileUrlView.vue';
import UnauthorizedView from '@/views/UnauthorizedView.vue';
import NotFoundView from '@/views/NotFoundView.vue';
import ProfileView from '@/views/ProfileView.vue';
import PostAJobView from '@/views/PostAJobView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/sign-in',
      name: 'signin',
      component: SignInView
    },
    {
      path: '/sign-in/linkedin-profile-url',
      name: "profileUrl",
      component: ProfileUrlView
    },

    {
      path:'/unauthorized',
      name:'unauthorized',
      component: UnauthorizedView
    },

    {
      path: '/404',
      name: "404",
      component: NotFoundView
    },
    {
      path: '/candidate/:candidate_id',
      name: 'profile',
      component: ProfileView
    },
    {
      path:'/post-a-job',
      name: 'postAJob',
      component: PostAJobView

    }

  ]
})

export default router
