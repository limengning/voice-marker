import request from './request'

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
