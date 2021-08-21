import request from './request'

export function getFiles(params) {
  return request.get('/file', { params })
}

export function addFile(file, projectId) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/file', formData, {
    params: { projectId }
  })
}

export function delFile(id) {
  return request.delete('/file/' + id)
}
