# Some scrap application 

## 🚰 Overview

Test of a login and be able to scrap links from an url. We use a local DB with H2 and JSoup for the scrapping part.
For the login we make use of JWTokens with the help of password4j to be able to hash passwords.

I did not get the time to work on the frontend.

## 🛠️ Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed: You'll need Java to compile and run this project.
- Integrated Development Environment (IDE): You can use any Java-supported IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code.
- [Maven](https://maven.apache.org/install.html). To be able to run or test the application.
- Git (optional): To clone and manage your project with Git.

## ⚙️ Getting Started

To get started with this project, follow these steps:

1. Clone the repository (if you're using Git):

   ```bash
   git clone https://github.com/bifr0ns/login-and-scrapping.git

2. Open the project in your preferred IDE.

3. Build and run the project.
   ```bash
   mvn clean install
   ```

   ```bash
   mvn spring-boot:run
   ```
4. We can test our application by running the available tests.
   ```bash
   mvn test
   ```

## 🖌️ Usage
Once we run our application we can test our endpoints. The application has five endpoints,
one for doing a signup, one to login, one to scrap an url, another one to see the urls that a user has, and the last one
to see the links from an url. Each call has a verify token, so after the login we need to save the JWT to do the calls, 
and have the token as a header.

`Some notes regarding the application: 
`

- The app has pagination, but It does not have the page number on the response, so you can only change the page number by updating the page number on the endpoint.
- The app can show a page even if the scrap has not finished scrapping, it shows an _"in progress"_ message, in totalLinks on the response. Sometimes you are not fast enough to see it, 
so I added a timer on _PageService:60_, that we can put like 10 seconds and be able to easily see the _"in progress"_ message before it finish scrapping.
The way this works is by saving the Page entity first, before the scrapping part. 

The application has five endpoints

`POST /signup` to create a new user:
```bash
   curl -X POST http://localhost:8080/signup \
-H "Content-Type: application/json" \
-d '{
  "username": "aaa",
  "email": "diana2@gmail.com",
  "password": "holagoku"
}'
   ```
Response:
```json
{"msgResponse":"Success."}
   ```
`POST /login` to log into the application:
```bash
   curl -X POST http://localhost:8080/login \
-H "Content-Type: application/json" \
-d '{
  "email": "diana2@gmail.com",
  "password": "holagoku"
}'
   ```
Response, where we get a JWT that we need to use for the other calls:
```json
{
  "msgResponse": "Success.",
  "response": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjk4OTQ0NzU5LCJleHAiOjE2OTk1NDk1NTl9.PtYpl1CyS_Pc8sMr4_oFZRb9PW9txWuS67vB96fUn5jdGz3yg8s9lPZZHUo-b2Iogt1iVddbY65xyL-OVSMmGyKWXwvbT_x95VpYt6NOQTFQsgPVM3UB7FynK8nlhnaVEk7wKguYJhKtlgAnTvxLZsh4E2qxjQWTB-YF0jqAzrE0AtPqmGgI7K2t3cfYzjCawApJZiOM2U7vwaygmZWLs8sdOrJ24wollzZaKrIJ_NTJn5zopxyXCT5bpZYhxgixn-UHACIWFXkr5Pw6gwj_f_aUR_sYHa5AcIzE0PEftxAIbIvwFV5OsmAPVraN2h2pz0ZuYqFomVkmYOgemQ2Fmw"
}
   ```

`POST /pages` Where we scrap an url (or page):
```bash
   curl -X POST http://localhost:8080/pages \
-H "Content-Type: application/json" \
-H "token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjk4OTQ0NzU5LCJleHAiOjE2OTk1NDk1NTl9.PtYpl1CyS_Pc8sMr4_oFZRb9PW9txWuS67vB96fUn5jdGz3yg8s9lPZZHUo-b2Iogt1iVddbY65xyL-OVSMmGyKWXwvbT_x95VpYt6NOQTFQsgPVM3UB7FynK8nlhnaVEk7wKguYJhKtlgAnTvxLZsh4E2qxjQWTB-YF0jqAzrE0AtPqmGgI7K2t3cfYzjCawApJZiOM2U7vwaygmZWLs8sdOrJ24wollzZaKrIJ_NTJn5zopxyXCT5bpZYhxgixn-UHACIWFXkr5Pw6gwj_f_aUR_sYHa5AcIzE0PEftxAIbIvwFV5OsmAPVraN2h2pz0ZuYqFomVkmYOgemQ2Fmw" \
-d '{
  "url": "https://en.wikipedia.org/"
}'
   ```
Response:
```json
{"msgResponse":"Success."}
   ```

`GET /pages?page=0&size=5` Where we get all the urls we have scrapped:
```bash
   curl "http://localhost:8080/pages?page=0&size=5" \
-H "token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjk4OTQ0NzU5LCJleHAiOjE2OTk1NDk1NTl9.PtYpl1CyS_Pc8sMr4_oFZRb9PW9txWuS67vB96fUn5jdGz3yg8s9lPZZHUo-b2Iogt1iVddbY65xyL-OVSMmGyKWXwvbT_x95VpYt6NOQTFQsgPVM3UB7FynK8nlhnaVEk7wKguYJhKtlgAnTvxLZsh4E2qxjQWTB-YF0jqAzrE0AtPqmGgI7K2t3cfYzjCawApJZiOM2U7vwaygmZWLs8sdOrJ24wollzZaKrIJ_NTJn5zopxyXCT5bpZYhxgixn-UHACIWFXkr5Pw6gwj_f_aUR_sYHa5AcIzE0PEftxAIbIvwFV5OsmAPVraN2h2pz0ZuYqFomVkmYOgemQ2Fmw"
   ```
Response:
```json
{
  "msgResponse": "Success.",
  "response": [
    {
      "id": 5,
      "name": "Wikipedia, the free encyclopedia",
      "totalLinks": "267"
    },
     {
        "id": 6,
        "name": "Processing: https://reddit.com/",
        "totalLinks": "in progress"
     }
  ]
}
   ```

`GET /pages/{pageId}` Based on the ID from the previous response we use that ID to get the links from that url:
```bash
   curl "www.localhost:8080/pages/5?page=0&size=5" \
-H "token: eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjk4OTQ0NzU5LCJleHAiOjE2OTk1NDk1NTl9.PtYpl1CyS_Pc8sMr4_oFZRb9PW9txWuS67vB96fUn5jdGz3yg8s9lPZZHUo-b2Iogt1iVddbY65xyL-OVSMmGyKWXwvbT_x95VpYt6NOQTFQsgPVM3UB7FynK8nlhnaVEk7wKguYJhKtlgAnTvxLZsh4E2qxjQWTB-YF0jqAzrE0AtPqmGgI7K2t3cfYzjCawApJZiOM2U7vwaygmZWLs8sdOrJ24wollzZaKrIJ_NTJn5zopxyXCT5bpZYhxgixn-UHACIWFXkr5Pw6gwj_f_aUR_sYHa5AcIzE0PEftxAIbIvwFV5OsmAPVraN2h2pz0ZuYqFomVkmYOgemQ2Fmw"
   ```
Response:
```json
{
  "msgResponse": "Success.",
  "response": [
    {
      "name": "Acapulco",
      "link": "https://en.wikipedia.org/wiki/Acapulco"
    },
    {
      "name": "amphibians",
      "link": "https://en.wikipedia.org/wiki/Amphibian"
    },
    {
      "name": "Cite this page",
      "link": "https://en.wikipedia.org/w/index.php?title=Special:CiteThisPage&page=Main_Page&id=1114291180&wpFormIdentifier=titleform"
    },
    {
      "name": "CBS",
      "link": "https://en.wikipedia.org/wiki/CBS"
    },
    {
      "name": "Srpskohrvatski / српскохрватски",
      "link": "https://sh.wikipedia.org/wiki/"
    }
  ]
}
   ```

## 🔋 Tools

- The application uses [H2](https://www.h2database.com/html/main.html) for a local DB, it has the schema and data on the resources package. Everytime we reload the application
the data will reset.
- It uses [JSoup](https://jsoup.org/) for the scrapping part, there's a pretty good [example](https://jsoup.org/cookbook/extracting-data/example-list-links) for scrapping urls in the docs as well.
- For the authentication it uses [password4j](https://github.com/Password4j/password4j) to hash passwords, 
and uses [JWT](https://jwt.io/) to use tokens for the sessions and user ids.
- When working with the data of the DB it makes use of Data JPA, so it works with JPA repositories.
