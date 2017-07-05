# InsightDataEngChallengeZacharyDeStefano

This is my submission for the Insight Data Engineering Challenge

The file run.sh successfully parses through the two log files in log_input directory
	and then creates a flagged purchases file in the log_output directory
	
The file run_myUnitTests.sh runs the informal unit tests I wrote as I developed 
	the code. It does not run JUnit. With more time I would have run formal JUnit tests
	but for this challenge I just manually checked the output. 


	
Here is a brief technical overview of the data structures I used. More detail is in the comments of the Java code:

	NOTE: The 50 MB batch log file took a max of 5 seconds to process with my code
	      Its associated 100 KB stream log file took a max of 2 seconds to process
	

	Important functionality around transaction information was implemented with Java's TreeMap structure
	I chose it due to time and space efficiency with search, insert, delete, and iterating

	For each User I stored the following:
	- Mean, Std of Transaction Amounts in Network
	- Set of Pointers to their friends
	- TreeMap of most recent T Transactions in Network
	- TreeMap of their most recent T Transactions
	
	NOTE: I do NOT store each user's social network. That proved to be too prohibitive on memory space when 
		you have millions of users. Instead I recalculate a user's social network on the fly when necessary. 
	
	For all the users, I stored the following:
	- TreeMap of all recent Transactions
		- Size of this set was (N+1)*T with N being number of users, T being number of transactions
		- That size is an upper bound on the amount necessary to build most recent transactions for each user
	
	(A)To calculate the recent transactions for a single user, I did the following:
		(1)Perform a Breadth-First Search to obtain a set that is their social network
		(2)Iterate through most recent transactions 
			-If transaction is associated with one of the users in (1), add it to the list
			-Continue iterating until T transactions have been added
		(3)Calculate mean,std if we in streaming mode
	
	(B)If a transaction is added to a map, I do the following:
		- Insert it into the map
		- Check if size of map is now greater than T
		- If so, then remove the oldest transaction
		- Recalculate the mean, std if we are streaming
	
	When a purchase happens, I do the following:
	- add it to users Transaction Map and the global Transaction Map, following (B)
	- calculate the user's social network and for each user there, add the transaction to their network map, following (B)
	
	When a befriend/unfriend happens, I do the following:
	- add/delete pointers from the list of friends for each user depending on the event
	- calculate both user's social network and for each user found as well as the users themselves, repeat (A)
	
	