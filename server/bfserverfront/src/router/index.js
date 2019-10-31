import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Index  from '@/components/Index'
import user from '@/page/user'
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
        path: '/user',
        name: 'user',
        component: user,
      }]
    }
   
  ]
})
