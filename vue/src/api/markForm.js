import request from './request'

export function getForms() {
  return request.get('/markForm')
}

export function getFields(formId) {
  return request.get(`/markForm/${formId}/field`)
}

export function saveFields(formId, data) {
  return request.post(`/markForm/${formId}/field`, data)
}
