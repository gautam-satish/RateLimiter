# Rate Limiter App
A rate limiter is a tool that monitors the number of requests per window time a service agrees to allow. If the request count exceeds the number agreed by the
service owner and the user (in a decided window time), the rate limiter blocks all the excess calls (say by throwing exceptions). This is a Maven Project with automated Junit tests that run during the build process.
## Table of contents
* [Project Design](#project-design)
* [Rate Limiter Algorithm](#rate-limiter-algorithm)
* [Scenarios](#scenarios)
* [Dependencies](#dependencies)
* [Property File](#property-file)
* [Technologies](#technologies)
* [Setup](#setup)

## Project Design
![image](https://user-images.githubusercontent.com/128305722/226242828-ee312e23-7ff7-4386-bd23-dbb7c62acc07.png)

## Rate Limiter Algorithm
The 'validateUserRequest' method in the 'RateLimiterService' class takes in two arguments: 'userId' and the 'method' which is being called by the user. To retrieve the 'User' object, we use the 'userId' attribute to fetch it from the 'HashTable<userId, userObj>', where all User objects are stored. Similarly, the specific 'Api' object for the same user is retrieved from the 'apiData' attribute, which is a 'HashTable<methodName, apiObj>' containing all 'Api' objects for this user.

The 'Api' object has several attributes such as 'availableCalls', 'callsMade', 'startTime', and 'endTime'. The first condition checks if the number of requests has exceeded the available number of calls. If it has, an error message is set indicating that the number of requests has been exceeded. The second condition checks if the request is within the specified time window. If it is not, an error message is set indicating that the request cannot be made at this time. If both conditions are true, the status is set to true, and the number of requests made for the specific API is increased by one. The number of calls made is set to zero since the next window is assumed to be scheduled after 23 hours.

If either of the above conditions fails, a custom exception (LimitException) is thrown with the appropriate error message. If the user is not present in the system, a custom exception (UserNotFoundException) is thrown.

## Scenarios
The mentioned scenarios are implemented in the main method of the Main class. 
* **Scenario 1** - 
Initially, the user attempts to make API calls outside of the allowed time window, but gains access after a few seconds. However, once the user exhausts their available requests for the specific API, their access is subsequently blocked.

* **Scenario 2** - 
The allowed time window gets expired when user is making requests even though the user hasn't exhausted their available number of requests for a specific API.

* **Scenario 3** - 
The user has exhausted the number of requests for a specific API during a valid time window.

* **Scenario 4** - 
The user makes requests during an invalid time window.

* **Scenario 5** - 
The user does not exist.


## Dependencies
```
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

## Property File
An **"application.properties"** file is created in the **"src/main/resources"** directory to set default values such as the allowed number of requests, start time and end time.
```
default.maxCalls=7
default.startTime= 10:00
default.endTime=11:00

```

## Technologies
Project is created with:
* JavaSE-1.8
* Eclipse IDE-2022-03 (4.23.0)
* Maven-3.8.4 (Eclipse embedded)
	
## Setup
Import the project as a Maven Project in your IDE. Execute the main method in the 'Main' class which runs all five scenarios mentioned above. 'UserGenerator' class creates 'User' and 'Api' objects using the 'createUsers' method, which sets the api attributes such as the maximum number of calls, start time, and end time for a specific api for a particular user. If the api attributes for the user are not set, the default values are used which are mentioned in application.properties file.

