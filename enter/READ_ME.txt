enter - 
	Is a Web based application with REST API services which have to retrive some travel statistics.
Application has no gui, and made in Spring boot framework. I used java 8, with JPA and hibernate. Database is MySql.
Also, application have WebSecurity authetification,and most of endpoints are covered and about that I will describe later.

Apps make db named travel_db and tables in db. 

Because, apps have no client side, I hardcoded some data into DB and here is a query that is needed for good apps behave.

1) insert into user values(null,true,'admin','admin');
2) insert into role values(null,'ADMIN');
3) insert into user_role values(1,1);

End_points:

URL: "/upload"
Entry point into application, after starting is to upload CSV file. This endpoint IS NOT covered with auth

URL:"/api/travelers"
This endpoint read table travelers and retuns JSON with all records, and needed authentification

URL:"/api/save"
This service is for saving new Traveler object and its need body param in json format, authentification is on

URL:"/api/edit"
Service for editing object from db, authentification is on

URL:"/api/remove"
Endpoint for delete Traveler object from db, authentification is on

URL:"/api/travelers/sort/{sort}/{column}" 
This service returns all records but sort like ASC or DESC for specific column. Need 2 pathVariable like "/ASC/country". Authentification is on

URL:"/api/travelers/filter/{country}/{fromDate}/{toDate}/{direction}"
This endpoint returns filtered search by country, direction, fromdate and todate. Authentification is on

URL:"/api/travelers/sum/{offset}/{limit}"
This endpoint returns amounth of travels with pagination. Autethification is on


All functionality are covered with console logging and I use slf4j.Logger for that.

Aplication is 50% covered with unit tests.

Docker:


developer:
Jovan Bizic
jbizic@yahoo.com
+381653808833