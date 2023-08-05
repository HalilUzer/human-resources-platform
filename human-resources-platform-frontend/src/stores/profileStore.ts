import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import jwtDecode from 'jwt-decode'


const useProfileStore = defineStore('profile', () => {

  const jwt = ref('');
  const role = ref('');
  const userId = ref('');
  const isAuthenticated = computed(() => {
    if (jwt.value === '') {
      return false;
    }
    else {
      return true;
    }
  })

  const getJwt = computed(() => {
      return jwt.value;
  })
  

  function $reset() {
    jwt.value = '';
    role.value = '';
  }

  const getUserId = computed(() => userId.value);

  function setUserId(value :string){
    userId.value = value;
  }

  const getRole = computed(() => role.value);


  function setJwt(value: string) {
    jwt.value = value;
  }

  function setRole(value: string) {
    role.value = value;
  }

  function checkExpiration(): boolean {
    const decoded: any = jwtDecode(jwt.value);
    if (Date.now() >= decoded.exp * 1000) {
      return true;
    }
    else {
      return false;
    }
  }

  return { getJwt, setJwt, getRole, setRole, $reset, checkExpiration, isAuthenticated, getUserId, setUserId }
})


export default useProfileStore;