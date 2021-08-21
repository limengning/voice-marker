import store from './store'


const Project = () => import('@/views/project/Index')
const Workspace = () => import('@/views/workspace/Index')

const routes = [
  { path: '/', redirect: '/project' },
  {
    name: 'project',
    path: '/project',
    component: Project
  },
  {
    name: 'workspace',
    path: '/project/:projectId/workspace',
    component: Workspace,
    beforeEnter: (to, from, next) => {
      console.log(to.params.projectId)
      store.dispatch('workplace/loadProject', to.params.projectId)
        .then(next)
    }
  }
]

export default routes
