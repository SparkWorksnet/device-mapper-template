# Spark Works Device API Mapper Template

The API Mapper acts as a translation proxy for data acquisition
and it is responsible for polling the devices infrastructure
through proprietary APIs and translating the received measurements
in a ready to process form for the platform. In general,
the API Mapper transforms data to and from the proposed API.
The data input type can be, based on each device capabilities, poll
based and/or push-based. In more details, the API Mapper is capable
to receive data from the IoT devices, but also to send messages/
commands to the devices. Furthermore, according to the
system design, the API Mappers introduce scalability and modularity
in the platform. Our solution offers two separate types of
API Mappers for integrating with external services and to retrieve
IoT sensor data:

  1. Polling API Mapper, and
  2. Message queue API Mapper 

Both solutions will be used in order to integrate with data
originating from IoT installation.
The first solution (Polling API Mapper) is based on polling. A
usage example is the following: weather stations are installed in
a subset of school buildings. Data produced by the
such stations are accessible through an external REST API. 
In order to integrate them in our platform,
the appropriate Polling API Mapper should be implemented. The external 
API is polled every a fixed number of minutes for updated data. 
When new data are found, they are formatted to the internal format of the proposed
platform and forwarded to the Processing/Analytics engine for
processing and analysis using the AMQP protocol. The data are
then processed and can be accessed from the proposed Data API.
Similar implementations based on the Polling API Mapper should be
used to integrate IoT devices provided by third parties and the
existing BMSs installed in school buildings.
The second solution is used when a pub/sub solution exists in
the external service that is going to be integrated. In that case, the
external service is capable of publishing the IoT data (generated
or gathered) to an MQTT endpoint. The API Mapper is then able
to receive new measurements asynchronously and format them to
the internal format of our platform. The data are then forwarded
to the proposed Processing/Analytics engine for processing and
analysis using the AMQP protocol. The data are then processed
and can be accessed from the proposed Data API. Messages inside
the MQTT broker can be transferred in multiple formats ranging
from plain text to any open or proprietary protocol. In our case
messages are transmitted in plain text following a simple format:
the topic of the message refers to the device and sensor that generated
the message while the actual payload represents the value
generated. All sensors forward their measurements periodically 
or on events (i.e., when motion is detected) and the API mapper
receives them and forwards the to the processing engine.
 
## Implementing your own Device Mapper

* Implement
  1. sendMeasurement of SenderService (Send queue)
  2. onMessage of ReceiverService (Commands queue)

* Sparkworks Message Template
  * Comma Separated Triplet
    1. Device URI
    2. Measurement value
    3. Timestamp
    
## Registering your own Device Mapper

1. Register to [Sparkworks Accounts](https://accounts.sparkworks.net)
2. Request for DEV account
3. Create your client
    * You will retrieve your secret client password (keep it safe)
    * Send and Commands queues will be automatically created for you
