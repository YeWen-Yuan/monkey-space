import {createRouter, createWebHistory} from 'vue-router'

import Welcome from "@/views/Welcome.vue";
import WorkSpace from '@/views/WorkSpace.vue'

const routes = [
    {name: 'welcome', path: '/', component: Welcome},
    {name: 'workSpace', path: '/work-space', component: WorkSpace},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
