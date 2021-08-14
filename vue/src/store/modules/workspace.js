import { getProject, saveProject } from '@/api/project'
import { getMarkForm, saveMarkForm } from '@/api/mark'

const state = () => ({
  project: null,
  markForm: [],
  audios: []
})

const getters = {
  markForm: state => {
    return state.markForm
  },
  projectId: state => {
    return state.project ? state.project.id : 0
  },
  projectName: state => {
    return state.project ? state.project.name : ''
  }
}
const actions = {
  async loadProject({ commit }, id) {
    try {
      const project = await getProject(id)
      commit('setProject', project)
      const markForm = await getMarkForm(id)
      commit('setMarkForm', markForm)
    } catch (e) {
      console.error(e)
      commit('setProject', null)
      commit('setMarkForm', [])
    }
  },
  async saveMarkForm({ commit, state }, { markForm, projectId }) {
    try {
      await saveMarkForm(markForm, projectId)
      if (state.project && state.project.id === projectId) {
        commit('setMarkForm', markForm)
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
  setMarkForm(state, markForm) {
    state.markForm = markForm
  }
}
export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
