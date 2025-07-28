package dev.arun;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CountdownNumberSolverTest {

  @Test
  void testProvidedExample() {
    // This test already uses 6 numbers.
    int target = 437;
    List<Integer> numbers = new ArrayList<>(Arrays.asList(75, 100, 5, 2, 2, 4));
    String solution = CountdownNumberSolver.findSolution(target, numbers);

    int evaluate = ExpressionValidator.evaluate(solution);

    assertEquals(target, evaluate, "evaluated expression should be equal to target");
  }
}