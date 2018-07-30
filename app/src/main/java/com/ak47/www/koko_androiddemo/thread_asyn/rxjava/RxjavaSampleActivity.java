package com.ak47.www.koko_androiddemo.thread_asyn.rxjava;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ak47.www.koko_androiddemo.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Rxjava2的使用教程
 */
public class RxjavaSampleActivity extends AppCompatActivity {
    private static final String TAG = "Rxjava";
    private final CompositeDisposable disposables = new CompositeDisposable();


    static Observable<String> sampleDeferObservable() {
        Log.e(TAG, " 开始 : ");
        //延迟发送的Observable
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(8000);
                return Observable.just("one", "two", "three", "four", "five");

                // 3 means,  it takes max of three from its start index and create list
                // 1 means, it jumps one step every time
                // so the it gives the following list
                // 1 - one, two, three
                // 2 - two, three, four
                // 3 - three, four, five
                // 4 - four, five
                // 5 - five
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_sample);

//        SimpleExample();

//        Operators_Map();
//        Operators_Zip();
//        Operators_Disposable();
//        Operators_Take();
//        Operators_Delay();
//        Operators_Timer();
//        Operators_Interval();
//        SingleObserver();
//        CompletableObserver();
//        Flowable();
//        Operators_Reduce();
//        Operators_Buffer();
//        Operators_Filter();
//        Operators_Skip();
//        Operators_Scan();
//        Operators_Replay();
//        Operators_Concat();
//        Operators_Merge();
//        Operators_Defer();
//        Operators_Distinct();
//        Operators_Last();
//        Operators_ThrottleFirst();
//        Operators_ThrottleLast();
//        Operators_Debounce();
//        Operators_Window();
//        Operators_SwitchMap();
//        PublishSubject();
        BehaviorSubject();
//        AsyncSubject();

        //use with Retrofit
        useWithRetrofit();

    }


    //基本使用流程
    public void SimpleExample() {

        Observable.just("koko", "shike")
                .subscribeOn(Schedulers.io())// 在指定的Scheduler上异步地将观察者订阅到此ObservableSource。
                .observeOn(AndroidSchedulers.mainThread())// 修改ObservableSource以在指定调度程序上执行其排放和通知
                .subscribe(new Observer<String>() {
                    //一般调用一次
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, " onNext : value : " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    //Operators -->Map
    public void Operators_Map() {
        Observable.create(new ObservableOnSubscribe<List<ApiUser>>() {

            @Override
            public void subscribe(ObservableEmitter<List<ApiUser>> e) throws Exception {
                if (!e.isDisposed())
                    e.onNext(Utils.getApiUserList());//传入的参数是发送的item，及时ObservableOnSubscribe<T>中的泛型对象
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                //通过对每个项目应用一个函数来转换Observable发出的项目
                .map(new Function<List<ApiUser>, List<User>>() {
                    @Override
                    public List<User> apply(List<ApiUser> apiUsers) throws Exception {
                        return Utils.convertApiUserListToUserList(apiUsers);
                    }
                })
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        for (User user : users) {
                            Log.e(TAG, " onNext : " + user.getFirstname() + "//" + user.getLastname());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, " onComplete");
                    }
                });
    }

    //Operators -->Zip
    public void Operators_Zip() {
        //通过指定函数将多个Observable的发射结合在一起，为每个Observable(会新生成一个Observable发送同样的item)
        //这里产生3个Observable，每个发送一个List<User>>，第三次个参数及返回的结果
        Observable.zip(getUser1Obervable(), getUser2Obervable(), new BiFunction<List<User>, List<User>, List<User>>() {
            @Override
            public List<User> apply(List<User> users1, List<User> users2) throws Exception {
                return Utils.filterUser1andUser2(users1, users2);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        for (User user : users) {
                            Log.e(TAG, " onNext : " + user.getLastname());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, " onComplete");
                    }
                });

    }

    //Operators -->Disposable
    public void Operators_Disposable() {
        disposables.add(sampleDeferObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //这里返回一个Observer的子类，DisposableObserver实现Disposable的Observer，支持打断
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, " onNext value : " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, " onComplete");
                    }
                })
        );
    }

    //Operators -->Take
    public void Operators_Take() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                //Take - >只发出Observable发出的前n个物品
                .take(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, " onNext value : " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    //Operators -->Delay
    public void Operators_Delay() {
        Observable.just("Amit").delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringObserver());
    }

    //Operators -->Timer
    public void Operators_Timer() {
        //eturns an Observable that emits {@code 0L} after a specified delay, and then completes.
        //onSubscribe调用后，延后，调用onNext和onComplete，之间无延迟
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, " onNext : value : " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, " onComplete");
                    }
                });
    }

    //Operators -->Interval
    public void Operators_Interval() {
        disposables.add(getIntervalObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getDisposableObserver()));
    }

    //SingleObserver
    public void SingleObserver() {
        Single.just("Amit")
                .subscribe(getSingleObserver());
    }

    //CompletableObserver
    public void CompletableObserver() {
        //Represents a deferred computation without any value but only indication for completion or exception.
        Completable completable = Completable.timer(2000, TimeUnit.MILLISECONDS);
        completable
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCompletableObserver());
    }

    //Operators-->Reduce
    public void Operators_Reduce() {
        Observable.just(1, 2, 3, 4)
                //reduce 通过应用一个累加函数，把每一个item累加，最后返回最后的item累加的值
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer t1, Integer t2) {
                        return t1 + t2;
                    }
                }).subscribe(getMaybeObserver());
    }

    //Operators-->Buffer
    public void Operators_Buffer() {
        Observable<List<String>> buffered =
                Observable.just("one", "two", "three", "four", "five")
                        .buffer(3, 1);
        // 3 means,  it takes max of three from its start index and create list
        // 1 means, it jumps one step every time
        // so the it gives the following list
        // 1 - one, two, three
        // 2 - two, three, four
        // 3 - three, four, five
        // 4 - four, five
        // 5 - five


        buffered.subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(List<String> strings) {
                Log.e(TAG, " onNext : size :" + strings.size());
                for (String value : strings) {
                    Log.e(TAG, " : value :" + value);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete");
            }
        });
    }

    //Operators-->Filter
    public void Operators_Filter() {
        Observable.just(1, 2, 3, 4, 5, 6)
                //filter:emit only those items from an Observable that pass a predicate test
                //Predicate:一个函数接口（回调），为给定的输入值返回true或false。
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                }).subscribe(getIntegerObserver());
    }

    //Operators-->Skip
    public void Operators_Skip() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(2)
                .subscribe(getIntegerObserver());
    }

    //Operators-->Scan
    public void Operators_Scan() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //每个item应用accumulator function,发送每个迭代的结果
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer1, Integer integer2) throws Exception {
                        Log.e(TAG, "integer1:" + integer1 + "///" + "integer2:" + integer2);
                        return integer1 + integer2;
                    }
                }).subscribe(getIntegerObserver());
    }

    //Operators-->Replay
    public void Operators_Replay() {
        //Subject类似于Observer和Observable的契约类
        //PublishSubject:Represents an Observer and an Observable at the same time
        //once an Observer has subscribed, emits all subsequently observed items to the subscriber.
        PublishSubject<Integer> source = PublishSubject.create();

        //ConnectableObservable和普通Observable一样，但在.connect()后才开始发送item
        //replay(bufferSize):bufferSize表示只能保存多大数量的item，后面会覆盖前面的，一般发送的是从后往前的bufferSize个数的item
        ConnectableObservable<Integer> connectableObservable = source.replay(3); // bufferSize = 3 to retain 3 values to replay
        connectableObservable.connect(); // connecting the connectableObservable
        connectableObservable.subscribe(getIntegerObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onNext(5);
        source.onNext(6);
        source.onNext(7);
        source.onComplete();

        connectableObservable.subscribe(getIntegerObserver());
    }

    //Operators-->Concat
    public void Operators_Concat() {
        final String[] aStrings = {"A1", "A2", "A3", "A4"};
        final String[] bStrings = {"B1", "B2", "B3"};

        final Observable<String> aObservable = Observable.fromArray(aStrings);
        final Observable<String> bObservable = Observable.fromArray(bStrings);

        //Returns an Observable that emits the items emitted by two ObservableSources,
        // one after the other, without interleaving them.
        Observable.concat(aObservable, bObservable)
                .subscribe(getStringObserver());
    }

    //Operators-->Merge
    public void Operators_Merge() {
        final String[] aStrings = {"A1", "A2", "A3", "A4"};
        final String[] bStrings = {"B1", "B2", "B3"};

        final Observable<String> aObservable = Observable.fromArray(aStrings);
        final Observable<String> bObservable = Observable.fromArray(bStrings);

        //merge合并Observable，不按顺序
        Observable.merge(aObservable, bObservable)
                .subscribe(getStringObserver());
    }

    //Operators-->Defer
    public void Operators_Defer() {
        User user = new User();
        Observable<String> firstnameDeferObservable = user.getDeferObservable();
        //Even if we are setting the brand after creating Observable
        //we can get the firstname as koko
        //If we had not user Defer,we would have got null as the koko
        user.setFirstname("koko");
        firstnameDeferObservable.subscribe(getStringObserver());

    }

    //Operators-->Distinct
    public void Operators_Distinct() {
        Observable.just(1, 2, 1, 1, 2, 3, 4, 6, 4)
                //suppresses duplicate items emitted by the source Observable.
                .distinct()
                .subscribe(getIntegerObserver());
    }

    //Operators-->Last
    public void Operators_Last() {
        Observable.just("A1", "A2", "A3", "A4", "A5", "A6")
                //emits only the last item emitted by the Observable.
                .last("A1")// the default item ("A1") to emit if the source ObservableSource is empty
                .subscribe(getSingleObserver());
    }

    //Operators-->ThrottleFirst
    public void Operators_ThrottleFirst() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Thread.sleep(0);
                e.onNext(1);// deliver
                e.onNext(2); // skip
                Thread.sleep(505);
                e.onNext(3);// deliver
                Thread.sleep(99);
                e.onNext(4);// skip
                Thread.sleep(100);
                e.onNext(5);// skip
                e.onNext(6);// skip
                Thread.sleep(305);
                e.onNext(7);// deliver
                Thread.sleep(510);
                e.onComplete();
            }
        })
                /*
                 * the Observable that results from this operator will emit no item for that sampling period.
                 * but emit first item in the sampling peroid
                 */
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getIntegerObserver());
    }

    //Operators-->ThrottleLast
    public void Operators_ThrottleLast() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Thread.sleep(0);
                e.onNext(1);// skip
                e.onNext(2);// deliver
                Thread.sleep(505);
                e.onNext(3);// skip
                Thread.sleep(99);
                e.onNext(4);// skip
                Thread.sleep(100);
                e.onNext(5);// skip
                e.onNext(6);// deliver
                Thread.sleep(305);
                e.onNext(7); // deliver
                Thread.sleep(510);
                e.onComplete();
            }
        })
                /*
                 *emit the most recent items emitted by an Observable within periodic time intervals
                 */
                .throttleLast(500, TimeUnit.MILLISECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getIntegerObserver());
    }

    //Operators-->Debounce
    public void Operators_Debounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);// skip
                Thread.sleep(400);

                e.onNext(2);// deliver
                Thread.sleep(505);//超过500毫秒所以3没覆盖2

                e.onNext(3);// skip
                Thread.sleep(100);//为超过500毫秒，所以4覆盖3发送
                e.onNext(4);// deliver

                Thread.sleep(605);
                e.onNext(5);// deliver
                Thread.sleep(510);
                e.onComplete();
            }
        })
                /*
                 *发送一个item后间隔时间，这段时间中发送的其他item，会让之前的item被drop
                 */
                .debounce(500, TimeUnit.MILLISECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getIntegerObserver());
    }

    //Operators-->Window
    public void Operators_Window() {
        Observable.interval(1, TimeUnit.SECONDS)
                //发送的最大值12
                .take(12)
                //每隔一段时间从source ObservableSource收集item返回一个Observable
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getConsumer());
    }

    //Operators-->SwitchMap
    public void Operators_SwitchMap() {
        Observable.just(1, 2, 3, 4, 5, 6)
                //为 每个 发送的item应用一个函数，来生成一个内部Observable
                //每当源Observable发出一个新项目时，它将取消订阅并停止镜像从先前发出的项目生成的Observable，并开始仅镜像当前的项目。
                .switchMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        int delay = new Random().nextInt(2);

                        return Observable.just(integer.toString() + "x")
                                .delay(delay, TimeUnit.SECONDS, Schedulers.io());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringObserver());
    }

    //Flowable
    public void Flowable() {
        Flowable<Integer> observable = Flowable.just(1, 2, 3, 4);
        //reduce 通过应用一个累加函数，把seed和每一个item通过累加函数，最后返回最后的item累加的值
        observable.reduce(50, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer1, Integer integer2) throws Exception {
                return integer1 + integer2;
            }
        }).subscribe(getIntegerSingleObserver());
    }

    //PublishSubject
    public void PublishSubject() {
        //PublishSubject仅在订阅时间后向观察者发出由源Observable发出的项目。
        PublishSubject<Integer> source = PublishSubject.create();
        source.subscribe(getIntegerObserver1());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        Log.e(TAG, "第二次订阅");
        source.subscribe(getIntegerObserver2());
        source.onNext(4);
        source.onComplete();
    }

    //BehaviorSubject
    public void BehaviorSubject() {
        BehaviorSubject<Integer> source = BehaviorSubject.create();
        source.subscribe(getIntegerObserver1());// it will get 1, 2, 3, 4 and onComplete
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        Log.e(TAG, "订阅第二个Observe");
        //it will emit 3(last emitted), 4 and onComplete for second observer also.
        source.subscribe(getIntegerObserver2());
        source.onNext(4);
        source.onComplete();

    }

    //AsyncSubject
    public void AsyncSubject() {
        /*
        * An AsyncSubject emits the last value (and only the last value) emitted by the source Observable， and only after that source Observable completes
        * If the source Observable does not emit any values, the AsyncSubject also completes without emitting any values.)
        */
        AsyncSubject<Integer> source = AsyncSubject.create();
        source.subscribe(getIntegerObserver1());
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        Log.e(TAG, "订阅第二个Observe");
        source.subscribe(getIntegerObserver2());

        source.onNext(4);
        source.onComplete();
    }

    private Observer<Integer> getIntegerObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, " onNext : value : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete");
            }
        };
    }

    private Observer<Integer> getIntegerObserver1() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe1 : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, " onNext1 : value : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError1 : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete1");
            }
        };
    }

    private Observer<Integer> getIntegerObserver2() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe2 : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, " onNext2 : value : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError2 : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete2");
            }
        };
    }

    private Observer<String> getStringObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, " onNext : value : " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete");
            }
        };
    }

    private Observable<? extends Long> getIntervalObservable() {
        //设定初始延迟时间和间隔时间发送item,发送的是Long
        return Observable.interval(0, 4, TimeUnit.SECONDS);
    }

    private Observable<List<User>> getUser1Obervable() {
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                if (!e.isDisposed())
                    e.onNext(Utils.getUserList1());
                e.onComplete();
            }
        });
    }

    private Observable<List<User>> getUser2Obervable() {
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                if (!e.isDisposed())
                    e.onNext(Utils.getUserList2());
                e.onComplete();
            }
        });
    }

    private DisposableObserver<Long> getDisposableObserver() {
        return new DisposableObserver<Long>() {

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, " onNext : value : " + aLong);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete");
            }
        };
    }

    private SingleObserver<String> getSingleObserver() {
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String s) {
                Log.e(TAG, " onNext value : " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }
        };
    }

    private CompletableObserver getCompletableObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());

            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }
        };
    }

    private SingleObserver<Integer> getIntegerSingleObserver() {
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.e(TAG, " onSuccess : value : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }
        };
    }

    //提供一种机制接受单个订阅， onSubscribe (onSuccess | onError | onComplete)?
    private MaybeObserver<Integer> getMaybeObserver() {
        return new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer integer) {
                Log.e(TAG, " onSuccess : value : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, " onComplete");
            }
        };
    }

    //Consumer消费者
    public Consumer<Observable<Long>> getConsumer() {
        return new Consumer<Observable<Long>>() {
            @Override
            public void accept(Observable<Long> longObservable) throws Exception {
                Log.e(TAG, "Sub Divide begin....+");
                longObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                Log.e(TAG, "Next:" + aLong);
                            }
                        });
            }
        };
    }

    //rxjava和Retrofit一起使用的例子
    public void useWithRetrofit() {

        HttpMethods.getInstance().getJoke(new Observer<List<MyJoke>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(List<MyJoke> myJokes) {
                for (MyJoke joke : myJokes) {
                    Log.e(TAG, "获取数据完成" + joke.getContent());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "error" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, " onDestroy()调用 : ");
        disposables.clear(); // do not send event after activity has been destroyed
    }
}
