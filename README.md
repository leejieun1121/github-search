# github-search
  
## ๐ Introduction


[Github Search API](https://docs.github.com/en/rest/reference/search#search-repositories) ๋ฅผ ์ฌ์ฉํด์ ๊ฒ์๋ ๋ ํฌ์งํ ๋ฆฌ ๋ฆฌ์คํธ๋ฅผ ๋ณด์ฌ์ค๋๋ค. EditText์์ ํ์คํธ๋ฅผ ์๋ ฅํ๋ฉด ๊ฒ์ API๋ฅผ ํธ์ถํ๊ณ , debounce๋ฅผ ์ฌ์ฉํด์ ํธ์ถ ๊ฐ๊ฒฉ์ ์กฐ์ ํ์ต๋๋ค. ๊ฐ ๋ ํฌ์งํ ๋ฆฌ๋ ์ ์ ํ๋กํ, ๋ ํฌ์งํ ๋ฆฌ ์ด๋ฆ, ์ค๋ช, ๋ฐ์ star๊ฐ์, ์ฌ์ฉ๋ ์ธ์ด๋ฅผ ๋ณด์ฌ์ค๋๋ค. 

## โ๏ธ Tech stack


- Kotlin

- MVVM Architecture Pattern

- AAC (ViewModel)

- Flow, Hilt, Paging, Rx

  

## ๐ Third party

- Retrofit2

- Glide 

  

## ๐ Project Structure


~~~
com.example.githubsearchapp
โโโ SearchApplication.kt
โโโ data
โย ย  โโโ paging
โย ย  โย ย  โโโ SearchPagingSource.kt
โย ย  โโโ remote
โย ย  โย ย  โโโ SearchRemoteDataSource.kt
โย ย  โโโ repository
โย ย  โย ย  โโโ SearchRepository.kt
โย ย  โโโ vo
โย ย      โโโ Owner.kt
โย ย      โโโ Repo.kt
โย ย      โโโ SearchResponse.kt
โโโ di
โย ย  โโโ ServiceModule.kt
โโโ network
โย ย  โโโ SearchRetrofit.kt
โย ย  โโโ service
โย ย      โโโ SearchService.kt
โโโ ui
    โโโ main
        โโโ LoadStateAdapter.kt
        โโโ LoadStateViewHolder.kt
        โโโ MainActivity.kt
        โโโ MainPagingAdapter.kt
        โโโ MainViewModel.kt
~~~


- **data**   

  - paging

    - `SearchPagingSource`

      ์๋ ฅ๋ ์ฟผ๋ฆฌ๋ฅผ ํตํด ๋ ํฌ์งํ ๋ฆฌ๋ฅผ ๊ฒ์ํ๋ API๋ฅผ ํธ์ถํฉ๋๋ค.  10๊ฐ์ ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ ธ์จ ๋ค ์คํฌ๋กคํ๋ฉด position์ ์ฆ๊ฐ์์ผ์ ๋ค์ ํธ์ถํ๊ณ , LoadResult.Page ๊ฐ์ฒด๋ก returnํฉ๋๋ค. 

  - remote

    - `SearchRemoteDataSource`

       service์ getSearchRepos ํจ์๋ฅผ ํธ์ถํฉ๋๋ค. 

  - repository

    - `SearchRepository`

      SearchPagingSource ๋ฅผ ํตํด ์ป์ Pager ๊ฐ์ฒด๋ฅผ flow๋ก ๋ฐ๊ฟ return ํฉ๋๋ค.    

  - vo

    - `SearchResponse`

      ์ ์ฒด ๋ ํฌ์งํ ๋ฆฌ ์์ ๋ ํฌ์งํ ๋ฆฌ ๋ฆฌ์คํธ๋ฅผ ๊ฐ์ง๊ณ  ์์ต๋๋ค.

    - `Repo`

      id, ์ด๋ฆ, ๋ ํฌ์งํ ๋ฆฌ ์์ฑ์, ์ค๋ช, star ๊ฐ์, ์ฌ์ฉ๋ ์ธ์ด๋ฅผ ๊ฐ์ง๊ณ  ์์ต๋๋ค. 

    - `Owner`

      ์ด๋ฆ๊ณผ ํ๋กํ ์ด๋ฏธ์ง๋ฅผ ๊ฐ์ง๊ณ  ์์ต๋๋ค. 

- **di** 

  - `ServiceModule`

    Retrofit๊ฐ์ฒด๋ฅผ ๋ง๋ค์ด SearchRemoteDataSource์ ๋ฃ์ด์ค๋๋ค. 

- **network**

  - service

    - `SearchService`

      ๊ฒ์ API๊ฐ ์์ต๋๋ค. 

  - `SearchRetrofi`t

    ๋ ํธ๋กํ ๋น๋๋ฅผ ์ฌ์ฉํ์ฌ Service๋ฅผ createํ๋ ํจ์๊ฐ ์์ต๋๋ค. 

- **ui**

  - main

    - `MainActivity`

      editText์ TextWatcher๋ฅผ ์ฌ์ฉํด์ ๊ฐ์ ์๋ ฅํ ๋๋ง๋ค Observable์ onNext๋ก ๋ฐํํฉ๋๋ค. (debounce๋ฅผ ์ฌ์ฉํด์ 500ms๋ด์ ๋ฐ์ํ ๋ง์ง๋ง ๋ฐ์ดํฐ๋ก ์ ํํฉ๋๋ค.) 

      ์๋ก์ด ๊ฒ์ ์ฟผ๋ฆฌ๋ฅผ ์๋ ฅํ๋ฉด ๋งจ์๋ก scroll ๋ฉ๋๋ค. ๊ทธ๋ฆฌ๊ณ  ๊ฒ์ ๊ฒฐ๊ณผ๋ฅผ ์คํฌ๋กค ํ ๋ ํค๋ณด๋๊ฐ hide๋๋ฉฐ editText์ focus๋ฅผ clearํด์ค๋๋ค.  

    - `MainPagingAdapter`

      ๋๊ฒจ๋ฐ์ PagingData๋ค์ item_main ๋ ์ด์์์ setํด์ค๋๋ค. 

    - `MainViewModel`

      repository์์ ํธ์ถํ ๋ ํฌ์งํ ๋ฆฌ ๋ฆฌ์คํธ ๊ฒฐ๊ณผ๊ฐ์ Flow๋ก retrunํฉ๋๋ค. 

    - `LoadStateAdapter & LoadStateViewHolder`

      ๋ค์ ํ์ด์ง์ ๋ฆฌ์คํธ๋ฅผ ๊ฐ์ ธ์ฌ ๋ ๋งจ ๋ฐ๋ฐ๋ฅ์์ LoadState์ ๋ฐ๋ผ ๊ฒฐ๊ณผ๋ฅผ ๋ณด์ฌ์ค๋๋ค.

      ์๋ฌ -> TextView(์๋ฌ ๋ด์ฉ ํ์), Button(Retry() ํธ์ถ) 

      ๋ก๋์ค -> Progressbar(๋ก๋ ์ค์ธ ์ํ ํ์)



## ๐ฑ Result

![ezgif com-gif-maker (3)](https://user-images.githubusercontent.com/53978090/148770891-c718aa8a-88b3-43e1-8322-f59ade811f40.gif)
