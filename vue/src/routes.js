import Workspace from '@/views/workspace/Index'

const routes = [
  { path: '/', redirect: '/workspace' },
  {
    path: '/project/:id/workspace',
    component: Workspace,
    props: route => ({ projectId: parseInt(route.params.id) })
  }
]

export default routes
