
package dev.arun;

import java.util.ArrayList;
import java.util.List;

public class CountdownNumberSolver {

  /**
   * A simple class to hold an expression string and its calculated value.
   */
  static class Expression {
    String str;
    int value;

    Expression(String str, int value) {
      this.str = str;
      this.value = value;
    }

    @Override
    public String toString() {
      return str + " = " + value;
    }
  }


  /**
   * Public method to initiate the search for a solution.
   *
   * @param target  The target number to reach.
   * @param numbers The list of available numbers.
   * @return A string representing the solution expression, or null if none is found.
   */
  public static String findSolution(int target, List<Integer> numbers) {
    List<Expression> expressions = generateExpressions(numbers);
    for (Expression expr : expressions) {
      if (expr.value == target) {
        // Check if a direct number is the solution
        return expr.str;
      }
    }
    return solve(target, numbers);
  }

  /**
   * The main recursive solver function. It tries to find an expression that
   * equals the target by recursively breaking down the problem.
   *
   * @param target  The current target value for this recursive step.
   * @param numbers The list of numbers currently available.
   * @return The expression string if a solution is found, otherwise null.
   */
  private static String solve(int target, List<Integer> numbers) {
    // Base case: If there's only one number left, check if it equals the target.
    if (numbers.size() == 1) {
      return numbers.get(0) == target ? String.valueOf(numbers.get(0)) : null;
    }

    // Generate all possible partitions of the numbers list into two sublists.
    int n = numbers.size();
    for (int i = 1; i < (1 << n) / 2; i++) {
      List<Integer> sublist1 = new ArrayList<>();
      List<Integer> sublist2 = new ArrayList<>();

      for (int j = 0; j < n; j++) {
        if ((i & (1 << j)) != 0) {
          sublist1.add(numbers.get(j));
        } else {
          sublist2.add(numbers.get(j));
        }
      }

      // Generate all possible expressions from the two sublists
      List<Expression> exprs1 = generateExpressions(sublist1);
      List<Expression> exprs2 = generateExpressions(sublist2);

      for (Expression e1 : exprs1) {
        for (Expression e2 : exprs2) {
          // --- Bit Shift Operations (Lowest Precedence) ---
          // a << b
          if (e1.value << e2.value == target) {
            return String.format("(%s << %s)", e1.str, e2.str);
          }
          // b << a
          if (e2.value << e1.value == target) {
            return String.format("(%s << %s)", e2.str, e1.str);
          }
          // a >> b
          if (e1.value >> e2.value == target) {
            return String.format("(%s >> %s)", e1.str, e2.str);
          }
          // b >> a
          if (e2.value >> e1.value == target) {
            return String.format("(%s >> %s)", e2.str, e1.str);
          }

          // --- Addition and Subtraction (Higher Precedence) ---
          // a + b
          if (e1.value + e2.value == target) {
            return String.format("(%s + %s)", e1.str, e2.str);
          }
          // a - b
          if (e1.value - e2.value == target) {
            return String.format("(%s - %s)", e1.str, e2.str);
          }
          // b - a
          if (e2.value - e1.value == target) {
            return String.format("(%s - %s)", e2.str, e1.str);
          }
        }
      }
    }
    return null; // No solution found for this path
  }

  /**
   * Generates all possible expressions that can be formed from a given list of numbers.
   * This is a recursive function that builds up expressions from smaller sub-expressions.
   *
   * @param numbers The list of numbers to use.
   * @return A list of all possible Expression objects.
   */
  private static List<Expression> generateExpressions(List<Integer> numbers) {
    List<Expression> result = new ArrayList<>();
    if (numbers.isEmpty()) {
      return result;
    }
    // Base case: a single number is an expression itself.
    if (numbers.size() == 1) {
      int num = numbers.get(0);
      result.add(new Expression(String.valueOf(num), num));
      return result;
    }

    // Add the expression representing the numbers as they are (e.g., just the number itself)
    for (int num : numbers) {
      result.add(new Expression(String.valueOf(num), num));
    }

    // Recursive step: Partition the list and combine expressions.
    int n = numbers.size();
    for (int i = 1; i < (1 << n) / 2; i++) {
      List<Integer> sublist1 = new ArrayList<>();
      List<Integer> sublist2 = new ArrayList<>();

      for (int j = 0; j < n; j++) {
        if ((i & (1 << j)) != 0) {
          sublist1.add(numbers.get(j));
        } else {
          sublist2.add(numbers.get(j));
        }
      }

      // Recursively generate expressions for the sublists
      List<Expression> exprs1 = generateExpressions(sublist1);
      List<Expression> exprs2 = generateExpressions(sublist2);

      for (Expression e1 : exprs1) {
        for (Expression e2 : exprs2) {
          // Combine expressions using all permitted operators
          // Addition
          result.add(
              new Expression(String.format("(%s + %s)", e1.str, e2.str), e1.value + e2.value));
          // Subtraction
          result.add(
              new Expression(String.format("(%s - %s)", e1.str, e2.str), e1.value - e2.value));
          result.add(
              new Expression(String.format("(%s - %s)", e2.str, e1.str), e2.value - e1.value));
          // Bitshift Left
          result.add(
              new Expression(String.format("(%s << %s)", e1.str, e2.str), e1.value << e2.value));
          result.add(
              new Expression(String.format("(%s << %s)", e2.str, e1.str), e2.value << e1.value));
          // Bitshift Right
          result.add(
              new Expression(String.format("(%s >> %s)", e1.str, e2.str), e1.value >> e2.value));
          result.add(
              new Expression(String.format("(%s >> %s)", e2.str, e1.str), e2.value >> e1.value));
        }
      }
    }
    return result;
  }
}
