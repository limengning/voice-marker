import store from './store'


const Project = () => import('@/views/project/Index')
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
    name: 'project',
    path: '/project',
    component: Project
  },
  {
    name: 'workspace',
    path: '/workspace',
    component: Workspace,
    beforeEnter: (to, from, next) => {
      store.dispatch('workplace/loadProject', to.query.projectId)
        .then(next)
    }
  }
]

export default routes
