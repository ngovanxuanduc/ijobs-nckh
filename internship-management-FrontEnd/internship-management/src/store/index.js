import { createStore, applyMiddleware } from 'redux'

import createSagaMiddleware from 'redux-saga'
import loggerMiddleware from 'redux-logger'

import combinedReducer from 'src/store/reducers'
import rootSaga from 'src/store/sagas'

const sagaMiddleware = createSagaMiddleware()
sagaMiddleware.run(rootSaga)

export default createStore(
  combinedReducer,
  applyMiddleware(
    sagaMiddleware,
    loggerMiddleware,
  ),
)