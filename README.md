# Account Book App

<br/><br/><br/>

## 1 나는 이런 서비스를 만들거에요
나의 수입과 지출 내역을 최대한 한 눈에 관리할 수 있도록 화면을 구성할 거에요.<br/>
캘린더로 일 별 내역을 볼 수도 있고 통계 그래프도 제공해줘요.

<br/><br/><br/>

## 2 이번에 도전해 볼 것들
- [의존성 주입 라이브러리](https://developer.android.com/training/dependency-injection/hilt-android)를 적용해 보자!
- [Jetpack Compose](https://github.com/android/compose-samples)를 적극적으로 활용해 보자!
- [Compose](https://developer.android.com/reference/kotlin/androidx/compose/ui/platform/ComposeView)와 [기본 Activity](https://foso.github.io/Jetpack-Compose-Playground/viewinterop/androidview/)를 연결하는 방법을 공부하여 적용해 보자!
- [SQLiteOpenHelper](https://developer.android.com/training/data-storage/sqlite)를 이용해서 로컬 DB를 구축해보자!
- Coroutine Flow를 적용해보면서 배워보자!

<br/><br/><br/>

## 3 나의 앱은 이런 구조를 가지고 있어요
### - DB
  - 결제수단 (payment_method)
  - 카테고리 (category)
  - 히스토리 (history)
  <img width="557" alt="image" src="https://user-images.githubusercontent.com/47631768/182009464-0b91bcc8-6bf6-44f0-a5fa-a1852f81c5cd.png">

### - Features
  - Main
  - History
    - Read
    - Create / Update
    - Delete
  - Calendar
  - Graph
  - Setting
    - Payment Methods
    - Categories
  - Model / DAO / Repository

<br/><br/><br/>
