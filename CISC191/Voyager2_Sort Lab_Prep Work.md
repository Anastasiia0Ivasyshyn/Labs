# Voyager 2 Sort Lab Prep Work

## asmSwap Function Design

### Pseudocode

**Inputs:**

- r0 : Address of first value (v1)
- r1 : Signed flag (1 = signed, 0 = unsigned)
- r2 : Element size (1, 2, or 4 bytes)

**Output:**

- r0 : -1 if either value is 0 (end of array), 0 if no swap, 1 if swapped

**Steps:**

1. Load v1 and v2 based on element size and signed flag.
2. If v1 == 0 or v2 == 0, return -1.
3. Compare v1 and v2:
   - If signed == 1, use signed compare.
   - Otherwise, use unsigned compare.
4. If v1 > v2:
   - Swap them using STRB/STRH/STR as appropriate.
   - Return 1.
5. If no swap, return 0.

### Flowchart (Text Version)

```
[Start]
  |
  v
Load v1 and v2 (using correct LDR variant)
  |
  v
Is v1 == 0 OR v2 == 0?
  |       \
 yes        no
  |          \
  v           v
return -1   Compare v1 ? v2
                |
   +------------+------------+
   |                         |
 v1 <= v2                 v1 > v2
   |                         |
   v                         v
return 0                Swap values
                        (use STRB/STRH/STR)
                           |
                           v
                        return 1
```

## asmSort Function Design

### Pseudocode

**Inputs:**

- r0 : Start address of array
- r1 : Signed flag (1 = signed, 0 = unsigned)
- r2 : Element size (1, 2, or 4 bytes)

**Output:**

- r0 : Total swaps performed

**Steps:**

1. Set totalSwaps = 0.
2. Outer loop:
   - Set madeSwap = 0.
   - Set p = start address.
   - Inner loop:
     - Call asmSwap(p, signed, elementSize).
     - If return == -1, break (end of array).
     - If return == 1, increment both madeSwap and totalSwaps.
     - Advance p by 4 (move to next pair).
   - If madeSwap == 0, exit outer loop.
3. Return totalSwaps.

### Flowchart (Text Version)

```
[Start]
  |
  v
totalSwaps = 0
  |
  v
Repeat Pass:
  madeSwap = 0
  p = startAddr
  |
  v
+------------------- Inner Loop --------------------+
| Call asmSwap(p, signed, elementSize)              |
| r0 == -1? -> break inner loop                     |
| r0 == 1?  -> madeSwap++, totalSwaps++             |
| p = p + 4 (move to next pair)                     |
| Loop back                                       <-+
+---------------------------------------------------+
  |
  v
madeSwap == 0 ?
  |         \
 yes         no
  |           \
  v            v
return total  Repeat pass
```

## Notes for Professors

- Used LDRB/LDRSB/LDRH/LDRSH/LDR and matching STRB/STRH/STR to avoid masking.
- Array traversal uses indexed addressing to step through pairs.
- End of array detected when either value equals zero.
- asmSwap handles signed vs unsigned comparison correctly.
- asmSort repeats passes until no swaps are made (bubble sort logic).
- Both functions are designed for in-place sorting without modifying unused bytes.

