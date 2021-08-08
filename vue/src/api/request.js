import axios from 'axios'

const request = axios.create({
  baseURL: process.env.VUE_APP_API_URL
})
request.interceptors.response.use(resp => {
  return resp.data
})
export default request
