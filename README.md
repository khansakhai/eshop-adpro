# eshop

Advanced Programming (Even Semester 2024/2025) Tutorial

Khansa Khairunisa - 2306152462

Tautan menuju Koyeb deployment dapat diakses [di sini](https://eshop-adpro-khansakhai.koyeb.app/). *Server Configuration: Port 8081*

### Daftar Isi

- [Module 1 Tutorial: Coding Standards](#module-1-tutorial)
- [Module 2 Tutorial: Implementing CI/CD using Gradle & GitHub Actions](#module-2-tutorial)

---

## Module 1 Tutorial

### Reflection 1

>You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code

Berikut merupakan prinsip clean code dan praktik secure coding yang saya terapkan dalam pengimplementasian fitur edit dan delete.

1. **Penamaan Variabel yang Jelas dan Bermakna**

    Variabel/fungsi/class yang digunakan memiliki nama deskriptif, seperti editedProduct, findById, productName, sehingga kode lebih mudah dipahami.

2. **Penggunaan Fungsi yang Efisien**

    Setiap fungsi dibuat untuk menangani satu tugas utama (Single Responsibility Principle). Misalnya, `edit()` dibuat untuk memperbarui/meng-edit data produk.

3. **Penulisan Comment yang Tidak Berlebihan**

    Karena variabel dan fungsi sudah diberi nama yang jelas dan mudah untuk dipahami, sehingga kode yang ditulis tidak memerlukan *comment* yang berlebihan dan kurang penting.

4. **Penggunaan UUID sebagai ID Product**

    ID produk menggunakan UUID agar memiliki *value* yang unik, sehingga dapat meningkatkan keamanan aplikasi.

5. **Melakukan Validasi Input**

    Input yang diterima harus diperiksa untuk meningkatkan keamanan, seperti memastika productId tidak null, productName tidak kosong, dan productQuantity tidak negatif.

Saat mengimplementasikan fitur edit dan delete, saya belum menerapkan validasi input dengan baik, sehingga productId bisa berupa null, productName bisa kosong, dan productQuantity bisa berupa angka negatif.

### Reflection 2

>After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

Setelah menuliskan unit test, saya merasa lebih yakin karena bisa memastikan bahwa fitur yang dibuat berjalan sesuai harapan. Bagian yang sedikit rumit adalah memastikan skenario yang diperlukan sudah di-test, baik yang sesuai dengan harapan maupun yang berpotensi menyebabkan error. Saya rasa, jumlah unit test yang ditulis tidak perlu berlebihan, asalkan bagian yang paling krusial/utama sudah diuji dengan baik. Untuk memastikan unit test sudah cukup, kita bisa menggunakan code coverage. Tetapi, walaupun code coverage menunjukkan persentase yang tinggi, misalnya 100%, bukan berarti kode kita sepenuhnya bebas dari bug/error, karena masih ada kemungkinan kesalahan logika yang tidak terdeteksi oleh test/pengujian.

>Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Jika membuat functional test suite baru dengan prosedur setup dan variabel instance yang sama, kode bisa menjadi terduplikasi, redundan, dan kurang efisien, sehingga melanggar prinsip DRY (Don't Repeat Yourself). Untuk menghindari ini, sebaiknya kita menggunakan inheritance dengan membuat kelas dasar abstrak yang menangani setup dan fungsi umum. Jika jumlah test masih sedikit, akan lebih efisien jika kita menyimpan semua test dalam satu kelas dengan metode terpisah. Tetapi, untuk test suite yang lebih kompleks, kita bisa melakukan pemisahan menggunakan inheritance agar struktur kode tetap rapi. Selain itu, kita juga bisa menggunakan utility classes atau helper methods untuk menghindari duplikasi tanpa harus menggunakan inheritance secara langsung.

## Module 2 Tutorial

### Reflection

>List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

Berikut adalah isu kualitas kode yang saya perbaiki selama latihan ini dan strategi untuk memperbaikinya.

1. **Unused Import in `ProductController.java`**

   Pada berkas `ProductController.java`, saya menggunakan menggunakan wildcard import (`import org.springframework.web.bind.annotation.*`) yang memungkinkan impor semua elemen dalam package tersebut. Hal ini tidak direkomendasikan karena dapat mengimpor banyak elemen yang tidak diperlukan. Untuk memperbaikinya, saya mengimpor kode yang diperlukan secara eksplisit, yaitu `GetMapping`, `PostMapping`, `ModelAttribute`, `PathVariable`, dan `RequestMapping`.

2. **Missing Braces in `ProductRepository.java`**

   Pada berkas `ProductRepository.java`, terdapat perbaikan pada method `edit`. Sebelumnya, saya hanya menggunakan satu baris untuk menuliskan kode `conditional`, yaitu `if` tanpa dibungkus oleh tanda kurung kurawal/*braces* `{}`. Untuk memperbaikinya, saya menambahkan tanda kurung kurawal tersebut agar kode tidak bermakna ambigu dan mencegah potensi bug.

3. **Unnecessary Modifier in `ProductService.java`**

   Pada `ProductService.java`, terdapat perubahan pada method yang terdapat dalam antarmuka (`interface`). Modifier `public` yang sebelumnya digunakan dalam metode dihapus, karena dalam Java, semua metode dalam `interface` secara default bersifat public. Oleh karena itu, penghapusan kata kunci `public` tidak mengubah fungsionalitas kode tetapi membuatnya lebih bersih dan sesuai dengan standar.

>Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Alur kerja CI/CD yang digunakan telah memenuhi konsep Continuous Integration (CI) dan Continuous Deployment (CD) karena mencakup pengujian otomatis, analisis kode, dan deployment langsung ke Koyeb. Workflow `ci.yml` menjalankan unit test setiap kali ada push atau pull request, sehingga perubahan kode bisa langsung dicek agar tidak merusak fungsionalitas yang ada. Selain itu, workflow `pmd.yml` memastikan kualitas kode tetap terjaga dengan menjalankan analisis statis menggunakan PMD, sehingga potensi kesalahan dapat dideteksi lebih awal. Dari sisi keamanan, workflow `scorecard.yml` melakukan analisis otomatis untuk memastikan standar keamanan tetap terjaga, meskipun tidak berperan langsung dalam proses deployment. Setelah semua tahap ini, kode yang telah melewati pengujian dan analisis akan secara otomatis dideploy ke Koyeb, memastikan proses CI/CD berjalan lancar tanpa perlu campur tangan manual.