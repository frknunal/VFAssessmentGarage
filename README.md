# VFAssessmentGarage

This project contains a controller that has following endpoints.
```
1.
Request: POST /api/v1/garage/park
Body: {
    "plate": String,
    "colour": String,
    "vehicleType": Enum [Car, Truck, Jeep]
    }
Response: 200 Result String.

2.
Request: POST /api/v1/garage/leave/{vehicleNumber}
Response: 200 OK or 400 Vehicle is not exist!

3.
Request: GET /api/v1/garage/status
Response: 200 List<StatusResource>
[
    {
        "plate": "34-BO-1987",
        "colour": "White",
        "assignedSlotNumbers": [
            1
        ]
    },
    {
        "plate": "34-BO-1987",
        "colour": "White",
        "assignedSlotNumbers": [
            3
        ]
    },
    {
        "plate": "34-BO-1987",
        "colour": "White",
        "assignedSlotNumbers": [
            5
        ]
    }
]
```

# Technical Details
Factory Pattern is used to create vehicles.

There are 2 different lists to store slots and vehicles. One list stores all the slots and other list stores all the vehicles which are parked in the garage.
If user parks a car into garage, one extra slot will be marked as unavailable except last slot is assigned to car. If last slot is assigned to car, one extra slot will
not be assigned as unavailable.

There is a readWriteLock which uses to prevent concurrency exceptions. 

TDD is used to develop service and persistence layers. 

Slf4j is used for logging.



