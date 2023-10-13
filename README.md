# Ktor-di

## Simple stupid Ktor project shows that it's not needed to import any dependency injection container/library to have DI.
Dependency container resides [here](adapters/src/main/kotlin/com/kamilf/config/dependency/DependencyContainer.kt) <br>
Core domain logic resides in `domain` module. In `adapters` module port's implementations are located.


- Kotlin.
- Ktor framework.
- Ports&Adapters (Hexagonal) architecture https://alistair.cockburn.us/hexagonal-architecture/.
- No Dependency Inversion library.
- Simple test setup.
- DDD.

## How to run
`./gradlew run`
