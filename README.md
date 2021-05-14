# Precedence Graph Threading using Blockchains

*project name: thread-blockchain*

**Abstract**
This application implements a small "network" of threads, based on specific "priorities" given as input by the user.
It is a java console application, which given a file describing thread priorities and a file containing processing times for each thread, creates a simulation.

## Usage

```
$ java -jar thread-blockchain.jar help

usage: Please choose 'json' to execute for model.json or 'text' to choose 'p_precedence.txt' and 'p_timings.txt'

arguments:
  help,    show this help message and exit
  json,    use model.json as input
  text,    use 'p_precedence.txt' and 'p_timings.txt' as inputs
```

### Text file

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

#### Text file explanation

- The *p_precedence.txt* file describes that thread named P1 has no dependency on any thread, thus can start. The other threads in this example have dependencies on either one or two threads, and can be executed only after completion of their dependencies. Of course, the dependencies can be of any form and any number. You will notice that in many cases, threads will be executed in parallel, depending on the input of the files.
- The file *p_timings.txt* contains the processing time, in milliseconds, of each thread (how long it will run before it completes). Absence of time means immediate completion of thread execution.

### JSON file example

The same logic is applied in this JSON file. 

```json
[ {"name" :"P1",
   "processTime" : 2000},
  {"name" :"P2",
   "dependencies": ["P1"]},
  {"name" :"P3",
   "dependencies": ["P1"]},
  {"name" :"P4",
   "dependencies": ["P2"]},
  {"name" :"P5",
   "processTime" : 1234,
   "dependencies": ["P3"]},
  {"name" :"P6",
   "processTime" : 2345,
   "dependencies": ["P3","P5"]},
  {"name" :"P7",
   "dependencies": ["P4"]},
  {"name" :"P8",
   "dependencies": ["P4","P7"]},
  {"name" :"P9",
   "processTime" : 1111,
   "dependencies": ["P6","P8"]}
]
```

## Demo

![Thread-Modeling](https://user-images.githubusercontent.com/3985557/118312804-989a3a00-b4fa-11eb-99b0-73af1357e0fa.gif)
