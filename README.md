# Yandex Lavka

This is a REST API simplified model of Yandex Lavka based on entrance tests (year 2023) of Yandex Backend School. It allows to register couriers, create orders, assign couriers to orders and gather statistics about couriers 

## The content of the service

The service contains:

1) Basic REST API requests;
2) Courier statistics calculation;
3) Rate limiter;

## 1. REST API

For all requests, an `HTTP 200 OK` response is expected if the answer is correct.

### POST /couriers
The interface described below is designed for loading the couriers into the system.

The handler accepts as input a list in json format with data about couriers and their work schedule.

Couriers work only in predetermined regions, and also differ in type: foot, bicycle and car courier. The number of orders that the courier is able to transport depends on his type. 

| Courier type | Number of regions | Comment |
|---|---|---|
| FOOT | 1 | Delivery is only available in 1 region |
| BIKE | 2 | Delivery is only available in 2 regions |
| AUTO | 3 | Delivery is only available in 3 regions |


The regions are positive integer numbers. The work schedule is specified by a list of strings in the format `HH:MM-HH:MM`, where HH is hours (from 0 to 23) and MM is minutes (from 0 to 59). Examples: “09:00-11:00”, “12:00-23:00”, “00:00-23:59”.

### GET /couriers/{courier_id}

Returns information about the courier.

### GET /couriers

Returns information about all couriers.

The request has parameters `offset` and `limit` for pagination.

If:
* `offset` or `limit` are missing, then their values by default will be `offset = 0`, `limit = 1`;
* there are no couriers found with given `offset` and `limit`, then empty list `couriers` will return.




### GET /couriers/assignments

Returns information about all assignments.

The request has parameters:

* `date` - order assignment date. If not specified, then the current one will be used
* `courier_id` - courier identifier for getting a list of orders assigned to him. If not specified, data for all couriers will be returned

### POST /orders

It takes as input a list data about orders in json format. The order characteristics are weight, region, delivery time and cost. The delivery time is specified by a list of strings in the format `HH:MM-HH:MM`. 

### GET /orders/{order_id}

Returns information about the order by its identifier, as well as additional information: weight, delivery region, delivery time intervals at which it is convenient to take an order.

### GET /orders

Returns information about all orders.

The request has parameters `offset` and `limit` for pagination.

If:
* `offset` or `limit` are missing, then their values by default will be `offset = 0`, `limit = 1`;
* there are no couriers found with given `offset` and `limit`, then empty list `orders` will return.

### POST /orders/assign

It accepts an array of objects consisting of three fields: courier id, order id and order completion date. 

After that the corresponding orders and couriers will get assigned to each other. 

### POST /orders/complete

It accepts an array of objects consisting of three fields: courier id, order id and order completion time, after which it notes that the order has been completed.

If the order:
* is not found, is assigned to a different courier or is not assigned at all, then `HTTP 400 Bad Request` will return.





## 2. Courier statistics calculation

### GET /couriers/meta-info/{courier_id}

The request has parameters:
* `start_date` - statistics start date
* `end_date` - statistics end date

It is assumed that all orders and dates for calculations have the same fixed time zone - UTC. The request returns the earnings of a courier and his rating.

Earnings are calculated as the amount of payment for each completed delivery in the period from `start_date` (including) to `end_date` (excluding)

`sum = ∑(cost * C)`

`C` is a coefficient, which depends on a courier type:
* FOOT — 2
* BIKE — 3
* AUTO — 4

The rating is calculated as follows:
((number of all completed orders from `start_date` to `end_date`) / (number of hours between `start_date` and `end_date`)) * C

`C` is a coefficient, which depends on a courier type:
* FOOT - 1
* BIKE - 2
* AUTO - 3


## 3. Rate limiter

The service limits the load to 10 RPS per every endpoint. If the allowed number of requests is exceeded, the service will respond with a 429 code.


## Documentation

You can find a detailed documentation in an ‘openapi.json’ file. The SQL script for creating a database is located in scripts/create_db.sql.


## What is coming in the future

1) Launch with Docker
2) Test sections
3) (maybe) An algorithm which would assign couriers to orders depending on the characteristics of both such as working schedule, weight limit and etc.
