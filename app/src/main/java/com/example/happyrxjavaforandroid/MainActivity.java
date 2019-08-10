package com.example.happyrxjavaforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
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
            default:

        }
    }

    private void executeForeach() {
        Observable.fromArray(fruits).subscribe(mObserver);
    }

    private Observable mObservable = Observable.create(emitter -> {
        emitter.onNext("a");
        emitter.onNext("b");
        emitter.onNext("c");
        emitter.onComplete();
    });

    private Observable justObservable = Observable.just(fruits);

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
        Observable.fromCallable(()->1).subscribe(o->Log.d(TAG, "================accept " + o));
    }


}
