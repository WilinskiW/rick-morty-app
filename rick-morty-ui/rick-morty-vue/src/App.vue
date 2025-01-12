<template>
  <div>
    <h1>Rick and Morty App</h1>
    <Episodes />
    <Characters />
    <Locations />
  </div>
</template>

<script>
import Episodes from './components/Episodes.vue';
import Characters from './components/Characters.vue';
import Locations from './components/Locations.vue';

export default {
  components: {
    Episodes,
    Characters,
    Locations
  }
};
</script>
