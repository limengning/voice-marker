import store from './store'


const Workspace = () => import('@/views/workspace/Index')
const Home = () => import('@/views/home/Index')

const routes = [
  { path: '/', redirect: '/home' },
  {
    name: 'home',
    path: '/home',
    component: Home
  },
  {
    name: 'workspace',
    path: '/workspace/:projectId',
    component: Workspace,
    props: true,
    beforeEnter: (to, from, next) => {
      if (/^[0-9]+$/.test(to.params.projectId)) {
        store.dispatch('workplace/loadProject', to.params.projectId)
          .then(next)
      } else {
        next({ name: 'home' })
      }
    }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/home'
  }
]

export default routes
