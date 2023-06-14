# Candidate Test Solution

This repository contains the solution to the given candidate test. The test solution can be accessed through the following link: [Candidate Test Solution](https://www.figma.com/file/59NRWhHuc4DLvLvNKpOfcp/Candidate-test?type=design&node-id=2-149&t=sEN1yJlBTGvPN7mp-0)

## Technologies Used

The solution has been developed using the following technologies:

- Compose: Compose is a modern UI toolkit for building native Android apps. It enables developers to build beautiful and responsive user interfaces using a declarative approach.

- LiveData: LiveData is a data holder class in the Android Architecture Components. It is designed to observe changes in data and react accordingly, providing an easy way to update the UI.

- Retrofit: Retrofit is a type-safe HTTP client for Android and Java. It simplifies the process of making network requests and handling the response by providing a high-level API.

## Architecture

The architecture used in this solution is MVI (Model-View-Intent). MVI is a reactive architecture pattern that aims to separate the state management and side effects from the UI layer. It provides a clear separation of concerns and promotes testability and maintainability.

## Testing Approach

Instead of writing traditional UI tests that cover all elements on the page, a full end-to-end test was written to test the workflow of the application. This approach focuses on verifying the expected behavior of the application rather than testing individual UI components. Also, viewmodels are covered with tests.
