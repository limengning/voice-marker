import { getProject, saveProject } from '@/api/project'
import { getMarkFields, saveMarkFields } from '@/api/mark'

const state = () => ({
  project: null,
  markFields: [],
  audios: []
})

const getters = {
  markFields: state => {
    return state.markFields
  },
  projectId: state => {
    return state.project ? state.project.id : 0
  },
  projectName: state => {
    return state.project ? state.project.name : ''
  },
  project: state => {
    return state.project
  }
}
const actions = {
  async loadProject({ commit }, id) {
    try {
      const project = await getProject(id)
      commit('setProject', project)
      const markFields = await getMarkFields(id)
      commit('setMarkFields', markFields)
    } catch (e) {
      console.error(e)
      commit('setProject', null)
      commit('setMarkFields', [])
    }
  },
  async saveMarkFields({ commit, state }, { markFields, projectId }) {
    try {
      await saveMarkFields(markFields, projectId)
      if (state.project && state.project.id === projectId) {
        commit('setMarkFields', markFields)
      }
    } catch (e) {
      console.error(e)
    }
  },
  async saveProject({ commit, state }, project) {
    try {
      saveProject(project)
      if (state.project && state.project.id === project.id) {
        commit('setProject', project)
      }
      return true
    } catch (e) {
      console.error(e)
      return false
    }
  }
}

const mutations = {
  setProject(state, project) {
    state.project = project
  },
  setMarkFields(state, markFields) {
    state.markFields = markFields
  }
}
export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
