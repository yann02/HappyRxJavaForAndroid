package com.example.happyrxjavaforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String[][] arrays = {{"a", "b"}, {"c", "d"}, {"e", "f"}};
    private String[] fruits = {"apple", "orange", "banana"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_foreach:
                executeForeach();
                break;
            case R.id.btn_simple:
                simpleRxJava();
                break;
            case R.id.btn_fromCallable:
                fromCallable();
                break;
            case R.id.btn_use_future:
                useFromFuture();
                break;
            default:

        }
    }

    /**
     * 遍历数组
     */
    private void executeForeach() {
        Observable.fromArray(fruits).subscribe(mObserver);
    }

    /**
     * create lambda写法
     */
    private Observable mObservable = Observable.create(emitter -> {
        emitter.onNext("a");
        emitter.onNext("b");
        emitter.onNext("c");
        emitter.onComplete();
    });

    /**
     * create Observable by create() method
     */
    private Observable createObservable = Observable.create(new ObservableOnSubscribe<Object>() {
        @Override
        public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
            emitter.onNext("hello");
            emitter.onComplete();
        }
    });

    /**
     * create Observable by just() method
     * event of emit can not more than 10
     * 发送的事件不能超过10个
     */
    private Observable justObservable = Observable.just(fruits);

    /**
     * 1.* from()
     * create Observable by fromArray() method
     * 发送的事件能超过10个
     */
    private Observable fromArrayObservable = Observable.fromArray(fruits);

    /**
     * create Observable by fromCallable() method
     * 没有返回值
     */
    private Observable fromCallableObservable = Observable.fromCallable(new Callable<Object>() {
        @Override
        public Object call() throws Exception {
            return null;
        }
    });

    private FutureTask mFutureTask = new FutureTask(new Callable() {
        @Override
        public Object call() throws Exception {
            return "好人";
        }
    });

    /**
     *fromFuture
     * 有返回值
     */
    private void useFromFuture() {
        Observable.fromFuture(mFutureTask).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mFutureTask.run();
            }
        }).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.d(TAG, "accept:" + o.toString());
            }
        });
    }

    private void useFromIterable() {
        List<String> list = new ArrayList<>();
        list.add("Anna");
        list.add("Ros");
        list.add("Jack");
        Observable.fromIterable(list).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }



    private Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "onSubscribe");
        }

        @Override
        public void onNext(String o) {
            Log.d(TAG, "onNext");
            Log.d(TAG, "value:" + o);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete");
        }
    };

    private void simpleRxJava() {
        justObservable.subscribe(mObserver);
    }

    private void fromCallable() {
        Observable.fromCallable(() -> 1).subscribe(o -> Log.d(TAG, "================accept " + o));
    }


}
