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
