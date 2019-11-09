### Spring Cloud Sleuth + Zipkin

Spring Cloud Sleuth is needed to trace the logs in your application.
This is the "add-on" for Spring Cloud and belongs to the Spring Cloud project family.
This is a very powerful logging tool, but it seems very simple.

In large applications (especially if it is a microservice architecture) without logging anywhere. This is an obvious fact.
Competent logs are often our best friends - because with their help, you can quickly deal with the problem and find ways to solve it.
But the logs are very large and often it is simply not clear where and what to look for. You can even add some unique id to the method in the request so that it can then be easily identified.

Spring Cloud Sleuth makes it easy to identify logs related to a particular job, request, or thread.
It integrates seamlessly with Logback, SLF4J, etc.

The essence of his work: he adds some kind of unique id with which you can easily diagnose using log logs.
It can also forward traces to services, such as Zipkin, for storing and analyzing logs.
Spring Cloud Sleuth uses Brave as an internal trace library.
Brave adds unique IDs to every web request that comes into our application.
This unique ID applies to the trace or to the trace range -> you can use a combination of trace ID and range ID, you can pinpoint the location of the problem.

You can read more about Brave here:
https://github.com/openzipkin/brave

___

Run and go through url 

    localhost:8080/start

In the console we see

    2019-11-04 23:09:43.684  INFO [test-service,8aa83093370c12f8,8aa83093370c12f8,false] 32712 --- [nio-8080-exec-1] TestController                           : Write some log

This is the basic information added by Sleuth in the format

    [application name, traceId, spanId, export]

Where
application name is the application name specified in application.yml
traceId - ID assigned to each request.
spanId - used to track work. Each request can have several steps, each step has its own unique spanId.
export is a flag that indicates whether to export a specific log to a log aggregation tool such as Zipkin.

Important!
The real problem here is not to identify the logs within the same microservice, but to track the chain of requests between several microservices.
The traceId parameter is exactly what will allow you to track the request when it moves from one service to another.

This will work similarly if one Sleuth-enabled application calls another, passing traceId and spanId in the headers.

If you are using Feign from Spring Cloud Netflix, trace information will also be added to these requests. In addition, Zuul from Spring Cloud Netflix will also forward proxy headers to other services. It is very convenient!


For example, you use ELK to collect and analyze logs from your microservices. Using Sleuth, you can easily search all collected logs using traceId and see how the request is transferred from one microservice to the next.

___

#### Zipkin

If you suddenly want to see time information, you can use Zipkin.

You can install Zipkin as follows:

1) Run using docker

    
    docker run -d -p 9411:9411 openzipkin/zipkin
    
2) Download from the site (you need a minimum of java 8 or higher)

    
    curl -sSL https://zipkin.io/quickstart.sh | bash -s
    java -jar zipkin.jar
    

#### Start

Launch your application, go to the url where your logs are configured.
You should see the logs in the console and notice that the export parameter (4th in a row) will now be true instead of false. This means that there is an export to Zipkin.

Now go to Zipkin (localhost: 9411) - you need to set a specific date range and click on Find Taces.
You will see the trace information for your url. Click on it and you will see all the details collected from Sleuth, including information about the time of the request.

___

Sleuth is a very powerful tool!
You can familiarize yourself with all its features in more detail by reading the official documentation:
https://cloud.spring.io/spring-cloud-sleuth/reference/html/#sleuth-adding-project
