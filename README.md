## Drones


### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (LIGHTWEIGHT, MIDDLEWEIGHT, CRUISERWEIGHT, HEAVYWEIGHT);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

#### REST API for communicating with drones. 

If service is running on http://localhost:8080/:

| Method     | Endpoint                              | Description                                       |
|------------|---------------------------------------|---------------------------------------------------|
| `[POST]`   | `/api/v1/drones`                      | register (add) a drone *                          |
| `[GET]`    | `/api/v1/drones/{id}`                 | find a drone by id                                |
| `[PUT]`    | `/api/v1/drones`                      | update a existing drone                           |
| `[DELETE]` | `/api/v1/drones/{id}`                 | unregister (remove) a drone                       |
| `[POST]`   | `/api/v1/drones/{id}/medication`      | load (add) medication items to drone *            |
| `[GET]`    | `/api/v1/drones/{id}`                 | check loaded medication items for a given drone * |
| `[GET]`    | `/api/v1/drones/available`            | check available drones for loading *              |
| `[GET]`    | `/api/v1/drones/{id}/battery`         | check drone battery level for a given drone *     |
|            |                                       |                                                   |
| `[POST]`   | `/api/v1/medication/{id}/uploadImage` | upload an image for medication *                  |
| `[GET]`    | `/api/v1/medication/{id}`             | find medication by id                             |

[]()* - mandatory. 

Also, available HAL-Explorer at http://localhost:8080/ and h2 console at http://localhost:8080/h2-console

Run tests:
```shell
mvn clean test 
```

Run app:
```shell
mvn spring-boot:run
```
