package com.itheima.pojo;


//统一响应结果

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }
//    这段代码是一个泛型方法的声明，其中 <E> 表示这是一个泛型方法，E 是一个类型参数。
//    Result<E> 表示返回类型是 Result 类，该类使用了类型参数 E。Result 类通常用于表示操作的结果，可能包含成功或失败的信息以及相关的数据。
//    success(E data) 方法表示这是一个成功的操作，它接受一个类型为 E 的参数 data，并返回一个 Result<E> 对象，其中包含成功操作的数据。
//    例如，假设 E 被指定为 Integer，那么调用 success(10) 将返回一个 Result<Integer> 类型的对象，表示操作成功并且携带整数数据 10。
//    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
