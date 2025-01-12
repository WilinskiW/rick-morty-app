<template>
  <div>
    <h2>Characters</h2>
    <ul v-if="characters.length">
      <li v-for="character in characters" :key="character.id">
        <strong>{{ character.name }}</strong> - {{ character.status }}
      </li>
    </ul>
    <p v-else>Loading characters...</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      characters: []
    };
  },
  created() {
    axios.get('http://localhost:8081/api/character/all')
        .then(response => {
          this.characters = response.data;
        })
        .catch(error => console.error("Error fetching characters:", error));
  }
};
</script>
