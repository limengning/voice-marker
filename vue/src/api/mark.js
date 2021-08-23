import request from './request'

export function getMarks(fileId) {
  return request.get('/mark/', { params: { fileId } })
}

export function saveMarks(data, fileId) {
  return request.post('/mark/', data, {
    params: { fileId }
  })
}

export function getMarkFields(projectId) {
  return request.get('/markForm', { params: { projectId } })
}

export function saveMarkFields(data, projectId) {
  return request.post('/markForm', data, { params: { projectId } })
}
