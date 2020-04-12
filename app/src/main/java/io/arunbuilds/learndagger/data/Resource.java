package io.arunbuilds.learndagger.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static io.arunbuilds.learndagger.data.Status.ERROR;
import static io.arunbuilds.learndagger.data.Status.LOADING;
import static io.arunbuilds.learndagger.data.Status.SUCCESS;

public class Resource<T> {
    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }


    public static <T> Resource<T> error(@Nullable T data, @Nullable String msg) {
        return new Resource<>(ERROR, data, msg);
    }


    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }


    boolean isSuccess() {
        return status == SUCCESS && data != null;
    }

    public boolean isLoading() {
        return status == LOADING;
    }

    public boolean isLoaded() {
        return status != LOADING;
    }


}
