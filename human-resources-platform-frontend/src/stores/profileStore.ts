import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import jwtDecode from 'jwt-decode'
import { useLocalStorage } from '@vueuse/core'

const useProfileStore = defineStore('profile', () => {

  const profile = ref(useLocalStorage("profile", {
    jwt: '',
    role: '',
    userId: '',
    blackListed: false
  }))

  const isAuthenticated = computed(() => {
    if (profile.value.jwt === '') {
      return false;
    }
    else {
      return true;
    }
  })

  const getJwt = computed(() => {
      return profile.value.jwt;
  })
  

  function $reset() {
    profile.value.jwt = '';
    profile.value.role = '';
    profile.value.userId = '';
    localStorage.clear();
  }

  const getUserId = computed(() => profile.value.userId);

  function setUserId(value :string){
    profile.value.userId = value;
  }

  const getRole = computed(() => profile.value.role);


  function setJwt(value: string) {
    profile.value.jwt = value;
  }

  function setRole(value: string) {
    profile.value.role = value;
  }

  const isExpired = computed(() => {
    const decoded: any = jwtDecode(profile.value.jwt);
    if (Date.now() >= decoded.exp * 1000) {
      return true;
    }
    else {
      return false;
    }
  })

  const isBlackListed = computed(() => {
    if(profile.value.role === 'CANDIDATE'){
      return profile.value.blackListed;
    }
    else{
      return false;
    }
  })

  function setBlackListed(value: boolean){
    profile.value.blackListed = value;
  }

  return { getJwt, setJwt, getRole, setRole, $reset, isExpired, isAuthenticated, getUserId, setUserId, isBlackListed, setBlackListed }
})


export default useProfileStore;