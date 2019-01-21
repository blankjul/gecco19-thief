GECCO 2019 Competition - Bi-objective Traveling Thief Problem
============================================================

Please have a look at the competition outline `Here 
<https://www.egr.msu.edu/coinlab/blankjul/gecco19-thief/>`_.

Requirements
------------------------------------------------------------
- Java 8
- (Maven)

Installation
------------------------------------------------------------

First, the repository needs to be cloned from GitHub:

.. code-block:: bash

    git clone https://github.com/julesy89/gecco19-thief

Feel free to use the IDE of your choice and import the Maven Project.


Structure
------------------------------------------------------------

In the following the project structure is explained:

::

    gecco19-thief
    ├── Runner.java: Execute an algorithm on all competition instance and to save the file in the derired format.
    ├── Competition.java: Contains the instance names to be solved and the maximum limit of solutions to submit.
    ├── model
        ├── TravelingThiefProblem.java: The problem object used to evaluate the problem for a given tour and packing plan.
        ├── Solution.java: Object to store the results of the evaluate function.
        └── Solution.java: NonDominatedSet.java: Example implementation of a non-dominated set. Can be done faster/better.
    ├── algorithms
        ├── Algorithm: Interface for the algorithm to be implemented from.
        ├── ExhaustiveSearch: Solves the problem exhaustively which means iterating over all possible tours and packing plans.
        └── RandomLocalSearch: Example algorithm to randomly fix a tour and then iterate over possible packing plans.



Getting Started
------------------------------------------------------------

Please have a look at our implementations of ExhaustiveSearch and RandomLocalSearch. The project should provide a starting point to get familiar with the problem and prototype quickly new ideas.
However, for this competition you have the freedom to use whatever you need, e.g. speed up the evaluation function, reimplement the problem in C, ...

Again, the competition details can be found `Here
<https://www.egr.msu.edu/coinlab/blankjul/gecco19-thief/>`_.

