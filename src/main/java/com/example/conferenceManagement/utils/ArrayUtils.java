package com.example.conferenceManagement.utils;

public class ArrayUtils {
  private ArrayUtils() {
    // private constructor Prevent instantiation
  };

  public static boolean isNullOrEmpty(Object[] arr) {
    return arr == null || arr.length == 0;
  }
}
