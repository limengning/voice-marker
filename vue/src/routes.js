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
    path: '/project/:id/workspace',
    component: Workspace,
    props: route => ({ projectId: parseInt(route.params.id) })
  }
]

export default routes
