# poc-test
Proof of Concept for different testing strategies: unit testing, mockmvc testing, integration testing and e2e testing.

- application (java module)
mockmvc tests

- cache module (java module)
integration test with redisson/redis (testcontainers)

- data module (java module)
integration test with jpa/postgres (testcontainers)

- domain module (java module)
unit tests

Other modules:

- migrate module (liquibase module)
There are no tests here
