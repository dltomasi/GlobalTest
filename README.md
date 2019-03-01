Global Test
=================

Architecture
----

I tried to showcase how to build an application using the libraries and components that I believe that are an excellent and reliable choice. 
This app is structured using Model View ViewModel architecture to ensure a good reading, testable and reusable design.
I gave preference to components of Google's JetPack.
- ViewModel class allows data to survive configuration changes such as screen rotations.
- LiveData LiveData is lifecycle-aware and this ensures LiveData only updates app component observers that are in an active lifecycle state.
    
Introduction
-------------

### Technologies
 - [Kotlin Language](https://kotlinlang.org/)
 - [RX](https://github.com/ReactiveX/RxJava)
 - [Databinding](https://developer.android.com/topic/libraries/data-binding)
 - [Dagger2](https://github.com/google/dagger)
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
 - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

----