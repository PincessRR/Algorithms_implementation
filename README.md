# Algorithms_implementation

This is a set of classic algorithms including shortest path(under difference conditions), clustering, knapsack, 2SAT(2 satisfiability) and such. It covers the greedy paradigm, dynamic paradigm and such.

********CLUSTERING********
Clustering is a task to group a bunch of objects in a way such that samples among the same group is more similar than from different groups. It is a methodology widely explored in machine learning, pattern recognition, statistical analysis, data mining, information retrival, etc. There have been several popular algorithm models in terms of clustering, i.e. KNN, Kmeans, hierachical clustering. 

In this package, the clustering is constructed in such a way that the shortest distance among any pair of samples from different clusters is the longest. The implementation is based on Krustal's MST (minimum spinning tree), a UNION-FIND data structure is constructed to gaurantee the time complexity.

********2SAT********
2SAT, also known as 2-satisfiability, is a problem of determining whether a bunch of boolean variables can statisfy all the provided conditions. It is a mathematical model widely applied as resolution for optimization with constraints. A few dynamic programs are designed to solve this question, though it is not a NP problem, it can be time-consuming.

In this package, the 2SAT problem is implemented via SCC(strongly connected component). With this example, it is demonstrated that some problems can be reduced to another problem. The time complexity is gauranteed.

********KNAPSACK********
knapsack is a classic model in combinatorial optimaztion. A general knapsack without any restraints on values and weights is a NP problem. In this package, we are concerning with the model where items values are positive, weights are positive intergers. It is not a NP problem, and can be solved in O(MN) using a dynamic paradigm. The same idea have also been applied to solve WIS (weighted independent sets), sequence alignment, optimal binary search tree, etc. 

********PRIM'S MST********
Prim's MST is designed to solve the shortest path in weighted directed graph.


