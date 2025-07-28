
package dev.arun;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidator {


  /**
   * Evaluates a mathematical expression string based on custom precedence rules
   * (Brackets > Add/Subtract > Bitshifts).
   *
   * @param expression The expression to evaluate.
   * @return The integer result of the calculation.
   * @throws IllegalArgumentException for syntax errors like mismatched parentheses or invalid operators.
   */
  public static int evaluate(String expression) {
    // Stacks for numbers (values) and operators.
    Stack<Integer> values = new Stack<>();
    Stack<String> ops = new Stack<>();

    // Use a regex to tokenize the expression into numbers, operators, and parentheses.
    Pattern tokenPattern = Pattern.compile("(\\d+|<<|>>|[+\\-()])");
    Matcher matcher = tokenPattern.matcher(expression.replaceAll("\\s+", ""));

    while (matcher.find()) {
      String token = matcher.group(1);

      if (token.matches("\\d+")) { // Token is a number
        values.push(Integer.parseInt(token));
      } else if (token.equals("(")) { // Token is an opening brace
        ops.push(token);
      } else if (token.equals(")")) { // Token is a closing brace
        // Evaluate the entire expression inside the parenthesis.
        while (!ops.peek().equals("(")) {
          values.push(applyOp(ops.pop(), values.pop(), values.pop()));
          if (ops.isEmpty()) {
            throw new IllegalArgumentException("Mismatched parentheses.");
          }
        }
        ops.pop(); // Pop the opening brace.
      } else { // Token is an operator
        // While top of 'ops' has same or greater precedence to current token,
        // apply operator on top of 'ops' to top two elements in values stack.
        while (!ops.empty() && hasPrecedence(token, ops.peek())) {
          values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }
        // Push current token to 'ops'.
        ops.push(token);
      }
    }

    // Entire expression has been parsed, apply remaining ops to remaining values.
    while (!ops.empty()) {
      values.push(applyOp(ops.pop(), values.pop(), values.pop()));
    }

    // Top of 'values' contains result, return it.
    if (values.size() != 1) {
      throw new IllegalArgumentException("Invalid expression format.");
    }
    return values.pop();
  }

  /**
   * Checks precedence of operators.
   * Rule: Add/Subtract have higher precedence than Bitshifts.
   *
   * @param op1 The first operator.
   * @param op2 The second operator (from the stack).
   * @return true if op2 has higher or equal precedence than op1.
   */
  private static boolean hasPrecedence(String op1, String op2) {
    if (op2.equals("(") || op2.equals(")")) {
      return false;
    }
    int prec1 = getPrecedence(op1);
    int prec2 = getPrecedence(op2);
    return prec2 >= prec1;
  }

  /**
   * Assigns a precedence level to an operator.
   * Higher number means higher precedence.
   */
  private static int getPrecedence(String op) {
    if (op.equals("+") || op.equals("-")) {
      return 2;
    }
    if (op.equals("<<") || op.equals(">>")) {
      return 1;
    }
    return 0; // For parentheses or other characters
  }

  /**
   * Applies an operator to two operands.
   * Note: The operands are popped from the stack in reverse order (b, then a for a op b).
   *
   * @param op The operator string.
   * @param b  The second operand.
   * @param a  The first operand.
   * @return The result of the operation.
   */
  private static int applyOp(String op, int b, int a) {
    switch (op) {
      case "+":
        return a + b;
      case "-":
        return a - b;
      case "<<":
        return a << b;
      case ">>":
        return a >> b;
    }
    throw new IllegalArgumentException("Invalid operator: " + op);
  }
}
