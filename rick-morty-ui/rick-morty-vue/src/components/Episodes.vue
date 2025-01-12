<template>
  <div>
    <h2>Episodes</h2>
    <ul v-if="episodes.length">
      <li v-for="episode in episodes" :key="episode.id">
        <strong>{{ episode.name }}</strong> - {{ episode.air_date }}
      </li>
    </ul>
    <p v-else>Loading episodes...</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      episodes: []
    };
  },
  created() {
    axios.get('http://localhost:8081/api/episode/all')
        .then(response => {
          this.episodes = response.data;
        })
        .catch(error => console.error("Error fetching episodes:", error));
  }
};
</script>
