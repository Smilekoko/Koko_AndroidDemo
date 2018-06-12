package com.ak47.www.koko_androiddemo.thread_asyn.rxjava;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ak47.www.koko_androiddemo.R;

import java.util.List;
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
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
//        Operators_Timer();
//        Operators_Interval();
//        SingleObserver();
//        CompletableObserver();
//        Flowable();
//        Operators_Reduce();
//        Operators_Buffer();
//        Operators_Filter();
    }

    //基本使用流程
    public void SimpleExample() {

        Observable.just("koko", "shike")
                .subscribeOn(Schedulers.io())// Run on a background thread 发送的事件的线程，一般是联网获取的数据
                .observeOn(AndroidSchedulers.mainThread())// Be notified on the main thread 订阅的线程 处理发送的数据
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
            //每个发送的item在被订阅时，调用关于Observer中重写的onNext onError onComplete方法的顺序
            //这里只发送了一个List
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

    private Observable<? extends Long> getIntervalObservable() {
        //设定初始时间和间隔时间发送item,发送的是Long
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, " onDestroy()调用 : ");
        disposables.clear(); // do not send event after activity has been destroyed
    }
}
