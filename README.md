# blog-bloggante
Progetto del team 3 per il corso di Develhope 


# Spring Boot , MySQL, Spring Security, JWT, JPA, Rest API

An example of back-end of blog website

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/MattiaForcella/blog-bloggante
```

**2. Create Mysql database**
```bash
create a new local db
```

**3. Update configurations on yml**

+ open `src/main/resources/application.yml`
+ change these datas with your informations:
  + `spring.datasource.username`;
  + `spring.datasource.password`;
  + `spring.datasource.url`;
  + `spring.mail.*`;
  + `app.jwt.*`;

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

### AuthController

| Method | Url                  | Decription | Sample Valid Request Body | 
| ------ |----------------------|------------|---------------------------|
| POST   | /api/auth/signup     | Sign up    | [JSON](#signup)           |
| POST   | /api/auth/signin     | Log in     | [JSON](#signin)           |
| POST   | /api/auth/activation | Activation | [JSON](#activation)       |

### UserController

| Method | Url | Description                                              | Sample Valid Request Body |
| ------ | --- |----------------------------------------------------------| ------------------------- |
| GET    | /api/users/me | Get logged in user profile                               | |
| GET    | /api/users/{username}/profile | Get user profile by username                             | |
| GET    | /api/users/{username}/posts | Get posts created by user                                | |
| PUT    | /api/users/{username} | Update user "username" (user logged must to be admin) | [JSON](#userupdate) |

### ArticleController

| Method | Url                                               | Description                                                | Sample Valid Request Body      |
|--------|---------------------------------------------------|------------------------------------------------------------|--------------------------------|
| GET    | /api/article                                      | Get all articles                                           |                                |
| GET    | /api/articles/search/{keywords}                   | Get articles by title                                      |                                |
| GET    | /api/posts/{id}                                   | Get articles by articleId                                  |                                |
| GET    | /api/home                                         | Get articles flagged true(if is a news)                    |                                |
| GET    | /api/user/{userId}/articles                       | Get articles by userId                                     |                                |
| GET    | /api/category/{categoryId}/articles               | Get articles by id categoryId                              |                                |
| PUT    | /api/update-content/{userId}/{articleId}/content  | Update article content                                     | [JSON](#updatearticlecontent)  |
| PUT    | /api/update-category/{userId}/{articleId}         | Update article category                                    | [JSON](#updatearticlecategory) |
| PUT    | /api/articles/{userId}/{categoryId}/{articleId}   | Update article                                             | [JSON](#updatearticle)         |
| DELETE | /api/articles/{userId}/{articleId}                | Delete article by articleId                                |                                |
| POST   | /api/user/{userId}/category/{categoryId}/articles | Create new article (By logged in user and active category) | [JSON](#articlecreate)         |

### CategoryController

| Method | Url                                            | Description                   | Sample Valid Request Body |
|--------|------------------------------------------------|-------------------------------|---------------------------|
| GET    | /api/categories/{category}                     | Get category by categoryId    |                           |
| GET    | /api/categories                                | Get all categories            |                           |
| PUT    | /update/user/{userId}/{categoryId}             | Update category by categoryId | [JSON](#updatecategory)   |
| DELETE | /api/categories/delete/{categoryId}            | Delete category by categoryId |                           |
| POST   | /api/categories/user/{userId}/create-category  | Create new category           | [JSON](#categorycreate)   |

### CommentController

| Method | Url | Description                                        | Sample Valid Request Body |
|--------| --- |----------------------------------------------------|---------------------------|
| GET    | /api/articles/{articleId}/comment-list | Get comments by articleId                          |                           |
| POST   | /api/articles/{articleId}/comments | Create new comment for article with id = articleId | [JSON](#commentcreate)    |
| DELETE | /api/articles/{articleId}/delete-comment/{commentId} | Delete comment of an article by commentId          |   |

### TagController

| Method | Url        | Description         | Sample Valid Request Body |
|--------|------------|---------------------|----------------------|
| GET    | /api/tags/alltag  | Get tags            |                      |
| POST   | /api/tags/{userId}/create-tag  | Create new tag      | [JSON](#tagcreate)   |
| DELETE | /api/tags{userId}/delete/{tagId}  | Delete tag by tagId |                      |



Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

##### <a id="signup">Sign Up -> /api/auth/signup</a>
```json
{
  "username":"test",
  "email":"test@test.com",
  "password":"test1234"
}
```

##### <a id="signin">Log In -> /api/auth/signin</a>
```json
{
  "username":"test",
  "password":"test1234"
}
```
##### <a id="activation">Activation -> /api/auth/activation</a>
```json
{
  "activationCode":"f0390af5-7fd8-4b58-adba-35b339e49528"
}
```

##### <a id="userupdate">Update User -> /api/users/{username}</a>
```json
{
  "ban":"true",
  "role":
  {
    "name":"ROLE_USER"
  }
}
```

##### <a id="articlecreate">Create Article -> /api/user/{userId}/category/{categoryId}/articles </a>
```json
{
  "title":"title",
  "content":"content",
  "tag":
          
  [
    {
      "name":"tag1"
    },
    {
      "name":"tag2"
    }
  ],

  "isNews": true
}
```

##### <a id="updatearticlecontent">Update ArticleContent -> /api/update-content/{userId}/{articleId}/content</a>
```json
{
  "content":"contenuto modificato"
}
```
##### <a id="updatearticlecategory">Update ArticleCategory -> /api/update-category/{userId}/{articleId}</a>
```json
{
  "id":2
}
```

##### <a id="updatearticle">Update Article -> /api/articles/{userId}/{categoryId}/{articleId}</a>
```json
{
  "title":"title updated",
  "content":"content updated",
  "tag":
  [
    {
      "name":"tag updated"
    },
    {
      "name":"tag"
    }
  ],

  "isNews": false
}
```
##### <a id="updatecategory">Update Category -> /update/user/{userId}/{categoryId}</a>
```json
{
    "title":"category to update",
    "description":"updated category"
}
```
##### <a id="createcategory">Create Category -> /api/categories/user/{userId}/create-category</a>
```json
{
    "title":"new category",
    "description":"new category description"
}

```

##### <a id="commentcreate">Create Comment -> /api/articles/{articleId}/comments</a>
```json
{
  "content":"first comment"
}
```

##### <a id="tagcreate">Create Tag -> /api/tags/{userId}/create-tag</a>
```json
{
  
}
```
