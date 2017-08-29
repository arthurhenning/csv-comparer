# CSV file comparer
## Written in Kotlin + Maven
### Input
1. The path to the first .csv file
2. The path to the second .csv file
* First line has to contain the header (column names), separated with comma
* The next lines should be the records
3. The unique identifier of the rows (header name, case sensitive)
* This will be used to match the records from each document (example: "Username")
### Logic
* If header size is different, stop
* If column names are different, stop
* If records number (row count) is different, stop
* Else, start the comparison
    * If the unique identifier is matched for two rows in the two documents, compare all of their columns
    * If all of the columns match, we have a total match
    * Else, we have a partial match
### Output
* Any exceptions found - command line
* Any partials matches found - output.txt file
* Number of rows parsed - command line
* Number of rows matched - command line
* Number of rows partially matched - output.txt file 
* Duration in milliseconds - command line

