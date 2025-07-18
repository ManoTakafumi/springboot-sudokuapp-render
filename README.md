# Sudoku Web App

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green?logo=spring)
![Render](https://img.shields.io/badge/Deploy-Render-purple)

就職活動向けに作成した、**Spring Boot製の数独(ナンプレ)Webアプリ**です。ログイン機能付きで、ランダム生成された問題を解くことができます。

---

## アプリURL

[https://sudokuapp-2yvs.onrender.com](https://sudokuapp-2yvs.onrender.com)

---

## 主な機能

- 数独のランダム生成と表示
- 入力フォームから解答を送信
- ユーザー登録 / ログイン機能(Spring Security使用)
- 自動検証による解答の正誤判定
- PostgreSQL によるデータ永続化

---

## 使用技術・環境

| 種類         | 内容                           |
|--------------|-------------------------------|
| フロントエンド | Thymeleaf, HTML/CSS           |
| バックエンド   | Spring Boot, Java             |
| データベース   | PostgreSQL (Render)           |
| セキュリティ   | Spring Security               |
| ビルドツール   | Maven                         |
| デプロイ      | Render (無料クラウドホスティング) | 

---