import Vue from 'vue'
import Router from 'vue-router'
import Index  from '@/components/Index'
import ArticleList from  '@/components/ArticleList'
import AddArticle from '@/components/AddArticle'
Vue.use(Router)

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // },
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
      }
    ]
    }
   
  ]
})
