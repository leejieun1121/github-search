# github-search
  
## 🔎 Introduction


[Github Search API](https://docs.github.com/en/rest/reference/search#search-repositories) 를 사용해서 검색된 레포지토리 리스트를 보여줍니다. EditText에서 텍스트를 입력하면 검색 API를 호출하고, debounce를 사용해서 호출 간격을 조절했습니다. 각 레포지토리는 유저프로필, 레포지토리 이름, 설명, 받은 star개수, 사용된 언어를 보여줍니다. 

## ⚔️ Tech stack


- Kotlin

- MVVM Architecture Pattern

- AAC (ViewModel)

- Flow, Hilt, Paging, Rx

  

## 📎 Third party

- Retrofit2

- Glide 

  

## 🗂 Project Structure


~~~
com.example.githubsearchapp
├── SearchApplication.kt
├── data
│   ├── paging
│   │   └── SearchPagingSource.kt
│   ├── remote
│   │   └── SearchRemoteDataSource.kt
│   ├── repository
│   │   └── SearchRepository.kt
│   └── vo
│       ├── Owner.kt
│       ├── Repo.kt
│       └── SearchResponse.kt
├── di
│   └── ServiceModule.kt
├── network
│   ├── SearchRetrofit.kt
│   └── service
│       └── SearchService.kt
└── ui
    └── main
        ├── LoadStateAdapter.kt
        ├── LoadStateViewHolder.kt
        ├── MainActivity.kt
        ├── MainPagingAdapter.kt
        └── MainViewModel.kt
~~~


- **data**   

  - paging

    - `SearchPagingSource`

      입력된 쿼리를 통해 레포지토리를 검색하는 API를 호출합니다.  10개의 데이터를 가져온 뒤 스크롤하면 position을 증가시켜서 다시 호출하고, LoadResult.Page 객체로 return합니다. 

  - remote

    - `SearchRemoteDataSource`

       service의 getSearchRepos 함수를 호출합니다. 

  - repository

    - `SearchRepository`

      SearchPagingSource 를 통해 얻은 Pager 객체를 flow로 바꿔 return 합니다.    

  - vo

    - `SearchResponse`

      전체 레포지토리 수와 레포지토리 리스트를 가지고 있습니다.

    - `Repo`

      id, 이름, 레포지토리 작성자, 설명, star 개수, 사용된 언어를 가지고 있습니다. 

    - `Owner`

      이름과 프로필 이미지를 가지고 있습니다. 

- **di** 

  - `ServiceModule`

    Retrofit객체를 만들어 SearchRemoteDataSource에 넣어줍니다. 

- **network**

  - service

    - `SearchService`

      검색 API가 있습니다. 

  - `SearchRetrofi`t

    레트로핏 빌더를 사용하여 Service를 create하는 함수가 있습니다. 

- **ui**

  - main

    - `MainActivity`

      editText의 TextWatcher를 사용해서 값을 입력할때마다 Observable의 onNext로 발행합니다. (debounce를 사용해서 500ms내에 발생한 마지막 데이터로 제한합니다.) 

      새로운 검색 쿼리를 입력하면 맨위로 scroll 됩니다. 그리고 검색 결과를 스크롤 할때 키보드가 hide되며 editText의 focus를 clear해줍니다.  

    - `MainPagingAdapter`

      넘겨받은 PagingData들을 item_main 레이아웃에 set해줍니다. 

    - `MainViewModel`

      repository에서 호출한 레포지토리 리스트 결과값을 Flow로 retrun합니다. 

    - `LoadStateAdapter & LoadStateViewHolder`

      다음 페이지의 리스트를 가져올 때 맨 밑바닥에서 LoadState에 따라 결과를 보여줍니다.

      에러 -> TextView(에러 내용 표시), Button(Retry() 호출) 

      로드중 -> Progressbar(로드 중인 상태 표시)



## 📱 Result

![ezgif com-gif-maker (3)](https://user-images.githubusercontent.com/53978090/148770891-c718aa8a-88b3-43e1-8322-f59ade811f40.gif)
