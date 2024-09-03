<script setup>

import BooksList from "@/components/BooksList.vue";
</script>

<template>
<h1>Welcome to your favorite library</h1>
  <!-- the add book form here -->
  <form v-model="form">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title">
    <label for="author">Author:</label>
    <input type="text" id="authorId" name="authorId">
    <label for="isbn">ISBN:</label>
    <input type="text" id="isbn" name="isbn">
    <label for="date">Date:</label>
    <input type="text" id="releaseDate" name="releaseDate">
    <button @click="submitForm">Add book</button>
  </form>
  <!-- the list of books here -->
  <BooksList />
</template>

<script>
export default {
  data() {
    return {
      form: {
        title: "",
        author: "",
        isbn: "",
        date: "",
      },
      books: [],
    };
  },
  methods: {
    async submitForm(form) {
      await fetch("http://localhost:18080/library/books", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
        },
        body: JSON.stringify(form),
      });
      const response = await fetch("http://localhost:18080/library/books",
          //handle cors
          {
            mode: 'cors',
            headers: {
              'Access-Control-Allow-Origin': '*',
              'Content-Type': 'application/json',
            }});
      this.books = await response.json();
    },
  },
}
</script>

<style scoped>

</style>