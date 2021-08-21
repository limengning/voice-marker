import { createStore, createLogger } from 'vuex'
import workplace from './modules/workspace'

const debug = process.env.NODE_ENV !== 'production'

const store =  createStore({
  modules: {
    workplace
  },
  strict: debug,
  plugins: debug ? [createLogger()] : []
})

export default store