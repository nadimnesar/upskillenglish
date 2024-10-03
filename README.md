# UpskillEnglish

UpskillEnglish is a web application designed to enhance English proficiency through two focused sections: **Practice Zone** and **Assessment Zone**. Whether you're looking to hone your skills or evaluate your progress, UpskillEnglish offers a comprehensive solution for advanced English learners.

Frontend Repository: https://github.com/nadimnesar/upskillenglish-frontend </br>
Swagger URL: http://localhost:8080/swagger-ui/index.html#/

## How to Run

### With Docker
#### Backend

1. Clone backend repository:
```bash
git clone https://github.com/nadimnesar/upskillenglish.git
```
2. To build the Docker image, navigate to the directory containing your Dockerfile and build docker image:
```bash
cd upskillenglish
docker build -t upskillenglish .
```
3. Then, to run the container, use (update env's with your configs):
```bash
docker run -d --network host --name upskillenglish \
  -e GEMINI_KEY=your_gemini_api_key \
  -e HUGGINGFACE_KEY=your_huggingface_key \
  -e POSTGRES_DBNAME=your_postgres_db_name \
  -e POSTGRES_USERNAME=your_postgres_db_username \
  -e POSTGRES_PASSWORD=your_postgres_db_password \
  -e BASE64_CODE=your_jwt_secret_key \
  upskillenglish
```
4. Access the application: Open postman and hit to http://localhost:8080 (Checkout swagger for API documentation).
5. To stop the container:
```bash
docker stop upskillenglish
```

### Frontend
1. Clone frontend repository:
```bash
git clone https://github.com/nadimnesar/upskillenglish-frontend.git
```
2. To build the Docker image, navigate to the directory containing your Dockerfile and build docker image:
```bash
cd upskillenglish-frontend
docker build -t upskillenglish-frontend .
```
3. Then, to run the container:
```bash
docker run -d --network host --name upskillenglish-frontend upskillenglish-frontend
```
4. Access the application: Open a web browser and navigate to http://localhost:4200
5. To stop the container:
```bash
docker stop upskillenglish-frontend
```

### Without Docker
#### Backend
1. Clone backend repository:
```bash
git clone https://github.com/nadimnesar/upskillenglish.git
```
2. Update `/src/main/resources/application.properties` file with your environment-specific configurations and install Gradle if not already installed.
3. Navigate to the backend directory and build the Spring Boot application:
```bash
cd upskillenglish
./gradlew build -x test
java -jar build/libs/upskillenglish-0.0.1-SNAPSHOT.jar
```

#### Frontend
1. Clone frontend repository:
```bash
git clone https://github.com/nadimnesar/upskillenglish-frontend.git
```
2. Install Node.js, npm, and Angular CLI if not already installed.
3. Open a new terminal, navigate to the frontend directory, and install dependencies:
```bash
cd upskillenglish-frontend
npm install
ng serve
```
4. Access the frontend at http://localhost:4200 and ensure the backend is running on http://localhost:8080.

## Features

### 1. Practice Zone
The Practice Zone is designed to help users improve their English through various interactive tools. Key features include:

- **Passage Learning Lab:** Users can access new academic-level passages, with translation options to aid comprehension.
- **Question Practicing Hub:** Generate four types of reading-related questions by inputting a passage, helping users engage with diverse question formats.
- **Improve Writing:** Submit written responses on specific topics and receive detailed corrections and feedback to refine writing skills.
- **Improve Listening:** Play audio clips to practice listening, with text and translation support to reinforce understanding.

### 2. Assessment Zone
The Assessment Zone allows users to test their English proficiency through a structured and rigorous evaluation process. This section provides instant feedback and detailed scoring:

- **Quick Practice:** Focused on testing core language skills such as grammar, sentence completion, synonyms, antonyms, word usage, vocabulary, coherence, cohesion, and idioms. Immediate correction is provided after submission.
- **Reading Test:** A two-part reading test designed to assess key reading abilities.
    - **Part 1:** Evaluates fact-checking and text cohesion.
    - **Part 2:** Focuses on paragraph matching and understanding.
- **Writing Test:** Two sections that assess written communication skills.
    - **Part 1:** Graph analysis and interpretation.
    - **Part 2:** Essay writing on a given topic.
- **Listening Test:** Users listen to an audio clip and answer various question types to assess their listening skills.

Each of the assessment features provides a score upon test completion, and a **Leaderboard** feature displays user rankings based on their performance.

## How It Works
1. Choose between the **Practice Zone** to improve your skills or the **Assessment Zone** to evaluate your progress.
2. Engage with the content, practice regularly, and track your scores on the leaderboard.
3. Benefit from instant feedback, detailed insights, and personalized corrections.

UpskillEnglish is your ultimate companion for mastering advanced English and preparing for exams like IELTS and TOEFL.

## Screenshots

### Home Page

![home](src/main/resources/static/img/upskilleng_home.png)

### Leaderboard Page

![leaderboard](src/main/resources/static/img/leaderboard.png)

### Improve Writing Page

![improveWriting](src/main/resources/static/img/improvewriting.png)

### Writing Test Page

![writingTest](src/main/resources/static/img/upskilleng_writingtest.png)

### Reading Test Page

![readingTest](src/main/resources/static/img/Upskilleng_reading.png)

### Listening Test Page

![listeningTest](src/main/resources/static/img/upskilleng_listening.jpg)