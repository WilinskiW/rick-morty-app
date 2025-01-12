<template>
  <div>
    <h2>Locations</h2>
    <ul v-if="locations.length">
      <li v-for="location in locations" :key="location.id">
        <strong>{{ location.name }}</strong> - {{ location.type }}
      </li>
    </ul>
    <p v-else>Loading locations...</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      locations: []
    };
  },
  created() {
    axios.get('http://localhost:8081/api/location/all')
        .then(response => {
          this.locations = response.data;
        })
        .catch(error => console.error("Error fetching locations:", error));
  }
};
</script>
