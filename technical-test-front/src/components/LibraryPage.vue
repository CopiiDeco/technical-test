<script setup>

import BooksList from "@/components/BooksList.vue";
</script>

<template>
<h1>Welcome to your favorite library</h1>
  <!-- the add book form here -->
  <form>
    <label for="title">Title:</label>
    <input type="text" id="title" name="title">
    <label for="author">Author:</label>
    <input type="text" id="author" name="author">
    <label for="isbn">ISBN:</label>
    <input type="text" id="isbn" name="isbn">
    <label for="date">Date:</label>
    <input type="text" id="date" name="date">
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
    async submitForm() {
      await fetch("http://localhost:18080/library/books", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.form),
      });
      const response = await fetch("http://localhost:18080/library/books");
      this.books = await response.json();
    },
  },
}
</script>

<style scoped>

</style>