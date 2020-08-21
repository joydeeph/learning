import java.util.Scanner;

public class Solution {
  /*
   * Complete the function below.
   */

  /* Write your custom functions here */
  static void mergeArray(int[] a, int[] b, int M) {

    int lo = 0;
    int mid = M;
    int hi = 2 * M - 1;
    int[] aux = new int[2 * M];
    for (int i = 0; i < M; i++) {
      aux[i] = b[i];
      aux[i + M] = a[i];
      b[i + M] = a[i];
    }
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid)
        b[k] = aux[j++];
      else if (j > hi)
        b[k] = aux[i++];
      else if (aux[j] < aux[i])
        b[k] = aux[j++];
      else
        b[k] = aux[i++];
    }

  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int _a_cnt = 0;
    int[] _a = new int[100001];
    int[] _b = new int[200002];

    try {
      _a_cnt = sc.nextInt();
    } catch (Exception e) {
      System.out.println("Here: " + e.getMessage());
    }

    for (int i = 0; i < _a_cnt; i++) {
      _a[i] = sc.nextInt();
    }
    for (int i = 0; i < _a_cnt; i++) {
      _b[i] = sc.nextInt();
    }
    mergeArray(_a, _b, _a_cnt);
    for (int i = 0; i < 2 * _a_cnt; i++) {
      System.out.print(_b[i] + " ");
    }
  }
}