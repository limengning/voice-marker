import Workspace from '@/views/workspace/Index'

const routes = [
  { path: '/', redirect: '/workspace' },
  { path: '/workspace', component: Workspace }
]

export default routes
