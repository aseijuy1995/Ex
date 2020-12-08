package edu.yujie.rxjavaex

class OperatorDoc {
    init {
//        // create
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
//        }.subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // from
//        Observable.fromArray(listOf(1, 2, 3))
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // just
//        Observable.just(1, 2, 3)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // defer - 確保訂閱後發射
//        val observable = Observable.defer {
//            Observable.just(1, 2, 3)
//        }
//        observable.subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // range
//        Observable.range(1, 10)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // interval - 定時發射
//        Observable.interval(3, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it =$it")
//            }

//        // repeat
//        Observable.just(1, 2, 3).repeat(3)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // timer - 延遲發射(發射數據只有一次)
//        Observable.timer(3, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // skip - 從前頭掠過n個數據
//        Observable.just(1, 2, 3)
//            .skip(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // skipLast - 從後頭略過n個數據
//        Observable.just(1, 2, 3)
//            .skipLast(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // debounce - 抖動
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1_500)
//            it.onNext(2)
//            sleep(500)
//            it.onNext(3)
//        }.debounce(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // distinct - 過濾重複
//        Observable.just(1, 2, 3, 1, 2)
//            .distinct()
//            .subscribe {
//                println("$TAG onNext() it $it")
//            }

//        // distinctUntilChanged - 過濾相鄰重複數據
//        Observable.just(1,2,3,3,2,1)
//            .distinctUntilChanged()
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // elementAt
//        Observable.just(1, 2, 3)
//            .elementAt(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // filter
//        Observable.just(1, 2, 3)
//            .filter {
//                it % 2 == 0
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // first
//        Observable.just(1, 2, 3)
//            .first(0)
//            .subscribe({
//                println("$TAG onNext() it = $it")
//            }, {
//                println("$TAG onError() it = $it")
//            })

//        // last
//        Observable.just(1, 2, 3)
//            .last(0)
//            .subscribe({
//                println("$TAG onNext() it = $it")
//            }, {
//                println("$TAG onError() it = $it")
//            })

//        // ignoreElements
//        Observable.just(1, 2, 3)
//            .ignoreElements()
//            .subscribe({
//                println("$TAG onComplete()")
//            }, {
//                println("$TAG onError() ")
//            })

//        // ofType
//        Observable.just(1, 2, 2.5, 3)
//            .ofType(Integer::class.java)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // sample - 一個週期內發射最新數據
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1500)
//            it.onNext(2)
//            sleep(2000)
//            it.onNext(3)
//        }.sample(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // throttleFirst - 指定時間內丟出最新數據
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1_000)
//            it.onNext(2)
//            sleep(300)
//            it.onNext(3)
//            sleep(1_200)
//            it.onNext(4)
//        }.throttleFirst(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // throttleLast - 同於sample
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1_200)
//            it.onNext(2)
//            sleep(300)
//            it.onNext(3)
//            sleep(1_200)
//            it.onNext(4)
//        }.throttleLast(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // throttleLast - 同於throttleLast
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(300)
//            it.onNext(2)
//            sleep(800)
//            it.onNext(3)
//            sleep(800)
//            it.onNext(4)
//        }.throttleLatest(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // take - 發射前n個數據
//        Observable.just(1, 2, 3)
//            .take(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // takeLast - 發射後n個數據
//        Observable.just(1, 2, 3)
//            .takeLast(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // timeout
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(300)
//            it.onNext(2)
//            sleep(1200)
//            it.onNext(3)
//        }.timeout(1, TimeUnit.SECONDS)
//            .subscribe(object : Observer<Int> {
//                override fun onSubscribe(d: Disposable?) {
//                }
//
//                override fun onNext(t: Int?) {
//                    println("$TAG onNext() t = $t")
//                }
//
//                override fun onError(e: Throwable?) {
//                    println("$TAG onError() e = $e")
//                }
//
//                override fun onComplete() {
//                }
//
//            })

//        // merge - 無序合併
//        val observable = Observable.just(1, 2, 3)
//        val observable2 = Observable.just(4, 5, 6)
//        Observable.merge(observable, observable2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // concat - 有序合併
//        val observable = Observable.just(1, 2, 3)
//        val observable2 = Observable.just(4, 5, 6)
//        Observable.concat(observable, observable2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // zip
//        val observable = Observable.just(1, 2, 3)
//        val observable2 = Observable.just(4, 5, 6)
//        Observable.zip(observable, observable2, BiFunction { i: Int, i2: Int ->
//            i + i2
//        }).subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // startWith
//        Observable.just(1, 2, 3)
//            .startWith(Observable.just(4, 5, 6))
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // join - 時間期限核定
//        Observable.just(1, 2, 3)
//            .join(Observable.just(1, 10, 100), Function<Int, ObservableSource<Long>> {
//                Observable.timer(1, TimeUnit.SECONDS)
//            }, Function<Int, ObservableSource<Long>> {
//                Observable.timer(2, TimeUnit.SECONDS)
//            }, BiFunction<Int, Int, Int> { i: Int, i2: Int ->
//                i + i2
//            }).subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // combineLatest - 取得最後一位做合併
//        val observable = Observable.just(1, 10,100)
//        val observable2 = Observable.interval(1, TimeUnit.SECONDS)
//        Observable.combineLatest(observable, observable2, BiFunction<Int, Long, Int> { i1: Int, i2: Long ->
//            (i1 + i2).toInt()
//        }).subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // switchOnNext
//        Observable.switchOnNext<Int> {
//            it.onNext(Observable.just(1, 2, 3))
//            it.onNext(Observable.just(4, 5, 6))
//        }.subscribe {
//            println("$TAG onNext() it = $it")
//        }

        //--------------------------------------------------------------------------------

//        // map - 轉型
//        Observable.just(1, 2, 3)
//            .map {
//                it * it
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // flatMap - 無序發射
//        Observable.just(1, 2, 3)
//            .flatMap {
//                println("$TAG flatMap() it = $it")
//                Observable.just(it, it * 2, it * 3).delay(1, TimeUnit.MILLISECONDS)
//            }
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // concatMap - 有序發射
//        Observable.just(1, 2, 3)
//            .concatMap {
//                println("$TAG concatMap() it = $it")
//                Observable.just(it, it * 2, it * 3).delay(1, TimeUnit.MILLISECONDS)
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // groupBy - 分組
//        Observable.just("Android", "Java", "Kotlin", "JS")
//            .groupBy {
//                it.elementAt(0)
//            }.subscribe { groupedObservable ->
//                groupedObservable.sorted().subscribe {
//                    println("$TAG key = ${groupedObservable.key}, value = $it")
//                }
//            }

//        // scan - 掃描,獲取當前數據以及上一輪發射的數據
//        Observable.just(1, 2, 3)
//            .scan { t1: Int?, t2: Int? ->
//                if (t1!! > t2!!) t1 else t2
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // buffer - 緩存
//        Observable.just(1, 2, 3, 4, 5, 6)
//            .buffer(3)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // window - 同於buffer,相差於window回傳為Observable可再對此操作
//        Observable.just(1, 2, 3, 4, 5, 6)
//            .window(3)
//            .subscribe {
//                it.map {
//                    it * it
//                }.subscribe {
//                    println("$TAG onNext() it = $it")
//                }
//            }

//        // cast - 修正類型,失敗丟出Exception
//        Observable.just(1, 2, 3)
//            .cast(Integer::class.java)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // concatMapDelayError - 同於concatMap,相差於失敗丟出Exception待到最後在處理
//        Observable.just(1, 2, 3)
//            .concatMapDelayError {
//                if (it == 2)
//                    Observable.error(IOException("Wrong"))
//                else
//                    Observable.just(it)
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // concatMapCompletable - 同於concatMap,相差於返回為一個Completable表完成or失敗
//        Observable.just(1, 2, 3)
//            .concatMapCompletable {value->
//                Completable.create {
//                    println("$TAG create() it = $value")
//                    it.onComplete()
//                }
//            }.subscribe {
//                println("$TAG onComplete()")
//            }

//        // concatMapCompletableDelayError - 同於concatMapCompletable & concatMapDelayError結合
//        Observable.just(1, 2, 3)
//            .concatMapCompletableDelayError { value ->
//                if (value == 2) Completable.error(IOException())
//                else Completable.create {
//                    println("$TAG create() it = $value")
//                    it.onComplete()
//                }
//            }.subscribe {
//                println("$TAG onComplete()")
//            }

//        // flattenAsFlowable - 可將Maybe/Single轉換成Flowable
//        Single.just(1)
//            .flattenAsFlowable {
//                listOf(it, it * 2, it * 3)
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // flattenAsObservable - 可將Maybe/Single轉換成Observable
//        Maybe.just(1)
//            .flattenAsObservable {
//                listOf(it, it * 2, it * 3)
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // onErrorReturn - 可對失敗處理,並給予預設後執行onComplete()
//        Observable.just("1", "2A", "3")
//            .map {
//                it.toInt()
//            }
//            .onErrorReturn {
//                if (it is Exception) 0
//                else throw IllegalArgumentException()
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // onErrorReturnItem - 同於onErrorReturn,不做處理給予預設值執行omComplete()
//        Observable.just("1", "2A", "3")
//            .map {
//                it.toInt()
//            }
//            .onErrorReturnItem(0)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

        // onExceptionResumeNext

//        // retry - 重複發送
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onError(NullPointerException())
//        }.retry(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // retryUntil - 發生錯誤是否繼續執行(true不繼續執行/false繼續執行)
//        Observable.create<String> {
//            it.onNext("1")
//            it.onNext("2A")
//            it.onNext("3")
//        }.map {
//            it.toInt()
//        }.retryUntil { true }
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // retryWhen - 通於retry,相差於錯誤傳進retryWhen()可做處理
//        Observable.create<String> {
//            it.onNext("1")
//            it.onNext("2A")
//            it.onNext("3")
//        }.map {
//            it.toInt()
//        }.retryWhen {
//            it.doOnError {
//                println("$TAG doOnError() it = $it")
//            }
//        }.subscribe {
//            println("$TAG onNext() it = $it")
//        }

        //--------------------------------------------------------------------------------

//        // doOnEach - 每發射一次數據就呼叫一次
//        Observable.just(1, 2, 3)
//            .doOnEach {
//                println("$TAG doOnEach")
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // doOnNext - 每執行一次onNext(),就呼叫一次
//        Observable.just(1, 2, 3)
//            .doOnNext {
//                println("$TAG doOnNext")
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // doOnError - 每執行一次onError(),就呼叫一次
//        Observable.just(1, 2, 3)
//            .doOnError {
//                println("$TAG doOnError")
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // doOnComplete - 每執行一次onComplete(),就呼叫一次
//        Observable.just(1, 2, 3)
//            .doOnComplete {
//                println("$TAG doOnComplete")
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // doOnSubscribe - 每執行一次onSubscribe(),就呼叫一次
//        Observable.just(1, 2, 3)
//            .doOnSubscribe {
//                println("$TAG doOnSubscribe")
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // doOnDispose - 每執行一次dispose(),就呼叫一次
//        Observable.just(1, 2, 3)
//            .doOnDispose {
//                println("$TAG doOnDispose")
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

    }
}