# Clothing EC Site - 衣類ECサイト

##　Spring Boot、Thymeleaf、MySQL、HTML、CSSを使用したデスクトップ向けECサイトです。

#開発経緯
>就職活動において実績をアピールしたいと考えたときに、
自分の好きなファッションに関係したアプリケーション開発をしたいと思って、この衣類ECサイト開発に至りました。
カレッジで学んだこと及び、独学で学んだことを駆使したアプリとなっております。

#開発にあたって工夫したポイント
>フロントエンド部分:サイト自体のデザイン的な部分についてはポートフォリオということもあり、わかりやすいようにシンプルなデザインにしました。

>バックエンド部分:ユーザーアカウントと管理者アカウントを分けることでよりECサイトとしてより動的な内容になるように仕上げました。
   商品をカテゴリー別に見れるようにしたりキーワード検索をできるようにしたことで、目的の商品へ素早くアクセスできるようにしました。

#開発する上で苦労した点、学んだ点について
>開発を行なっていく中、javaファイルのコードを書いているときに頻繁にエラーが吐き出されたことにとても苦労しました。
 よくエラーを吐かれた後、よくわからないからとエラーを読まずに、実装コードを数分間眺めてしまう過去の自分がいましたが、
 エラー文をよく読んで論理的に淡々と情報を整理していくことが重要だと学びました。
 その結果エラーが出ても冷静に考えて修正していくことで、エラー修正にかける時間を大幅に短くすることができました。
「なぜエラーが起きていたのか」、そして「なぜエラーが治ったのか」それを理解することがとても大切だと思いました。

## 機能一覧

### 一般ユーザー機能
- ✅ ユーザー登録・ログイン
- ✅ 商品一覧表示
- ✅ 商品詳細表示
- ✅ カテゴリー別商品検索
- ✅ キーワード検索
- ✅ ショッピングカート機能
- ✅ カート内商品の数量変更・削除
- ✅ 注文機能（チェックアウト）
- ✅ 注文履歴表示
- ✅ 注文詳細表示

### 管理者機能
- ✅ ダッシュボード
- ✅ 商品登録（画像アップロード対応）
- ✅ 商品編集
- ✅ 商品ステータス変更
- ✅ 商品削除
- ✅ ユーザー管理
- ✅ ユーザー削除
- ✅ 注文管理
- ✅ 注文ステータス変更
- ✅ 注文詳細表示

## 技術スタック

- **バックエンド**: Java 17, Spring Boot 3.2.0
- **フロントエンド**: HTML5, CSS3, Thymeleaf
- **データベース**: MySQL 8.0
- **ビルドツール**: Maven

## 必要な環境

- Java 17以上
- MySQL 8.0以上
- Maven 3.6以上

## アプリケーション使用手順

### 1. データベースの起動

-ターミナルでのMySQLへのログイン

>1️⃣ cd /Applications/MAMP/Library/bin/mysql80/bin　を入力。
>2️⃣　./mysql -u root -p　を入力。
>3️⃣　ログインパスワード『root』を入力。

### 2. アプリケーションの起動

>Eclipseにてclothingec（右クリック　→　実行　→　Spring Bootアプリケーション）を選択して起動する。

### 3. アクセス

ブラウザで以下のURLにアクセスします。

```
http://localhost:8080
```

## 4.ログイン画面


以下でデフォルトアカウントを作成済
※新規作成の場合は「アカウントをお持ちでない方はこちら」をクリックして会員登録を行う。

### 管理者アカウント
- **ユーザー名**: admin
- **パスワード**: admin123

### 一般ユーザーアカウント
- **ユーザー名**: user1
- **パスワード**: user123

## プロジェクト構造
```
clothing-ec-site/
├── src/
│   ├── main/
│   │   ├── java/com/clothingec/
│   │   │   ├── ClothingEcApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   └── CartItem.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   └── CartItemRepository.java
│   │   │   └── service/
│   │   │       ├── UserService.java
│   │   │       ├── OrderService.java
│   │   │       ├── ProductService.java
│   │   │       └── CartService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       │   └── css/
│   │       │       └── style.css
│   │       ├── images/
│   │       └── templates/
│   │           ├── login.html
│   │           ├── register.html
│   │           ├── home.html
│   │           ├── product-list.html
│   │           ├── product-detail.html
│   │           ├── cart.html
│   │           └── admin/
│   │               ├── dashboard.html
│   │               ├── product-register.html
│   │               ├── product-edit.html
│   │               ├── product-list.html
│   │               └── user-management.html
│   └── test/
└── pom.xml
```

## データベーステーブル

### users
ユーザー情報を管理

- id (PRIMARY KEY)
- username (UNIQUE)
- password (平文)
- email (UNIQUE)
- full_name
- role (USER/ADMIN)
- created_at
- updated_at

### products
商品情報を管理

- id (PRIMARY KEY)
- name
- description
- price
- category
- size
- color
- stock_quantity
- image_url
- status (ACTIVE/INACTIVE/OUT_OF_STOCK)
- created_at
- updated_at

### carts
ショッピングカートを管理

- id (PRIMARY KEY)
- user_id (FOREIGN KEY -> users.id)
- created_at
- updated_at

### cart_items
カート内の商品を管理

- id (PRIMARY KEY)
- cart_id (FOREIGN KEY -> carts.id)
- product_id (FOREIGN KEY -> products.id)
- quantity
- added_at

### orders
注文情報を管理

- id (PRIMARY KEY)
- user_id (FOREIGN KEY -> users.id)
- total_amount
- status (PENDING/CONFIRMED/PROCESSING/SHIPPED/DELIVERED/CANCELLED)
- shipping_address
- phone_number
- order_date
- shipped_date
- delivered_date

### order_items
注文商品詳細を管理

- id (PRIMARY KEY)
- order_id (FOREIGN KEY -> orders.id)
- product_id (FOREIGN KEY -> products.id)
- quantity
- price

- 
## 主要な機能の使い方

### 新規ユーザー登録
1. トップページから「こちら」をクリック
2. 必要情報を入力して登録
3. ログイン画面にリダイレクトされます

### 商品の購入
1. ホーム画面または商品一覧から商品を選択
2. 商品詳細ページで数量を選択
3. 「カートに追加」をクリック
4. カートページで内容を確認
5. 「注文手続きへ進む」をクリック
6. 配送先情報を入力
7. 「注文を確定する」をクリック

### 注文履歴の確認
1. ナビゲーションバーから「注文履歴」をクリック
2. 注文一覧が表示されます
3. 「詳細を見る」で注文詳細を確認

### 管理者として商品を登録
1. 管理者アカウントでログイン
2. ナビゲーションバーから「管理画面」をクリック
3. 「商品管理」→「新規商品登録」
4. 商品情報を入力
5. 画像ファイルをアップロードまたは画像URLを入力
6. 登録ボタンをクリック

### 注文管理
1. 管理画面から「注文管理」をクリック
2. 注文一覧が表示されます
3. 「操作」ボタンで注文状態（ステータスを）を変更


### 商品のステータス変更
1. 管理画面の商品一覧から
2. ステータスのドロップダウンを変更
3. 自動的に更新されます


## トラブルシューティング

### データベース接続エラー
- MySQLが起動しているか確認
- `application.properties` の接続情報が正しいか確認
- データベースが作成されているか確認

### 画像が表示されない
- `image_url` に有効なURLが設定されているか確認
- プレースホルダー画像が表示されます

## 今後の拡張機能（予定）

- [ ] 決済機能の統合
- [ ] 商品レビュー機能
- [ ] メール通知機能
- [ ] 在庫管理の自動化
- [ ] レスポンシブデザイン対応
- [ ] 配送追跡機能
- [ ] クーポン・割引機能

## 作成者
　倉本海

## お問い合わせ

バグ報告や機能要望は、GitHubのIssueにてお願いします。
