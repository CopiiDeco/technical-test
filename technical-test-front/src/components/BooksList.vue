<script setup>

import { ref } from 'vue';

const books = ref([]);

async function fetchBooks() {
  const response = await fetch("http://localhost:18080/library/books");
  books.value = await response.json();
}
fetchBooks();

</script>

<template>
  <div v-for="(book,isbn) in books" :key="isbn">
    <p>
      {{ book.title }}
      <br/>
      {{ book.author }}
      <br/>
      {{ book.description }}
      <br/>
      {{ book.date }}
    </p>
  </div>
</template>

<style scoped>

</style>