import Vue from 'vue'
import Router from 'vue-router'
import Index  from '@/components/Index'
import Axios from 'axios'
import ArticleList from  '@/components/ArticleList'
import AddArticle from '@/components/AddArticle'
import EditArticle from '@/components/EditArticle'
import AddAudio from '@/components/AddAudio'
import AddVedio from '@/components/AddVedio'
import VedioList from '@/components/VedioList'
import AudioList from '@/components/AudioList'
Vue.prototype.$axios = Axios
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index,
      children:[{
        path: '/articlelist',
        name: 'articlelist',
        component: ArticleList,
      },
      {
        path: '/addarticle',
        name: 'addarticle',
        component: AddArticle,
      },
      {
        path: '/editarticle',
        name: 'editarticle',
        component: EditArticle,
      },
      {
        path: '/addaudio',
        name: 'addaudio',
        component: AddAudio,
      },
      {
        path: '/addvedio',
        name: 'addvedio',
        component: AddVedio,
      },
      {
        path: '/vediolist',
        name: 'vediolist',
        component: VedioList,
      },
      {
        path: '/audiolist',
        name: 'audiolist',
        component: AudioList,
      },
    ]
    }
   
  ]
})
