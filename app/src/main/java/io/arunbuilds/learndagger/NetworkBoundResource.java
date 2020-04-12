package io.arunbuilds.learndagger;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private Observable<Resource<ResultType>> result;

    @MainThread
    protected NetworkBoundResource() {
        Observable<Resource<ResultType>> source;
        if (shouldFetch()) {
            source = createCall()
                    .subscribeOn(Schedulers.io())
                    .doOnNext(apiResponse -> saveCallResult(processResponse(apiResponse)))
                    .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
                    .doOnError(t -> onFetchFailed())
                    .onErrorResumeNext(t -> {
                        return loadFromDb().toObservable().map(data -> Resource.error(data, t.getMessage()));
                    })
                    .observeOn(AndroidSchedulers.mainThread());

        } else {
            source = loadFromDb()
                    .toObservable()
                    .map(Resource::success);
        }

        result = Observable.concat(
                loadFromDb()
                        .toObservable()
                        .map(Resource::loading)
                        .take(1),
                source
        );
    }

    public Observable<Resource<ResultType>> getAsObservable() {
        return result;
    }

    protected void onFetchFailed() {
    }

    protected abstract Flowable<ResultType> loadFromDb();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @WorkerThread
    protected RequestType processResponse(Resource<RequestType> response) {
        return response.data;
    }

    @NonNull
    @MainThread
    protected abstract Observable<Resource<RequestType>> createCall();

    @MainThread
    protected abstract boolean shouldFetch();
}
