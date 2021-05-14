# Precedence Graph Threading using Blockchains

*project name: thread-blockchain*

**Abstract**
This application implements a small "network" of threads, based on specific "priorities" given as input by the user.
It is a java console application, which given a file describing thread priorities and a file containing processing times for each thread, creates a simulation.

## Usage

```shell
java -jar thread-blockchain.jar help

usage: Please choose 'json' to execute for model.json or 'text' to choose 'p_precedence.txt' and 'p_timings.txt'

arguments:
  help,    show this help message and exit
  json,    use model.json as input
  text,    use 'p_precedence.txt' and 'p_timings.txt'
```

**text File Example**

A priority file (p_precedence.txt) for 9 threads could be be the following:

```csv
P1
P2 waitfor P1
P3 waitfor P1
P4 waitfor P2
P5 waitfor P3
P6 waitfor P3,P5
P7 waitfor P4
P8 waitfor P4,P7
P9 waitfor P6,P8
```

While the corresponding processing times file (p_timings.txt) for the above 9 Threads could be the following:

```csv
P1 2000
P2
P3
P4
P5 1234
P6 2345
P7
P8
P9 1111
```

Επεξηγήσεις αρχείων:

- Το αρχείο *p_precedence.txt* περιγράφει ότι το νήμα Ρ1 δεν έχει εξάρτηση από κάποιο άλλο νήμα και μπορεί να εκτελεστεί. Τα υπόλοιπα νήματα στο συγκεκριμένο παράδειγμα έχουν εξαρτήσεις είτε από 1, είτε από 2 άλλα νήματα και μπορούν να εκτελεστούν μετά την ολοκλήρωση των νημάτων από τα οποία εξαρτώνται. Φυσικά, οι εξαρτήσεις μπορούν να είναι οποιασδήποτε μορφής και οποιουδήποτε πλήθους. Θα παρατηρήσετε βέβαια ότι σε πολλές περιπτώσεις, νήματα θα εκτελούνται και παράλληλα, ανάλογα με το input των αρχείων.
- Το αρχείο *p_timings.txt* περιέχει το χρόνο επεξεργασίας σε milliseconds, του κάθε
νήματος (πόσο χρόνο θα εκτελείται πριν ολοκληρωθεί). Απουσία χρόνου σημαίνει
άμεση ολοκλήρωση της εκτέλεσης του νήματος.

## Demo

![Thread-Modeling](https://user-images.githubusercontent.com/3985557/118312804-989a3a00-b4fa-11eb-99b0-73af1357e0fa.gif)
