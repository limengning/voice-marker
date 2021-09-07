import request from './request'

const apiUrl = process.env.VUE_APP_API_URL

export function getProjects(params) {
  return request.get('/project/', { params })
}

export function getProject(id) {
  return request.get('/project/' + id)
}

export function saveProject(data) {
  if (data.id) {
    return request.put('/project/' + data.id, data)
  } else {
    return request.post('/project/', data)
  }
}

export function delProject(id) {
  return request.delete('/project/' + id)
}

export function getMarkFields(projectId) {
  return request.get(`/project/${projectId}/field`)
}

export function saveMarkFields(projectId, data) {
  return request.post(`/project/${projectId}/field`, data)
}


export function exportProject(projectId) {
  window.open(`${apiUrl}/project/${projectId}/export`, 'blank')
}