# MODULE 1 - REFLECTIONS
## Reflection 1
After working on the Edit and Delete features for the EShop application, I’ve had a chance to evaluate my code against the standards we’ve been learning. I tried to stick to Clean Code principles by keeping my methods small and focused on one specific task, like how the ProductRepository has distinct methods for findById, update, and delete. I also made sure my naming conventions were clear and descriptive so that anyone reading the code could immediately understand that productData is our in-memory storage. By using the Layered Architecture (separating the Controller, Service, and Repository), the logic stays organized and isn't all cluttered in one place.

In terms of Secure Coding, I focused on basic data integrity and flow control. For example, when implementing the edit feature, I used a hidden ID field in the HTML form to ensure the backend correctly maps the update to the right product. I also added a confirmation check on the delete button to prevent accidental data loss. One thing I think I could improve is adding more robust validation; right now, the system might accept empty strings or negative numbers for quantities. Moving forward, I want to look into using Spring Boot's validation annotations to make the application even more secure and reliable.

## Reflection 2  
After writing the unit tests for the Edit and Delete features, I’ve realized that while writing tests can feel a bit repetitive, it really gives me peace of mind knowing the core logic actually works without having to manually check it every time. I think the number of unit tests in a class should be enough to cover all possible execution paths, both the "happy path" where everything goes right and the edge cases where things might fail. To make sure my tests are enough, I look at code coverage, which shows me exactly which lines of my code were executed during the tests. However, having 100% code coverage doesn't automatically mean the code is bug-free; it just means every line was run. It doesn't guarantee that I've tested every possible logical combination or weird user input.

Regarding the functional tests, creating a new test suite for the product list quantity by copying the same setup procedures and instance variables from CreateProductFunctionalTest.java definitely creates a code duplication issue. While it's faster to just copy-paste, it reduces the overall code quality because any change to the setup logic (like a URL change) would have to be updated in multiple places. This is a "clean code" violation. To improve this, I could create a base test class that contains the common setup procedures and then have both functional test classes inherit from it. This way, the code remains DRY (Don't Repeat Yourself) and much easier to maintain in the long run.

## Reflection 3
After implementing the CI/CD pipelines using GitHub Actions, I can really see the value of automating the testing and deployment processes. Integrating SonarCloud was a great learning experience for maintaining clean and secure code. It immediately caught a security hotspot regarding dependency verification, which I fixed by generating a verification-metadata.xml file. I also spent time ensuring my unit tests covered all execution paths, finally achieving 100% code coverage with JaCoCo by adding tests for edge cases, like creating a product without an ID or searching for a non-existent product. Having these automated checks run on every push gives me a lot of confidence that new features won't break existing logic.

Setting up Continuous Deployment to AWS Elastic Beanstalk was a bit challenging but incredibly rewarding. I learned that cloud environments have their own specific configurations, like how the Elastic Beanstalk Nginx proxy routes traffic to port 5000 by default, causing a 502 Bad Gateway error when my Spring Boot app was listening on port 8080. Fixing that and successfully automating the deployment means I no longer have to manually build and upload .jar files. 

## Reflection 4
### 1) Explain what principles you apply to your project!
For this exercise, I basically applied three SOLID principles to clean up my code:

- **Single Responsibility Principle (SRP):** Initially, I had the `CarController` class just sitting at the bottom of my `ProductController.java` file. I thought it would be better to separate them into two completely different files. So now, `ProductController` only deals with products, and `CarController` strictly handles car-related routing. This way, each class basically only has one reason to change.
- **Liskov Substitution Principle (LSP):** My original code had `class CarController extends ProductController`, which I realized is a violation because a car controller really isn't a valid substitute for a product controller. I just removed the inheritance so `CarController` can stand on its own.
- **Dependency Inversion Principle (DIP):** In the `CarController`, the service was originally injected using the concrete class `CarServiceImpl`. I changed this to use the `CarService` interface instead. In my opinion, this is much better since the high-level module now depends on an abstraction rather than the low-level implementation details.

### 2) Explain the advantages of applying SOLID principles to your project with examples.
I think applying these principles makes the codebase way cleaner, more modular, and honestly much easier to maintain over time.

For example, by using **SRP**, my files are smaller and more focused. If I need to fix a bug with how cars are edited, I know exactly where to look (`CarController.java`) without having to scroll past a bunch of unrelated product code.

Also, with **DIP**, the code is basically much more flexible. Since `CarController` now relies on the `CarService` interface, we could completely swap out the backend logic for managing cars later on (like saving to a real database instead of a local list). As long as the new implementation follows the interface, we wouldn't have to change a single line of code in the controller, which is pretty cool.

### 3) Explain the disadvantages of not applying SOLID principles to your project with examples.
In my opinion, without SOLID principles, the code quickly becomes fragile, tightly coupled, and a total headache to update.

For instance, if I ignored **LSP** and kept `CarController extends ProductController`, the car controller would accidentally inherit all the product routes. This could cause really weird routing clashes and unexpected bugs when trying to navigate the app.

Also, if I didn't use **SRP**, the `ProductController` file would eventually turn into a massive "god class." If I kept adding more features later (like users, orders, etc.) into the exact same file, it would become practically impossible to read. Plus, if multiple people tried to work on different features at the same time for a group project, we would constantly run into merge conflicts.

