package com.lengjiye.code.mvptest;


import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.code.lengjiye.mvp.BasicMvpActivity;
import com.lengjiye.code.R;
import com.lengjiye.code.mvptest.bean.PlayHomeFeedData;
import com.lengjiye.code.mvptest.contract.MvpTestContract;
import com.lengjiye.code.mvptest.presenter.MvpTestPresenter;
import com.lengjiye.tools.LogTool;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 通知栏activity
 */
public class MVPTestActivity extends BasicMvpActivity<MvpTestContract.View, MvpTestContract.Presenter>
        implements MvpTestContract.View {

    private TextView textView;

    @Override
    public int getResourceId() {
        return R.layout.activity_mvp_test;
    }

    @Override
    protected void initViews() {
        super.initViews();
        textView = findViewById(R.id.text);
    }

    @Override
    public MvpTestContract.Presenter createPresenter() {
        return new MvpTestPresenter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(findViewById(R.id.button), findViewById(R.id.button1));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button:
                getPresenter().getData();
                break;

            case R.id.button1:
//                testRxjava();
                test1();
                break;
        }
    }

    @Override
    public boolean isAlived() {
        return false;
    }

    @Override
    public void getDataSuc(PlayHomeFeedData o) {
        LogTool.e("o:" + o);
        textView.setText(Html.fromHtml(String.valueOf(o)));
    }

    @Override
    public void getDataFail() {

    }

    private void testRxjava() {
        Observable.create(new ObservableOnSubscribe<Integer>() { // 第一步：初始化Observable
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                LogTool.e("Observable emit 1" + "\n");
                e.onNext(1);
                LogTool.e("Observable emit 2" + "\n");
                e.onNext(2);
                LogTool.e("Observable emit 3" + "\n");
                e.onNext(3);
                LogTool.e("Observable emit 4" + "\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() { // 第三步：订阅

            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
                LogTool.e("mDisposable: " + mDisposable);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogTool.e("onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                LogTool.e("onComplete" + "\n");
            }
        });
    }

    /**
     * rxjava2 分开步骤
     */
    private void test1() {
        Observable<Integer> objectObservable = Observable.create(new ObservableOnSubscribe<Integer>() {

            // ObservableEmitter： Emitter是发射器的意思，那就很好猜了，这个就是用来发出事件的，
            // 它可以发出三种类型的事件，通过调用emitter的onNext(T value)、onComplete()和
            // onError(Throwable error)就可以分别发出next事件、complete事件和error事件。
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                LogTool.e("subscribe：" + 1);
                e.onNext(2);
                LogTool.e("subscribe：" + 2);
                e.onNext(3);
                LogTool.e("subscribe：" + 3);
                e.onComplete();
                LogTool.e("subscribe：" + 4);
                e.onNext(4);
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogTool.e("Disposable:" + d);
            }

            @Override
            public void onNext(Integer integer) {
                LogTool.e("onNext:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                LogTool.e("onError");
            }

            @Override
            public void onComplete() {
                LogTool.e("onComplete");
            }
        };

        objectObservable.subscribe(observer);
    }
}
