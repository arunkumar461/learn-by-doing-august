# Countdown Number Game

## How to Play

This game is inspired by Countdown's number round. The objective is to use a combination of the 6 numbers shown to reach the target number at the top.

**Key Differences from TV Countdown:**
- Every puzzle is solvable.
- The permitted operators are different.

## Permitted Operators
- **Addition:** `3 + 2 = 5`
- **Subtraction:** `3 - 2 = 1`
- **Bitshift Left:** `3 << 2 = 12`
- **Bitshift Right:** `9 >> 1 = 4`
- **Parentheses:** `()` to clarify order of operations
- Spaces, newlines, and tabs can be used for readability.

**Rules:**
- Each number may be used once. You do not have to use all numbers.

## Operator Precedence (Modified BIDMAS/BODMAS)
1. Brackets (highest precedence)
2. Addition and Subtraction
3. Bitshifts (lowest precedence)
- Operators of equal precedence are parsed left to right.

## How to Submit Your Solution
Enter your solution in the textbox above and click the **Check solution** button to see if you are correct.

### Example
If your target is `437` and your numbers are `75, 100, 5, 2, 2, 4`, a valid solution is:

```
5 + ((100 - 75 + 2) << 4) = 437
```
