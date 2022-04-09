import {createRouter, createWebHistory} from 'vue-router'
import Home from "@/views/Home";
import Profile from "@/views/user/Profile";
import Signin from "@/views/Signin";
import Account from "@/views/user/Account";
import NotFound from "@/views/error/NotFound";

const routes = [
    {
        path: '/signin.html',
        name: 'Signin',
        component: Signin,
    },
    {
        path: '/',
        name: 'Home',
        redirect: '/profile.html',
        component: Home,
        children: [
            {
                path: '/profile.html',
                name: 'Profile',
                component: Profile,
            },
            {
                path: '/account.html',
                name: 'Account',
                component: Account,
            },
        ]
    },
    {
        path: '/:catchAll(.*)',
        name: '404',
        component: NotFound
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
