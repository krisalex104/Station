#This project contains Rest End Points for creating  number of entry and exit happening regarding a Station on daily basis 
and also for getting details about all the station which is ,how many entry exit  happend between two dates.



#I followed two approach for doing this  and made their respective end points

  1) By taking single entity and maintaining entry and exit on a single column which is
     of boolean type and every time a new entry is inserted in table whenever  entry or exit happen.

  2) In second approach I maintain seperate column for entry and exit , so that every time an entry or exit happen on a station
     on a particular date we update their value of entry and exit in the table if data is already present in the
     database with the specified station and current date,if not then create a new entry in database regarding the
     particular station with current date , so we don't have to create a new entry everytime
     like in first approach which consumes so much space in database.
     
***********************************************************************************************************************************

#Following are the rest endpoints for First approach

This is for creating a entry record in the table :-

curl --request POST \
  --url http://localhost:9899/station/entry \
  --header 'Content-Type: application/json' \
  --data '{
	"stationName": "New Delhi"
}'

This is for creating a exit record in the table :-

curl --request POST \
  --url http://localhost:9899/station/exit \
  --header 'Content-Type: application/json' \
  --data '{
	"stationName": "New Delhi"
}'

This both are for fethcing all the entry exit of all the stations between two dates , date format is yyyy-MM-dd :-

curl --request GET \
  --url 'http://localhost:9899/station?fromDate=2023-07-20&toDate=2023-07-21'

curl --request GET \
  --url 'http://localhost:9899/station/details?fromDate=2023-07-20&toDate=2023-07-21'


**************************************************************************************************************************************
#Following are the rest endpoints for Second approach

This is for creating and updating  entry record in the table :-

curl --request POST \
  --url http://localhost:9899/stationdetails/entry \
  --header 'Content-Type: application/json' \
  --data '{
	"stationName": "New Delhi"
}'

This is for creating and updating exit record in the table :-

curl --request POST \
  --url http://localhost:9899/stationdetails/exit \
  --header 'Content-Type: application/json' \
  --data '{
	"stationName": "New Delhi"
}'

This is for fethcing all the entry exit of all the stations between two dates , date format is yyyy-MM-dd :-

curl --request GET \
  --url 'http://localhost:9899/stationdetails?fromDate=2023-07-20&toDate=2023-07-21'




  
        


