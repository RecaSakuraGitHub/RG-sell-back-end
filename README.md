# 目標成果
建立基礎電商系統，包含**User**、**Product**等資料庫。
## 結構概述
***前端*** **WordPress**-搭建網頁界面，並使用簡單**JS**語句與中間層**n8n**溝通。<br>
***中間層*** **n8n**-讓前端能夠統一格式，利用**n8n**往後串接與解藕，簡單的與不同**API**做溝通。<br>
***後端*** 採用**Java**的**Spring Boot**搭建-運行相關業務邏輯與資料庫處理。<br>
***部屬位置*** 前後端皆架設在筆者自家的迷你電腦上。<br>

## 2025-04-13 專案進展

#### 新增購物車功能邏輯
1. 使用者可以將商品加入購物車。
2. 如果購物車為空時會簡單傳入購物車為空。
3. 若商品不存在時嘗試加入購物車，會拋出處理流程。
#### 使用者系統強化
1. register 註冊成功時會回傳 session。
2. UserResponse 中加入 role 欄位，供前端顯示權限使用。
#### 錯誤與權限處理
1. 新增 UnLoginException，處理使用者未登入的情況。
2. 新增 UserPermissionDenyException，處理非管理員操作受限。
3. 為以上例外新增 handleUnLoginException 與 handlePermissionDenyException handler。
#### Exception 結構優化
1. 調整語意UnauthorizedException 更名為 UserPermissionDenyException。
2. 調整例外訊息開頭大寫。
#### Model 優化
1. CartItemResponse 中欄位 productId 改為 name 與 quantity(商品名稱、商品數量)。
2. 將 price: BigDecimal 改為使用 quantity: Integer。
#### Service 結果調整
1. createUser() 的回傳型別從 UserResponse 改為 User。
#### 修正加入購物車數量錯誤
1. 修復原本無論傳入多少 quantity 都只加入一個商品的問題。
### 遇到的問題
#### 問題一
##### 錯誤狀況
* 實際部屬在前端網頁時，向 **n8n** 發送請求時遭遇錯誤。
* **cookie** 無法直接傳給 **n8n**。
* 將 **Spring Boot** 部屬到與 **n8n** 相同伺服器上時，**n8n** 無法與 **Spring Boot** 通信。
##### 鎖定問題
* 是不是前端處理邏輯錯誤 -> 不是。
* 是不是 **n8n** 處理邏輯失誤 -> 不是。
* 查找網頁 console 定位錯誤 -> 成功定位錯誤為 **CORS**。
##### 排查錯誤歸屬
* **Spring Boot** 已經設定 @CrossOrigin(origin = "*") -> 不是 **Java** 後端的問題。
* **n8n** 沒有設定 **CORS** -> 應該是錯誤來源。
##### 怎麼修復
* 使用 **n8n** 底下自帶的 **CORS** 方法處理方法 -> 失敗。
* 詢問 **ChatGPT** 查找處理方法 (在 **docker** 中加入 **CORS** 參數) -> 失敗。
* 上 **n8n** 社群查找錯誤 -> 得出使用反向代理 **nginx** 可解決跨域請求問題。
##### 修復步驟
* 解決來源:[Allowed Origins (CORS) error](https://community.n8n.io/t/allowed-origins-cors-error/80385)
* 但直接套用還是不行。
```
location /webhook/ {
    proxy_pass http://localhost:5678;
    add_header 'Access-Control-Allow-Origin' 'https://mywebsite.co.uk' always;
    add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS' always;
    add_header 'Access-Control-Allow-Headers' 'Content-Type' always;
}
```
* 使用**ChatGPT**輔助成功解決 **CORS** 版本:
```
server {
    server_name my.n8n.website.com;
    listen 80;
    location / {
        proxy_pass https://localhost:5678;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;

        # 避免重複的 CORS header（最關鍵）
        proxy_hide_header Access-Control-Allow-Origin;
        add_header Access-Control-Allow-Origin "https://my.website.com" always;
        add_header Access-Control-Allow-Methods "GET, POST, PUT, DELETE, OPTIONS" always;
        add_header Access-Control-Allow-Headers "Content-Type" always;
        add_header Access-Control-Allow-Credentials "true" always;
    }
}
```

#### 問題二
##### 錯誤狀況
* 前端使用**document.cookie** 來設定 **cookie**，但無法將 **cookie** 發送給 **n8n**。
##### 鎖定問題
* 應該是前端設定問題 -> 定位錯誤為前端設定 **cookie** 問題。
##### 排查錯誤歸屬
* 因為使用 **JSON** 將 **JSESSIONID** 回傳，因此無法直接設定網頁 **cookie**。
##### 怎麼修復
* 查詢 **ChatGPT** 給出 **n8n** 需在 **header** 添加 **Set-Cookie** 回傳 **session** 就會自動設定。
##### 修復步驟


#### 問題三
* 將打包後的 **Jar** 檔案部屬在 **Server** 上，**n8n** 在接收情求後與 **Spring Boot** 後端溝通時會卡住。
##### 鎖定問題
* 是否是 **port** 衝突 -> 不是。
* 是否是 **n8n** 掛掉 -> 不是。
* 是否是 **n8n** 使用 **docker** 部屬造成的 -> 成功定位問題來自於 **n8n** 使用 **docker** 部屬。
##### 排查錯誤歸屬
* **docker** 與宿主機並不在同個網段下。
* 查詢 **Linux** 防火牆，最開始設定為開放**192.168.123.0/24**連線，但 **docker** 不在此網段 -> 確認為防火牆問題。
##### 怎麼修復
* 將防火牆開啟 **Spring Boot** 所需的 **port**。
##### 修復步驟


## 2025-04-10 專案進展

#### 使用者登入與權限管理
1. 新增登入功能，透過 Session 儲存使用者資訊。
2. 在 Controller 中加入 Session 驗證機制，以根據使用者角色（如 ADMIN、USER）判斷是否具備執行操作的權限。
3. 在 Service 層新增登入邏輯，並調整 createUser 與 deleteUser 方法的權限檢查。
4. 增加 UnauthorizedException 與對應的錯誤處理機制，若使用者權限不足將回傳錯誤訊息。
#### 購物車功能建置
1. 新增 CartController 控制器來處理加入與查看購物車的 API。
2. 建立 CartService 處理業務邏輯，並透過 Session 儲存購物車資料。
3. 新增 CartAddRequest 與 CartItemResponse DTO，用於前端傳送加入商品的資訊與回傳購物車內容。
#### 資料模型與 DTO 擴充
1. 修改使用者資料模型，加入 role 欄位以識別權限。
2. 新增登入用的 DTO 物件以接收前端登入請求。

### 遇到了什麼問題?
* 在使用 **Postman** 與 **n8n** 串接時，因為加入了 **session** 導致出現 **Spring Boot** 收不到 **JSESSIONID** 一直無法處理。

### 怎麼解決?
1. 使用 **ChatGPT** 詢問該錯誤 -> 依然無法處理。
2. 從 **n8n** 社群查詢答案 -> 成功知道方法。

### 解決方法
* 在 **n8n** 的 **http** 請求區有提供 **Send Headers** ，將其打開後選擇 **Using JSON** 傳入以下格式:
```
{
  "cookie": "此處放入JSESSIONID即可"
}
```
* 以下是我的放法
```
{
  "cookie": "{{ $json.headers.cookie }}"
}

```
## 2025-04-08 專案進展
1. 新增 ProductController，並加入多種處理方法（新增、查詢、刪除等）。
2. 新增 UserService 中對應方法，包含查詢所有使用者、依據 ID 查詢、建立新使用者等。
3. 新增 ProductService，作為產品邏輯處理的服務層。
4. 建立 UserDeleteRequest，在刪除使用者後保護使用者隱私。
5. 新增 ProductRequest / ProductResponse / ProductProjection 三種資料傳輸類別以管理產品資料格式。
6. 新增 UserProjection 並重新整理至 model/user/ 資料夾。
7. 將 Product 模型類別移動至 model/product/ 資料夾。
8. 將 GenerationType 從 AUTO 改為 IDENTITY，以符合資料庫自增主鍵邏輯。
9. 新增 ProductAlreadyExistsException 與 ProductNotFoundException，處理產品相關錯誤狀況。
10. 新增 ErrorResponse 統一錯誤回傳格式。
11. 優化 UserRepository 與 ProductRepository 結構，增加多種查詢方法。
12. 重構專案內部的 class 路徑與 package 結構，提升模組劃分清晰度。

### 我學到了什麼?
1. 今天將全部的Product結構整個移除重做，再度複習了整個流程，加深印象。
2. 學習使用ErrorResponse來處理錯誤的回傳，讓中間層**n8n**不會需要針對錯誤處理時添加更多邏輯。

## 2025-04-06 專案進展
1. 自行製造**git**遠端與本地端衝突，並使用**git**指令解決該衝突，而非用**IDE**處理。
2. 修改**UserController**，將原本回傳字串修改成回傳**UserResponse**。

```
git fetch backup main // 使用fetch拉下最新程式
git merge FETCH_HEAD // 開始合併
git status // 發生衝突確認檔案
nvim README.md
// 衝突檔案為README.md，因此使用編輯器編輯並解決衝突
git add README.md
git commit -m "merge(backup/main): sync remote change from GitHub"
git merge FETCH_HEAD // 完成衝突後再度merge
```

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
### 當前目錄結構
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
