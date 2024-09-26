import {createMemoryHistory, createRouter, createWebHashHistory, createWebHistory} from 'vue-router'

import Welcome from "@/views/Welcome.vue";
import OptionSpace from '@/views/OptionSpace.vue'

const routes = [
    {name: 'welcome', path: '/', component: Welcome},
    {name: 'workSpace', path: '/work-space', component: OptionSpace},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
