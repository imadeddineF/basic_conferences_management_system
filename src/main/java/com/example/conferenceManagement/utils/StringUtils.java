package com.example.conferenceManagement.utils;

public class StringUtils {
  private StringUtils() {
    // private constructor Prevent instantiation
  }

  public static boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public static String capitalizeFirstLetter(String str) {
    if (isNullOrEmpty(str)) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
}
