import request from './request'

export function getMarks(fileId) {
  return request.get('/mark/', { params: { fileId } })
}

export function saveMarks(data, fileId) {
  return request.post('/mark/', data, {
    params: { fileId }
  })
}
