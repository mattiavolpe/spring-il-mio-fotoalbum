import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './assets/css/style.scss'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

/* import the fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* import specific icons */
import { faEnvelope, faUser } from '@fortawesome/free-regular-svg-icons'
import { faPlus, faHeart, faBrain, faSpinner, faHourglass, faBan } from '@fortawesome/free-solid-svg-icons'

/* add icons to the library */
library.add(faEnvelope, faUser, faHourglass, faPlus, faHeart, faBrain, faSpinner, faBan)

const app = createApp(App)

app.use(router)

app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
