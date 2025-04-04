## 2025-04-04 專案進展
### 結構概述
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
