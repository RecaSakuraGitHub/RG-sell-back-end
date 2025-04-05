# 目標成果
建立基礎電商系統，包含**User**、**Product**等資料庫。
## 結構概述
***前端*** **WordPress**-搭建網頁界面，並使用簡單**JS**語句與中間層**n8n**溝通。<br>
***中間層*** **n8n**-讓前端能夠統一格式，利用**n8n**往後串接與解藕，簡單的與不同**API**做溝通。<br>
***後端*** 採用**Java**的**Spring Boot**搭建-運行相關業務邏輯與資料庫處理。<br>
***部屬位置*** 前後端皆架設在筆者自家的迷你電腦上。<br>
## 2025-04-05 專案進展
1. 在**WordPress**上添加文章，作為前端網頁。<br>
2. 使用n8n通一將**GET**、**POST**請求統一合併成**POST**，根據**JSON**格式中含有的**process**參數選擇相應的**API**。<br>

```
原請求建立使用者為:
{
    "name": "RecaSakura",
    "email": "recasakura@gmail.com",
    "phone": "0912345678"
}
改為:
{
    "process": "createUser",
    "name": "RecaSakura",
    "email": "recasakura@gmail.com",
    "phone": "0912345678"
}
```
### 我學到了什麼？
1. 利用n8n簡化前後端的耦合，改成利用n8n來處理不同請求對應的API。
### 可能或已發生的問題
1. 業務複雜後對於n8n壓力會增大，高併發可能會出現速度過慢或崩潰的問題。<br>
2. 對於某些可直接轉送給前端的回傳不符合n8n可接收的格式，須透額外透過n8n進行轉換，像是對exception的情況須額外做處理，增加複雜度。<br>

## 2025-04-04 專案進展
### 結構概述
```
sellbackend/
├── App.java
├── controller
│   ├── ProductController.java
│   └── UserController.java  ***重構UserController*** 調整業務邏輯至service/處理
├── exception
│   ├── GlobalExceptionHandler.java ***新功能*** 新增GlobalExceptionHandler統一處理其他例外
│   ├── UserAlreadyExistsException.java ***新功能*** 新增UserAlreadyExistsException，處理創建User已存在相同email或phone的情況
│   └── UserNotFoundException.java ***新功能*** 新增UserNotFoundException，處理Get請求時找不到User情況
├── model
│   ├── Product.java
│   └── user
│       ├── UserCreateRequest.java ***新功能UserCreateRequest*** 新增UserCreateRequest處理傳入資料，避免暴露User的具體結構
│       ├── User.java
│       └── UserResponse.java ***新功能UserResponse*** 新增UserResponse，處理回傳資料，避免洩漏User隱私訊息
├── repository
│   ├── ProductRepository.java
│   ├── projection
│   │   └── UserProjection.java ***新功能UserProjection*** 新增UserProjection讓JPA，可以直接返回不含隱私訊息的User
│   └── UserRepository.java ***新增功能於UserRepository*** 新增查找使用者的method給JPA映射
└── service
    └── UserService.java ***新功能UserService*** 新增UserService，專門處理User的業務邏輯

```
### 我學到了什麼？
1. 練習DTO pattern，轉換相關資料
 使用UserCreateRequest與UserResponse分別隱藏傳入與傳出時具體User的數據，防止隱私暴露給前端。<br>
2. 練習例外處理
 使用GlobalExceptionHandler來控制自定的例外情況。
 建立UserAlreadyExistsException針對使用者email或phone已經建立過的例外。
 建立UserNotFoundException恩對查詢查詢使用者不存在的例外。
3. 練習將業務邏輯抽離，達成單一責任
 建立service/ 存放業務邏輯。
 建立UserService處理對User的業務邏輯。
4. 練習利用JPA代替實做SQL查詢
 在UserRepository新增按照JPA命名規則的method，讓JPA實做相應的查詢與判斷。
 建立projection/ 存放projection檔案
 建立UserProjection，讓我可以從UserRepository 建立findAllBy轉換成不帶隱私數據的projection。
