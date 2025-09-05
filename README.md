# CSV and MongoDB Batch Processing

A Spring Boot project demonstrating **batch processing of ExamResult data** with conversions between **CSV files and MongoDB** using **Spring Batch** and **MongoTemplate**.

---

## Repository Name Suggestion

`csv-mongodb-batch`

> Handles both CSV → MongoDB and MongoDB → CSV conversions

---

## Key Highlights

* **CSV → MongoDB:** Reads `topbrains.csv` and writes to `sembrains` collection using `MongoItemWriter`
* **MongoDB → CSV:** Reads from `superbrains` collection (with filter criteria, e.g., percentage >= 90) and writes to `superbrains.csv`
* Uses **FlatFileItemReader/Writer** for CSV operations
* Uses **MongoItemWriter/MongoCursorItemReader** for MongoDB operations
* Chunk-based processing for efficient batch handling
* Job execution monitored via `JobListener`
* Programmatic data processing via `ExamProcessor`
* Configurable batch size and sorting for MongoDB reads
* CSV input/output paths configurable via `ClassPathResource` and `FileSystemResource`
* Verified using **MongoDB Compass** and CSV files

---

## Features

* Import top-performing exam results from CSV into MongoDB (`sembrains` collection)
* Export filtered exam results from MongoDB (`superbrains` collection) to CSV file
* Supports automatic batch execution via Spring Batch jobs (`ExamResultjob` and `Mongojob`)
* Efficient processing using chunk-oriented steps
* Easily extendable for additional CSV ↔ MongoDB operations

---

## CSV File Structure

* Input CSV (`topbrains.csv`) for CSV → MongoDB:

```
id,dob,percentage,semester,gender,email,name
```

* Output CSV (`superbrains.csv`) for MongoDB → CSV:

```
id,dob,percentage,semester,gender,email,name
```

---

## How to Run

1. **Clone the repository**

```bash
git clone <your-repository-url>
cd csv-mongodb-batch
```

2. **Configure MongoDB**

* Update `application.properties` with MongoDB connection details:

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=examDB
```

3. **Run the project**

* Using Maven:

```bash
mvn spring-boot:run
```

* Or from your IDE: run the main class

> Both batch jobs (CSV → MongoDB and MongoDB → CSV) will execute automatically or can be triggered programmatically.

---

## Author

Vinod Lucky
