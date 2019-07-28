import {
  HTTP,
  SetHeader
} from './../../http-common';

let user = {
  roles: [],
  username: '',
  authenticated: false
}
export default {
  login: function (context, username, password, redirect) {
    let token = btoa(username + ':' + password)
    let headers = {
      'Authorization': `Basic ${token}`
    };
    SetHeader(headers)
    HTTP().get('authenticate')
      .then(response => {
        user.username = username
        user.roles = response.data
        console.log(user.roles)
        user.authenticated = true
        window.localStorage.setItem('token-' + username, token)
        if (redirect) {
          context.$router.push({
            path: redirect
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
  },
  hasAnyOf: function (roles) {
    return !!user.roles.find(role => roles.includes(role))
  },
  logout: function () {
    window.localStorage.removeItem('token-' + user.username)
    user = {
      roles: [],
      username: '',
      authenticated: false
    }
  },
  authenticated: function () {
    return user.authenticated
  },
  getUserName: function(){
    return user.username;
  },
  getAuthHeader: function () {
    return {
      'Authorization': `Basic ${window.localStorage.getItem('token-' + user.username)}`
    }
  }
}