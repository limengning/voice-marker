import request from './request'

export function getProjects(params) {
  return request.get('/project/', { params })
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

export function getFiles(params) {
  return request.get('/file/', { params })
}

export function addFile(file, projectId) {
  const formData = new FormData()
  formData.append('file', file)
  return request.post('/file/', formData, {
    params: { projectId }
  })
}

export function delFile(id) {
  return request.delete('/file/' + id)
}

export function getMarks(fileId) {
  return request.get('/mark/', { params: { fileId } })
}

export function saveMarks(data, fileId) {
  return request.post('/mark/', data, {
    params: { fileId }
  })
}
